package com.wyy.jwt.autoconfig;

import com.wyy.jwt.config.EncryptConfigProperties;
import com.wyy.jwt.config.JWTConfigProperties;
import com.wyy.jwt.service.IEncryptService;
import com.wyy.jwt.service.ITokenService;
import com.wyy.jwt.service.impl.EncryptServiceImpl;
import com.wyy.jwt.service.impl.TokenServiceImpl;
import com.wyy.jwt.util.JWTMemberDataService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({JWTConfigProperties.class, EncryptConfigProperties.class}) // 配置注入属性
public class JWTAutoConfiguration {
    @Bean("tokenService")
    public ITokenService getTokenServiceBean() {
        return new TokenServiceImpl();
    }
    @Bean("encryptService")
    public IEncryptService getEncryptServiceBean() {
        return new EncryptServiceImpl();
    }
    @Bean("memberDataService")
    public JWTMemberDataService getMemberDataService() {
        return new JWTMemberDataService();
    }
}
