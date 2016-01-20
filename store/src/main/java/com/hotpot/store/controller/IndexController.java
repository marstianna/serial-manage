package com.hotpot.store.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.domain.Order;
import com.hotpot.domain.Store;
import com.hotpot.service.Context;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.queueup.QueueUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zoupeng on 16/1/19.
 */
@RequestMapping("/index/")
@Controller
public class IndexController extends BaseController{
    @Autowired
    StoreService storeService;
    @Autowired
    ValueCardService valueCardService;
    @Autowired
    QueueUpService queueUpService;
    @Autowired
    OrderService orderService;

    @RequestMapping("queueUp")
    @ResponseBody
    public Object queue(String phoneNubmer,Integer countOfPeople,String wechat){
        Integer storeId = getStoreId();
        return queueUpService.queueUp(phoneNubmer, countOfPeople, wechat);
    }

    @RequestMapping("tableList")
    @ResponseBody
    public Object tableList(){
        Integer storeId = getStoreId();
        return storeService.getAllTablesByStoreId(storeId);
    }

    @RequestMapping("checkOut")
    @ResponseBody
    public Object checkOut(Integer tableId,@ModelAttribute Order order){
        Store store = Context.get();
        orderService.pay(order);
        return null;
    }

    @RequestMapping("checkOrder")
    @ResponseBody
    public Object checkOrder(Integer tableId){
        return null;
    }

    private Integer getStoreId(){
        return Integer.parseInt(String.valueOf(getRequest().getAttribute("storeId")));
    }

}
