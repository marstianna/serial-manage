package com.hotpot.searcher;

/**
 * Created by zoupeng on 15/12/26.
 */
public class OrderSearcher {
    private Integer id;     //订单号
    private Integer vipId;  //会员 ID
    private Integer payType;//支付方式
    private Integer storeId;//店铺 ID
    private Integer cardId; //储值卡编号
    private Long startTime; //查询起始时间
    private Long endTime;   //查询结束时间
    private Integer settle; //是否已经结账

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

    public Long getStartTime() {
        return startTime;
    }

    public OrderSearcher setStartTime(Long startTime) {
        this.startTime = startTime;
        return this;
    }

    public Long getEndTime() {
        return endTime;
    }

    public OrderSearcher setEndTime(Long endTime) {
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
