package com.hotpot.service.impl;

import com.hotpot.dao.AdminMapper;
import com.hotpot.dao.OwnerMapper;
import com.hotpot.dao.StoreMapper;
import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;
import com.hotpot.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zoupeng on 15/12/30.
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    StoreMapper storeMapper;
    @Autowired
    OwnerMapper ownerMapper;
    @Autowired
    AdminMapper adminMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void addStoreAndOwner(Store store,Owner owner){
        newOwner(owner);

        store.setOwnerId(store.getOwnerId());
        newStore(store);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void newStore(Store store) {
        storeMapper.insertSelective(store);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void newOwner(Owner owner) {
        ownerMapper.insertSelective(owner);
    }

    @Override
    public boolean login(String loginName, String loginPassword) {
        Integer count = adminMapper.login(loginName,loginPassword);
        return count > 0;
    }

    @Override
    public Owner getOwnerById(Integer ownerId) {
        return ownerMapper.selectByPrimaryKey(ownerId);
    }
}
