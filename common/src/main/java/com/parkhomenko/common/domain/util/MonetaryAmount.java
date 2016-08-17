package com.parkhomenko.common.domain.util;

/**
 * @author Dmytro Parkhomenko
 * Created on 09.08.16.
 */

public interface MonetaryAmount<T> extends Comparable<MonetaryAmount<T>> {
    boolean equals(MonetaryAmount<T> monetaryAmount);
    MonetaryAmount<T> multiply(Double coef);
    MonetaryAmount<T> subtraction(MonetaryAmount<T> monetaryAmount);
    MonetaryAmount<T> ZERO();
    MonetaryAmount<T> add(MonetaryAmount<T> monetaryAmount);
    T getValue();
}
