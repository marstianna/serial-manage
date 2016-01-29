package com.hotpot.store.controller;

import com.google.common.collect.ImmutableMap;
import com.hotpot.commons.DateTool;
import com.hotpot.commons.framework.BaseController;
import com.hotpot.constenum.PayTypeEnum;
import com.hotpot.constenum.TableSizeEnum;
import com.hotpot.domain.Order;
import com.hotpot.domain.RuntimeTable;
import com.hotpot.domain.Store;
import com.hotpot.domain.ValueCard;
import com.hotpot.entity.QueueUp;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.Context;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.queueup.QueueUpService;
import com.hotpot.store.vo.CheckOutVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
    public Object checkOut(@ModelAttribute CheckOutVo checkOutVo){
        Store store = Context.get();
        Order order = checkOutVo.getOrder();
        RuntimeTable runtimeTable = storeService.getRuntimeTable(Context.get().getId(),checkOutVo.getTableCode());
        ValueCard card = null;

        if(checkOutVo.getPayType().intValue() == PayTypeEnum.VALUE_CARD.getKey()){

            order.setQueueUp(runtimeTable.getIsQueueUp());
            order.setStoreId(store.getId());
            order.setCountOfPeople(runtimeTable.getPeopleCount());

            if(!StringUtils.isBlank(checkOutVo.getPhone()) && !StringUtils.isBlank(checkOutVo.getPassword())){
                card = orderService.payByPhone(order, checkOutVo.getPhone(), checkOutVo.getPassword());
            }else{
                card = orderService.payByCard(order,checkOutVo.getCardId(),checkOutVo.getCardUuid());
            }
        }else{
            orderService.pay(order);
        }

//        orderService.pay(order);
        if(card != null) {
            return ImmutableMap.of("order", order, "card",card);
        }else{
            return ImmutableMap.of("order",order,"card","null");
        }
    }

    @RequestMapping("/createOrderAndCheckOut")
    @ResponseBody
    public Object createOrderAndCheckOut(){
        return null;
    }

    @RequestMapping("takeSeat")
    @ResponseBody
    public Object takeSeat(String tableCode,Integer count,Integer isQueueUp){
        Store store = Context.get();
        storeService.takeSeat(tableCode,store.getId(),count,isQueueUp);
        return ImmutableMap.of("success","success");
    }

    @RequestMapping("/checkOrder")
    @ResponseBody
    public Object checkOrder(String tableCode){
        RuntimeTable runtimeTable = storeService.getRuntimeTable(Context.get().getId(),tableCode);
        if(runtimeTable.getOrderId() == null && runtimeTable.getOrderId() == 0){
            return ImmutableMap.of("success","fail");
        }
        Order order = orderService.getOrdersBySearcher(new OrderSearcher().setId(runtimeTable.getOrderId())).get(0);
        return ImmutableMap.of("success","success","result",order);
    }

    @RequestMapping("/createOrder")
    @ResponseBody
    public Object createOrder(String tableCode,Integer foodPrice,Integer drinkPrice){
        Order order = new Order();
        order.setFoodPrice(foodPrice);
        order.setDrinkPrice(drinkPrice);
        order.setPaperPrice(foodPrice+drinkPrice);
        order.setCreateTime(DateTool.getDateTime());
        order.setStoreId(Context.get().getId());
        order.setTableCode(tableCode);
        orderService.createOrder(order);
        return ImmutableMap.of("success","success");
    }

    private Integer getStoreId(){
        return Integer.parseInt(String.valueOf(getRequest().getSession().getAttribute("storeId")));
    }

}
