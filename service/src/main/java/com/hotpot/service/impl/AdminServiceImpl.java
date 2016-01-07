package com.hotpot.service.impl;

import com.hotpot.commons.DateTool;
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

import java.util.Map;

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
    public void addStoreAndOwner(Map<String, Object> params){
        String name,phone,mobilephone,loginName,loginPassword,identity,email,address,storeName;

        name = String.valueOf(params.get("name"));
        phone = String.valueOf(params.get("phone"));
        mobilephone = String.valueOf(params.get("mobilephone"));
        loginName = String.valueOf(params.get("loginName"));
        loginPassword = String.valueOf(params.get("loginPassword"));
        identity = String.valueOf(params.get("identity"));
        email = String.valueOf(params.get("email"));
        address = String.valueOf(params.get("address"));
        storeName = String.valueOf(params.get("storeName"));

        Owner owner = new Owner();
        owner.setPhone(phone);
        owner.setMobilephone(mobilephone);
        owner.setLoginPassword(loginPassword);
        owner.setLoginName(loginName);
        owner.setCreateTime(DateTool.unixTime());
        owner.setIdentity(identity);
        owner.setEmail(email);
        owner.setName(name);
        newOwner(owner);

        Store store = new Store();
        store.setAddress(address);
        store.setCreateTime(DateTool.unixTime());
        store.setOwnerId(owner.getId());
        store.setStoreName(storeName);
        store.setPhone(phone);
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
}
