package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Product;

/**
 * Interface for impl by service.
 *
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

public interface DiscountFetcher {
    DiscountOne fetchDiscountTypeOne(Product product);
    DiscountTwo fetchDiscountTypeTwo(Product product);
}
