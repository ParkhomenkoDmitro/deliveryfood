package com.parkhomenko.service.impl;

import com.parkhomenko.common.domain.Admin;
import com.parkhomenko.persistence.dao.AdminRepository;
import com.parkhomenko.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
    @Transactional(readOnly = true)
    public List<Admin> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    @Override
    @Transactional
    public Long save(Admin entity) {
        Long id = entity.getId();

        if(Objects.nonNull(id)) {
            repository.update(entity);
            return id;
        }
        return repository.create(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Admin findOne(Long id) {
        return repository.findOne(id);
    }

    @Override
    @Transactional(readOnly = true)
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
    public void delete(Set<Long> ids) {
        repository.delete(ids);
    }

    @Override
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Admin> search(Admin admin) {
        return repository.search(admin);
    }
}