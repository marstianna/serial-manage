package com.hotpot.service.queueup;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Queues;
import com.google.common.collect.Table;
import com.hotpot.commons.DateTool;
import com.hotpot.constenum.TableSizeEnum;
import com.hotpot.entity.QueueUp;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zoupeng on 15/12/26.
 */
@Component
public class QueueUpCache {
    //table<storeId,number,queueup>;
    private Table<Integer,Integer,Entity> lines =  HashBasedTable.create();
    private static final long DAY_TIME = 60 * 60 * 11;
    private static long currentTimeLine = 0;

    public void queueUp(Integer storeId,QueueUp queueUp){
        if(currentTimeLine == 0){
            currentTimeLine = DateTool.unixTime();
        }
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

    public BlockingQueue<QueueUp> getAllQueuesByStoreIdAndTableSize(Integer storeId,Integer tableSize){

        Entity entity = lines.get(storeId, tableSize);
        return entity == null ? new LinkedBlockingQueue<>() : entity.tmp;
    }

    public Map<Integer,BlockingQueue<QueueUp>> getAllQueuesByStoreId(Integer id){
        Map<Integer, Entity> row = lines.row(id);
        return ImmutableMap.of(TableSizeEnum.SMALL.getCount(),row.get(TableSizeEnum.SMALL.getCount()).tmp,
                TableSizeEnum.BIG.getCount(),row.get(TableSizeEnum.BIG.getCount()).tmp);
    }

    public QueueUp popup(Integer storeId,Integer number){
        Entity entity = lines.get(storeId, number);
        if(entity == null || entity.tmp == null || entity.tmp.isEmpty()){
            return null;
        }
        return entity.tmp.poll();
    }

    static class Entity{
        BlockingQueue<QueueUp> tmp = Queues.newLinkedBlockingQueue();
        AtomicInteger count = new AtomicInteger(0);

        public void push(QueueUp queueUp,TableSizeEnum targetTable){
            synchronized (tmp) {
                Integer size = tmp.size();
                queueUp.setFrontCount(size);
                if((DateTool.unixTime() - currentTimeLine ) > DAY_TIME){
                    count.set(1);
                }
                queueUp.setLineCode(targetTable.getCode() + count.incrementAndGet());
                tmp.add(queueUp);
            }
        }
    }
}
