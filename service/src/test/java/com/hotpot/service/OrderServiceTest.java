package com.hotpot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoupeng on 16/1/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-all.xml")
public class OrderServiceTest {
    @Autowired
    OrderService orderService;

    @Test
    public void testGetOrdersByStoreId() throws Exception {
        orderService.getOrdersByStoreId(1);
    }

    @Test
    public void testGetUnsettleOrderByOrderId() throws Exception {

    }

    @Test
    public void testSettleOrders() throws Exception {

    }

    @Test
    public void testPay() throws Exception {

    }

    @Test
    public void testGetOrdersBySearcher() throws Exception {

    }
}