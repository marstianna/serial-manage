package com.hotpot.service.queueup;

import com.hotpot.entity.QueueUp;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface QueueUpService {

    void queueUp(Integer storeId, String phone, Integer waitingNumber, String weChat);

    Map<Integer,BlockingQueue<QueueUp>> getAllQueuesByStoreId(Integer storeId);

    QueueUp popup(String tableNumber, Integer storeId);
}
