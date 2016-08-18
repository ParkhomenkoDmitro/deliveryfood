package com.parkhomenko.persistence.dao.impl;

import com.parkhomenko.common.domain.Client;
import com.parkhomenko.persistence.dao.ClientRepository;
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
public class ClientRepositoryImpl extends CommonUserRepository implements ClientRepository {

    @Autowired
    public ClientRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Iterable<Client> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Client save(Client entity) {
        return null;
    }

    @Override
    public Iterable<Client> save(Iterable entities) {
        return null;
    }

    @Override
    public Client findOne(Long aLong) {
        return null;
    }

    @Override
    public boolean exists(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Client> findAll() {
        return null;
    }

    @Override
    public Iterable<Client> findAll(Iterable<Long> longs) {
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
    public void delete(Client entity) {

    }

    @Override
    public void delete(Iterable<? extends Client> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
