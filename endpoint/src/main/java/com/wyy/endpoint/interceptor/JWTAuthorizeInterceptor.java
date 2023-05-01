package com.wyy.endpoint.interceptor;

import com.wyy.common.TokenConst;
import com.wyy.jwt.annotation.JWTCheckToken;
import com.wyy.jwt.code.JWTResponseCode;
import com.wyy.jwt.service.ITokenService;
import com.wyy.jwt.util.JWTMemberDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class JWTAuthorizeInterceptor implements HandlerInterceptor {
    @Autowired // 区分出角色和权限的信息
    private JWTMemberDataService memberDataService;
    @Autowired // JWT有效性的检查
    private ITokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean flag = true; // 拦截
        if (!(handler instanceof HandlerMethod)) {  // 类型不匹配
           return flag;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler; // 因为需要对Action进行解析处理
        Method method = handlerMethod.getMethod(); // 获取调用的方法对象
        if (method.isAnnotationPresent(JWTCheckToken.class)) {  // 当前的方法上存在有指定注解
            // 如果发现此时出现了Token的错误则肯定要直接进行响应，不会走到Action响应上
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=utf-8");
            JWTCheckToken checkToken = method.getAnnotation(JWTCheckToken.class); // 获取配置注解
            if (checkToken.required()) { // 启用JWT检查
                // JWT的数据可能来自于参数或者是头信息
                String token = this.memberDataService.getToken(request, TokenConst.TOKEN_NAME);
                if (!StringUtils.hasLength(token)) {    // 没有Token数据
                    flag = false;
                    response.getWriter().println(JWTResponseCode.NO_AUTH_CODE); // 直接响应错误代码
                } else {    // 此时的Token存在
                    if (!this.tokenService.verifyToken(token)) {    // Token校验失败
                        flag = false;
                        response.getWriter().println(JWTResponseCode.TOKEN_TIMEOUT_CODE);
                    } else {    // Token没有失败
                        if (!(checkToken.role() == null || "".equals(checkToken.role()))) { // 需要进行角色检查
                            // 根据Token字符串解析出所有的角色集合，而后判断是否存在有指定的角色信息
                            if (this.memberDataService.roles(token).contains(checkToken.role())) {
                                flag = true; // 允许访问
                            } else { // 失败访问
                                response.getWriter().println(JWTResponseCode.NO_AUTH_CODE);
                                flag = false; // 不允许访问
                            }
                        } else if (!(checkToken.action() == null || "".equals(checkToken.action()))) {
                            if (this.memberDataService.actions(token).contains(checkToken.action())) {
                                flag = true; // 允许访问
                            } else { // 失败访问
                                response.getWriter().println(JWTResponseCode.NO_AUTH_CODE);
                                flag = false; // 不允许访问
                            }
                        } else {
                            flag = true;
                        }
                    }
                }
            }
        }
        return flag;
    }
}
