package com.parkhomenko.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Parkhomenko
 *         Created on 27.09.16.
 */

public final class SimpleInMemoryCache {

    private static Map<String, Object> map;

    public static void put(String key, Object value) {
        getMap().put(key, value);
    }

    public static Object get(String key) {
        return getMap().get(key);
    }

    public static void remove(String key) {
        getMap().remove(key);
    }

    private static Map<String, Object> getMap() {
        if(map == null) {
            map = Collections.synchronizedMap(new HashMap<String, Object>());
        }

        return map;
    }
}
