package com.hotpot.service.impl;

import com.hotpot.commons.Const;
import com.hotpot.commons.DateTool;
import com.hotpot.dao.PointsGiftMapper;
import com.hotpot.dao.PointsHistoryMapper;
import com.hotpot.dao.PointsMapper;
import com.hotpot.domain.Points;
import com.hotpot.domain.PointsGift;
import com.hotpot.domain.PointsHistory;
import com.hotpot.service.PointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    public void insertOrUpdatePointRecord(Integer vipId, Integer type, Integer balancePoints, Integer storeId) {
        Points points = new Points();
        points.setType(type);
        points.setVipId(vipId);
        points.setBalancePoints(balancePoints);
        pointsMapper.insertOrUpdatePoints(points);
        recordPointsHistory(storeId, Const.OPERATE_ADD,balancePoints,type,vipId);
    }

    @Override
    public void addGift(PointsGift gift){
        pointsGiftMapper.insertSelective(gift);
    }

    @Override
    public List<PointsGift> getAllGiftByStoreId(Integer storeId){
        return pointsGiftMapper.getAllGiftsByStoreId(storeId);
    }

    @Override
    public boolean getGiftByPoints(Integer giftId, Integer vipId) {
        PointsGift gift = pointsGiftMapper.selectByPrimaryKey(giftId);
        Points points = getPointsByVipInfo(vipId);
        boolean success = false;
        if(gift.getCost() <= points.getBalancePoints()){
            success = true;
        }
        return success;
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

    private void convert(){

    }

    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    private void recordPointsHistory(Integer storeId, Integer operate, Integer pointsAccount, Integer type, Integer vipId){
        PointsHistory pointsHistory = new PointsHistory();
        pointsHistory.setCreateTime(DateTool.unixTime());
        pointsHistory.setStoreId(storeId);
        pointsHistory.setOperate(operate);
        pointsHistory.setPointsAccount(pointsAccount);
        pointsHistory.setType(type);
        pointsHistory.setVipId(vipId);
        pointsHistoryMapper.insertSelective(pointsHistory);
    }
}
