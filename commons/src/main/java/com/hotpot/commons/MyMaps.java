package com.hotpot.commons;

import com.hotpot.commons.function.FunctionForListToMap;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;

/**
 * Created by zoupeng on 16/1/5.
 */
public final class MyMaps  {
    public static <K,V> Map<K,V> transform(List<? extends Object> lists, FunctionForListToMap<K,V> function){
        Map<K,V> results = new HashedMap();
        for(Object obj : lists){
            Pair<K,V> pair = function.transfomr(obj);
            results.put(pair.getKey(),pair.getValue());
        }
        return results;
    }
}
