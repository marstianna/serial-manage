package com.hotpot.searcher;

/**
 * Created by zoupeng on 15/12/26.
 */
public class OrderSearcher {
    private Integer id;
    private Integer vipId;
    private Integer payType;
    private Integer storeId;
    private Integer cardId;
    private Integer startTime;
    private Integer endTime;
    private Integer settle;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipId() {
        return vipId;
    }

    public void setVipId(Integer vipId) {
        this.vipId = vipId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCardId() {
        return cardId;
    }

    public void setCardId(Integer cardId) {
        this.cardId = cardId;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }

    public Integer getSettle() {
        return settle;
    }

    public void setSettle(Integer settle) {
        this.settle = settle;
    }
}
