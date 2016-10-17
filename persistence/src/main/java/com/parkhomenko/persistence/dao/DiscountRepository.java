package com.parkhomenko.persistence.dao;

import com.parkhomenko.common.domain.discount.Discount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dmytro Parkhomenko
 *         Created on 05.09.16.
 */

public interface DiscountRepository <T extends Discount> {
    Page<T> findAll(Pageable pageable);
    T save(T entity);
    Iterable<T> save(Iterable<T> entities);
    T findOne(Long aLong);
    boolean exists(Long aLong);
    Iterable<T> findAll(Iterable<Long> longs);
    long count();
    void delete(Long aLong);
    void delete(Iterable<T> entities);
    void deleteAll();
}
