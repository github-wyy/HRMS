package com.wyy.test;

import com.wyy.common.dto.EmpDTO;
import com.wyy.endpoint.StartHREndpointApplication;
import com.wyy.endpoint.service.IEmpService;
import com.wyy.endpoint.vo.Emp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Map;

@ExtendWith(SpringExtension.class) 		// Junit5测试工具
@WebAppConfiguration                 // 表示需要启动Web配置才可以进行测试
@SpringBootTest(classes = StartHREndpointApplication.class)  // 定义要测试的启动类
public class TestEmpService {
    @Autowired
    private IEmpService empService;
    @Test
    public void testList() throws Exception {
        Map<String, Object> all = this.empService.split(1, 5, null, null);
        System.out.println("总记录数：" + all.get("count"));
        List<Emp> emps = (List<Emp>) all.get("data"); // 获取数据
        System.out.println("返回记录：" + emps.size());
    }
    @Test
    public void testAdd() throws Exception {
        EmpDTO emp = new EmpDTO();
        emp.setEmpno(8789L);
        emp.setEname("小李老师");
        emp.setJob("教书匠");
        emp.setDeptno(10L);
        emp.setRtid("yootk-3");
        emp.setSal(15900.00);
        System.out.println(this.empService.add(emp, "admin", "管理员"));
    }
    @Test
    public void testPreAdd() {
        System.out.println(this.empService.preAdd());
    }
    @Test
    public void testPreEdit() {
        System.out.println(this.empService.preEdit(99878L));
    }
    @Test
    public void testEdit() throws Exception {
        EmpDTO emp = new EmpDTO();
        emp.setEmpno(8788L);
        emp.setEname("小李老师");
        emp.setJob("教书先生");
        emp.setDeptno(10L);
        emp.setRtid("yootk-3");
        emp.setSal(5900.00);
        System.out.println(this.empService.edit(emp, "admin", "管理员"));
    }
    @Test
    public void testRemove() throws Exception {
        System.out.println(this.empService.remove(10123L, "admin", "管理员"));
    }
}
