package com.parkhomenko.persistence.config;

/**
 * Created by dmytro on 19.07.16.
 */

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.parkhomenko.persistence.dao.impl")
public class DaoConfig {
}
