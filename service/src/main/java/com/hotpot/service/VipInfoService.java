package com.hotpot.service;

import com.hotpot.domain.VipInfo;

import java.util.List;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface VipInfoService {
    void addVip(VipInfo vip);

    List<VipInfo> getAllVips();
}
