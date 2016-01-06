package com.hotpot.controller;

import com.hotpot.commons.Const;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.constenum.PayTypeEnum;
import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 15/12/31.
 */
@Controller
@RequestMapping("/order/")
public class OrderController{
    @Autowired
    OrderService orderService;
    @Autowired
    ValueCardService valueCardService;
    @Autowired
    StoreService storeService;

    @RequestMapping({"index","order"})
    public String order(HttpServletRequest request){
        request.setAttribute("payTypeList", PayTypeEnum.getMap());
        request.setAttribute("storeList",storeService.getStoreMap());
        return "order/order";
    }

    @RequestMapping("getAllOrders")
    @ResponseBody
    @Pagination
    public List<Order> getAllOrders(@ModelAttribute OrderSearcher searcher){
        return orderService.getOrdersBySearcher(searcher);
    }


    @RequestMapping("getUnsettleOrders")
    @ResponseBody
    @Pagination
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
