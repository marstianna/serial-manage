package com.hotpot.searcher;

/**
 * Created by zoupeng on 16/1/5.
 */
public class ValueCardSearcher {
    private String cardId;
    private String vipId;
    private Integer startTimeForCreate;
    private Integer endTimeForCreate;
    private Integer startTimeForModified;
    private Integer endTimeForModified;
    private String startTime;
    private String endTime;
    private String phone;

    public String getCardId() {
        return cardId;
    }

    public ValueCardSearcher setCardId(String cardId) {
        this.cardId = cardId;
        return this;
    }

    public String getVipId() {
        return vipId;
    }

    public ValueCardSearcher setVipId(String vipId) {
        this.vipId = vipId;
        return this;
    }

    public Integer getStartTimeForCreate() {
        return startTimeForCreate;
    }

    public ValueCardSearcher setStartTimeForCreate(Integer startTimeForCreate) {
        this.startTimeForCreate = startTimeForCreate;
        return this;
    }

    public Integer getStartTimeForModified() {
        return startTimeForModified;
    }

    public ValueCardSearcher setStartTimeForModified(Integer startTimeForModified) {
        this.startTimeForModified = startTimeForModified;
        return this;
    }

    public Integer getEndTimeForCreate() {
        return endTimeForCreate;
    }

    public ValueCardSearcher setEndTimeForCreate(Integer endTimeForCreate) {
        this.endTimeForCreate = endTimeForCreate;
        return this;
    }

    public Integer getEndTimeForModified() {
        return endTimeForModified;
    }

    public ValueCardSearcher setEndTimeForModified(Integer endTimeForModified) {
        this.endTimeForModified = endTimeForModified;
        return this;
    }

    public String getStartTime() {
        return startTime;
    }

    public ValueCardSearcher setStartTime(String startTime) {
        this.startTime = startTime;
        return this;
    }

    public String getEndTime() {
        return endTime;
    }

    public ValueCardSearcher setEndTime(String endTime) {
        this.endTime = endTime;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public ValueCardSearcher setPhone(String phone) {
        this.phone = phone;
        return this;
    }
}
