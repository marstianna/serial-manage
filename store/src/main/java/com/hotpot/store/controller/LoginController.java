package com.hotpot.store.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.domain.Store;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Created by zoupeng on 16/1/8.
 */
@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
    @Autowired
    StoreService storeService;

    @RequestMapping("/turnToLogin")
    public String turnToLogin(){
        return "login/login";
    }

    @RequestMapping({"/login",""})
    public String login(String loginName,String password){
        boolean login = storeService.login(loginName, password);
        String response;
        if(login){
            getRequest().getSession().setAttribute("loginInfo","success");
            List<Store> store = storeService.getStoreByLoginInfo(loginName, password);
            getRequest().getSession().setAttribute("storeId",store.get(0).getId());
//            response = "index/info";
            response = "index/info";
        }else{
            response = "login/login";
        }
        return response;
    }

    @RequestMapping("/logout")
    public String logout(){
        getRequest().getSession().removeAttribute("loginInfo");
        getRequest().getSession().removeAttribute("storeId");
        return "login/login";
    }
}
