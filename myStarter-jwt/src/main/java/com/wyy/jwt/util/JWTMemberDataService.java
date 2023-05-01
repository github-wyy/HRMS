package com.wyy.jwt.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyy.jwt.service.ITokenService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

// 所有的数据最终都是通过JSON的形式设置在JWT附加数据之中的
public class JWTMemberDataService { // 自定义的数据的解析类
    @Autowired
    private ITokenService tokenService;
    @Autowired
    private ObjectMapper objectMapper; // 解析JSON数据为Map集合

    public Map<String, String> headers(String token) {  // 通过JWT解析所有的头信息
        Jws<Claims> claimsJws = this.tokenService.parseToken(token);
        Map<String, String> headers = new HashMap<>(); // 保存所有的头信息的集合
        claimsJws.getHeader().forEach((key, value) -> { // 将JWT头信息转为Map
            headers.put(key.toString(), value.toString()); // 数据以String的方式存储
        });
        return headers;
    }
    public Set<String> roles(String token) {    // 解析全部的角色数据
        Jws<Claims> claimsJws = this.tokenService.parseToken(token);
        try {
            Map<String, List<String>> map = this.objectMapper.readValue(claimsJws.getBody().getSubject(), Map.class);
            Set<String> roles = new HashSet<>();
            roles.addAll(map.get("roles")); // 将获取的全部角色保存在Set集合
            return roles;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public Set<String> actions(String token) {    // 解析全部的权限数据
        Jws<Claims> claimsJws = this.tokenService.parseToken(token);
        try {
            Map<String, List<String>> map = this.objectMapper.readValue(claimsJws.getBody().getSubject(), Map.class);
            Set<String> actions = new HashSet<>();
            actions.addAll(map.get("actions")); // 将获取的全部角色保存在Set集合
            return actions;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String id(String token) {
        Jws<Claims> claimsJws = this.tokenService.parseToken(token);
        return claimsJws.getBody().getId();
    }
    public String adminFlag(String token) {
        Jws<Claims> claimsJws = this.tokenService.parseToken(token);
        try {
            Map<String, String> map = this.objectMapper.readValue(claimsJws.getBody().getSubject(), Map.class);
            return map.get("flag");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String mid(String token) {
        Jws<Claims> claimsJws = this.tokenService.parseToken(token);
        try {
            Map<String, String> map = this.objectMapper.readValue(claimsJws.getBody().getSubject(), Map.class);
            return map.get("mid");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String name(String token) {
        Jws<Claims> claimsJws = this.tokenService.parseToken(token);
        try {
            Map<String, String> map = this.objectMapper.readValue(claimsJws.getBody().getSubject(), Map.class);
            return map.get("name");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getToken(HttpServletRequest request, String name) {  // Token获取
        String token = request.getParameter(name); // name为参数的名称
        if (token == null || "".equals(token)) {    // 无法通过参数获取数据
            token = request.getHeader(name); // 通过头信息传递
        }
        return token;
    }
}
