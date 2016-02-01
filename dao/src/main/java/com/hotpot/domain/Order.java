package com.hotpot.domain;

public class Order {

    private Integer id;
    private Integer storeId;
    private Integer payType;
    private String cardId;
    private Integer paperPrice;
    private Integer actualPrice;
    private Integer drinkPrice;
    private Integer foodPrice;
    private Integer otherPrice;
    private String createTime;
    private Integer queueUp;
    private String tableCode;
    private Integer received;
    private Integer countOfPeople;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getPaperPrice() {
        return paperPrice;
    }

    public void setPaperPrice(Integer paperPrice) {
        this.paperPrice = paperPrice;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(Integer actualPrice) {
        this.actualPrice = actualPrice;
    }

    public Integer getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(Integer drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public Integer getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Integer foodPrice) {
        this.foodPrice = foodPrice;
    }

    public Integer getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(Integer otherPrice) {
        this.otherPrice = otherPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getQueueUp() {
        return queueUp;
    }

    public void setQueueUp(Integer queueUp) {
        this.queueUp = queueUp;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Integer getCountOfPeople() {
        return countOfPeople;
    }

    public void setCountOfPeople(Integer countOfPeople) {
        this.countOfPeople = countOfPeople;
    }
}