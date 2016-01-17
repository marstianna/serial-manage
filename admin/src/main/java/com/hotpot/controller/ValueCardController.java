package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.domain.VipInfo;
import com.hotpot.searcher.ValueCardHistorySearcher;
import com.hotpot.searcher.ValueCardSearcher;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.VipInfoService;
import com.hotpot.view.CardHistoryView;
import com.hotpot.view.CardView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zoupeng on 16/1/4.
 */
@Controller
@RequestMapping("/valuecard/")
public class ValueCardController extends BaseController {
    @Autowired
    ValueCardService valueCardService;
    @Autowired
    StoreService storeService;
    @Autowired
    VipInfoService vipInfoService;

    @RequestMapping({"index"})
    public String toValueCardIndex(){
        return "valuecard/valuecard";
    }

    @RequestMapping("topUp")
    @ResponseBody
    public String topUp(String cardId,String cardUuid,Integer storeId,Integer account,Integer price){
        ValueCard card = valueCardService.topUp(cardId,cardUuid,storeId,account,price);
        return null;
    }

    @RequestMapping("getAllCards")
    @Pagination
    @ResponseBody
    public Object getAllCards(@ModelAttribute ValueCardSearcher searcher){
        List<ValueCard> allCards = valueCardService.getAllCards(searcher);
        List<CardView> results = new ArrayList<>();
        for(ValueCard card : allCards){
            CardView cardView = new CardView();
            cardView.setCard(card);
            VipInfo vipInfo = vipInfoService.getVipInfoById(card.getVipId());
            cardView.setVipName(vipInfo.getName()+"("+vipInfo.getId()+")");
            results.add(cardView);
        }
        return results;
    }

    @RequestMapping("historyindex")
    public String historyindex(HttpServletRequest request){
        request.setAttribute("storeList",storeService.getStoreMap());
        return "valuecard/valuecard.history";
    }


    @RequestMapping("getAllCardHistory")
    @Pagination
    @ResponseBody
    public Object getAllCardHistory(@ModelAttribute ValueCardHistorySearcher searcher){
        List<ValueCardHistory> allCardHistory = valueCardService.getAllCardHistory(searcher);
        List<CardHistoryView> results = new ArrayList<>();
        for (ValueCardHistory cardHistory : allCardHistory){
            CardHistoryView view = new CardHistoryView();
            view.setValueCardHistory(cardHistory);
            view.setStoreName(storeService.getStoreByStoreId(cardHistory.getStoreId()).getStoreName());
            results.add(view);
        }
        return results;
    }

    @RequestMapping("getCardHistoryByCardId")
    @ResponseBody
    public List<ValueCardHistory> getCardHistoryByCardId(String cardId){
        return  valueCardService.getCardHistory(cardId);
    }

    @RequestMapping("settleForCom")
    @ResponseBody
    public Object settleForCom(@RequestParam String rids){
        List<Integer> ids = new ArrayList<>();
        for(String id : rids.split(",")){
            ids.add(Integer.parseInt(id));
        }
        return valueCardService.settleOrdersForCom(ids);
    }

    @RequestMapping("turnToAddCard")
    public String turnToAddCard(HttpServletRequest request){
        return "valuecard/valuecard.add";
    }

    @RequestMapping("addCard")
    @ResponseBody
    public Object addCard(@ModelAttribute ValueCard card){
        return "success";
    }

}