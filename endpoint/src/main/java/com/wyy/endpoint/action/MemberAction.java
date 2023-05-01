package com.wyy.endpoint.action;

import com.wyy.common.dto.MemberDTO;
import com.wyy.common.http.ResponseData;
import com.wyy.endpoint.action.abs.AbstractEndpointAction;
import com.wyy.endpoint.service.IMemberService;
import com.wyy.jwt.annotation.JWTCheckToken;
import com.wyy.jwt.service.IEncryptService;
import com.wyy.jwt.util.JWTMemberDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/hr/endpoint/member/*")
public class MemberAction extends AbstractEndpointAction {
    @Autowired
    private IMemberService memberService; // 用户服务接口实例
    @Autowired
    private JWTMemberDataService jwtMemberDataService; // 用于刷新token
    @Autowired
    private IEncryptService encryptService; // 加密服务实例
    @PostMapping("login") // 只能以POST提交类型为住
    public Object login(@RequestBody MemberDTO dto) {
        dto.setPassword(this.encryptService.getEncryptPassword(dto.getPassword())); // 密码加密
        Map<String, Object> result = this.memberService.login(dto); // 登录认证处理
        ResponseData data = new ResponseData(); // 数据响应处理
        // 业务处理完成后，如果用户认证成功，则会在Map集合之中保存有mid数据项
        if (result.containsKey("mid")) { // 登录成功，进行JWT处理
            String id = "hr-" + UUID.randomUUID(); // 随意生成一个JWT-ID数据
            data.setCode(HttpServletResponse.SC_OK); // 设置200状态码，表示当前用户登录成功
            // Token数据的生成需要在application.yml中配置“yootk.security.config”相关处理项
            data.setToken(super.tokenService.createToken(id, result)); // 生成JWT的Token数据
            data.setResult(result); // 将数据响应给客户端
        } else {    // 登录失败
            data.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 认证失败返回500错误
        }
        return data; // 数据响应
    }
    @JWTCheckToken(role = "member", action = "member:list")
    @GetMapping("list")
    public Object list(Long currentPage, Long lineSize, String column, String keyword) { // 查询全部工资等级
        ResponseData data = new ResponseData(); // 定义响应数据
        data.setCode(HttpServletResponse.SC_OK); // 状态码为200
        // 考虑到Token存在有效性时间的问题，在每次响应时都重新生成一个新的Token数据，如果Token有效时间较长，则可以屏蔽此操作
        data.setToken(super.refresh());
        // long currentPage, int lineSize, String column, String keyword
        data.setResult(this.memberService.split(currentPage, lineSize, column, keyword)); // 调用业务处理方法
        return data; // 响应数据
    }
}
