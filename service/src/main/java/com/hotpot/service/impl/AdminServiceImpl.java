package com.hotpot.service.impl;

import com.hotpot.dao.OwnerMapper;
import com.hotpot.dao.StoreMapper;
import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;
import com.hotpot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zoupeng on 15/12/30.
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    OwnerMapper ownerMapper;

    @Override
    public void newStore(Store store) {
        storeMapper.insertSelective(store);
    }

    @Override
    public void newOwner(Owner owner) {
        ownerMapper.insertSelective(owner);
    }
}
