package com.hotpot.service.queueup.impl;

import com.hotpot.commons.DateTool;
import com.hotpot.dao.StoreTableMapper;
import com.hotpot.domain.Store;
import com.hotpot.domain.StoreTable;
import com.hotpot.entity.QueueUp;
import com.hotpot.service.Context;
import com.hotpot.service.queueup.QueueUpCache;
import com.hotpot.service.queueup.QueueUpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.BlockingQueue;

/**
 * Created by zoupeng on 15/12/26.
 */
@Service
public class QueueUpServiceImpl implements QueueUpService {
    @Autowired
    StoreTableMapper storeTableMapper;
    @Autowired
    QueueUpCache queueUpCache;

    @Override
    public QueueUp queueUp(String phone, Integer waitingNumber, String weChat) {
        Store store = Context.get();
        if(null == store){
            throw new RuntimeException("没有获取到本应拿到的店铺信息");
        }
        QueueUp queueUp = new QueueUp();

        queueUp.setStorePhone(store.getPhone());
        queueUp.setDatetime(DateTool.getDateTime());
        queueUp.setStoreName(store.getStoreName());
        queueUp.setPhone(phone);
        queueUp.setWaitingNumber(waitingNumber);

        if(!StringUtils.isBlank(weChat)) {
            queueUp.setWeChat(weChat);
        }
        queueUpCache.queueUp(store.getId(),queueUp);
        return queueUp;
    }

    @Override
    public Map<Integer,BlockingQueue<QueueUp>> getAllQueuesByStoreId(Integer storeId){
        return queueUpCache.getAllQueuesByStoreId(storeId);
    }

    @Override
    public QueueUp popup(Integer tableSize , Integer storeId){
        return queueUpCache.popup(storeId,tableSize);
    }

    @Override
    public QueueUp popup(String tableCode, Integer storeId){
        StoreTable table = storeTableMapper.getStoreTable(tableCode,storeId);
        return queueUpCache.popup(storeId,table.getDefaultNumber());
    }
}
