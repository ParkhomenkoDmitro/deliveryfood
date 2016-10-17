package com.parkhomenko.persistence.dao.util;

/**
 * @author Dmytro Parkhomenko
 *         Created on 30.09.16.
 */

public class LoggingMessage {

    public static String logMessage(Long id) {
        return "Entity with id = " + id + " not found in db";
    }
}
