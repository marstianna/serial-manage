package com.hotpot.domain;

/**
 * Created by zoupeng on 16/1/21.
 */
public class RuntimeTable {
    private Integer id;
    private Integer storeId;
    private String tableCode;
    private Integer peopleCount = 0;
    private String createTime;
    private Integer isQueueUp;

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

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public Integer getPeopleCount() {
        return peopleCount;
    }

    public void setPeopleCount(Integer peopleCount) {
        this.peopleCount = peopleCount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getIsQueueUp() {
        return isQueueUp;
    }

    public void setIsQueueUp(Integer isQueueUp) {
        this.isQueueUp = isQueueUp;
    }
}
