package com.hotpot.service;

import com.hotpot.domain.Store;

/**
 * Created by zoupeng on 16/1/20.
 */
public class Context {
    private static ThreadLocal<Store> storeInfo = new ThreadLocal<>();

     public static void set(Store store){
         storeInfo.set(store);
     }

    public static Store get(){
        return storeInfo.get();
    }

    public static void remove(){
        storeInfo.remove();
    }
}
