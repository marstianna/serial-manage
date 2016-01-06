package com.hotpot.service;

import com.hotpot.domain.Order;
import com.hotpot.domain.Promotion;
import com.hotpot.searcher.PromotionSearcher;

import java.util.List;

/**
 * Created by zoupeng on 15/12/26.
 */

public interface PromotionService {
    Promotion getPromotionById(Integer id);

    void promotion(Order order);

    void addPromotion(Promotion promotion);

    List<Promotion> getAllPromotionsBySearcher(PromotionSearcher searcher);
}
