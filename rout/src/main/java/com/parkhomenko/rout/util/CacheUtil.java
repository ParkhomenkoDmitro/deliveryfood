package com.parkhomenko.rout.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Dmytro Parkhomenko
 *         Created on 28.09.16.
 */

public class CacheUtil {
    public static String getCacheKey(HttpServletRequest req) {
        return req.getRequestedSessionId() + req.getRequestURI();
    }
}
