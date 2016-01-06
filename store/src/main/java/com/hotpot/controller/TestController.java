package com.hotpot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
