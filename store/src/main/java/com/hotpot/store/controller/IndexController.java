package com.hotpot.store.controller;

import com.google.common.collect.ImmutableMap;
import com.hotpot.commons.framework.BaseController;
import com.hotpot.constenum.TableSizeEnum;
import com.hotpot.domain.Store;
import com.hotpot.entity.QueueUp;
import com.hotpot.service.Context;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.queueup.QueueUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zoupeng on 16/1/19.
 */
@RequestMapping("/index")
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

    @RequestMapping("/turnToInfo")
    public String turnToInfo(){
        return "index/info";
    }

    @RequestMapping("/turnToQueue")
    public String turnToQueue(){
        return "index/queue";
    }

    @RequestMapping("/queue")
    @ResponseBody
    public Object queue(Integer tableSize){
        if(tableSize == null){
            tableSize = TableSizeEnum.SMALL.getCount();
        }
        return queueUpService.getAllQueuesByStoreId(Context.get().getId(),tableSize);
    }

    @RequestMapping("/queueUp")
    @ResponseBody
    public Object queue(String phoneNubmer,Integer countOfPeople,String wechat){
        return queueUpService.queueUp(phoneNubmer, countOfPeople, wechat);
    }

    @RequestMapping("/nextOne")
    @ResponseBody
    public Object nextOne(String tableCode){
        QueueUp popup = queueUpService.popup(tableCode, Context.get().getId());
        String key = "success";
        if(popup == null){
            key = "false";
            popup = new QueueUp();
        }
        return ImmutableMap.of("success",key,"result",popup);
    }

    @RequestMapping("/tableList")
    @ResponseBody
    public Object tableList(){
        Integer storeId = getStoreId();
        return storeService.getRuntimeTablesByStoreId(storeId);
    }

    @RequestMapping("/checkOut")
    @ResponseBody
    public Object checkOut(Map<String,Object> params){
        Store store = Context.get();
        params.get("payType");
        params.get("drinkPrice");
        params.get("foodPrice");
//        orderService.pay(order);
        return null;
    }

    @RequestMapping("takeSeat")
    @ResponseBody
    public String takeSeat(String tableCode,Integer count){
//        storeService.
        return null;
    }

    @RequestMapping("/checkOrder")
    @ResponseBody
    public Object checkOrder(Integer tableId){
        return null;
    }

    private Integer getStoreId(){
        return Integer.parseInt(String.valueOf(getRequest().getSession().getAttribute("storeId")));
    }

}
