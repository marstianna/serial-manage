package com.hotpot.service.impl;

import com.hotpot.commons.DateTool;
import com.hotpot.dao.*;
import com.hotpot.domain.*;
import com.hotpot.service.Context;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 15/12/30.
 */
@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    StaffMapper staffMapper;
    @Autowired
    StoreTableMapper storeTableMapper;
    @Autowired
    OwnerMapper ownerMapper;
    @Autowired
    RuntimeTableMapper runtimeTableMapper;

    @Override
    public void addStaff(Staff staff) {
        staffMapper.insertSelective(staff);
    }

    @Override
    public List<Staff> getStaffByStoreId(Integer storeId) {
        return staffMapper.getStaffByStoreId(storeId);
    }

    @Override
    public List<Store> getAllStores() {
        return storeMapper.getAllStores();
    }

    @Override
    public Map<Integer, String> getStoreMap() {
        List<Store> stores= getAllStores();
        Map<Integer,String> results = new HashMap<>();
        stores.stream()
                .forEach((store)->
            results.putIfAbsent(store.getId(),store.getStoreName())
        );
        return results;
    }

    @Override
    public List<StoreTable> getAllTablesByStoreId(Integer storeId) {
        return storeTableMapper.getTablesByStoreId(storeId);
    }

    @Override
    public List<RuntimeTable> getRuntimeTablesByStoreId(Integer storeId){
        return runtimeTableMapper.getRuntimeTablesByStoreId(storeId);
    }

    @Override
    public boolean login(String loginName, String loginPassword) {
        return ownerMapper.login(loginName,loginPassword) > 0;
    }

    @Override
    public List<Store> getStoreByLoginInfo(String loginName, String loginPassword){
        Owner owner = ownerMapper.getOwner(loginName, loginPassword);
        return storeMapper.getStoresByOwnerId(owner.getId());
    }

    @Override
    public Store getStoreByStoreId(Integer storeId) {
        return storeMapper.selectByPrimaryKey(storeId);
    }

    @Override
    public void takeSeat(String tableCode,Integer storeId,Integer count){
        RuntimeTable runtimeTable = new RuntimeTable();
        runtimeTable.setStoreId(storeId);
        runtimeTable.setCreateTime(DateTool.getDateTime());
        runtimeTable.setPeopleCount(count);
        runtimeTable.setTableCode(tableCode);
        runtimeTableMapper.haveASeat(runtimeTable);
    }

    @Override
    public void createOrderForRuntimeTable(String tableCode, Integer orderId) {
        Integer storeId = Context.get().getId();
        Integer count = runtimeTableMapper.updateOrderId(orderId,tableCode,storeId);
        if(count == 0){
            throw new RuntimeException("更新餐桌的订单号失败,有可能是当前餐桌已经结账");
        }
    }

    @Override
    public RuntimeTable getRuntimeTable(Integer storeId, String tableCode) {
        return null;
    }
}
