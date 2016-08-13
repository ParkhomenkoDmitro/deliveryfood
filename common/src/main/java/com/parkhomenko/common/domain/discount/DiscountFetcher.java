package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Product;

/**
 * Created by dmytro on 13.08.16.
 */

/**
 * Interface for impl by service
 */
public interface DiscountFetcher {
    DiscountTypeOne fetchDiscountTypeOne(Product product);
    DiscountTypeTwo fetchDiscountTypeTwo(Product product);
}
