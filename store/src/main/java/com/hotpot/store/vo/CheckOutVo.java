package com.hotpot.store.vo;

import com.hotpot.commons.DateTool;
import com.hotpot.domain.Order;

/**
 * Created by zoupeng on 16/1/29.
 */
public class CheckOutVo {
    private Integer foodPrice = 0;
    private Integer drinkPrice = 0;
    private Integer otherPrice = 0;
    private String tableCode;
    private Integer payType;
    private String cardId;
    private String cardUuid;
    private String phone;
    private String password;
    private Integer receive;

    public Integer getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(Integer foodPrice) {
        this.foodPrice = foodPrice;
    }

    public Integer getDrinkPrice() {
        return drinkPrice;
    }

    public void setDrinkPrice(Integer drinkPrice) {
        this.drinkPrice = drinkPrice;
    }

    public Integer getOtherPrice() {
        return otherPrice;
    }

    public void setOtherPrice(Integer otherPrice) {
        this.otherPrice = otherPrice;
    }

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
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

    public String getCardUuid() {
        return cardUuid;
    }

    public void setCardUuid(String cardUuid) {
        this.cardUuid = cardUuid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getReceive() {
        return receive;
    }

    public void setReceive(Integer receive) {
        this.receive = receive;
    }

    public Order getOrder(){
        Order order = new Order();
        order.setTableCode(tableCode);
        order.setCreateTime(DateTool.getDateTime());
        order.setPaperPrice(foodPrice+drinkPrice+otherPrice);
        order.setDrinkPrice(drinkPrice);
        order.setFoodPrice(foodPrice);
        order.setOtherPrice(otherPrice);
        order.setPayType(payType);
        order.setReceived(receive);
        return order;
    }
}
