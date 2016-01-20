package com.hotpot.entity;

/**
 * Created by zoupeng on 15/12/25.
 */
public class QueueUp {
    private Integer waitingNumber;
    private String phone;
    private String weChat;
    private String lineCode;            //排队编号
    private Integer frontCount;
    private String datetime;
    private String storeName;
    private String storePhone;

    public Integer getWaitingNumber() {
        return waitingNumber;
    }

    public void setWaitingNumber(Integer waitingNumber) {
        this.waitingNumber = waitingNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWeChat() {
        return weChat;
    }

    public void setWeChat(String weChat) {
        this.weChat = weChat;
    }

    public String getLineCode() {
        return lineCode;
    }

    public void setLineCode(String lineCode) {
        this.lineCode = lineCode;
    }

    public Integer getFrontCount() {
        return frontCount;
    }

    public void setFrontCount(Integer frontCount) {
        this.frontCount = frontCount;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStorePhone() {
        return storePhone;
    }

    public void setStorePhone(String storePhone) {
        this.storePhone = storePhone;
    }
}
