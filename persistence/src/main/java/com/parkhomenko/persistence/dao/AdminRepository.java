package com.parkhomenko.persistence.dao;

import com.parkhomenko.common.domain.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Dmytro Parkhomenko
 *         Created on 18.08.16.
 */

public interface AdminRepository {
    Page<Admin> findAll(Pageable pageable);
    Long create(Admin admin);
    void update(Admin admin);
    Admin findOne(Long id);
    boolean exists(Long id);
    Iterable<Admin> findAll(Iterable<Long> ids);
    long count();
    void delete(Long id);
    void delete(Iterable<Long> ids);
    void deleteAll();
}
