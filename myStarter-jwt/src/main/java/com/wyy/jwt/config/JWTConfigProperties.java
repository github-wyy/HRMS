package com.wyy.jwt.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data // Lombok直接生成的所有代码
@ConfigurationProperties(prefix = "hr.security.config.jwt") // 配置项的前缀
public class JWTConfigProperties { // JWT配置类
    private String sign; // 保存签名信息
    private String issuer; // 证书签发者
    private String secret; // 加密的密钥
    private long expire; // 失效时间
}
