package com.hotpot.service.impl;

import com.hotpot.commons.Const;
import com.hotpot.commons.DateTool;
import com.hotpot.dao.ValueCardHistoryMapper;
import com.hotpot.dao.ValueCardMapper;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.domain.VipInfo;
import com.hotpot.service.ValueCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by zoupeng on 15/12/26.
 */
@Service
public class ValueCardServiceImpl implements ValueCardService{
    @Autowired
    ValueCardMapper valueCardMapper;
    @Autowired
    ValueCardHistoryMapper valueCardHistoryMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public String addNewCard(String cardId, Integer storeId, Integer money, Integer account, Integer vipId, String password){
        ValueCard card = new ValueCard();
        card.setCardId(cardId);
        String cardUuid = UUID.nameUUIDFromBytes(cardId.getBytes()).toString();
        card.setCardUuid(cardUuid);
        card.setBalance(account);
        card.setVipId(vipId);
        card.setPassword(password);
        valueCardMapper.insertSelective(card);
        recordHistory(cardId,storeId,Const.OPERATE_ADD,account,money);
        return cardUuid;
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
    public  List<ValueCard> getCardBalanceByVipInfo(VipInfo vipInfo) {
        return valueCardMapper.getValueCardInfoByVip(vipInfo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ValueCard topUp(String cardId, String cardUuid, Integer storeId, Integer account, Integer money) {
        Integer count = valueCardMapper.topUp(cardId,cardUuid,account);
        if(count == 0){
            throw new RuntimeException("充值失败,请稍后再试");
        }
        recordHistory(cardId,storeId, Const.OPERATE_ADD,account,money);
        return getCardBalanceByCardUniqueKey(cardId,cardUuid);
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
        List<ValueCard> valueCards = valueCardMapper.getValueCardByVipMobilePhone(mobilePhone);
        ValueCard paymentCard = null;
        for(ValueCard card : valueCards){
            Integer count = valueCardMapper.payment(card.getCardId(),card.getCardUuid(),price);
            if(count != 0){
                paymentCard = getCardBalanceByCardUniqueKey(card.getCardId(),card.getCardUuid());
                recordHistory(card.getCardId(),storeId, Const.OPERATE_MINUS,account,price);
                break;
            }
        }
        return paymentCard;
    }

    @Override
    public List<ValueCardHistory> getCardHistoryByStoreId(Integer storeId) {
        return valueCardHistoryMapper.getCardHistoryByStoreId(storeId);
    }

    @Override
    public List<ValueCard> getAllCards() {
        return valueCardMapper.getAllCards();
    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    private void recordHistory(String cardId,Integer storeId,Integer operate,Integer account,Integer price){
//        throw new Exception();
        ValueCardHistory history = new ValueCardHistory();
        history.setCardId(cardId);
        history.setStoreId(storeId);
        history.setOperate(operate);
        history.setAccount(account);
        history.setPrice(price);
        history.setCreateTime(DateTool.unixTime());
        valueCardHistoryMapper.insertSelective(history);
    }
}
