package com.hotpot.store.controller;

import com.google.common.collect.ImmutableMap;
import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;
import com.hotpot.service.AdminService;
import com.hotpot.service.Context;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zoupeng on 16/1/28.
 */
@RequestMapping("/store/")
@Controller
public class StoreController {
    @Autowired
    StoreService storeService;
    @Autowired
    AdminService adminService;

    @RequestMapping("turnToStoreInfo")
    public String turnToStoreInfo(Model model){
        Store store = storeService.getStoreByStoreId(Context.get().getId());
        Owner owner = adminService.getOwnerById(store.getOwnerId());

        model.addAttribute("store",store);
        model.addAttribute("owner",owner);

        return "store/store.info";

    }

    @RequestMapping("turnToStoreTable")
    public String turnToStoreTable(Model model){
        return "store/store.table";
    }

    @RequestMapping("createNewTables")
    @ResponseBody
    public Object createNewTables(Integer from ,Integer to ,String code,Integer countOfPeople){
        Integer countOfSuccss = storeService.createTables(from, to, code, countOfPeople);
        if(countOfSuccss > 0){
            return ImmutableMap.of("success","success","result",countOfSuccss);
        }
        return ImmutableMap.of("success","false");
    }

    @RequestMapping("changePassword")
    @ResponseBody
    public Object changePassword(String oldPassword,String newPassword){
        Integer count = storeService.changePassword(Context.get().getOwnerId(),oldPassword,newPassword);
        String success;
        if(count != 0){
            success = "success";
        }else{
            success = "false";
        }
        return ImmutableMap.of("success",success);
    }
}
