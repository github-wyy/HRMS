package com.wyy.jwt.config;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Configuration
public class JacksonConfig {
//    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
