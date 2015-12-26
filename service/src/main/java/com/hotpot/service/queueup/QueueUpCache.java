package com.hotpot.service.queueup;

import com.google.common.collect.Queues;
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
    private Map<Integer,BlockingQueue<QueueUp>> queueUps = new HashMap<>();

    public void queueUp(Integer storeId,QueueUp queueUp){
        if(queueUps.containsKey(storeId)){
            queueUps.get(storeId).add(queueUp);
        }else{
            BlockingQueue<QueueUp> tmp = Queues.newLinkedBlockingQueue();
            tmp.add(queueUp);
            queueUps.put(storeId, tmp);
        }
    }

    public QueueUp popup(Integer storeId){
        return queueUps.get(storeId).poll();
    }
}
