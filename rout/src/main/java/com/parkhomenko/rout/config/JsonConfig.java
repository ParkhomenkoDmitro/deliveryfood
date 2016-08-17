package com.parkhomenko.rout.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.parkhomenko.common.domain.User;
import com.parkhomenko.rout.serializer.UserSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author Dmytro Parkhomenko
 * Created on 01.08.16.
 */

@Configuration
public class JsonConfig {
    @Bean
    @Primary
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializer(User.class));

        //TODO: add here other serializers

        mapper.registerModule(module);
        return mapper;
    }
}
