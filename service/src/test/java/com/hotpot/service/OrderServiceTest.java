package com.hotpot.service;

import com.alibaba.fastjson.JSON;
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
    public void testSettleOrders() throws Exception {
    }

    @Test
    public void testPay() throws Exception {
        Order order = new Order();
        order.setCreateTime(DateTool.getDateTime());
        order.setPayType(0);
        order.setPaperPrice(1500);
        order.setStoreId(1);
        order.setDrinkPrice(50);
        order.setFoodPrice(100);
//        order.setVipId(1);
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
        List<Order> orders = orderService.getOrdersBySearcher(new OrderSearcher().setEndTime(DateTool.getDateTime()));
        System.out.println(orders.size());
    }

}