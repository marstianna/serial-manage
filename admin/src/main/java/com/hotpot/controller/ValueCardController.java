package com.hotpot.controller;

import com.hotpot.service.ValueCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zoupeng on 16/1/4.
 */
@Controller
@RequestMapping("/valuecard/")
public class ValueCardController {
    @Autowired
    ValueCardService valueCardService;

    @RequestMapping("topUp")
    @ResponseBody
    public String topUp(String cardId,String cardUuid,Integer account,Integer price){
        return null;
    }
}