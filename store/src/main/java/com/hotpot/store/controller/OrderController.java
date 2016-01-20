package com.hotpot.store.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.Page;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.constenum.PayTypeEnum;
import com.hotpot.domain.Order;
import com.hotpot.domain.Store;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.Context;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.store.view.OrderView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zoupeng on 16/1/20.
 */
@RequestMapping("/order/")
@Controller
public class OrderController extends BaseController{
    @Autowired
    OrderService orderService;
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
    public Object getAllOrders(@ModelAttribute OrderSearcher searcher){
        Store store = Context.get();
        List<Order> ordersBySearcher = orderService.getOrdersBySearcher(searcher.setStoreId(store.getId()));
        List<OrderView> views = ordersBySearcher.stream().collect(Collectors.mapping(OrderView::transform, Collectors.toList()));
        return getResultPage((Page<Order>)ordersBySearcher,views);
    }

}
