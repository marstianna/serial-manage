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

    public OrderSearcher setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getVipId() {
        return vipId;
    }

    public OrderSearcher setVipId(Integer vipId) {
        this.vipId = vipId;
        return this;
    }

    public Integer getPayType() {
        return payType;
    }

    public OrderSearcher setPayType(Integer payType) {
        this.payType = payType;
        return this;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public OrderSearcher setStoreId(Integer storeId) {
        this.storeId = storeId;
        return this;
    }

    public Integer getCardId() {
        return cardId;
    }

    public OrderSearcher setCardId(Integer cardId) {
        this.cardId = cardId;
        return this;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public OrderSearcher setStartTime(Integer startTime) {
        this.startTime = startTime;
        return this;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public OrderSearcher setEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getSettle() {
        return settle;
    }

    public OrderSearcher setSettle(Integer settle) {
        this.settle = settle;
        return this;
    }
}
