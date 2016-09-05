package com.parkhomenko.common.domain.discount;

import com.parkhomenko.common.domain.Product;

import java.time.LocalDateTime;

/**
 * Interface for impl by service.
 *
 * @author Dmytro Parkhomenko
 * Created on 13.08.16.
 */

public interface DiscountSupplier {
    Discount fetch(Product product, LocalDateTime orderCreationDateTime);
}
