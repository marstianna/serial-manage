package com.hotpot.service;

import com.hotpot.commons.DateTool;
import com.hotpot.domain.Staff;
import junit.framework.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zoupeng on 16/1/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-all.xml")
public class StoreServiceTest {
    @Autowired
    StoreService storeService;

    @Test
    public void testAddStaff() throws Exception {
        Staff staff = new Staff();
        staff.setIdentity("513099199004040303");
        staff.setCreateTime(DateTool.unixTime());
        staff.setMobilephone("13502940294");
        staff.setEmail("41414@qq.com");
        staff.setName("XXX");
        staff.setStaffLevel(1);
        storeService.addStaff(staff);
    }

    @Test
    public void testGetStaffByStoreId() throws Exception {
        List<Staff> staffList = storeService.getStaffByStoreId(1);
        Assert.assertTrue(staffList.size() == 1);
    }
}