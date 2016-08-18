package com.parkhomenko.persistence.dao.impl;

import com.parkhomenko.common.domain.Driver;
import com.parkhomenko.persistence.dao.DriverRepository;
import com.parkhomenko.persistence.dao.util.CommonUserRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

/**
 * @author Dmytro Parkhomenko
 *         Created on 18.08.16.
 */

@Repository
public class DriverRepositoryImpl extends CommonUserRepository implements DriverRepository {

    @Autowired
    public DriverRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Iterable<Driver> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Driver> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Driver save(Driver entity) {
        return null;
    }

    @Override
    public Iterable<Driver> save(Iterable entities) {
        return null;
    }

    @Override
    public Driver findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Driver> findAll() {
        return null;
    }

    @Override
    public Iterable<Driver> findAll(Iterable<Long> longs) {
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
    public void delete(Driver entity) {

    }

    @Override
    public void delete(Iterable<? extends Driver> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
