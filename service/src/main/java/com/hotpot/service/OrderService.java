package com.hotpot.service;

import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;
import com.hotpot.searcher.OrderSearcher;

import java.util.List;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface OrderService {
    void createOrder(Order order);

    List<Order> getOrdersByStoreId(Integer storeId);

    ValueCard payByCard(Order order, String cardId, String cardUuid);

    ValueCard payByPhone(Order order, String phone, String password);

    Order pay(Order order);

    List<Order> getOrdersBySearcher(OrderSearcher searcher);
}
