package com.hotpot.service;

/**
 * Created by zoupeng on 15/12/26.
 */
public interface PointsService {
    void insertOrUpdatePointRecord(Integer vipId,Integer type,Integer balancePoints);

    boolean getGiftByPoints(Integer giftId,Integer vipId);
}
