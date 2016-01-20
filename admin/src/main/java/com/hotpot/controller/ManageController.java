package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.Page;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.domain.Store;
import com.hotpot.service.StoreService;
import com.hotpot.view.StoreView;
import com.hotpot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by zoupeng on 16/1/7.
 */
@Controller
@RequestMapping("/manage/")
public class ManageController extends BaseController {
    @Autowired
    AdminService adminService;
    @Autowired
    StoreService storeService;

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

    @RequestMapping("index")
    public String index(){
        return "manage/store.list";
    }

    @RequestMapping("getAllStores")
    @Pagination
    @ResponseBody
    public Object getAllStores(){
        List<Store> allStores = storeService.getAllStores();
        List<StoreView> view = allStores.stream().collect(Collectors.mapping(StoreView :: transform,Collectors.toList()));
        return getResultPage((Page<Store>)allStores,view);
    }
}
