package com.wyy.jwt.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyy.jwt.config.JWTConfigProperties;
import com.wyy.jwt.service.ITokenService;
import io.jsonwebtoken.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
// 此时的组件中的代码需要被其他的模块去引用，所以未必会与扫描包相同
public class TokenServiceImpl implements ITokenService {
    @Autowired // SpringBoot容器启动时会自动提供Jacks实例
    private ObjectMapper objectMapper; // Jackson的数据处理类对象
    @Autowired
    private JWTConfigProperties jwtConfigProperties; // 获取JWT的相关配置属性
    @Value("${spring.application.name}") // 通过SpEL进行配置注入
    private String applicationName; // 应用名称
    private SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 签名算法
    @Override
    public SecretKey generalKey() {
        byte [] encodeKey = Base64.decodeBase64(Base64.encodeBase64(this.jwtConfigProperties.getSecret().getBytes()));
        SecretKey key = new SecretKeySpec(encodeKey, 0, encodeKey.length, "AES"); // 获取加密KEY
        return key;
    }

    @Override
    public String createToken(String id, Map<String, Object> subject) {
        // 使用JWT数据结构进行开发，目的之一就是不需要进行JWT数据的分布式存储，所以所谓的缓存组件、数据库都用不到
        // 所有的Token都存在有保存时效的问题，所以就需要通过当前时间来进行计算
        Date nowDate = new Date(); // 获取当前的日期时间
        Date expireDate = new Date(nowDate.getTime() + this.jwtConfigProperties.getExpire() * 1000); // 证书过期时间
        Map<String, Object> cliams = new HashMap<>(); // 保存所有附加数据
        cliams.put("site", "附加数据site");
        cliams.put("msg", "附加数据msg");
        Map<String, Object> headers = new HashMap<>(); // 保存头信息
        headers.put("author", "wyy");
        headers.put("module", this.applicationName);
        JwtBuilder builder = null;
        try {
            builder = Jwts.builder()    // 进行JWTBuilder对象实例化
                    .setClaims(cliams) // 保存附加的数据内容
                    .setHeader(headers) // 保存头信息
                    .setId(id)// 保存ID信息
                    .setIssuedAt(nowDate) // 签发时间
                    .setIssuer(this.jwtConfigProperties.getIssuer()) // 设置签发者
                    .setSubject(this.objectMapper.writeValueAsString(subject)) // 所要传递的数据转为JSON
                    .signWith(this.signatureAlgorithm, this.generalKey()) // 获取签名算法
                    .setExpiration(expireDate); // 配置失效时间
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return builder.compact(); // 创建Token
    }

    @Override
    public Jws<Claims> parseToken(String token) throws JwtException {
        if (this.verifyToken(token)) {  // 只有正确的时候再进行Token解析
            Jws<Claims> claims = Jwts.parser().setSigningKey(this.generalKey()).parseClaimsJws(token);
            return claims;
        }
        return null; // 解析失败返回null
    }

    @Override
    public boolean verifyToken(String token) {
        try {
            Jwts.parser().setSigningKey(this.generalKey()).parseClaimsJws(token.trim()).getBody();
            return true; // 没有异常就返回true
        } catch (Exception e) {}
        return false;
    }

    @Override
    public String refreshToken(String token) {
        if (this.verifyToken(token)) {
            Jws<Claims> jws = this.parseToken(token); // 解析Token数据
            try {
                return this.createToken(jws.getBody().getId(), this.objectMapper.readValue(jws.getBody().getSubject(), Map.class));
            } catch (JsonProcessingException e) {
            }
        }
        return null;
    }
}
