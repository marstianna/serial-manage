package com.hotpot.view;

import com.hotpot.commons.ApplicationContextUtil;
import com.hotpot.domain.VipInfo;
import com.hotpot.service.VipInfoService;
import com.hotpot.service.impl.VipInfoServiceImpl;
import com.hotpot.domain.ValueCard;

/**
 * Created by zoupeng on 16/1/18.
 */
public class CardView {
    private ValueCard card;
    private String vipName;
    private static VipInfoService vipInfoService = ApplicationContextUtil.getBean(VipInfoServiceImpl.class);

    public CardView(ValueCard valueCard){
        this.card = valueCard;
    }


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

    public static CardView transform(ValueCard valueCard) {
        CardView cardView = new CardView(valueCard);
        VipInfo vipInfo = vipInfoService.getVipInfoById(valueCard.getVipId());
        cardView.setVipName(vipInfo.getName() + "(" + vipInfo.getId() + ")");
        return cardView;
    }
}
