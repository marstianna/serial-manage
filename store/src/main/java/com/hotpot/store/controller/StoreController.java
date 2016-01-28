package com.hotpot.store.controller;

import com.google.common.collect.ImmutableMap;
import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;
import com.hotpot.service.AdminService;
import com.hotpot.service.Context;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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

    @RequestMapping("storeInfo")
    @ResponseBody
    public Object getStoreInfo(){
        Store store = storeService.getStoreByStoreId(Context.get().getId());
        Owner owner = adminService.getOwnerById(store.getOwnerId());
        return ImmutableMap.of("store",store,"owner",owner);
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
}
