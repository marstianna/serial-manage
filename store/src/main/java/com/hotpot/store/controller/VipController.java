package com.hotpot.store.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.Page;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.domain.VipInfo;
import com.hotpot.searcher.VipSearcher;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.VipInfoService;
import com.hotpot.store.view.VipInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zoupeng on 16/1/11.
 */
@Controller
@RequestMapping("/vip/")
public class VipController extends BaseController {
    @Autowired
    private VipInfoService vipInfoService;
    @Autowired
    private ValueCardService valueCardService;
    @Autowired
    private StoreService storeService;

    @RequestMapping({"","index"})
    public String index(){
        setRequestAttribute("storeList",storeService.getStoreMap());
        return "vip/vip.list";
    }

    @RequestMapping("list")
    @Pagination
    @ResponseBody
    public Object getVipList(@ModelAttribute VipSearcher searcher){
        List<VipInfo> vips = vipInfoService.getAllVips(searcher);
        List<VipInfoView> results = vips.stream().collect(Collectors.mapping(VipInfoView::transform, Collectors.toList()));
        return getResultPage((Page<VipInfo>)vips,results);
    }


}
