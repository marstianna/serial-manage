package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.constenum.PayTypeEnum;
import com.hotpot.service.AdminService;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @RequestMapping("login")
    @ResponseBody
    public boolean login(String loginName,String password){
        return adminService.login(loginName,password);
    }

    @RequestMapping({"","index"})
    public String index(HttpServletRequest request){
        request.setAttribute("payTypeList", PayTypeEnum.getMap());
        request.setAttribute("storeList",storeService.getStoreMap());
        return "order/order";
    }
}
