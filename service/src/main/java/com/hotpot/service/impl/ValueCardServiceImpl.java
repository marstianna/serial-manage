package com.hotpot.service.impl;

import com.google.common.collect.Lists;
import com.hotpot.commons.Const;
import com.hotpot.commons.DateTool;
import com.hotpot.dao.ValueCardHistoryMapper;
import com.hotpot.dao.ValueCardMapper;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.domain.VipInfo;
import com.hotpot.searcher.ValueCardHistorySearcher;
import com.hotpot.searcher.ValueCardSearcher;
import com.hotpot.service.ValueCardService;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by zoupeng on 15/12/26.
 */
@Service
public class ValueCardServiceImpl implements ValueCardService {
    @Autowired
    ValueCardMapper valueCardMapper;
    @Autowired
    ValueCardHistoryMapper valueCardHistoryMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ValueCard addNewCard(String cardId, Integer storeId, Integer money, Integer account, Integer vipId, String password,String phone){
        ValueCard card = new ValueCard();
        card.setCardId(cardId);
        String cardUuid = UUID.nameUUIDFromBytes((cardId+DateTool.getDateTime()).getBytes()).toString()+"-"+new Random().nextInt(1000);
        card.setCardUuid(cardUuid);
        card.setBalance(account);
        card.setVipId(vipId);
        card.setPassword(password);
        card.setCreateTime(DateTool.getDateTime());
        card.setPhone(phone);
        valueCardMapper.insertSelective(card);
        recordHistory(cardId,storeId, Const.OPERATE_ADD,account,money);
        return card;
    }

    @Override
    public List<ValueCardHistory> getCardHistory(String cardId) {
        return valueCardHistoryMapper.getCardHistoryByCardId(cardId);
    }

    @Override
    public Map<String,List<ValueCardHistory>> getCardHistory(VipInfo vipInfo) {
        List<ValueCard> cards = getCardBalanceByVipInfo(vipInfo);
        Map<String,List<ValueCardHistory>> results = new HashMap<>();
        for(ValueCard card : cards){
            String cardId = card.getCardId();
            results.put(cardId,getCardHistory(cardId));
        }
        return results;
    }

    @Override
    public ValueCard getCardBalanceByCardUniqueKey(String cardId, String cardUuid) {
        return valueCardMapper.getValueCardInfo(cardId,cardUuid);
    }

    @Override
    public ValueCard getCardByPhoneAndPassword(String phone, String password) {
        return valueCardMapper.getValueCardInfoByPhoneAndPassword(phone,password);
    }

    @Override
    public  List<ValueCard> getCardBalanceByVipInfo(VipInfo vipInfo) {
        return valueCardMapper.getValueCardInfoByVip(vipInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ValueCard topUp(String cardId, Integer storeId, Integer account, Integer money) {
        Integer count = valueCardMapper.topUp(cardId,account);
        if(count == 0){
            throw new RuntimeException("充值失败,请稍后再试");
        }
        recordHistory(cardId,storeId, Const.OPERATE_ADD,account,money);
        return getCardBalanceByCardUniqueKey(cardId,null);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public ValueCard payment(String cardId, String cardUuid, Integer storeId, Integer account, Integer price){
        Integer count = valueCardMapper.payment(cardId, cardUuid, account);
        if(count == 0){
            throw new RuntimeException("余额不足,无法支付");
        }
        recordHistory(cardId,storeId, Const.OPERATE_MINUS,account,price);
        return getCardBalanceByCardUniqueKey(cardId,cardUuid);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public ValueCard paymentWithPassword(String mobilePhone, String password, Integer storeId, Integer account, Integer price) {
        Integer count = valueCardMapper.paymentByPassword(mobilePhone, password, account);
        if(count == 0){
            throw new RuntimeException("余额不足,无法支付");
        }
        ValueCard valueCard = valueCardMapper.getValueCardInfoByPhoneAndPassword(mobilePhone,password);
        recordHistory(valueCard.getCardId(),storeId, Const.OPERATE_MINUS,account,price);
        return valueCard;
    }

    @Override
    public List<ValueCardHistory> getCardHistoryByStoreId(Integer storeId) {
        return valueCardHistoryMapper.getCardHistoryByStoreId(storeId);
    }

    @Override
    public List<ValueCard> getAllCards(ValueCardSearcher searcher) {
        return valueCardMapper.getCardHistoryBySearcher(searcher);
    }

    @Override
    public Map<String,List<Pair<Integer,Integer>>> settleOrdersForCom(List<Integer> ids){
        Map<String,List<Pair<Integer,Integer>>> result = new HashMap<>();
        String success = "success";
        String fail = "fail";
        result.put(success, Lists.newArrayList());
        result.put(fail,Lists.newArrayList());
        for(Integer id: ids){
            try {
                Integer count = valueCardHistoryMapper.settle(id,Const.SETTLE_FROM_COMP,Const.OPERATE_MINUS);
                if (count == 0){
                    result.get(fail).add(new Pair<>(id,0));
                }else{
                    ValueCardHistory history = valueCardHistoryMapper.selectByPrimaryKey(id);
                    result.get(success).add(new Pair<>(id,history.getAccount()));
                }
            }catch(Exception e){
                //TODO log
                result.get(fail).add(new Pair<>(id,0));
            }
        }
        return result;
    }

    @Override
    public Map<String,List<Pair<Integer,Integer>>> settleForStore(List<Integer> orderIds) {
        Map<String,List<Pair<Integer,Integer>>> result = new HashMap<>();
        String success = "success";
        String fail = "fail";
        result.put(success, Lists.newArrayList());
        result.put(fail,Lists.newArrayList());
        for(Integer id: orderIds){
            try {
                Integer count = valueCardHistoryMapper.settle(id,Const.SETTLE_FROM_STORE,Const.OPERATE_ADD);
                if (count == 0){
                    result.get(fail).add(new Pair<>(id,0));
                }else{
                    ValueCardHistory history = valueCardHistoryMapper.selectByPrimaryKey(id);
                    result.get(success).add(new Pair<>(id,history.getAccount()));
                }
            }catch(Exception e){
                //TODO log
                result.get(fail).add(new Pair<>(id,0));
            }
        }
        return result;
    }

    @Override
    public List<ValueCardHistory> getAllCardHistory(ValueCardHistorySearcher searcher) {
        if(searcher.getPhone() != null && !searcher.getPhone().equals("")){
            List<ValueCard> cards = valueCardMapper.getCardHistoryBySearcher(new ValueCardSearcher().setPhone(searcher.getPhone()));
            if(!cards.isEmpty()){
                searcher.setCardId(cards.get(0).getCardId());
            }
        }
        return valueCardHistoryMapper.getCardHistoryBySearcher(searcher);
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    private void recordHistory(String cardId,Integer storeId,Integer operate,Integer account,Integer price){
        ValueCardHistory history = new ValueCardHistory();
        history.setCardId(cardId);
        history.setStoreId(storeId);
        history.setOperate(operate);
        history.setAccount(account);
        history.setPrice(price);
        history.setCreateTime(DateTool.getDateTime());
        history.setSettle(storeId == 0 ? Const.SETTLE : Const.SETTLE_INIT);
        valueCardHistoryMapper.insertSelective(history);
    }

    public static void main(String[] args) {
        System.out.println(new Random().nextInt(1000));
    }
}
