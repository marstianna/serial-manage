package com.hotpot.service;

import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;

import java.util.List;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface OrderService {
    List<Order> getOrdersByStoreId(Integer storeId);

    List<Order> getUnsettleOrderByOrderId(Integer storeId);

    ValueCard pay(Order order);
}
