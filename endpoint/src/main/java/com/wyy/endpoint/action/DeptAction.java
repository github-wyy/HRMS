package com.wyy.endpoint.action;

import com.wyy.common.dto.DeptDTO;
import com.wyy.common.http.ResponseData;
import com.wyy.endpoint.action.abs.AbstractEndpointAction;
import com.wyy.endpoint.service.IDeptService;
import com.wyy.jwt.annotation.JWTCheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/hr/endpoint/dept/*")
public class DeptAction extends AbstractEndpointAction {
    @Autowired
    private IDeptService deptService; // 注入部门业务实例
    @JWTCheckToken(role = "dept", action = "dept:list")
    @GetMapping("list")
    public Object list() { // 查询全部工资等级
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 获取新token
        data.setResult(this.deptService.list()); // 调用业务处理方法
        return data; // 响应数据
    }
    @JWTCheckToken(role = "dept", action = "dept:edit")
    @PostMapping("edit")
    public Object edit(@RequestBody DeptDTO dto) {
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        data.setResult(this.deptService.edit(dto, super.mid(), super.name()));// 雇员编辑
        data.setToken(super.refresh()); // 获取新的token
        return data;
    }
    @JWTCheckToken(role = "dept", action = "dept:edit")
    @GetMapping("get")
    public Object get(long deptno) {
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        data.setResult(this.deptService.get(deptno));// 获取部门数据
        data.setToken(super.refresh()); // 获取新的token
        return data;
    }
    @JWTCheckToken(role = "dept", action = "dept:edit")
    @PostMapping("editMgr")
    public Object editMgr(@RequestBody DeptDTO dto) {
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        data.setResult(this.deptService.editMgr(dto, super.mid(), super.name()));
        data.setToken(super.refresh()); // 获取新的token
        return data;
    }
    @JWTCheckToken(role = "dept", action = "dept:add")
    @PostMapping("add")
    public Object add(@RequestBody DeptDTO dto) {
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        data.setResult(this.deptService.add(dto, super.mid(), super.name()));
        data.setToken(super.refresh()); // 获取新的token
        return data;
    }
    @JWTCheckToken(role = "dept", action = "dept:remove")
    @DeleteMapping("remove")
    public Object remove(long deptno) {
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        data.setResult(this.deptService.remove(deptno, super.mid(), super.name()));
        data.setToken(super.refresh()); // 获取新的token
        return data;
    }
}
