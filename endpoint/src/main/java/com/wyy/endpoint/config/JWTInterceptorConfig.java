package com.wyy.endpoint.config;

import com.wyy.endpoint.interceptor.JWTAuthorizeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class JWTInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.getDefaultHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public HandlerInterceptor getDefaultHandlerInterceptor() {
        return new JWTAuthorizeInterceptor();
    }
}
