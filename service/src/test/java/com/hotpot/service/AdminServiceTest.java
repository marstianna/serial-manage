package com.hotpot.service;

import com.hotpot.commons.DateTool;
import com.hotpot.domain.Owner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoupeng on 16/1/1.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-all.xml")
public class AdminServiceTest {
    @Autowired
    AdminService adminService;

    @Test
    public void testNewStore() throws Exception {

    }

    @Test
    public void testNewOwner() throws Exception {
        Owner owner = new Owner();
        owner.setCreateTime(DateTool.unixTime());
        owner.setEmail("111@qq.com");
        owner.setIdentity("513900199009090303");
        owner.setLoginName("name");
        owner.setLoginPassword("password");
        owner.setMobilephone("13888888888");
        owner.setPhone("023-80140524");
        adminService.newOwner(owner);
    }
}