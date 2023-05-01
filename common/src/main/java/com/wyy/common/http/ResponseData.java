package com.wyy.common.http;

import lombok.Data;

@Data
public class ResponseData {
    private int code; // HTTP响应状态码
    private Object result; // 最终响应的数据
    private String token; // JWT认证数据
}
