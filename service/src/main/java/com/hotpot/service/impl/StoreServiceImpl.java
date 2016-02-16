package com.hotpot.service.impl;

import com.google.common.base.Preconditions;
import com.hotpot.commons.DateTool;
import com.hotpot.dao.*;
import com.hotpot.domain.*;
import com.hotpot.service.Context;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    public void takeSeat(String tableCode,Integer storeId,Integer count,Integer isQueueUp){
        RuntimeTable runtimeTable = new RuntimeTable();
        runtimeTable.setStoreId(storeId);
        runtimeTable.setCreateTime(DateTool.getDateTime());
        runtimeTable.setPeopleCount(count);
        runtimeTable.setTableCode(tableCode);
        runtimeTable.setIsQueueUp(isQueueUp);
        runtimeTableMapper.haveASeat(runtimeTable);
    }

    @Override
    public boolean isExistRuntimeTable(String tableCode,Integer storeId) {
        Integer count = runtimeTableMapper.isExistRuntimeTable(tableCode, storeId);
        return count > 0;
    }

    @Override
    public RuntimeTable getRuntimeTable(Integer storeId, String tableCode) {
        return runtimeTableMapper.getRuntimeTableInfo(storeId, tableCode);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer createTables(Integer from, Integer to, String code, Integer countOfPeople) {
        Preconditions.checkArgument(from < to, "输入参数不合法,From,应该小于To");
        Integer storeId = Context.get().getId();
        int result = 0;
        for(int i = from;i<=to;i++,result++){
            StoreTable table = new StoreTable();
            table.setStoreId(storeId);
            table.setTableCode(code+i);
            table.setDefaultNumber(countOfPeople);
            storeTableMapper.insertSelective(table);
        }
        return result;
    }

    @Override
    public Integer changePassword(Integer ownerId, String oldPwd, String newPwd) {
        return ownerMapper.changePassword(ownerId, oldPwd, newPwd);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public Integer clearTable(Integer storeId, String tableCode){
        return runtimeTableMapper.delete(tableCode,storeId);
    }

    @Override
    public Integer deleteTable(String tableCode,Integer storeId){
        if(isExistRuntimeTable(tableCode,storeId)) {
            throw new RuntimeException("当前桌子还存在客人就餐,暂时不能删除!");
        }
        return storeTableMapper.deleteTable(tableCode, storeId);
    }
}
