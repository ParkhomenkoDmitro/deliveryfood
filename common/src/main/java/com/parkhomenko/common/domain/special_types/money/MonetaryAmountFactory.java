package com.parkhomenko.common.domain.special_types.money;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

public class MonetaryAmountFactory {
    public static MonetaryAmount ZERO = new DecimalMonetaryAmount(BigDecimal.ZERO, Currency.getInstance(CurrencyCode.USD));

    public static MonetaryAmount money(double price) {
        BigDecimal bigDecimal = new BigDecimal(price);
        return new DecimalMonetaryAmount(bigDecimal, Currency.getInstance(CurrencyCode.USD));
    }

    public static MonetaryAmount money(BigDecimal price, String currencyCode) {
        return new DecimalMonetaryAmount(price, Currency.getInstance(currencyCode));
    }
}
