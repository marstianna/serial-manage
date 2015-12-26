package com.hotpot.service.impl;

import com.google.common.collect.Lists;
import com.hotpot.dao.OrderMapper;
import com.hotpot.domain.Order;
import com.hotpot.domain.ValueCard;
import com.hotpot.service.OrderService;
import com.hotpot.service.PromotionService;
import com.hotpot.service.ValueCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Order> getOrdersByStoreId(Integer storeId) {
        return null;
    }

    @Override
    public List<Order> getUnsettleOrderByOrderId(Integer storeId) {
        return null;
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
    public ValueCard pay(Order order, String cardId, String cardUuid) {
        promotionService.promotion(order);
        return valueCardService.payment(cardId,cardUuid,order.getStoreId(),order.getActualPrice(),order.getPaperPrice());
    }
}
