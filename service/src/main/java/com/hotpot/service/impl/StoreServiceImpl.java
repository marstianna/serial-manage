package com.hotpot.service.impl;

import com.hotpot.dao.StaffMapper;
import com.hotpot.dao.StoreMapper;
import com.hotpot.domain.Staff;
import com.hotpot.domain.Store;
import com.hotpot.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zoupeng on 15/12/30.
 */
@Service
public class StoreServiceImpl implements StoreService{
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    StaffMapper staffMapper;

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
}
