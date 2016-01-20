package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.domain.Staff;
import com.hotpot.domain.Store;
import com.hotpot.service.StoreService;
import com.hotpot.service.AdminService;
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
public class StoreController extends BaseController {
    @Autowired
    private StoreService storeService;
    @Autowired
    private AdminService adminService;

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
    @Pagination
    @ResponseBody
    public List<Store> getAllStores(){
        return storeService.getAllStores();
    }


}
