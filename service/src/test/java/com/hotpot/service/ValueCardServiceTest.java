package com.hotpot.service;

import com.alibaba.fastjson.JSON;
import com.hotpot.domain.ValueCard;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.domain.VipInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 16/1/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-all.xml")
public class ValueCardServiceTest {
    @Autowired
    ValueCardService valueCardService;

    @Test
    public void testAddNewCard() throws Exception {
        valueCardService.addNewCard("No.000001",1,1000,1200,1,"testPassword","testephon");
    }

    @Test
    public void testGetCardHistory() throws Exception {
        List<ValueCardHistory> historyList = valueCardService.getCardHistory("No.000001");
        System.out.println(JSON.toJSONString(historyList));
    }

    @Test
    public void testGetCardHistoryByStoreId() throws Exception {
        List<ValueCardHistory> historyList = valueCardService.getCardHistoryByStoreId(1);
        System.out.println(JSON.toJSONString(historyList));
    }

    @Test
    public void testGetCardHistory1() throws Exception {
        VipInfo vipInfo = new VipInfo();
        vipInfo.setId(1);
        Map<String,List<ValueCardHistory>> historyList = valueCardService.getCardHistory(vipInfo);
        System.out.println(JSON.toJSONString(historyList));
    }

    @Test
    public void testGetCardBalanceByCardUniqueKey() throws Exception {
        ValueCard valueCard = valueCardService.getCardBalanceByCardUniqueKey("No.000001","887a85f2-45e4-3cbe-a2ac-5d1f1dfff588");
        System.out.println(JSON.toJSONString(valueCard));
    }

    @Test
    public void testGetCardBalanceByVipInfo() throws Exception {
        VipInfo vipInfo = new VipInfo();
        vipInfo.setId(1);
        List<ValueCard> histories = valueCardService.getCardBalanceByVipInfo(vipInfo);
        System.out.println(JSON.toJSONString(histories));
    }

    @Test
    public void testTopUp() throws Exception {
        valueCardService.topUp("No.000001",1,1000,233);
    }

    @Test
    public void testPayment() throws Exception {
        valueCardService.payment("No.000001","887a85f2-45e4-3cbe-a2ac-5d1f1dfff588",1,2000,1000);
    }

    @Test
    public void testPaymentWithPassword() throws Exception {
        ValueCard valueCard = valueCardService.paymentWithPassword("13888888889","testPassword",1,100,100);
        System.out.println(JSON.toJSONString(valueCard));
    }
}