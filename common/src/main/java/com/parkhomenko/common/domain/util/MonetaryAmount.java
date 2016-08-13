package com.parkhomenko.common.domain.util;

/**
 * Created by dmytro on 09.08.16.
 */
public interface MonetaryAmount extends Comparable<MonetaryAmount> {
    boolean equals(Object o);
    MonetaryAmount multiply(Double coef);
    MonetaryAmount subtraction(MonetaryAmount monetaryAmount);
    MonetaryAmount ZERO();
    MonetaryAmount add(MonetaryAmount monetaryAmount);
}
