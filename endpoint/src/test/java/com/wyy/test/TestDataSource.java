package com.wyy.test;

import com.wyy.endpoint.StartHREndpointApplication;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.sql.DataSource;

@ExtendWith(SpringExtension.class) 		// Junit5测试工具
@WebAppConfiguration                 // 表示需要启动Web配置才可以进行测试
@SpringBootTest(classes = StartHREndpointApplication.class)  // 定义要测试的启动类
public class TestDataSource {
    @Autowired
    private DataSource dataSource;
    @Test
    public void testDataSource() throws Exception {
        System.out.println(this.dataSource.getConnection());
    }
}
