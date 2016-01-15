package com.hotpot.view;

import com.hotpot.domain.VipInfo;

import java.util.List;

/**
 * Created by zoupeng on 16/1/15.
 */
public class VipInfoView{
    private VipInfo vipInfo;
    private String storeName;
    private List<String> cardList;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public VipInfo getVipInfo() {
        return vipInfo;
    }

    public void setVipInfo(VipInfo vipInfo) {
        this.vipInfo = vipInfo;
    }

    public List<String> getCardList() {
        return cardList;
    }

    public void setCardList(List<String> cardList) {
        this.cardList = cardList;
    }
}
