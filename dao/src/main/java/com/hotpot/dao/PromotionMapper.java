package com.hotpot.dao;

import com.hotpot.domain.Promotion;
import com.hotpot.searcher.PromotionSearcher;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by zoupeng on 15/12/30.
 */
@Repository
public interface PromotionMapper {
    void insert(Promotion promotion);

    List<Promotion> getAllPromotions(Long currentTime);

    List<Promotion> getAllPromotionsBySearcher(PromotionSearcher searcher);

    Promotion getPromotionById(Integer id);
}
