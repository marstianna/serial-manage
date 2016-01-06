package com.hotpot.controller;

import com.hotpot.commons.framework.BaseController;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.searcher.ValueCardHistorySearcher;
import com.hotpot.searcher.ValueCardSearcher;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
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
    @ResponseBody
    public List<ValueCard> getAllCards(@ModelAttribute ValueCardSearcher searcher){
        return  valueCardService.getAllCards(searcher);
    }

    @RequestMapping("historyindex")
    public String historyindex(HttpServletRequest request){
        request.setAttribute("storeList",storeService.getStoreMap());
        return "valuecard/valuecard.history";
    }


    @RequestMapping("getAllCardHistory")
    @ResponseBody
    public List<ValueCardHistory> getAllCardHistory(@ModelAttribute ValueCardHistorySearcher searcher){
        if(null == searcher){
            searcher = new ValueCardHistorySearcher();
        }
        return valueCardService.getAllCardHistory(searcher);
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


}