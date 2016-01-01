package com.hotpot.service.impl;

import com.hotpot.dao.PointsGiftMapper;
import com.hotpot.dao.PointsHistoryMapper;
import com.hotpot.dao.PointsMapper;
import com.hotpot.domain.Points;
import com.hotpot.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zoupeng on 15/12/31.
 */
@Service
public class PointsServiceImpl implements PointsService {
    @Autowired
    PointsMapper pointsMapper;
    @Autowired
    PointsHistoryMapper pointsHistoryMapper;
    @Autowired
    PointsGiftMapper pointsGiftMapper;

    @Override
    public void insertOrUpdatePointRecord(Integer vipId, Integer type, Integer balancePoints) {
        Points points = new Points();
        points.setType(type);
        points.setVipId(vipId);
        points.setBalancePoints(balancePoints);
        pointsMapper.insertOrUpdatePoints(points);
    }

    @Override
    public boolean getGiftByPoints(Integer giftId, Integer vipId) {
        return false;
    }

    @Override
    public Points getPointsByVipInfo(Integer vipId){
        List<Points> allTypeOfPoints = pointsMapper.getPointsByVipInfo(vipId);
        Points result = new Points();
        result.setVipId(vipId);
        result.setBalancePoints(0);
        for(Points point : allTypeOfPoints){
            Integer p = point.getBalancePoints() + result.getBalancePoints();
            result.setBalancePoints(p);
        }
        return result;
    }
}
