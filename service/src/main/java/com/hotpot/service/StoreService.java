package com.hotpot.service;

import com.hotpot.domain.RuntimeTable;
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

    List<RuntimeTable> getRuntimeTablesByStoreId(Integer storeId);

    boolean login(String loginName,String loginPassword);

    List<Store> getStoreByLoginInfo(String loginName, String loginPassword);

    Store getStoreByStoreId(Integer storeId);

    void takeSeat(String tableCode, Integer storeId, Integer count);

    void createOrderForRuntimeTable(String tableCode,Integer orderId);

    RuntimeTable getRuntimeTable(Integer storeId,String tableCode);

    Integer createTables(Integer from ,Integer to ,String code,Integer countOfPeople);
}
