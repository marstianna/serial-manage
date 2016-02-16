package com.hotpot.service;

import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface AdminService {
    void addStoreAndOwner(Store store , Owner owner);

    void newStore(Store store);

    void newOwner(Owner owner);

    boolean login(String loginName,String loginPassword);

    Owner getOwnerById(Integer ownerId);
}
