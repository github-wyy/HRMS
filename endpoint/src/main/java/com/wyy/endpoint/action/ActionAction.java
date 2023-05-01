package com.wyy.endpoint.action;

import com.wyy.common.http.ResponseData;
import com.wyy.endpoint.action.abs.AbstractEndpointAction;
import com.wyy.endpoint.service.IActionService;
import com.wyy.jwt.annotation.JWTCheckToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@RestController
@RequestMapping("/hr/endpoint/action/*")
public class ActionAction extends AbstractEndpointAction {
    @Autowired
    private IActionService actionService; // 注入部门业务实例
    @JWTCheckToken(role = "member")
    @GetMapping("list_role_action")
    public Object listByRole(@RequestBody Map<String, String> params) { // 查询全部工资等级
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh()); // 刷新token
        data.setResult(this.actionService.listByRole(params.get("rid"))); // 调用业务处理方法
        return data; // 响应数据
    }
}
