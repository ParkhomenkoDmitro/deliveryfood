package com.parkhomenko.persistence.dao.util;

import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.dialect.Dialect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author Dmytro Parkhomenko
 *         Created on 18.08.16.
 */

public abstract class CommonUserRepository extends CommonRepository {

    public CommonUserRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public <T> Page<T> findAll(Pageable pageable, Supplier<Criteria> supplier) {
        Criteria criteria = supplier.get();
        addOrderCriteria(criteria, pageable.getSort());
        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        List<T> result = criteria.list();
        return new PageImpl<>(result);
    }

    public void addOrderCriteria(Criteria criteria, Sort sort) {
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            Sort.Direction direction = order.getDirection();

            criteria.addOrder((direction.equals(Sort.Direction.ASC) ?
                    Order.asc(property) :
                    Order.desc(property))
                    .nulls(NullPrecedence.LAST));
        }
    }

    //TODO: I think it is not the best impl for butch save!!!.
    public <T> Iterable save(Iterable iterable, Consumer<T> consumer) {
        final int DEFAULT_BATCH_SIZE = Integer.valueOf(Dialect.DEFAULT_BATCH_SIZE);
        List<T> result = new ArrayList<>();
        int counter = 0;
        Iterable<T> adminIterable = iterable;
        for(T user : adminIterable) {
            consumer.accept(user);
            result.add(user);
            if( counter % DEFAULT_BATCH_SIZE == 0 ) {
                getCurrentSession().flush();
                getCurrentSession().clear();
            }
            counter++;
        }
        return result;
    }

    public <T> long count(Supplier<Criteria> supplier) {
        Criteria criteria = supplier.get();
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }
}
