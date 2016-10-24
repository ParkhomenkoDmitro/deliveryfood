package com.parkhomenko.persistence.dao.impl;

import com.parkhomenko.common.domain.Admin;
import com.parkhomenko.persistence.dao.AdminRepository;
import com.parkhomenko.persistence.dao.util.HibernateUtil;
import com.parkhomenko.persistence.dao.util.LoggingMessage;
import org.apache.lucene.search.Query;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.BooleanJunction;
import org.hibernate.search.query.dsl.MustJunction;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hsqldb.lib.tar.TarHeaderField.name;

/**
 * @author Dmytro Parkhomenko
 *         Created on 18.08.16.
 */

@Repository
public class AdminRepositoryImpl implements AdminRepository {

    private SessionFactory sessionFactory;

    @Autowired
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return HibernateUtil.findAll(pageable, () -> getCurrentSession().createCriteria(Admin.class));
    }

    @Override
    public Long create(Admin entity) {
        return (Long) getCurrentSession().save(entity);
    }

    @Override
    public void update(Admin entity) {
        getCurrentSession().update(entity);
    }

    @Override
    public Admin findOne(Long id) {
        Admin admin = getCurrentSession().get(Admin.class, id);

        if(Objects.isNull(admin)) {
            throw new EntityNotFoundException(LoggingMessage.logMessage(id));
        }

        Hibernate.initialize(admin.getRoles()); //TODO: replace by one HQL
        Hibernate.initialize(admin.getWarehouses()); //TODO: replace by one HQL

        return admin;
    }

    @Override
    public boolean exists(Long aLong) {
        return getCurrentSession().get(Admin.class, aLong) != null;
    }

    @Override
    public Iterable<Admin> findAll(Iterable<Long> longs) {
        List<Admin> result = new ArrayList<>();
        longs.forEach(id -> result.add(getCurrentSession().get(Admin.class, id)));
        return result;
    }

    @Override
    public long count() {
        System.out.println(getCurrentSession());
        return HibernateUtil.count(() -> getCurrentSession().createCriteria(Admin.class));
    }

    @Override
    public void delete(Long id) {
        getCurrentSession().delete(getCurrentSession().get(Admin.class, id));
    }

    @Override
    public void delete(Iterable<Long> ids) {
        ids.forEach(this::delete);
    }

    @Override
    public void deleteAll() {
        Session session = getCurrentSession();
        session.getNamedQuery("deleteAllUserRoleNativeSQL").executeUpdate();
        session.getNamedQuery("deleteAllWarehouseAdminNativeSQL").executeUpdate();
        session.getNamedQuery("deleteAllAdmins").executeUpdate();
    }

    @Override
    public List<Admin> search(Admin admin) {
        Session session = getCurrentSession();
        FullTextSession fullTextSession = Search.getFullTextSession(session);
        QueryBuilder qb =
                fullTextSession
                .getSearchFactory()
                .buildQueryBuilder()
                        .forEntity(Admin.class).get();

        BooleanJunction<BooleanJunction> booleanJunction = qb.bool();
        MustJunction mustJunction = null;

        //Name search

        if(Objects.nonNull(admin.getName())) {
            String name = admin.getName().toLowerCase() + "*";
            Query q = qb.keyword().wildcard().onField("name").matching(name).createQuery();
            mustJunction = booleanJunction.must(q);
        }

        //Email search

        if(Objects.nonNull(admin.getEmail())) {
            String email = admin.getEmail().toLowerCase() + "*";
            Query q = qb.keyword().wildcard().onField("email").matching(email).createQuery();

            if (mustJunction == null) {
                mustJunction = booleanJunction.must(q);
            } else {
                mustJunction = mustJunction.must(q);
            }
        }

        //Phone search

        if(Objects.nonNull(admin.getPhone())) {
            String phone = admin.getPhone();
            Query q = qb.keyword().onField("phone").matching(phone).createQuery();

            if (mustJunction == null) {
                mustJunction = booleanJunction.must(q);
            } else {
                mustJunction = mustJunction.must(q);
            }
        }

        //Login search

        if(Objects.nonNull(admin.getLogin())) {
            String login = admin.getLogin().toLowerCase() + "*";
            Query q = qb.keyword().wildcard().onField("login").matching(login).createQuery();

            if (mustJunction == null) {
                mustJunction = booleanJunction.must(q);
            } else {
                mustJunction = mustJunction.must(q);
            }
        }

        org.apache.lucene.search.Query query = mustJunction.createQuery();
        org.hibernate.Query hibQuery = fullTextSession.createFullTextQuery(query, Admin.class);
        List<Admin> result = hibQuery.list();
        return result;
    }
}