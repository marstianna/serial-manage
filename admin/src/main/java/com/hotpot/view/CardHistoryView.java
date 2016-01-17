package com.hotpot.view;

import com.hotpot.domain.ValueCardHistory;

/**
 * Created by zoupeng on 16/1/17.
 */
public class CardHistoryView {
    private ValueCardHistory valueCardHistory;
    private String storeName;

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
}
