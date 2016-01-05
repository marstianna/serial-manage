package com.hotpot.commons.function;

import org.apache.commons.lang3.tuple.Pair;

/**
 * Created by zoupeng on 16/1/5.
 */
public interface FunctionForListToMap<K,V> {
    Pair<K,V> transfomr(Object obj);
}
