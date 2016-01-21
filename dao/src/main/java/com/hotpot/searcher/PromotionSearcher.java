package com.hotpot.searcher;

/**
 * Created by zoupeng on 16/1/6.
 */
public class PromotionSearcher {
    private Integer id;
    private Integer type;
    private Integer storeId;
    private Integer startTime;
    private Integer endTime;
    private Integer payType;

    public Integer getId() {
        return id;
    }

    public PromotionSearcher setId(Integer id) {
        this.id = id;
        return this;
    }

    public Integer getType() {
        return type;
    }

    public PromotionSearcher setType(Integer type) {
        this.type = type;
        return this;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public PromotionSearcher setStoreId(Integer storeId) {
        this.storeId = storeId;
        return this;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public PromotionSearcher setStartTime(Integer startTime) {
        this.startTime = startTime;
        return this;
    }

    public Integer getEndTime() {
        return endTime;
    }

    public PromotionSearcher setEndTime(Integer endTime) {
        this.endTime = endTime;
        return this;
    }

    public Integer getPayType() {
        return payType;
    }

    public PromotionSearcher setPayType(Integer payType) {
        this.payType = payType;
        return this;
    }
}
