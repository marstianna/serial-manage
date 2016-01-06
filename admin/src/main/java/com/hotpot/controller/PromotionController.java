package com.hotpot.controller;

import com.hotpot.constenum.PromotionTypeEnum;
import com.hotpot.domain.Promotion;
import com.hotpot.searcher.PromotionSearcher;
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
public class PromotionController {
    @Autowired
    private PromotionService promotionService;
    @Autowired
    private StoreService storeService;

    @RequestMapping({"index",""})
    public String index(HttpServletRequest request){
        request.setAttribute("promotionTypeList", PromotionTypeEnum.getMap());
        request.setAttribute("storeList",storeService.getStoreMap());
        return "promotion/promotion.list";
    }

    @RequestMapping("getAllPromotions")
    @ResponseBody
    public Object getAllPromotions(@ModelAttribute PromotionSearcher searcher){
        return promotionService.getAllPromotionsBySearcher(searcher);
    }

    @RequestMapping("turnToAddPromotion")
    public String turnToAddPromotion(HttpServletRequest request){
        request.setAttribute("promotionTypeList", PromotionTypeEnum.getMap());
        request.setAttribute("storeList",storeService.getStoreMap());
        return "promotion/promotion.add";
    }

    @RequestMapping("addPromotion")
    @ResponseBody
    public Object addPromotion(@ModelAttribute Promotion promotion){
        promotionService.addPromotion(promotion);
        return "success";
    }

    @RequestMapping("modifyPromotion")
    public ModelAndView turnToModifyPromotion(HttpServletRequest request,Integer id){
        if(id == null || id == 0){
            throw new RuntimeException("illegal input key id");
        }
        ModelAndView mav = new ModelAndView("promotion/promotion.modify");
        mav.addObject("promotion",promotionService.getPromotionById(id));

        request.setAttribute("promotionTypeList", PromotionTypeEnum.getMap());
        request.setAttribute("storeList",storeService.getStoreMap());
        return mav;
    }
}
