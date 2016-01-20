package com.hotpot.service;

import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;

import java.util.Map;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface AdminService {
    void addStoreAndOwner(Map<String, Object> params);

    void newStore(Store store);

    void newOwner(Owner owner);

    boolean login(String loginName,String loginPassword);

    Owner getOwnerById(Integer ownerId);
}
