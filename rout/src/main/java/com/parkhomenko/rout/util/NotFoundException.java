package com.parkhomenko.rout.util;

/**
 * @author Dmytro Parkhomenko
 *         Created on 25.08.16.
 */

public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
