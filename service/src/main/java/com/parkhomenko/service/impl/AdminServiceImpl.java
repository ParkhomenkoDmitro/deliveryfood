package com.parkhomenko.service.impl;

import com.parkhomenko.common.domain.Admin;
import com.parkhomenko.persistence.dao.AdminRepository;
import com.parkhomenko.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Parkhomenko
 * Created on 19.07.16.
 */

@Service
public class AdminServiceImpl implements AdminService {

    private AdminRepository repository;

    @Autowired
    public void setRepository(AdminRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public List<Admin> findAll(Sort sort) {
        List<Admin> result = new ArrayList<>();
        repository.findAll(sort).forEach(result::add);
        return result;
    }

    @Override
    @Transactional
    public List<Admin> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public Admin save(Admin entity) {
        return repository.save(entity);
    }

    @Override
    @Transactional
    public List<Admin> save(Iterable entities) {
        List<Admin> result = new ArrayList<>();
        Iterable<Admin> admins = repository.save(entities);
        admins.forEach(admin -> result.add(admin));
        return result;
    }

    @Override
    @Transactional
    public Admin findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    public boolean exists(Long id) {
        return repository.exists(id);
    }

    @Override
    @Transactional
    public List<Admin> findAll() {
        List<Admin> result = new ArrayList<>();
        Iterable<Admin> admins = repository.findAll();
        admins.forEach(admin -> result.add(admin));
        return  result;
    }

    @Override
    @Transactional
    public List<Admin> findAll(Iterable<Long> longs) {
        List<Admin> result = new ArrayList<>();
        Iterable<Admin> admins = repository.findAll(longs);
        admins.forEach(admin -> result.add(admin));
        return result;
    }

    @Override
    @Transactional
    public long count() {
        return repository.count();
    }

    @Override
    @Transactional
    public void delete(Long id) {
        repository.delete(id);
    }

    @Override
    @Transactional
    public void delete(Admin entity) {
        repository.delete(entity);
    }

    @Override
    @Transactional
    public void delete(Iterable<Admin> entities) {
        repository.delete(entities);
    }

    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}