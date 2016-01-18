package com.hotpot.view;

/**
 * Created by zoupeng on 16/1/18.
 */
public interface ViewTransform<R,V> {
    V transform(R origin);
}
