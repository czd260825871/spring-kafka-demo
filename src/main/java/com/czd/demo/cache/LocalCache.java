package com.czd.demo.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author admin
 */
public class LocalCache {

    private static final Map<String, Boolean> CACHE = new ConcurrentHashMap<>();


    public static Boolean get(String key){
        Boolean value = CACHE.get(key);
        return null == value ? false : value;
    }



    public static void put(String key, Boolean value){
        CACHE.put(key, value);
    }
}
