package com.hotpot.service.impl;

import com.hotpot.commons.MyMaps;
import com.hotpot.commons.function.FunctionForListToMap;
import com.hotpot.dao.StaffMapper;
import com.hotpot.dao.StoreMapper;
import com.hotpot.dao.StoreTableMapper;
import com.hotpot.domain.Staff;
import com.hotpot.domain.Store;
import com.hotpot.domain.StoreTable;
import com.hotpot.service.StoreService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 15/12/30.
 */
@Service
public class StoreServiceImpl implements StoreService{
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    StaffMapper staffMapper;
    @Autowired
    StoreTableMapper storeTableMapper;

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
        Map<Integer,String> results = MyMaps.transform(stores, new FunctionForListToMap<Integer, String>() {
            @Override
            public Pair<Integer, String> transfomr(Object obj) {
                Store store = (Store)obj;
                Pair<Integer,String> pair = ImmutablePair.of(store.getId(),store.getStoreName());
                return pair;
            }
        });
        return results;
    }

    @Override
    public List<StoreTable> getAllTablesByStoreId(Integer storeId) {
        return null;
    }
}
