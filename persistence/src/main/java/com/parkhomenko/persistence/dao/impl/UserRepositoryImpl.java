package com.parkhomenko.persistence.dao.impl;

import com.parkhomenko.common.domain.User;
import com.parkhomenko.persistence.dao.UserRepository;
import org.hibernate.*;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.dialect.Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dmytro on 19.07.16.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Iterable<User> findAll(Sort sort) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        addOrderCriteria(criteria, sort);
        return criteria.list();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        addOrderCriteria(criteria, pageable.getSort());
        criteria.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        criteria.setMaxResults(pageable.getPageSize());
        List<User> result = criteria.list();
        return new PageImpl<>(result);
    }

    private void addOrderCriteria(Criteria criteria, Sort sort) {
        for (Sort.Order order : sort) {
            String property = order.getProperty();
            Sort.Direction direction = order.getDirection();

            criteria.addOrder((direction.equals(Sort.Direction.ASC) ?
                    Order.asc(property) :
                    Order.desc(property))
                    .nulls(NullPrecedence.LAST));
        }
    }

    @Override
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        Long id = (Long) session.save(user);
        return (User) session.get(User.class, id);
    }

    @Override
    public Iterable save(Iterable iterable) {
        Session session = sessionFactory.getCurrentSession();
        Iterable<User> userIterable = iterable;
        List<User> result = new ArrayList<>();
        final int DEFAULT_BATCH_SIZE = Integer.valueOf(Dialect.DEFAULT_BATCH_SIZE);
        int counter = 0;

        for(User user : userIterable) {
            Long id = (Long) session.save(user);
            user.setId(id);
            result.add(user);

            if( counter % DEFAULT_BATCH_SIZE == 0 ) {
                session.flush();
                session.clear();
            }

            counter++;
        }

        return result;
    }

    @Override
    public User findOne(Long aLong) {
        Session session = sessionFactory.getCurrentSession();
        return (User) session.get(User.class, aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, aLong) != null;
    }

    @Override
    public Iterable<User> findAll() {
        Session session = sessionFactory.getCurrentSession();
        Query query = session.createQuery("FROM com.parkhomenko.common.domain.User");
        return query.list();
    }

    @Override
    public Iterable<User> findAll(Iterable<Long> iterable) {
        Session session = sessionFactory.getCurrentSession();
        List<User> result = new ArrayList<>();

        for(Long id : iterable) {
            User user = (User) session.get(User.class, id);
            result.add(user);
        }

        return result;
    }

    @Override
    public long count() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteriaCount = session.createCriteria(User.class);
        criteriaCount.setProjection(Projections.rowCount());
        return (Long) criteriaCount.uniqueResult();
    }

    @Override
    public void delete(Long aLong) {
        Session session = sessionFactory.getCurrentSession();
        User user = (User) session.get(User.class, aLong);
        session.delete(user);
    }

    @Override
    public void delete(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }

    @Override
    public void delete(Iterable<? extends User> iterable) {
        Session session = sessionFactory.getCurrentSession();
        for(User user : iterable) {
            session.delete(user);
        }

    }

    @Override
    public void deleteAll() {
        final int PAGE_SIZE = 15;
        final int PAGE_NUMBER = 0;
        final String SORTING_PROPERTY_NAME = "id";
        List<User> result;

        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.addOrder(Order.asc(SORTING_PROPERTY_NAME));
        criteria.setFirstResult(PAGE_NUMBER);
        criteria.setMaxResults(PAGE_SIZE);

        do {
            result = criteria.list();
            result.forEach(session::delete);
        } while (result.size() == PAGE_SIZE);
    }
}
