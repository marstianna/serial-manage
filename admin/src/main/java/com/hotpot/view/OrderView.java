package com.hotpot.view;

import com.hotpot.domain.Order;

/**
 * Created by zoupeng on 16/1/17.
 */
public class OrderView {
    private Order order;
    private String vipName;
    private String storeName;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getVipName() {
        return vipName;
    }

    public void setVipName(String vipName) {
        this.vipName = vipName;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
}
