package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zoupeng on 16/1/7.
 */
@Controller
public class IndexController extends BaseController{

    @RequestMapping({"","index"})
    public String index(HttpServletRequest request){
        return "login/login";
    }
}
