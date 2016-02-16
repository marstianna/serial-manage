package com.hotpot.vo;

import com.hotpot.commons.DateTool;
import com.hotpot.domain.Owner;
import com.hotpot.domain.Store;

/**
 * Created by zoupeng on 16/2/7.
 */
public class NewStoreVo {
    private String ownerName;
    private String mobilephone;
    private String identity;
    private String email;
    private String loginName;
    private String loginPassword;
    private String storeName;
    private String address;
    private String phone;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getMobilephone() {
        return mobilephone;
    }

    public void setMobilephone(String mobilephone) {
        this.mobilephone = mobilephone;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Owner getOwner(){
        Owner owner = new Owner();
        owner.setCreateTime(DateTool.getDateTime());
        owner.setPhone(phone);
        owner.setEmail(email);
        owner.setMobilephone(mobilephone);
        owner.setIdentity(identity);
        owner.setLoginName(loginName);
        owner.setLoginPassword(loginPassword);
        owner.setName(ownerName);
        return owner;
    }

    public Store getStore(){
        Store store = new Store();
        store.setStoreName(storeName);
        store.setAddress(address);
        store.setCreateTime(DateTool.getDateTime());
        store.setPhone(phone);
        return store;
    }

}
