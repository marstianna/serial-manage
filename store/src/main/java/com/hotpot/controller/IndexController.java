package com.hotpot.controller;

import com.hotpot.commons.DateTool;
import com.hotpot.commons.framework.BaseController;
import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;
import com.hotpot.entity.QueueUp;
import com.hotpot.service.OrderService;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.queueup.QueueUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * Created by zoupeng on 16/1/6.
 */
@Controller
@RequestMapping("/index/")
public class IndexController extends BaseController {
    @Autowired
    OrderService orderService;
    @Autowired
    ValueCardService valueCardService;
    @Autowired
    StoreService storeService;
    @Autowired
    QueueUpService queueUpService;

    @RequestMapping("login")
    @ResponseBody
    public boolean login(String loginName,String loginPassword){
        return storeService.login(loginName, loginPassword);
    }

    @RequestMapping("info")
    public ModelAndView info(Integer storeId){
        ModelAndView mav = new ModelAndView("index/info");
        mav.addObject("tableList",storeService.getAllTablesByStoreId(storeId));
        mav.addObject("queueList",queueUpService.getAllQueuesByStoreId(storeId));
        return mav;
    }

    private static final int PAY_BY_PASSWORD = 0;
    private static final int PAY_BY_CARD = 1;

    @RequestMapping("pay")
    @ResponseBody
    public ValueCard pay(Map<String,Object> params){
        Integer paperPrice = Integer.parseInt(String.valueOf(params.get("paperPrice")));
        Integer foodPrice = Integer.parseInt(String.valueOf(params.get("foodPrice")));
        Integer drinkPrice = Integer.parseInt(String.valueOf(params.get("drinkPrice")));
        Integer payByPassword = Integer.parseInt(String.valueOf(params.get("payByPassword")));

        Order order = new Order();
        order.setPaperPrice(paperPrice);
        order.setCreateTime(DateTool.unixTime());
        order.setFoodPrice(foodPrice);
        order.setDrinkPrice(drinkPrice);

        ValueCard card = null;
        if(payByPassword == PAY_BY_PASSWORD) {
            String phone = String.valueOf(params.get("mobilephone"));
            String password = String.valueOf(params.get("password"));
            card = orderService.payByPhone(order,phone,password);
        }else {
            String cardId = String.valueOf(params.get("cardId"));
            String cardUuid = String.valueOf(params.get("cardUuid"));
            card = orderService.payByCard(order,cardId,cardUuid);
        }
        return card;
    }

    @RequestMapping("nextOne")
    @ResponseBody
    public QueueUp nextOne(String tableCode){
        return queueUpService.popup(tableCode,getStoreId());
    }

    @RequestMapping("queueups")
    @ResponseBody
    public Object queueups(){
        return queueUpService.getAllQueuesByStoreId(getStoreId());
    }

    private Integer getStoreId(){
        return 1;
    }
}
