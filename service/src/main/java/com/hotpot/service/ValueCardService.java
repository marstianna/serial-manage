package com.hotpot.service;

import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.domain.VipInfo;

import java.util.List;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface ValueCardService {
    void addNewCard(String cardId, String cardUuid, Integer balance, Integer vipId);

    List<ValueCardHistory> getCardHistory(String cardId);

    List<ValueCardHistory> getCardHistory(VipInfo vipInfo);

    ValueCard getCardBalanceByCardUniqueKey(String cardId,String cardUuid);

    ValueCard getCardBalanceByVipInfo(VipInfo vipInfo);

    ValueCard topUp(String cardId, String cardUuid, Integer storeId, Integer account, Integer money);

    ValueCard payment(String cardId, String cardUuid, Integer storeId, Integer account, Integer price);
}
