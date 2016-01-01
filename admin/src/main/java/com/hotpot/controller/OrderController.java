package com.hotpot.controller;

import com.hotpot.commons.Const;
import com.hotpot.domain.Order;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zoupeng on 15/12/31.
 */
@Controller
@RequestMapping("order/")
public class OrderController {
    @Autowired
    OrderService orderService;

    @RequestMapping("getAllOrders")
    @ResponseBody
    public List<Order> getAllOrders(){
        return orderService.getOrdersBySearcher(new OrderSearcher());
    }

    @RequestMapping("getOrdersByConditions")
    @ResponseBody
    public List<Order> getOrdersByConditions(@ModelAttribute OrderSearcher orderSearcher){
        return orderService.getOrdersBySearcher(orderSearcher);
    }

    @RequestMapping("getUnsettleOrders")
    @ResponseBody
    public List<Order> getUnsettleOrders(){
        return orderService.getOrdersBySearcher(new OrderSearcher().setSettle(Const.ORDER_UNSETTLE));
    }
}
