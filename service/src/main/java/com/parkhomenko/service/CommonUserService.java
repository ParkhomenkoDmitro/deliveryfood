package com.parkhomenko.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * @author Dmytro Parkhomenko
 *         Created on 19.08.16.
 */

public interface CommonUserService<T> {
    List<T> findAll(Sort sort);
    List<T> findAll(Pageable pageable);
    T save(T entity);
    List<T> save(Iterable entities);
    T findOne(Long id);
    boolean exists(Long id);
    List<T> findAll();
    List<T> findAll(Iterable<Long> longs);
    long count();
    void delete(Long id);
    void delete(T entity);
    void delete(Iterable<T> entities);
    void deleteAll();
}
