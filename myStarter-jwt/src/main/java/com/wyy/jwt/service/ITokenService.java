package com.wyy.jwt.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;

import javax.crypto.SecretKey;
import java.util.Map;

public interface ITokenService { // 创建一个JWT的操作接口
    public SecretKey generalKey(); // 获取当前JWT数据的加密KEY
    // 创建Token的数据内容，同时要求保存用户的id以及所需要的附加数据
    public String createToken(String id, Map<String, Object> subject);
    public Jws<Claims> parseToken(String token) throws JwtException; // 解析Token数据
    public boolean verifyToken(String token); // 验证Token有效性
    public String refreshToken(String token); // 刷新Token内容
}
