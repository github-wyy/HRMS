package com.wyy.jwt.service.impl;

import com.wyy.jwt.config.EncryptConfigProperties;
import com.wyy.jwt.service.IEncryptService;
import com.wyy.util.encrypt.MD5Code;
import com.wyy.util.encrypt.EncryptSalt;
import org.springframework.beans.factory.annotation.Autowired;

public class EncryptServiceImpl implements IEncryptService {
    @Autowired
    private EncryptConfigProperties encryptConfigProperties; // 属性配置

    @Override
    public String getEncryptPassword(String password) {
        String newPass = EncryptSalt.encrypt(password, this.encryptConfigProperties.getSalt(),
                this.encryptConfigProperties.getRepeat()) ; // Base64加密
        MD5Code md5 = new MD5Code() ; // 获取MD5加密对象实例
        for (int x = 0 ; x < this.encryptConfigProperties.getRepeat() ; x ++) {
            newPass = md5.getMD5ofStr(newPass) ;
        }
        return newPass ;
    }
}
