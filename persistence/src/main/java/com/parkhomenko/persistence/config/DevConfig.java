package com.parkhomenko.persistence.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

/**
 * @author Dmytro Parkhomenko
 * Created on 31.07.16.
 */

@Configuration
@Profile("development")
@PropertySource({"classpath:dev-hibernate.properties", "classpath:dev-hikari.properties"})
public class DevConfig {
}
