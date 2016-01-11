package com.hotpot.controller;

import com.hotpot.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zoupeng on 16/1/11.
 */
@Controller
@RequestMapping("/vip/")
public class VipController {
    @Autowired
    private VipInfoService vipInfoService;

    @RequestMapping({"","index"})
    public String index(){
        return "vip/vip.list";
    }

    @RequestMapping("list")
    @ResponseBody
    public Object getVipList(){
        return vipInfoService.getAllVips();
    }

}
