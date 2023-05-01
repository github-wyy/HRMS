package com.wyy.endpoint.action.abs;

import com.wyy.common.TokenConst;
import com.wyy.common.action.abs.AbstractBaseAction;
import com.wyy.jwt.service.ITokenService;
import com.wyy.jwt.util.JWTMemberDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 负责处理本模块中Action的公共操作类
 */
public class AbstractEndpointAction extends AbstractBaseAction {
    @Autowired
    protected JWTMemberDataService jwtMemberDataService;
    @Autowired
    protected ITokenService tokenService; // 注入Token操作业务接口实例
    public String mid() {    // 通过JWT获取保存的mid数据
        return this.jwtMemberDataService.mid(token()); // 通过token获取MID
    }
    public String name() { // 通过JWT获取name数据
        return this.jwtMemberDataService.name(token()); // 通过token获取NAME
    }
    public String token() { // 获取token数据
        return this.jwtMemberDataService.getToken(this.request(), TokenConst.TOKEN_NAME);
    }
    public String refresh() { // 获取新token
        return this.tokenService.refreshToken(this.token());
    }
    public HttpServletRequest request() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();		// 获取请求属性
        HttpServletRequest request = attributes.getRequest();	// 获取Request
        return request;
    }
    public HttpServletResponse response() {
        ServletRequestAttributes attributes = (ServletRequestAttributes)
                RequestContextHolder.getRequestAttributes();		// 获取请求属性
        HttpServletResponse response = attributes.getResponse();	// 获取Response
        return response;
    }
}
