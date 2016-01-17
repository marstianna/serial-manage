package com.hotpot.controller;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.domain.Store;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.VipInfo;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.VipInfoService;
import com.hotpot.view.VipInfoView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoupeng on 16/1/11.
 */
@Controller
@RequestMapping("/vip/")
public class VipController {
    @Autowired
    private VipInfoService vipInfoService;
    @Autowired
    private ValueCardService valueCardService;
    @Autowired
    private StoreService storeService;

    @RequestMapping({"","index"})
    public String index(){
        return "vip/vip.list";
    }

    @RequestMapping("list")
    @Pagination
    @ResponseBody
    public Object getVipList(){
        List<VipInfo> vips = vipInfoService.getAllVips();
        List<VipInfoView> results = new ArrayList<>();
        for(VipInfo vip : vips){
            VipInfoView view = new VipInfoView();
            view.setVipInfo(vip);
            List<String> cards = Lists.transform(valueCardService.getCardBalanceByVipInfo(vip), new Function<ValueCard, String>() {
                @Override
                public String apply(ValueCard input) {
                    return input.getCardId();
                }
            });
            view.setCardList(cards);

            Store store = storeService.getStoreByStoreId(vip.getStoreId());
            view.setStoreName(store.getStoreName());

            results.add(view);
        }
        return results;
    }

}
