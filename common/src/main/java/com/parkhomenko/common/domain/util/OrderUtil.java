package com.parkhomenko.common.domain.util;

import com.parkhomenko.common.domain.Order;
import com.parkhomenko.common.domain.discount.CalculateResultPriceType;
import com.parkhomenko.common.domain.discount.DiscountFetcher;
import com.parkhomenko.common.domain.discount.DiscountTypeOne;
import com.parkhomenko.common.domain.discount.DiscountTypeTwo;

/**
 * Created by dmytro on 12.08.16.
 */
public class OrderUtil {
    public static void calcOrderPriceWithDiscounts(Order order, DiscountFetcher fetcher) {
        DiscountTypeOne discountTypeOne = new DiscountTypeOne();
        DiscountTypeTwo discountTypeTwo = new DiscountTypeTwo();
        CalculateResultPriceType calculateResultPriceType = new CalculateResultPriceType();

        discountTypeOne.setNextDiscount(discountTypeTwo);
        discountTypeTwo.setNextDiscount(calculateResultPriceType);

        discountTypeOne.setFetcher(fetcher);
        discountTypeTwo.setFetcher(fetcher);

        discountTypeOne.calculateDiscount(null, order);
    }
}
