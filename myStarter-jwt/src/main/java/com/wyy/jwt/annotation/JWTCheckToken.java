package com.wyy.jwt.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) // 在方法上使用
@Retention(RetentionPolicy.RUNTIME) // 运行时生效
public @interface JWTCheckToken {
    boolean required() default true; // 配置的启用，认证排查
    String role() default ""; // 角色检查
    String action() default ""; // 权限检查
}
