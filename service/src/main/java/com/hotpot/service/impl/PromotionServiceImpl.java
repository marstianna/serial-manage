package com.hotpot.service.impl;

import com.hotpot.commons.Const;
import com.hotpot.commons.DateTool;
import com.hotpot.dao.PromotionMapper;
import com.hotpot.domain.Order;
import com.hotpot.domain.Promotion;
import com.hotpot.searcher.PromotionSearcher;
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
    public Promotion getPromotionById(Integer id) {
        return promotionMapper.getPromotionById(id);
    }

    @Override
    public void promotion(Order order) {
        List<Promotion> promotions = promotionMapper.getAllPromotions(DateTool.unixTime());
        order.setActualPrice(order.getPaperPrice());
        if(CollectionUtils.isEmpty(promotions)){
            return;
        }
        for(Promotion promotion : promotions){
            if(checkIsShoted(order,promotion)) {
                switch (promotion.getType()) {
                    case Const.PROMOTION_TYPE_MINUS:
                        order.setActualPrice(order.getPaperPrice() - promotion.getDiscount());
                        break;
                    case Const.PROMOTION_TYPE_DISCOUNT:
                        order.setActualPrice(order.getPaperPrice() * promotion.getDiscount());
                        break;
                }
            }
        }
    }

    @Override
    public void addPromotion(Promotion promotion) {
        promotionMapper.insert(promotion);
    }

    @Override
    public List<Promotion> getAllPromotionsBySearcher(PromotionSearcher searcher) {
        return null;
    }

    private boolean checkIsShoted(Order order, Promotion promotion){
        return order.getPayType().intValue() == promotion.getPayType().intValue()
                && (order.getStoreId().intValue() == promotion.getStoreId().intValue() || order.getStoreId() == Const.ALL)
                && order.getPaperPrice().intValue() >= promotion.getEnough().intValue();
    }
}
