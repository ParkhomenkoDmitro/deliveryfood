package com.parkhomenko.persistence.dao.impl;

import com.parkhomenko.common.domain.User;
import com.parkhomenko.persistence.dao.AdminRepository;
import com.parkhomenko.persistence.dao.util.CommonUserRepository;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Parkhomenko
 *         Created on 18.08.16.
 */

@Repository
public class AdminRepositoryImpl extends CommonUserRepository implements AdminRepository {

    @Autowired
    public AdminRepositoryImpl(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Iterable<Admin> findAll(Sort sort) {
        return null;
    }

    @Override
    public Page<Admin> findAll(Pageable pageable) {
        return findAll(pageable, () -> getCurrentSession().createCriteria(Admin.class));
    }

    @Override
    public Admin save(Admin entity) {
        Long id = (Long) getCurrentSession().save(entity);
        return (Admin) getCurrentSession().get(Admin.class, id);
    }

    @Override
    public Iterable<Admin> save(Iterable entities) {
        return save(entities, (Admin admin) -> {
            Long id = (Long) getCurrentSession().save(admin);
            admin.setId(id);});
    }

    @Override
    public Admin findOne(Long aLong) {
        return (Admin) getCurrentSession().get(Admin.class, aLong);
    }

    @Override
    public boolean exists(Long aLong) {
        return getCurrentSession().get(Admin.class, aLong) != null;
    }

    @Override
    public Iterable<Admin> findAll() {
        Query query = getCurrentSession().createQuery("FROM com.parkhomenko.common.domain.Admin");
        return query.list();
    }

    @Override
    public Iterable<Admin> findAll(Iterable<Long> longs) {
        List<Admin> result = new ArrayList<>();
        longs.forEach(id -> result.add((Admin) getCurrentSession().get(Admin.class, id)));
        return result;
    }

    @Override
    public long count() {
        return count(() -> getCurrentSession().createCriteria(Admin.class));
    }

    @Override
    public void delete(Long aLong) {
        User user = (User) getCurrentSession().get(User.class, aLong);
        getCurrentSession().delete(user);
    }

    @Override
    public void delete(Admin entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public void delete(Iterable<? extends Admin> entities) {
        entities.forEach(entity -> getCurrentSession().delete(entity));
    }

    @Override
    public void deleteAll() {
        Session session = getCurrentSession();
        session.getNamedQuery("deleteAllUserRoleNativeSQL").executeUpdate();
        session.getNamedQuery("deleteAllWarehouseAdminNativeSQL").executeUpdate();
        session.getNamedQuery("deleteAllAdmins").executeUpdate();
    }
}