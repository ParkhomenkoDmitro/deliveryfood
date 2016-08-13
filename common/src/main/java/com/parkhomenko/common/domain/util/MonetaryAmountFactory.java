package com.parkhomenko.common.domain.util;

/**
 * Created by dmytro on 13.08.16.
 */
public class MonetaryAmountFactory {
    public static MonetaryAmount create() {
        return new DecimalMonetaryAmount();
    }

    public static MonetaryAmount ZERO = new DecimalMonetaryAmount();

}
