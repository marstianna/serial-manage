package com.hotpot.service;

import com.hotpot.domain.Order;
import com.hotpot.domain.Promotion;

/**
 * Created by zoupeng on 15/12/26.
 */

public interface PromotionService {
    void promotion(Order order);

    void addPromotion(Promotion promotion);
}
