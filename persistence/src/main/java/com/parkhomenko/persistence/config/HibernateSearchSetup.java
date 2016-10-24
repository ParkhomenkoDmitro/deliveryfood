package com.parkhomenko.persistence.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.stereotype.Repository;

import java.util.Objects;

/**
 * @author Dmytro Parkhomenko
 *         Created on 21.10.16.
 */

@Repository
@Conditional(HibernateSearchSetup.IndexCondition.class)
public class HibernateSearchSetup implements InitializingBean {

    static class IndexCondition implements Condition {

        @Override
        public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
            String property = context.getEnvironment().getProperty("hibernate.search.index");

            if(Objects.isNull(property)) {
                return false;
            }

            if(Boolean.valueOf(property)) {
                return true;
            }

            return false;
        }
    }

    private SessionFactory sessionFactory;

    @Autowired
    public HibernateSearchSetup(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void afterPropertiesSet() throws InterruptedException {
        Session session = sessionFactory.openSession();

        try {
            FullTextSession fullTextSession = Search.getFullTextSession(session);
            fullTextSession.createIndexer().startAndWait();
        } finally {
            if(Objects.nonNull(session)) {
                session.close();
            }
        }
    }
}
