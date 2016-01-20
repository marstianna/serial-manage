package com.hotpot.store.view;

import com.hotpot.commons.ApplicationContextUtil;
import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;
import com.hotpot.service.AdminService;
import com.hotpot.service.impl.AdminServiceImpl;

/**
 * Created by zoupeng on 16/1/19.
 */
public class StoreView {
    private Store store;
    private Owner owner;
    private static AdminService adminService = ApplicationContextUtil.getBean(AdminServiceImpl.class);

    public StoreView(Store store) {
        this.store = store;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public static StoreView transform(Store store){
        StoreView storeView = new StoreView(store);
        storeView.setOwner(adminService.getOwnerById(store.getOwnerId()));
        return storeView;
    }
}
