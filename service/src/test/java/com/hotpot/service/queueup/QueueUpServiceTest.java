package com.hotpot.service.queueup;

import com.alibaba.fastjson.JSON;
import com.hotpot.domain.Store;
import com.hotpot.service.Context;
import com.hotpot.service.StoreService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-all.xml")
public class QueueUpServiceTest {

    @Autowired
    QueueUpService queueUpService;
    @Autowired
    StoreService storeService;

    @Test
    public void testQueueUp() throws Exception {
        queueUpService.queueUp("1377777777",5,null);
        queueUpService.queueUp("1377777778",5,null);
        queueUpService.queueUp("1377777779",10,null);
        queueUpService.queueUp("1377777770",2,null);
        queueUpService.queueUp("1377777771",4,null);
        queueUpService.queueUp("1377777772",13,null);
    }

    @Test
    public void testGetAllQueuesByStoreId() throws Exception {

    }

    @Test
    public void testPopup() throws Exception {
        Store store = storeService.getStoreByStoreId(1);
        Context.set(store);
        queueUpService.queueUp("1377777777",5,null);
        queueUpService.queueUp("1377777778",5,null);
        queueUpService.queueUp("1377777779",10,null);
        queueUpService.queueUp("1377777770",2,null);
        queueUpService.queueUp("1377777771",4,null);
        queueUpService.queueUp("1377777772",13,null);
        System.out.println(JSON.toJSONString(queueUpService.popup("B01",1)));
        System.out.println(JSON.toJSONString(queueUpService.popup("B01",1)));
        System.out.println(JSON.toJSONString(queueUpService.popup("A01",1)));
        System.out.println(JSON.toJSONString(queueUpService.popup("B01",1)));
        System.out.println(JSON.toJSONString(queueUpService.popup("B01",1)));
        queueUpService.queueUp("1377777773",13,null);
        System.out.println(JSON.toJSONString(queueUpService.popup("B01",1)));
    }
}