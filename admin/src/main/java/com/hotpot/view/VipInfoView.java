package com.hotpot.view;

import com.hotpot.commons.ApplicationContextUtil;
import com.hotpot.domain.Store;
import com.hotpot.domain.VipInfo;
import com.hotpot.domain.ValueCard;
import com.hotpot.service.StoreService;
import com.hotpot.service.ValueCardService;
import com.hotpot.service.impl.StoreServiceImpl;
import com.hotpot.service.impl.ValueCardServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by zoupeng on 16/1/18.
 */
public class VipInfoView {
    private VipInfo vipInfo;
    private String storeName;
    private List<String> cardList;

    private static ValueCardService valueCardService = ApplicationContextUtil.getBean(ValueCardServiceImpl.class);
    private static StoreService storeService = ApplicationContextUtil.getBean(StoreServiceImpl.class);

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

    public static VipInfoView transform(VipInfo vipInfo) {
        VipInfoView view = new VipInfoView();

        view.setVipInfo(vipInfo);
        List<String> cards = valueCardService.getCardBalanceByVipInfo(vipInfo).stream().collect(Collectors.mapping(ValueCard:: getCardId,Collectors.toList()));
        view.setCardList(cards);
        Store store = storeService.getStoreByStoreId(vipInfo.getStoreId());
        view.setStoreName(store.getStoreName());

        return view;
    }
}
