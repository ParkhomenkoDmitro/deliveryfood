package com.parkhomenko.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Dmytro Parkhomenko
 * Created on 31.07.16.
 */

@Configuration
@Profile("production")
@PropertySource({"classpath:prod-hibernate.properties", "classpath:prod-hikari.properties"})
public class ProdConfig {
}
