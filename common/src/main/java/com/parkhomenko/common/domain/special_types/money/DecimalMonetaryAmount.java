package com.parkhomenko.common.domain.special_types.money;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Currency;

/**
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

public class DecimalMonetaryAmount implements MonetaryAmount, Serializable {

    private final BigDecimal value;
    private final Currency currency;

    public DecimalMonetaryAmount(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    @Override
    public BigDecimal getValue() {
        return value;
    }

    @Override
    public Currency getCurrency() {
        return currency;
    }

    @Override
    public MonetaryAmount add(MonetaryAmount monetaryAmount) {
        if(isCurrencyEqual(monetaryAmount.getCurrency())) {
            BigDecimal newValue = value.add(monetaryAmount.getValue());
            return new DecimalMonetaryAmount(newValue, currency);
        }

        //TODO: impl conversation
        return null;
    }

    @Override
    public MonetaryAmount multiply(int count) {
        BigDecimal newValue = value.multiply(BigDecimal.valueOf(count));
        return new DecimalMonetaryAmount(newValue, currency);
    }

    @Override
    public int compareTo(MonetaryAmount monetaryAmount) {
        if(isCurrencyEqual(monetaryAmount.getCurrency())) {
            return value.compareTo(monetaryAmount.getValue());
        }

        //TODO: impl conversation
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DecimalMonetaryAmount that = (DecimalMonetaryAmount) o;

        if (!value.equals(that.value)) return false;
        return isCurrencyEqual(that.currency);

    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + currency.hashCode();
        return result;
    }

    private boolean isCurrencyEqual(Currency currency) {
        return this.currency.getCurrencyCode().equals(currency.getCurrencyCode());
    }
}
