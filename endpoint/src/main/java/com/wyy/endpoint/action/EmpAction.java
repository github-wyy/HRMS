package com.wyy.endpoint.action;

import com.wyy.common.dto.EmpDTO;
import com.wyy.common.http.ResponseData;
import com.wyy.endpoint.action.abs.AbstractEndpointAction;
import com.wyy.endpoint.service.IEmpService;
import com.wyy.jwt.annotation.JWTCheckToken;
import com.wyy.jwt.util.JWTMemberDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/hr/endpoint/emp/*")
public class EmpAction extends AbstractEndpointAction {
    @Autowired
    private IEmpService empService; // 注入部门业务实例
    @Autowired
    private JWTMemberDataService jwtMemberDataService; // 用于刷新token
    @JWTCheckToken(role = "emp", action = "emp:list")
    @GetMapping("list")
    public Object list(Long currentPage, Long lineSize, String column, String keyword) { // 查询全部工资等级
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 刷新token
        data.setResult(this.empService.split(currentPage, lineSize, column, keyword)); // 调用业务处理方法
        return data; // 响应数据
    }
    @JWTCheckToken(role = "emp", action = "emp:add")
    @GetMapping("pre_add")
    public Object preAdd() {    // 雇员增加前查询
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 刷新token
        data.setResult(this.empService.preAdd()); // 调用业务处理方法
        return data; // 响应数据
    }
    @JWTCheckToken(role = "emp", action = "emp:add")
    @PostMapping("add")
    public Object add(@RequestBody EmpDTO dto) {    // 雇员增加
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 刷新token
        data.setResult(this.empService.add(dto, super.mid(), super.name())); // 调用业务处理方法
        return data; // 响应数据
    }
    @JWTCheckToken(role = "emp", action = "emp:edit")
    @GetMapping("pre_edit")
    public Object preEdit(long empno) {    // 雇员更新前查询
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 刷新token
        data.setResult(this.empService.preEdit(empno)); // 调用业务处理方法
        return data; // 响应数据
    }
    @JWTCheckToken(role = "emp", action = "emp:edit")
    @PostMapping("edit")
    public Object edit(@RequestBody EmpDTO dto) {    // 雇员增加
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 刷新token
        data.setResult(this.empService.edit(dto, super.mid(), super.name())); // 调用业务处理方法
        return data; // 响应数据
    }
    @JWTCheckToken(role = "emp", action = "emp:remove")
    @DeleteMapping("remove")
    public Object remove(long empno) {    // 雇员更新前查询
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 刷新token
        data.setResult(this.empService.remove(empno, super.mid(), super.name())); // 调用业务处理方法
        return data; // 响应数据
    }
}
