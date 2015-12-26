package com.hotpot.service.impl;

import com.hotpot.dao.ValueCardHistoryMapper;
import com.hotpot.dao.ValueCardMapper;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.domain.VipInfo;
import com.hotpot.service.ValueCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public void addNewCard(String cardId, String cardUuid, Integer balance, Integer vipId){
        ValueCard card = new ValueCard();
        card.setCardId(cardId);
        card.setCardUuid(cardUuid);
        card.setBalance(balance);
        card.setVipId(vipId);
    }

    @Override
    public List<ValueCardHistory> getCardHistory(String cardId) {
        return valueCardHistoryMapper.getCardHistory(cardId);
    }

    @Override
    public List<ValueCardHistory> getCardHistory(VipInfo vipInfo) {
        return valueCardHistoryMapper.getCardHistory(vipInfo);
    }

    @Override
    public ValueCard getCardBalanceByCardUniqueKey(String cardId, String cardUuid) {
        return valueCardHistoryMapper.getValueCardInfo(cardId,cardUuid);
    }

    @Override
    public ValueCard getCardBalanceByVipInfo(VipInfo vipInfo) {
        return valueCardMapper.getValueCardInfoByVip(vipInfo);
    }

    @Override
    public ValueCard topUp(String cardId, String cardUuid, Integer storeId, Integer account, Integer money) {
        valueCardMapper.topUp(cardId,cardUuid,money);
        return getCardBalanceByCardUniqueKey(cardId,cardUuid);
    }

    @Override
    public ValueCard payment(String cardId, String cardUuid, Integer storeId, Integer account, Integer price){
        Integer count = valueCardMapper.payment(cardId, cardUuid, price);
        if(count == 0){
            throw new RuntimeException("余额不足,无法支付");
        }
        return getCardBalanceByCardUniqueKey(cardId,cardUuid);
    }

    private void recordHistory(String cardId,Integer storeId,Integer operate,Integer account,Integer price){
        ValueCardHistory history = new ValueCardHistory();
        history.setCardId(cardId);
        history.setStoreId(storeId);
        history.setOperate(operate);
        history.setAccount(account);
        history.setPrice(price);
        valueCardHistoryMapper.insert(history);
    }
}
