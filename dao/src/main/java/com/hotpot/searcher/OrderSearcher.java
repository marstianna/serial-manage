package com.hotpot.searcher;

/**
 * Created by zoupeng on 15/12/26.
 */
public class OrderSearcher {
    private Integer id;     //订单号
    private Integer vipId;  //会员 ID
    private Integer payType;//支付方式
    private Integer storeId;//店铺 ID
    private String cardId; //储值卡编号
    private String startTime; //查询起始时间
    private String endTime;   //查询结束时间

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

    public String getCardId() {
        return cardId;
    }

    public OrderSearcher setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public OrderSearcher setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public OrderSearcher setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }
}
