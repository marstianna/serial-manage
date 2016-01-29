package com.hotpot.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zoupeng on 15/12/25.
 */
@Controller
@RequestMapping("/test/")
public class TestController {
    @RequestMapping("")
    @ResponseBody
    public String test(){
        return "okay;";
    }

    @RequestMapping("order")
    public String order(){
        return "order/order";
    }

    @RequestMapping("getCardInfo")
    @ResponseBody
    public Object getCardInfo(HttpServletResponse response){
//        response.setHeader("Access-Control-Allow-Origin","*");
        return ImmutableMap.of("cardId","123123","cardUuid","testest");
    }
}

