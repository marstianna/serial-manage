package com.hotpot.service.queueup;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Queues;
import com.google.common.collect.Table;
import com.hotpot.commons.Const;
import com.hotpot.entity.QueueUp;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zoupeng on 15/12/26.
 */
@Component
public class QueueUpCache {
    //table<storeId,number,queueup>;
    private Table<Integer,Integer,BlockingQueue<QueueUp>> lines =  HashBasedTable.create();
    private Map<Integer,BlockingQueue<QueueUp>> queueUps = new HashMap<>();

    public void queueUp(Integer storeId,QueueUp queueUp){
        Integer number = (queueUp.getWaitingNumber() > Const.DEFAULT_SMALL_TABLE) ? Const.DEFAULT_BIG_TABLE : Const.DEFAULT_SMALL_TABLE;
        if(queueUps.containsKey(storeId)){
            lines.get(storeId,number).add(queueUp);
        }else{
            BlockingQueue<QueueUp> tmp = Queues.newLinkedBlockingQueue();
            tmp.add(queueUp);
            lines.put(storeId,number, tmp);
        }
    }

    public Map<Integer,BlockingQueue<QueueUp>> getAllQueuesByStoreId(Integer id){
        return lines.row(id);
    }

    public QueueUp popup(Integer storeId,Integer number){
        return lines.get(storeId,number).poll();
    }
}
