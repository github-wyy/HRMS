package com.wyy.common.type;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT) // 枚举实例转JSON数据
public enum DeptResponseType {
    SUCCESS(1000, "部门数据更新成功。"),
    FAILURE(1001, "部门数据更新失败。"),
    DEPT_INVALIDATE(1002, "雇员不属于本部门，无法设置为部门领导"),
    RATING_INVALIDATE(1003, "雇员等级不符合部门主管标准"),
    DEPT_MORE_NUMBER(1005, "部门雇员人数过多"),
    UNKNOW_EMP(1006, "未知雇员信息");
    private int status; // 保存一个自定义的状态值
    private String message; // 保存错误信息

    private DeptResponseType(int status, String message) {
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
