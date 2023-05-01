package com.wyy.test;

import com.wyy.jwt.StartJWTConfiguration;
import com.wyy.jwt.service.IEncryptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@SpringBootTest(classes = StartJWTConfiguration.class)
public class TestEncryptService {
    @Autowired
    private IEncryptService encryptService;
    @Test
    public void testCreatePassword() {
        System.out.println(this.encryptService.getEncryptPassword("hello"));
    }
}
