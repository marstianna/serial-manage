package com.hotpot.controller;

import com.hotpot.domain.Staff;
import com.hotpot.domain.Store;
import com.hotpot.service.AdminService;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zoupeng on 16/1/4.
 */
@Controller
@RequestMapping("/store/")
public class StoreController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private AdminService adminService;

    //admin不需要这个方法

//    @RequestMapping("addStaff")
//    @ResponseBody
//    public String addStaff(@ModelAttribute Staff staff){
//        String result = "添加成功";
//
//        try {
//            storeService.addStaff(staff);
//        } catch (Exception e) {
//            //TODO:LOG
//            result = "添加失败,请重试";
//        }
//
//        return result;
//    }

    @RequestMapping("getStaffByStoreId")
    @ResponseBody
    public List<Staff> getStaffByStoreId(Integer storeId){
        return storeService.getStaffByStoreId(storeId);
    }

    @RequestMapping("addStore")
    @ResponseBody
    public String addStore(@ModelAttribute Store store){
        String result = "添加门店成功";

        try {
            adminService.newStore(store);
        } catch (Exception e) {
            //TODO:LOG
            result = "添加门店失败,请重试";
        }

        return result;
    }

    @RequestMapping("getAllStores")
    @ResponseBody
    public List<Store> getAllStores(){
        return storeService.getAllStores();
    }
}
