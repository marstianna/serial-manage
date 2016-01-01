package com.hotpot.service.impl;

import com.hotpot.commons.DateTool;
import com.hotpot.dao.PromotionMapper;
import com.hotpot.domain.Order;
import com.hotpot.domain.Promotion;
import com.hotpot.service.PromotionService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zoupeng on 15/12/30.
 */
@Service
public class PromotionServiceImpl implements PromotionService{
    @Autowired
    PromotionMapper promotionMapper;

    @Override
    public void promotion(Order order) {
        List<Promotion> promotions = promotionMapper.getAllPromotions(DateTool.unixTime());
        if(CollectionUtils.isEmpty(promotions)){
            order.setActualPrice(order.getPaperPrice());
            return;
        }
    }

    @Override
    public void addPromotion(Promotion promotion) {
        promotionMapper.insert(promotion);
    }
}
