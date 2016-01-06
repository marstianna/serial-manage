package com.hotpot.controller;

import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.queueup.QueueUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by zoupeng on 16/1/6.
 */
@Controller
@RequestMapping("/index/")
public class IndexController {
    @Autowired
    OrderService orderService;
    @Autowired
    ValueCardService valueCardService;
    @Autowired
    StoreService storeService;
    @Autowired
    QueueUpService queueUpService;

    @RequestMapping("info")
    public ModelAndView info(Integer storeId){
        ModelAndView mav = new ModelAndView("index/info");
        mav.addObject("tableList",storeService.getAllTablesByStoreId(storeId));
        mav.addObject("queueList",queueUpService.getAllQueuesByStoreId(storeId));
        return mav;
    }
}
