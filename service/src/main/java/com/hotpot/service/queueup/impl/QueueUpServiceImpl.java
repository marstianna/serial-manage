package com.hotpot.service.queueup.impl;

import com.hotpot.dao.StoreTableMapper;
import com.hotpot.domain.StoreTable;
import com.hotpot.entity.QueueUp;
import com.hotpot.service.queueup.QueueUpCache;
import com.hotpot.service.queueup.QueueUpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zoupeng on 15/12/26.
 */
@Service
public class QueueUpServiceImpl implements QueueUpService{
    @Autowired
    StoreTableMapper storeTableMapper;
    @Autowired
    QueueUpCache queueUpCache;

    @Override
    public void queueUp(Integer storeId, String phone, Integer waitingNumber, String weChat) {
        QueueUp queueUp = new QueueUp();
        queueUp.setPhone(phone);
        queueUp.setWaitingNumber(waitingNumber);
        if(!StringUtils.isBlank(weChat)) {
            queueUp.setWeChat(weChat);
        }
        queueUpCache.queueUp(storeId,queueUp);
    }

    @Override
    public QueueUp popup(String tableCode, Integer storeId){
        StoreTable table = storeTableMapper.getStoreTable(tableCode,storeId);
        return queueUpCache.popup(storeId,table.getDefaultNumber());
    }
}
