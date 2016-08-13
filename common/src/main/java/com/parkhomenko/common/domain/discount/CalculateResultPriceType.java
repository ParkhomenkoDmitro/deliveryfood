package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.*;
import com.parkhomenko.common.domain.util.MonetaryAmount;
import com.parkhomenko.common.domain.util.MonetaryAmountFactory;

import java.util.List;
import java.util.Set;

/**
 * Created by dmytro on 12.08.16.
 */
public class CalculateResultPriceType extends AbstractDiscount {
    @Override
    protected void calculate(Client client, Order order) {
        Set<OrderProduct> products = order.getOrderProducts();

        for (OrderProduct orderProduct : products) {
            Product product = orderProduct.getProduct();
            List<AppliedDiscount> appliedDiscounts = orderProduct.getAppliedDiscounts();
            if (appliedDiscounts == null) {
                MonetaryAmount price = product.calcPrice(orderProduct.getCount());
                orderProduct.setPrice(price);
                orderProduct.setCount(orderProduct.getCount());
            } else {
                MonetaryAmount monetaryAmount = MonetaryAmountFactory.create();
                appliedDiscounts.forEach(appliedDiscount -> monetaryAmount.add(appliedDiscount.getTotalPrice()));
                orderProduct.setPrice(monetaryAmount);
            }
        }
    }
}
