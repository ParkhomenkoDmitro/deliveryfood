package com.parkhomenko.persistence.config;

import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;

/**
 * @author Dmytro Parkhomenko
 * Created on 31.07.16.
 */

@Configuration
@Profile("production")
@PropertySource({"classpath:prod-hikari.properties"})
public class ProdConfig {

    @Bean(name = "hibernate-properties")
    public PropertiesFactoryBean mapper() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("prod-hibernate.properties"));
        return bean;
    }
}
