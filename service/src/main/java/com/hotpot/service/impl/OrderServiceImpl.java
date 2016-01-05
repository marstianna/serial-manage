package com.hotpot.service.impl;

import com.hotpot.commons.Const;
import com.hotpot.dao.OrderMapper;
import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.OrderService;
import com.hotpot.service.PromotionService;
import com.hotpot.service.ValueCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zoupeng on 15/12/26.
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    PromotionService promotionService;
    @Autowired
    ValueCardService valueCardService;
    @Autowired
    OrderMapper orderMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    public void createOrder(Order order) {
        orderMapper.insertSelective(order);
    }

    @Override
    public List<Order> getOrdersByStoreId(Integer storeId) {
        return getOrdersBySearcher(new OrderSearcher().setStoreId(storeId));
    }

    @Override
    public List<Order> getUnsettleOrderByStoreId(Integer storeId) {
        return getOrdersBySearcher(new OrderSearcher().setStoreId(storeId)
                                                    .setSettle(Const.ORDER_UNSETTLE));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ValueCard payByCard(Order order, String cardId, String cardUuid) {
        promotionService.promotion(order);
        createOrder(order);
//        throw new Exception();
        return valueCardService.payment(cardId, cardUuid, order.getStoreId(), order.getActualPrice(), order.getPaperPrice());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public Order pay(Order order){
        promotionService.promotion(order);
        createOrder(order);
        return order;
    }

    @Override
    public List<Order> getOrdersBySearcher(OrderSearcher searcher) {
        return orderMapper.getOrderBySearcher(searcher);
    }
}
