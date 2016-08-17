package com.parkhomenko.service.impl;

import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.discount.DiscountFetcher;
import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.common.domain.discount.DiscountTwo;
import com.parkhomenko.service.DiscountService;

/**
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

public class DiscountServiceImpl implements DiscountFetcher, DiscountService {
    @Override
    public DiscountOne fetchDiscountTypeOne(Product product) {
        return null;
    }

    @Override
    public DiscountTwo fetchDiscountTypeTwo(Product product) {
        return null;
    }
}
