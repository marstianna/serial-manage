package com.hotpot.view;

import com.hotpot.commons.ApplicationContextUtil;
import com.hotpot.domain.ValueCardHistory;
import com.hotpot.service.StoreService;
import com.hotpot.service.impl.StoreServiceImpl;

/**
 * Created by zoupeng on 16/1/18.
 */
public class CardHistoryView {
    private ValueCardHistory valueCardHistory;
    private String storeName;
    private static StoreService storeService = ApplicationContextUtil.getBean(StoreServiceImpl.class);

    public CardHistoryView(ValueCardHistory _valueCardHistory){
        this.valueCardHistory = _valueCardHistory;
    }

    public ValueCardHistory getValueCardHistory() {
        return valueCardHistory;
    }

    public void setValueCardHistory(ValueCardHistory valueCardHistory) {
        this.valueCardHistory = valueCardHistory;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public static CardHistoryView transform(ValueCardHistory origin) {
        CardHistoryView view = new CardHistoryView(origin);
        view.setValueCardHistory(origin);
        if(origin.getStoreId() == 0){
            view.setStoreName("总公司");
        }else {
            view.setStoreName(storeService.getStoreByStoreId(origin.getStoreId()).getStoreName());
        }
        return view;
    }
}
