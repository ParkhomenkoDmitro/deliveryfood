package com.parkhomenko.persistence.dao.impl;

import com.parkhomenko.common.domain.Product;
import com.parkhomenko.common.domain.discount.Discount;
import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.persistence.dao.DiscountRepository;
import com.parkhomenko.persistence.dao.util.CommonRepository;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

/**
 * @author Dmytro Parkhomenko
 *         Created on 05.09.16.
 */

@Repository
public class DiscountOneRepositoryImpl extends CommonRepository implements DiscountRepository<DiscountOne> {

    @Autowired
    public DiscountOneRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Discount fetch(Product product) {
        Criteria criteria = getCurrentSession().createCriteria(DiscountOne.class);
        DiscountOne discountOne = null;
        //TODO fetch DiscountOne instance from RDBMS and return it if found else return sigelton;
        return Discount.getEmptyDiscount();
    }

    @Override
    public Discount fetch(Product product, LocalDateTime orderCreationDateTime) {
        DiscountOne discountOne = null;
        //TODO fetch DiscountOne instance from RDBMS and return it if found else return sigelton;
        return Discount.getEmptyDiscount();
    }

    @Override
    public Iterable<DiscountOne> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<DiscountOne> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public DiscountOne save(DiscountOne entity) {
        return null;
    }

    @Override
    public Iterable<DiscountOne> save(Iterable entities) {
        return null;
    }

    @Override
    public DiscountOne findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<DiscountOne> findAll() {
        return null;
    }

    @Override
    public Iterable<DiscountOne> findAll(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void delete(Long aLong) {

    }

    @Override
    public void delete(DiscountOne entity) {

    }

    @Override
    public void delete(Iterable<? extends DiscountOne> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
