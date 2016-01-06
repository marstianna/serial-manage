package com.hotpot.controller;

import com.hotpot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

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
}
