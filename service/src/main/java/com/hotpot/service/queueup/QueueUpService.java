package com.hotpot.service.queueup;

import com.hotpot.entity.QueueUp;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface QueueUpService {

    void queueUp(Integer storeId, String phone, Integer waitingNumber);

    QueueUp popup(String tableNumber, Integer storeId);
}
