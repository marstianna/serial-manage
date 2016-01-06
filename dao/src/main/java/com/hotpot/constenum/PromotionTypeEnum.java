package com.hotpot.constenum;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by zoupeng on 16/1/6.
 */
public enum PromotionTypeEnum {
    PROMOTION_MINUS(1,"满减"),PROMOTION_DISCOUNT(2,"满折");

    private Integer key;
    private String value;

    PromotionTypeEnum(Integer _key,String _value) {
        this.key = _key;
        this.value = _value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static Map<Integer,String> getMap(){
        return ImmutableMap.of(PROMOTION_MINUS.getKey(),PROMOTION_MINUS.getValue(),PROMOTION_DISCOUNT.getKey(),PROMOTION_DISCOUNT.getValue());
    }
}
