package com.hotpot.service;

import com.hotpot.commons.DateTool;
import com.hotpot.domain.VipInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zoupeng on 16/1/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-all.xml")
public class VipInfoServiceTest {
    @Autowired
    VipInfoService vipInfoService;

    @Test
    public void testAddVip() throws Exception {
        VipInfo vipInfo = new VipInfo();
        vipInfo.setName("test_vip_name");
        vipInfo.setCreateTime(DateTool.unixTime());
        vipInfo.setEmail("123@qq.com");
        vipInfo.setMobilephone("13888888889");
        vipInfo.setStoreId(1);
        vipInfoService.addVip(vipInfo);
    }

    @Test
    public void testGetAllVips() throws Exception {
        List<VipInfo> vips = vipInfoService.getAllVips();
        Assert.assertTrue(vips.size() == 1);
    }

    @Test
    public void testGetVipsByStoreId() throws Exception {
        List<VipInfo> vips = vipInfoService.getVipsByStoreId(1);
        Assert.assertTrue(vips.size() == 1);
    }

    @Test
    public void testGetVipInfoByMobilephone() throws Exception {
        VipInfo vip = vipInfoService.getVipInfoByMobilephone("13888888889");
        Assert.assertTrue(vip.getId() == 1);
    }
}