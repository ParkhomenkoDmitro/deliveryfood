package com.parkhomenko.common.domain.util;

import com.parkhomenko.common.domain.special_types.DecimalMonetaryAmount;
import com.parkhomenko.common.domain.special_types.MonetaryAmount;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

public class MonetaryAmountFactory {
    public static MonetaryAmount getUSDZeroMonetaryAmount() {
        return new DecimalMonetaryAmount(BigDecimal.ZERO, Currency.getInstance("USD"));
    }
}
