package com.wyy.common.action.abs;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public abstract class AbstractBaseAction { // 定义一个公共的Action类
    // 在现在的开发之中如果要将字符串转为日期时间，考虑到多线程环境下的并发问题，所以一定要使用LocalDate
    private static final DateTimeFormatter LOCAL_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.util.Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                LocalDate localDate = LocalDate.parse(text, LOCAL_DATE_FORMAT);
                Instant instant = localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                super.setValue(java.util.Date.from(instant));
            }
        });
    }

    public int parseInteger(String data) { // 字符串转整型
        if (data.matches("\\d+")) {
            return Integer.parseInt(data);
        } else {
            return 0;
        }
    }
    public long parseLong(String data) { // 字符串转长整型
        if (data.matches("\\d+")) {
            return Long.parseLong(data);
        } else {
            return 0L;
        }
    }
    public double parseDouble(String data) {
        if (data.matches("\\d+(\\.\\d+)?")) {
            return Double.parseDouble(data);
        } else {
            return 0.0;
        }
    }
    public Boolean parseBoolean(String data) {
        return Boolean.parseBoolean(data);
    }
}
