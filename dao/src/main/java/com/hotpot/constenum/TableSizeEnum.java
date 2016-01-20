package com.hotpot.constenum;

/**
 * Created by zoupeng on 16/1/20.
 */
public enum TableSizeEnum {
    SMALL(4,"A"),BIG(8,"B");
    String code;
    Integer count;

    TableSizeEnum(Integer count,String code) {
        this.code = code;
        this.count = count;
    }

    public String getCode() {
        return code;
    }

    public Integer getCount() {
        return count;
    }
}
