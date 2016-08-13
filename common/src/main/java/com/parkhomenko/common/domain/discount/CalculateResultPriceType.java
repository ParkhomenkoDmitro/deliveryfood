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
        Set<OrderProductItem> products = order.getOrderProductItems();

        for (OrderProductItem orderProductItem : products) {
            Product product = orderProductItem.getProduct();
            List<AppliedDiscount> appliedDiscounts = orderProductItem.getAppliedDiscounts();
            if (appliedDiscounts == null) {
                MonetaryAmount price = product.calcPrice(orderProductItem.getCount());
                orderProductItem.setPrice(price);
                orderProductItem.setCount(orderProductItem.getCount());
            } else {
                MonetaryAmount monetaryAmount = MonetaryAmountFactory.create();
                appliedDiscounts.forEach(appliedDiscount -> monetaryAmount.add(appliedDiscount.getTotalPrice()));
                orderProductItem.setPrice(monetaryAmount);
            }
        }
    }
}
