package com.wyy.test;

import com.wyy.common.dto.DeptDTO;
import com.wyy.endpoint.StartHREndpointApplication;
import com.wyy.endpoint.service.IDeptService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

@ExtendWith(SpringExtension.class) // Junit5测试工具
@WebAppConfiguration  // 表示需要启动Web配置才可以进行测试
@SpringBootTest(classes = StartHREndpointApplication.class)  // 定义要测试的启动类
public class TestDeptService {
    @Autowired
    private IDeptService deptService;
    @Test
    public void testEditMgr() throws Exception {
        DeptDTO dto = new DeptDTO();
        dto.setDeptno(10L);
        dto.setEmpno(10653L);
        System.out.println(this.deptService.editMgr(dto,"admin","管理员"));
    }
    @Test
    public void testRemove() throws Exception {
        System.out.println(this.deptService.remove(10, "admin", "管理员"));
    }
    @Test
    public void testEdit() {
        DeptDTO dto = new DeptDTO();
        dto.setDname("财务部");
        dto.setDeptno(20L);
        dto.setBound(1);
        System.out.println(this.deptService.edit(dto, "admin", "管理员"));
    }
}
