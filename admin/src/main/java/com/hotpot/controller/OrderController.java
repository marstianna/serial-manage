package com.hotpot.controller;

import com.hotpot.commons.Const;
import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.OrderService;
import com.hotpot.service.ValueCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 15/12/31.
 */
@Controller
@RequestMapping("/order/")
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    ValueCardService valueCardService;

    @RequestMapping("order")
    public String order(){
        return "order/order";
    }
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

    @RequestMapping("createOrderByValueCard")
    @ResponseBody
    public ValueCard createOrderByValueCard(Map<String,Object> params){
        Integer price = Integer.parseInt(String.valueOf(params.get("price")));
        Integer foodPrice = Integer.parseInt(String.valueOf(params.get("foodPrice")));
        Integer drinkPrice = Integer.parseInt(String.valueOf(params.get("drinkPrice")));

        String phone = String.valueOf(params.get("mobilephone"));
        String password = String.valueOf(params.get("password"));

        String cardId = String.valueOf(params.get("cardId"));
        String cardUuid = String.valueOf(params.get("cardUuid"));

//        valueCardService.p
        return null;
    }
}
