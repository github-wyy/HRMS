package com.wyy.test;

import com.wyy.util.encrypt.EncryptUtil;

public class CreatePassword {
    public static void main(String[] args) {
        String password = "hello"; // 密码明文
        System.out.println(EncryptUtil.encode(password)); // 密码加密
    }
}
