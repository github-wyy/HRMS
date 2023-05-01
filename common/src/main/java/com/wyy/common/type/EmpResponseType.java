package com.wyy.common.type;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT) // 枚举实例转JSON数据
public enum EmpResponseType {
    SUCCESS(1000, "雇员数据更新成功。"),
    EMPNO_EXISTS(1001, "雇员编号已存在，无法增加新雇员。"),
    DEPT_FULL(1002, "部门人数已满，无法增加新雇员。"),
    RATING_ERROR(1003, "该雇员的等级与工资待遇不满足公司要求。"),
    FAILURE(1004, "雇员信息错误，无法实现雇员更新操作。"),
    DEPT_MGR_ERROR(1005, "该雇员为部门主管，不允许更新。");
    private int status; // 保存一个自定义的状态值
    private String message; // 保存错误信息

    private EmpResponseType(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "{'status':" + this.status + ",'message':'" + this.message + "'}";
    }
}
