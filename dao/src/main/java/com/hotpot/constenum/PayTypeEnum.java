package com.hotpot.constenum;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * Created by zoupeng on 16/1/5.
 */
public enum PayTypeEnum {
    //0储值卡,1现金,2支付宝,3微信,4团购优惠支付
    VALUE_CARD(0,"储值卡"),CASH(1,"现金"),ALIPAY(2,"支付宝"),WEIXIN(3,"微信"),TUAN(4,"团购优惠支付");
    private Integer key;
    private String value;

    PayTypeEnum(Integer _key,String _value){
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
        return ImmutableMap.of(VALUE_CARD.getKey(),VALUE_CARD.getValue(),
                CASH.getKey(),CASH.getValue(),
                ALIPAY.getKey(),ALIPAY.getValue(),
                WEIXIN.getKey(),WEIXIN.getValue(),
                TUAN.getKey(),TUAN.getValue());
    }
}
