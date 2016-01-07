package com.hotpot.controller;

import com.hotpot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by zoupeng on 16/1/7.
 */
@Controller
@RequestMapping("/manage/")
public class ManageController {
    @Autowired
    AdminService adminService;

    @RequestMapping("turnToAdd")
    public String turnToAdd(HttpServletRequest request){
        return "manage/store.add";
    }

    @RequestMapping("addStore")
    @ResponseBody
    public String addStore(Map<String,Object> params){
        adminService.addStoreAndOwner(params);
        return "添加店铺成功";
    }
}
