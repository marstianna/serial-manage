package com.hotpot.view;

import com.hotpot.domain.ValueCard;

/**
 * Created by zoupeng on 16/1/17.
 */
public class CardView {
    private ValueCard card;
    private String vipName;

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public ValueCard getCard() {
        return card;
    }

    public void setCard(ValueCard card) {
        this.card = card;
    }
}
