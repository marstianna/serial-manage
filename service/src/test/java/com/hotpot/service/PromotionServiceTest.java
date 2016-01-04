package com.hotpot.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoupeng on 16/1/2.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring-all.xml")
public class PromotionServiceTest {
    @Autowired
    PromotionService promotionService;
    @Test
    public void testPromotion() throws Exception {

    }

    @Test
    public void testAddPromotion() throws Exception {

    }
}