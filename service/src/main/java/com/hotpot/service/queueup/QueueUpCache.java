package com.hotpot.service.queueup;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.collect.Table;
import com.hotpot.constenum.TableSizeEnum;
import com.hotpot.entity.QueueUp;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zoupeng on 15/12/26.
 */
@Component
public class QueueUpCache {
    //table<storeId,number,queueup>;
    private Table<Integer,Integer,Entity> lines =  HashBasedTable.create();
    private Map<Integer,BlockingQueue<QueueUp>> queueUps = new HashMap<>();

    public void queueUp(Integer storeId,QueueUp queueUp){
        TableSizeEnum targetTable = (queueUp.getWaitingNumber() > TableSizeEnum.SMALL.getCount()) ? TableSizeEnum.BIG : TableSizeEnum.SMALL;
        if(lines.contains(storeId,targetTable.getCount())){
            Entity tableLine = lines.get(storeId, targetTable.getCount());
            tableLine.push(queueUp,targetTable);
        }else{
            Entity tableLine = new Entity();
            tableLine.push(queueUp,targetTable);
            lines.put(storeId,targetTable.getCount(),tableLine);
        }
    }

    public Map<Integer,BlockingQueue<QueueUp>> getAllQueuesByStoreId(Integer id){
        Map<Integer, Entity> row = lines.row(id);
        return ImmutableMap.of(TableSizeEnum.SMALL.getCount(),row.get(TableSizeEnum.SMALL.getCount()).tmp,
                TableSizeEnum.BIG.getCount(),row.get(TableSizeEnum.BIG.getCount()).tmp);
    }

    public QueueUp popup(Integer storeId,Integer number){
        return lines.get(storeId,number).tmp.poll();
    }

    static class Entity{
        BlockingQueue<QueueUp> tmp = Queues.newLinkedBlockingQueue();
        AtomicInteger count = new AtomicInteger(0);

        public void push(QueueUp queueUp,TableSizeEnum targetTable){
            synchronized (tmp) {
                Integer size = tmp.size();
                queueUp.setFrontCount(size);
                if(size == 0){
                    count.set(1);
                }
                queueUp.setLineCode(targetTable.getCode() + count.incrementAndGet());
                tmp.add(queueUp);
            }
        }
    }
}
