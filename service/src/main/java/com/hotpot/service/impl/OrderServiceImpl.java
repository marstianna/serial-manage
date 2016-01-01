package com.hotpot.service.impl;

import com.google.common.collect.Lists;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public List<Order> getUnsettleOrderByOrderId(Integer storeId) {
        return getOrdersBySearcher(new OrderSearcher().setStoreId(storeId)
                                                    .setSettle(Const.ORDER_UNSETTLE));
    }

    @Override
    public Map<String,List<Integer>> settleOrders(List<Integer> orderIds){
        Map<String,List<Integer>> result = new HashMap<>();
        String success = "success";
        String fail = "fail";
        result.put(success, Lists.newArrayList());
        result.put(fail,Lists.newArrayList());
        for(Integer orderId: orderIds){
            try {
                Integer count = orderMapper.settleOrder(orderId);
                if (count == 0){
                    result.get(fail).add(orderId);
                }else{
                    result.get(success).add(orderId);
                }
            }catch(Exception e){
                //TODO log
                result.get(fail).add(orderId);
            }
        }
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public ValueCard pay(Order order, String cardId, String cardUuid) {
        promotionService.promotion(order);
        createOrder(order);
//        throw new Exception();
        return valueCardService.payment(cardId, cardUuid, order.getStoreId(), order.getActualPrice(), order.getPaperPrice());
    }

    @Override
    public List<Order> getOrdersBySearcher(OrderSearcher searcher) {
        return orderMapper.getOrderBySearcher(searcher);
    }
}
