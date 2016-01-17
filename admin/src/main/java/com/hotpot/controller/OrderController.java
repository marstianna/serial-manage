package com.hotpot.controller;

import com.hotpot.commons.Const;
import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.constenum.PayTypeEnum;
import com.hotpot.domain.Order;
import com.hotpot.domain.VipInfo;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.VipInfoService;
import com.hotpot.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoupeng on 15/12/31.
 */
@Controller
@RequestMapping("/order/")
public class OrderController extends BaseController {
    @Autowired
    OrderService orderService;
    @Autowired
    ValueCardService valueCardService;
    @Autowired
    StoreService storeService;
    @Autowired
    VipInfoService vipInfoService;

    @RequestMapping({"index","order"})
    public String order(HttpServletRequest request){
        request.setAttribute("payTypeList", PayTypeEnum.getMap());
        request.setAttribute("storeList",storeService.getStoreMap());
        return "order/order";
    }

    @RequestMapping("getAllOrders")
    @ResponseBody
    @Pagination
    public Object getAllOrders(@ModelAttribute OrderSearcher searcher){
        List<Order> ordersBySearcher = orderService.getOrdersBySearcher(searcher);
        List<OrderView> results = new ArrayList<>();
        for(Order order : ordersBySearcher){
            OrderView orderView = new OrderView();
            orderView.setOrder(order);
            orderView.setStoreName(storeService.getStoreByStoreId(order.getStoreId()).getStoreName());
            VipInfo vipInfo = vipInfoService.getVipInfoById(order.getVipId());
            orderView.setVipName(vipInfo.getName()+"("+vipInfo.getId()+")");
            results.add(orderView);
        }
        return results;
    }


    @RequestMapping("getUnsettleOrders")
    @ResponseBody
    @Pagination
    public List<Order> getUnsettleOrders(){
        return orderService.getOrdersBySearcher(new OrderSearcher().setSettle(Const.ORDER_UNSETTLE));
    }

}
