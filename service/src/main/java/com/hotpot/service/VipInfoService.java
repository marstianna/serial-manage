package com.hotpot.service;

import com.hotpot.domain.VipInfo;

import java.util.List;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface VipInfoService {
    /**
     * 添加一个 vip
     * @param vip
     */
    void addVip(VipInfo vip);

    /**
     * 获取所有 vip 信息
     * @return
     */
    List<VipInfo> getAllVips();

    /**
     * 根据门店 id 获取该门店下创建的所有VIP 信息
     * @param storeId
     * @return
     */
    List<VipInfo> getVipsByStoreId(Integer storeId);

    /**
     * 根据注册是提供的电话号码获取 vip 信息
     * @param mobilephone
     * @return
     *
     */
    VipInfo getVipInfoByMobilephone(String mobilephone);

    VipInfo getVipInfoById(Integer id);
}
