package com.parkhomenko.service;

import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * @author Dmytro Parkhomenko
 *         Created on 19.08.16.
 */

public interface CommonUserService<T> {
    Iterable<T> findAll(Pageable pageable);
    Long save(T entity);
    T findOne(Long id);
    long count();
    void delete(Long id);
    void delete(Set<Long> ids);
    void deleteAll();
}
