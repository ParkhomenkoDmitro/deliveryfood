package com.parkhomenko.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author Dmytro Parkhomenko
 * Created on 18.07.16.
 */

@Configuration
@EnableTransactionManagement
public class HibernateConfig implements TransactionManagementConfigurer {

    @Autowired
    private Environment env;

    @Autowired
    @Qualifier("hibernate-properties")
    private Properties hibernateProperties;

    @Bean
    public DataSource configureDataSource() {
        HikariConfig config = new HikariConfig();
        config.setDriverClassName(env.getProperty("dataSource.driverClassName"));
        config.setJdbcUrl(env.getProperty("dataSource.jdbcUrl"));
        config.setUsername(env.getProperty("dataSource.user"));
        config.setPassword(env.getProperty("dataSource.password"));
        return new HikariDataSource(config);
    }

    @Override
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(configureDataSource());
        sessionFactory.setMappingDirectoryLocations(
                new ClassPathResource(hibernateProperties.getProperty("hibernate.mapping_directory_locations")));
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}