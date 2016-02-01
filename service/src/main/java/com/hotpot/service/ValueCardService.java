package com.hotpot.service;

import com.hotpot.domain.ValueCardHistory;
import com.hotpot.domain.VipInfo;
import com.hotpot.domain.ValueCard;
import com.hotpot.searcher.ValueCardHistorySearcher;
import com.hotpot.searcher.ValueCardSearcher;

import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface ValueCardService {

    /**
     * 新增一张储值卡
     * @param cardId
     * @param storeId
     * @param money
     * @param account
     * @param vipId
     * @param password
     * @return  返回一个创建的卡的 uuid,需要记录到储值卡上
     */
    ValueCard addNewCard(String cardId, Integer storeId, Integer money, Integer account, Integer vipId, String password);

    /**
     * 根据卡号获取流水
     * @param cardId
     * @return
     */
    List<ValueCardHistory> getCardHistory(String cardId);

    /**
     * 根据店铺 id 获取跟该店铺相关的储值卡消费和充值记录
     * @param storeId
     * @return
     */
    List<ValueCardHistory> getCardHistoryByStoreId(Integer storeId);
    /**
     * 根据会员信息获取其名下的所有会员卡的消费流水
     * @param vipInfo
     * @return
     */
    Map<String,List<ValueCardHistory>> getCardHistory(VipInfo vipInfo);

    /**
     * 根据卡号获取储值卡的余额
     * @param cardId
     * @param cardUuid
     * @return
     */
    ValueCard getCardBalanceByCardUniqueKey(String cardId,String cardUuid);

    ValueCard getCardByPhoneAndPassword(String phone,String password);

    /**
     * 根据会员信息获取其明显的所有会员卡余额信息
     * @param vipInfo
     * @return
     */
    List<ValueCard> getCardBalanceByVipInfo(VipInfo vipInfo);

    /**
     * 给指定储值卡充值
     * @param cardId
     * @param cardUuid
     * @param storeId
     * @param account   储值卡充值金额
     * @param money     实际支付金额
     * @return
     */
    ValueCard topUp(String cardId, String cardUuid, Integer storeId, Integer account, Integer money);

    /**
     * 使用指定会员卡消费
     * @param cardId
     * @param cardUuid
     * @param storeId
     * @param account   储值卡扣减金额
     * @param price     实际消费金额
     * @return
     */
    ValueCard payment(String cardId, String cardUuid, Integer storeId, Integer account, Integer price);

    /**
     * 使用电话和密码支付
     * @param mobilePhone
     * @param password
     * @param storeId
     * @param account
     * @param price
     * @return
     */
    ValueCard paymentWithPassword(String mobilePhone,String password,Integer storeId,Integer account,Integer price);

    /**
     * 获取所有储值卡
     * @return
     * @param searcher
     */
    List<ValueCard> getAllCards(ValueCardSearcher searcher);

    Map<String,List<Integer>> settleOrdersForCom(List<Integer> orderIds);
    Map<String,List<Integer>> settleForStore(List<Integer> orderIds);

    List<ValueCardHistory> getAllCardHistory(ValueCardHistorySearcher searcher);
}
