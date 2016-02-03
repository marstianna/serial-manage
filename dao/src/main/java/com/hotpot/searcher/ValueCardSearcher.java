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

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getVipId() {
        return vipId;
    }

    public void setVipId(String vipId) {
        this.vipId = vipId;
    }

    public Integer getStartTimeForCreate() {
        return startTimeForCreate;
    }

    public void setStartTimeForCreate(Integer startTimeForCreate) {
        this.startTimeForCreate = startTimeForCreate;
    }

    public Integer getStartTimeForModified() {
        return startTimeForModified;
    }

    public void setStartTimeForModified(Integer startTimeForModified) {
        this.startTimeForModified = startTimeForModified;
    }

    public Integer getEndTimeForCreate() {
        return endTimeForCreate;
    }

    public void setEndTimeForCreate(Integer endTimeForCreate) {
        this.endTimeForCreate = endTimeForCreate;
    }

    public Integer getEndTimeForModified() {
        return endTimeForModified;
    }

    public void setEndTimeForModified(Integer endTimeForModified) {
        this.endTimeForModified = endTimeForModified;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
