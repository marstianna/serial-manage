package com.hotpot.service.impl;

import com.hotpot.dao.OrderMapper;
import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;
import com.hotpot.searcher.OrderSearcher;
import com.hotpot.service.OrderService;
import com.hotpot.service.PromotionService;
import com.hotpot.service.StoreService;
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
    @Autowired
    StoreService storeService;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ValueCard payByCard(Order order, String cardId, String cardUuid) {
        promotionService.promotion(order);
        ValueCard card = valueCardService.getCardBalanceByCardUniqueKey(cardId,cardUuid);
        if (card == null){
            throw new RuntimeException("不存在对应的卡号");
        }
        if(card.getBalance() < order.getActualPrice()){
            throw new RuntimeException("余额不足,请充值");
        }
        order.setCardId(card.getCardId());
        createOrder(order);
        storeService.clearTable(order.getStoreId(),order.getTableCode());
        return valueCardService.payment(cardId, cardUuid, order.getStoreId(), order.getActualPrice(), order.getPaperPrice());
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public Order pay(Order order){
        promotionService.promotion(order);
        createOrder(order);
        storeService.clearTable(order.getStoreId(),order.getTableCode());
        return order;
    }

    @Override
    public List<Order> getOrdersBySearcher(OrderSearcher searcher) {
        return orderMapper.getOrderBySearcher(searcher);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ValueCard payByPhone(Order order, String phone, String password) {
        promotionService.promotion(order);
        ValueCard card = valueCardService.getCardByPhoneAndPassword(phone,password);
        if (card == null){
            throw new RuntimeException("不存在对应的卡号");
        }
        if(card.getBalance() < order.getActualPrice()){
            throw new RuntimeException("余额不足,请充值");
        }
        order.setCardId(card.getCardId());
        createOrder(order);
        storeService.clearTable(order.getStoreId(),order.getTableCode());
        return valueCardService.paymentWithPassword(phone, password, order.getStoreId(), order.getActualPrice(), order.getPaperPrice());
    }
}
