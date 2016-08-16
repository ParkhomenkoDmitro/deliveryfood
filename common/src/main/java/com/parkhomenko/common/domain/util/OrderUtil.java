package com.parkhomenko.common.domain.util;

import com.parkhomenko.common.domain.Order;
import com.parkhomenko.common.domain.OrderProduct;
import com.parkhomenko.common.domain.discount.AbstractDiscount;
import com.parkhomenko.common.domain.discount.DiscountFetcher;
import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.common.domain.discount.DiscountTwo;

/**
 * Created by dmytro on 12.08.16.
 */
public class OrderUtil {
    public static void calcOrderPriceWithDiscounts(Order order, DiscountFetcher fetcher) {
        AbstractDiscount discountOne = new DiscountOne();
        AbstractDiscount discountTwo = new DiscountTwo();
        discountOne.setNextDiscount(discountTwo);
        discountOne.setFetcher(fetcher);
        discountTwo.setFetcher(fetcher);
        discountOne.calculateDiscount(null, order);
        order.getOrderProducts().forEach(OrderProduct::calculateTotalPrice);
    }
}
