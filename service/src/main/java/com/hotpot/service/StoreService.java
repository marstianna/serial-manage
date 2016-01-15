package com.hotpot.service;

import com.hotpot.domain.Staff;
import com.hotpot.domain.Store;
import com.hotpot.domain.StoreTable;

import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface StoreService {
    /**
     *  添加一个员工
     * @param staff
     */
    void addStaff(Staff staff);

    /**
     *
     * @param storeId
     * @return
     */
    List<Staff> getStaffByStoreId(Integer storeId);

    /**
     * 获取所有店铺
     * @return
     */
    List<Store> getAllStores();

    /**
     *
     * @return
     */
    Map<Integer,String> getStoreMap();

    List<StoreTable> getAllTablesByStoreId(Integer storeId);

    boolean login(String loginName,String loginPassword);

    Store getStoreByStoreId(Integer storeId);
}
