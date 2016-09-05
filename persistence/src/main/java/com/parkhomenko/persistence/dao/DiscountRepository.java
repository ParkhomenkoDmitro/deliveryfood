package com.parkhomenko.persistence.dao;

import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.discount.Discount;
import com.parkhomenko.common.domain.discount.DiscountSupplier;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;

/**
 * @author Dmytro Parkhomenko
 *         Created on 05.09.16.
 */

@NoRepositoryBean
public interface DiscountRepository <T extends Discount> extends PagingAndSortingRepository<T, Long>, DiscountSupplier {
    Discount fetch(Product product);
}
