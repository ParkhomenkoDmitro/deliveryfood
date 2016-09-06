package com.parkhomenko.common.domain.special_types;

import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Dmytro Parkhomenko
 * Created on 09.08.16.
 */

public interface MonetaryAmount extends Comparable<MonetaryAmount> {
    BigDecimal getValue();
    Currency getCurrency();
    MonetaryAmount add(MonetaryAmount monetaryAmount);
}
