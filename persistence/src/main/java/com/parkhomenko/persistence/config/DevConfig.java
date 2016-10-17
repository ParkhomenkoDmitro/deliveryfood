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
@Profile("development")
@PropertySource({"classpath:dev-hikari.properties"})
public class DevConfig {

    @Bean(name = "hibernate-properties")
    public PropertiesFactoryBean mapper() {
        PropertiesFactoryBean bean = new PropertiesFactoryBean();
        bean.setLocation(new ClassPathResource("dev-hibernate.properties"));
        return bean;
    }
}
