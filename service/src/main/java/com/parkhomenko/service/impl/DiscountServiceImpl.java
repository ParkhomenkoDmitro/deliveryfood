package com.parkhomenko.service.impl;

import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.discount.DiscountFetcher;
import com.parkhomenko.common.domain.discount.DiscountTypeOne;
import com.parkhomenko.common.domain.discount.DiscountTypeTwo;
import com.parkhomenko.service.DiscountService;

/**
 * Created by dmytro on 13.08.16.
 */
public class DiscountServiceImpl implements DiscountFetcher, DiscountService {
    @Override
    public DiscountTypeOne fetchDiscountTypeOne(Product product) {
        return null;
    }

    @Override
    public DiscountTypeTwo fetchDiscountTypeTwo(Product product) {
        return null;
    }
}
