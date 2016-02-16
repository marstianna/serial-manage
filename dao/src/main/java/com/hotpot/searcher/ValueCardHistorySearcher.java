package com.hotpot.searcher;

/**
 * Created by zoupeng on 16/1/5.
 */
public class ValueCardHistorySearcher {
    private Integer operate;
    private String cardId;
    private Integer storeId;
    private String startTime;
    private String endTime;
    private String phone;

    public Integer getOperate() {
        return operate;
    }

    public ValueCardHistorySearcher setOperate(Integer operate) {
        this.operate = operate;
        return this;
    }

    public String getCardId() {
        return cardId;
    }

    public ValueCardHistorySearcher setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public ValueCardHistorySearcher setStoreId(Integer storeId) {
        this.storeId = storeId;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPhone() {
        return phone;
    }

    public ValueCardHistorySearcher setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
