package com.hotpot.store.view;

import com.hotpot.commons.ApplicationContextUtil;
import com.hotpot.domain.Order;
import com.hotpot.domain.VipInfo;
import com.hotpot.service.StoreService;
import com.hotpot.service.VipInfoService;
import com.hotpot.service.impl.StoreServiceImpl;
import com.hotpot.service.impl.VipInfoServiceImpl;

/**
 * Created by zoupeng on 16/1/17.
 */
public class OrderView {
    private Order order;
    private String vipName;
    private String storeName;

    private static StoreService storeService = ApplicationContextUtil.getBean(StoreServiceImpl.class);
    private static VipInfoService vipInfoService = ApplicationContextUtil.getBean(VipInfoServiceImpl.class);

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

    public static OrderView transform(Order order){
        OrderView orderView = new OrderView();
        orderView.setOrder(order);
        orderView.setStoreName(storeService.getStoreByStoreId(order.getStoreId()).getStoreName());
        VipInfo vipInfo = vipInfoService.getVipInfoById(order.getVipId());
        orderView.setVipName(vipInfo.getName()+"("+vipInfo.getId()+")");
        return orderView;
    }
}
