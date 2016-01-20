package com.hotpot.searcher;

/**
 * Created by zoupeng on 16/1/5.
 */
public class ValueCardHistorySearcher {
    private Integer operate;
    private String cardId;
    private Integer storeId;
    private Integer startTime;
    private Integer endTime;

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

    public Integer getStartTime() {
        return startTime;
    }

    public ValueCardHistorySearcher setStartTime(Integer startTime) {
        this.startTime = startTime;
        return this;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public ValueCardHistorySearcher setEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }
}
