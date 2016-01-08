package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by zoupeng on 16/1/8.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController{
    @Autowired
    AdminService adminService;

    @RequestMapping("/turnToLogin")
    public String turnToLogin(){
        return "login/login";
    }

    @RequestMapping({"/login",""})
    public String login(String loginName,String password){
        boolean login = adminService.login(loginName, password);
        String response;
        if(login){
            getRequest().getSession().setAttribute("loginInfo","success");
            response = "order/order";
        }else{
            response = "login/login";
        }
        return response;
    }

    @RequestMapping("/logout")
    public String logout(){
        getRequest().getSession().removeAttribute("loginInfo");
        return "login/login";
    }
}
