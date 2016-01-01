package com.hotpot.service;

import com.hotpot.domain.Points;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface PointsService {
    /**
     * 给 vipId 新增积分
     * @param vipId
     * @param type  积分类型
     * @param balancePoints
     */
    void insertOrUpdatePointRecord(Integer vipId,Integer type,Integer balancePoints);

    /**
     * 使用积分兑换礼物
     * @param giftId
     * @param vipId
     * @return
     */
    boolean getGiftByPoints(Integer giftId,Integer vipId);

    /**
     * 根据 vipId 获取积分详情
     * @param vipId
     * @return
     */
    Points getPointsByVipInfo(Integer vipId);
}
