package com.hotpot.store.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.constenum.PayTypeEnum;
import com.hotpot.constenum.PromotionTypeEnum;
import com.hotpot.domain.Promotion;
import com.hotpot.domain.Store;
import com.hotpot.searcher.PromotionSearcher;
import com.hotpot.service.Context;
import com.hotpot.service.PromotionService;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zoupeng on 16/1/6.
 */
@Controller
@RequestMapping("/promotion/")
public class PromotionController extends BaseController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private StoreService storeService;

    @RequestMapping({"index",""})
    public String index(){
        setRequestAttribute("promotionTypeList", PromotionTypeEnum.getMap());
        setRequestAttribute("storeList",storeService.getStoreMap());
        return "promotion/promotion.list";
    }

    @RequestMapping("getAllPromotions")
    @Pagination
    @ResponseBody
    public Object getAllPromotions(@ModelAttribute PromotionSearcher searcher){
        Store store = Context.get();
        return promotionService.getAllPromotionsBySearcher(searcher.setStoreId(store.getId()));
    }

    @RequestMapping("turnToAddPromotion")
    public String turnToAddPromotion(HttpServletRequest request){
        request.setAttribute("promotionTypeList", PromotionTypeEnum.getMap());
        request.setAttribute("storeList",storeService.getStoreMap());
        request.setAttribute("payTypeList", PayTypeEnum.getMap());
        return "promotion/promotion.add";
    }

    @RequestMapping("addPromotion")
    public Object addPromotion(@ModelAttribute Promotion promotion){
        setRequestAttribute("useLayout",false);
        Store store = Context.get();
        promotion.setStoreId(store.getId());
        promotionService.addPromotion(promotion);
        return index();
    }

    @RequestMapping("modifyPromotion")
    public ModelAndView turnToModifyPromotion(HttpServletRequest request,Integer id){
        if(id == null || id == 0){
            throw new RuntimeException("illegal input key id");
        }
        ModelAndView mav = new ModelAndView("promotion/promotion.modify");
        mav.addObject("promotion",promotionService.getPromotionById(id));

        request.setAttribute("promotionTypeList", PromotionTypeEnum.getMap());
//        request.setAttribute("storeList",storeService.getStoreMap());
        return mav;
    }
}
