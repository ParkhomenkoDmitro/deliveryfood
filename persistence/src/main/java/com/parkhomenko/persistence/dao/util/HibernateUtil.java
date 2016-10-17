package com.parkhomenko.persistence.dao.util;

import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Supplier;

/**
 * @author Dmytro Parkhomenko
 *         Created on 18.08.16.
 */

public final class HibernateUtil {
    public static <T> Page<T> findAll(Pageable pageable, Supplier<Criteria> supplier) {
        Criteria criteria = supplier.get();
        addOrderCriteria(criteria, pageable.getSort());
        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        List<T> result = criteria.list();
        return new PageImpl<>(result);
    }

    public static void addOrderCriteria(Criteria criteria, Sort sort) {
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            Sort.Direction direction = order.getDirection();

            criteria.addOrder((direction.equals(Sort.Direction.ASC) ?
                    Order.asc(property) :
                    Order.desc(property))
                    .nulls(NullPrecedence.LAST));
        }
    }

    public static <T> long count(Supplier<Criteria> supplier) {
        Criteria criteria = supplier.get();
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}