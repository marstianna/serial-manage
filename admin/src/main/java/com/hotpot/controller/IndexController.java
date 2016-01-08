package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.service.AdminService;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zoupeng on 16/1/7.
 */
@Controller
public class IndexController extends BaseController{
    @Autowired
    StoreService storeService;
    @Autowired
    AdminService adminService;

    @RequestMapping("loginadmin")
    public String login(String loginName,String password){
        boolean login = adminService.login(loginName, password);
        return login ? "order/order" : "login";
    }

    @RequestMapping({"","index"})
    public String index(HttpServletRequest request){
        return "login/turnToLogin";
    }
}
