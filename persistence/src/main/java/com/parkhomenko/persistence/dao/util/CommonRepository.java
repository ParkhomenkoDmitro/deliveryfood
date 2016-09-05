package com.parkhomenko.persistence.dao.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * @author Dmytro Parkhomenko
 *         Created on 05.09.16.
 */

public abstract class CommonRepository {
    private SessionFactory sessionFactory;

    public CommonRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
}
