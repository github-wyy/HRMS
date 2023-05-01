package com.wyy.jwt.service;

public interface IEncryptService { // 密码加密
    public String getEncryptPassword(String password); // 得到一个加密后的密码
}
