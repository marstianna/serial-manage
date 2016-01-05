package com.hotpot.entity;

import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 16/1/5.
 */
public class SettleResultEntity {
    private Map<String,List<Integer>> results;
    private Integer needToPay;

    public Map<String, List<Integer>> getResults() {
        return results;
    }

    public void setResults(Map<String, List<Integer>> results) {
        this.results = results;
    }

    public Integer getNeedToPay() {
        return needToPay;
    }

    public void setNeedToPay(Integer needToPay) {
        this.needToPay = needToPay;
    }
}
