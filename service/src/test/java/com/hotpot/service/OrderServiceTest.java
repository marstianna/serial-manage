package com.hotpot.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.hotpot.commons.DateTool;
import com.hotpot.domain.Order;
import com.hotpot.searcher.OrderSearcher;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

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
        List<Order> ordersByStoreId = orderService.getOrdersByStoreId(1);
        System.out.println(JSON.toJSONString(ordersByStoreId));
    }

    @Test
    public void testGetUnsettleOrderByOrderId() throws Exception {
        List<Order> unsettles = orderService.getUnsettleOrderByStoreId(1);
        System.out.println(JSON.toJSONString(unsettles));
    }

    @Test
    public void testSettleOrders() throws Exception {
        Object result = orderService.settleOrders(Lists.newArrayList(1,2));
        System.out.println(JSON.toJSONString(result));
    }

    @Test
    public void testPay() throws Exception {
        Order order = new Order();
        order.setCreateTime(DateTool.unixTime());
        order.setPayType(0);
        order.setPaperPrice(1500);
        order.setStoreId(1);
        order.setDrinkPrice(50);
        order.setFoodPrice(100);
        order.setVipId(1);
        order.setCardId("No.000001");
        order.setStoreId(1);
        try {
            orderService.payByCard(order, "No.000001", "887a85f2-45e4-3cbe-a2ac-5d1f1dfff588");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testGetOrdersBySearcher() throws Exception {
        List<Order> orders = orderService.getOrdersBySearcher(new OrderSearcher().setEndTime(1451657874l)
                                                                                .setStartTime(1451657224l)
                                                                                .setStoreId(1));
        System.out.println(orders.size());
    }

}