package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.commons.pagination.Page;
import com.hotpot.commons.pagination.annotation.Pagination;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
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
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

    @Pagination
    @ResponseBody
    @RequestMapping("getAllCards")
    public Object getAllCards(@ModelAttribute ValueCardSearcher searcher){
        List<ValueCard> allCards = valueCardService.getAllCards(searcher);
//        List<CardView> results = new ArrayList<>();
//        allCards.stream().forEach((card) -> results.add(new CardView().apply(card)));
//        return results;
        List<CardView> collect = allCards.stream().collect(Collectors.mapping(CardView::apply, Collectors.toList()));
        return getResultPage((Page<ValueCard>)allCards,collect);
    }

    @RequestMapping("historyindex")
    public String historyindex(HttpServletRequest request){
        request.setAttribute("storeList",storeService.getStoreMap());
        return "valuecard/valuecard.history";
    }


    @Pagination
    @ResponseBody
    @RequestMapping("getAllCardHistory")
    public Object getAllCardHistory(@ModelAttribute ValueCardHistorySearcher searcher){
        List<ValueCardHistory> allCardHistory = valueCardService.getAllCardHistory(searcher);
//        Page<ValueCardHistory> page = (Page<ValueCardHistory>)allCardHistory;
//        List<CardHistoryView> results = new ArrayList<>();
//        page.stream().forEach((history)->results.add(new CardHistoryView().apply(history)));
//        PageInfo pageInfo = new PageInfo(page);
//        pageInfo.setList(results);
//        return pageInfo;
        List<CardHistoryView> collect = allCardHistory.stream().collect(Collectors.mapping(CardHistoryView::apply, Collectors.toList()));
        return getResultPage((Page<ValueCardHistory>)allCardHistory,collect);
    }

    @RequestMapping("getCardHistoryByCardId")
    @ResponseBody
    public List<ValueCardHistory> getCardHistoryByCardId(String cardId){
        return  valueCardService.getCardHistory(cardId);
    }

    @RequestMapping("settleForCom")
    @ResponseBody
    public Object settleForCom(@RequestParam String rids){
        List<Integer> ids = Arrays.asList(rids.split(",")).stream().collect(Collectors.mapping(Integer::parseInt,Collectors.toList()));
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