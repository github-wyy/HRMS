package com.wyy.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "hr.security.config.password.encrypt") // 配置前缀
public class EncryptConfigProperties { // 加密配置属性
    private Integer repeat; // 定义重复的次数
    private String salt; // 加密的盐值
}
