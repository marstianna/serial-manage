package com.hotpot.service.impl;

import com.hotpot.dao.VipInfoMapper;
import com.hotpot.domain.VipInfo;
import com.hotpot.service.VipInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zoupeng on 15/12/30.
 */
@Service
public class VipInfoServiceImpl implements VipInfoService {
    @Autowired
    VipInfoMapper vipInfoMapper;

    @Override
    public void addVip(VipInfo vip) {
        vipInfoMapper.insertSelective(vip);
    }

    @Override
    public List<VipInfo> getAllVips() {
        return vipInfoMapper.getAllVips();
    }

    @Override
    public List<VipInfo> getVipsByStoreId(Integer storeId) {
        return vipInfoMapper.getVipsByStoreId(storeId);
    }

    @Override
    public VipInfo getVipInfoByMobilephone(String mobilephone) {
        return vipInfoMapper.getVipByMobilephone(mobilephone);
    }

    @Override
    public VipInfo getVipInfoById(Integer id) {
        return vipInfoMapper.selectByPrimaryKey(id);
    }
}
