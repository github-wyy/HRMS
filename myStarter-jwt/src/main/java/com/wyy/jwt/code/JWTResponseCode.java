package com.wyy.jwt.code;

import javax.servlet.http.HttpServletResponse;

public enum JWTResponseCode { // 定义为一个枚举类
    SUCCESS_CODE(HttpServletResponse.SC_OK, "Token数据正确，服务正常访问！"),
    TOKEN_TIMEOUT_CODE(HttpServletResponse.SC_BAD_REQUEST, "Token信息已经失效，需要重新申请！"),
    NO_AUTH_CODE(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "没有找到匹配的Token信息，无法进行服务访问！");
    private int code; // 响应的代码
    private String result; // 响应信息
    private JWTResponseCode(int code, String result) {
        this.code = code;
        this.result = result;
    }
    public String toString() {  // 直接将数据以JSON的形式返回
        return "{\"code\":" + this.code + ",\"result\":" + this.result + "}";
    }
}
