package com.hotpot.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by zoupeng on 16/2/3.
 */
@Controller
public class CardReaderControllerForTest {

    @RequestMapping("/test/readCard")
    @ResponseBody
    public Object readCard(HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        return ImmutableMap.of("code",0,"data",ImmutableMap.of("cardId","123456789","cardUuid","uuid1231231231231"));
    }

    @RequestMapping("/test/writeCard")
    @ResponseBody
    public Object writeCard(String cardId,String cardUuid,HttpServletResponse response){
        response.setHeader("Access-Control-Allow-Origin","*");
        return ImmutableMap.of("code",0,"data","success");
    }
}
