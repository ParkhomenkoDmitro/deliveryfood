package com.parkhomenko.persistence.dao.impl;

import com.parkhomenko.common.domain.discount.DiscountOne;
import com.parkhomenko.persistence.dao.DiscountRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**
 * @author Dmytro Parkhomenko
 *         Created on 05.09.16.
 */

@Repository
public class DiscountOneRepositoryImpl implements DiscountRepository<DiscountOne> {

    private SessionFactory sessionFactory;

    @Autowired
    public DiscountOneRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
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
    public Iterable<DiscountOne> save(Iterable<DiscountOne> entities) {
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
    public void delete(Iterable<DiscountOne> entities) {

    }

    @Override
    public void deleteAll() {

    }
}