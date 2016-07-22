package com.parkhomenko.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by dmytro on 19.07.16.
 */
@Configuration
@ComponentScan({"com.parkhomenko.persistence.config", "com.parkhomenko.service.impl"})
public class Configurer {
}
