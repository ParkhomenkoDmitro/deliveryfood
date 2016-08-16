package com.parkhomenko.common.domain.util;

import java.math.BigDecimal;

/**
 * Created by dmytro on 13.08.16.
 */
public class DecimalMonetaryAmount implements MonetaryAmount<BigDecimal> {

    @Override
    public boolean equals(MonetaryAmount<BigDecimal> monetaryAmount) {
        return false;
    }

    @Override
    public MonetaryAmount<BigDecimal> multiply(Double coef) {
        return null;
    }

    @Override
    public MonetaryAmount<BigDecimal> subtraction(MonetaryAmount<BigDecimal> monetaryAmount) {
        return null;
    }

    @Override
    public MonetaryAmount<BigDecimal> ZERO() {
        return null;
    }

    @Override
    public MonetaryAmount<BigDecimal> add(MonetaryAmount<BigDecimal> monetaryAmount) {
        return null;
    }

    @Override
    public BigDecimal getValue() {
        return null;
    }

    @Override
    public int compareTo(MonetaryAmount<BigDecimal> o) {
        return 0;
    }
}
