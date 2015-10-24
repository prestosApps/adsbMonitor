package com.prestos.adsbmonitor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by prestos on 24/10/2015.
 */
public class Cache {

    private static Map<String, Object> cacheMap = new HashMap<String, Object>();

    private Cache() {

    }

    public static void setCacheItem(String key, Object object) {
        cacheMap.put(key, object);
    }

    public static Object getCacheItem(String key) {
        return cacheMap.get(key);
    }
}
