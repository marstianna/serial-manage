package com.hotpot.controller;

import com.hotpot.commons.Const;
import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.Page;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.constenum.PayTypeEnum;
import com.hotpot.domain.Order;
import com.hotpot.domain.VipInfo;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.VipInfoService;
import com.hotpot.view.OrderView;
import com.hotpot.searcher.OrderSearcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

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
        List<OrderView> views = ordersBySearcher.stream().collect(Collectors.mapping(OrderView::transform,Collectors.toList()));
        return getResultPage((Page<Order>)ordersBySearcher,views);
    }


    @RequestMapping("getUnsettleOrders")
    @ResponseBody
    @Pagination
    public List<Order> getUnsettleOrders(){
        return orderService.getOrdersBySearcher(new OrderSearcher().setSettle(Const.ORDER_UNSETTLE));
    }

    private OrderView createOrderView(Order order){
        OrderView orderView = new OrderView();
        orderView.setOrder(order);
        orderView.setStoreName(storeService.getStoreByStoreId(order.getStoreId()).getStoreName());
        VipInfo vipInfo = vipInfoService.getVipInfoById(order.getVipId());
        orderView.setVipName(vipInfo.getName()+"("+vipInfo.getId()+")");
        return orderView;
    }

}
