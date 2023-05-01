package com.wyy.common.dto;

import lombok.Data;

import java.util.Date;
@Data
public class EmpDTO {
    private Long empno;
    private Long deptno;
    private String rtid;
    private String ename;
    private Date hiredate;
    private Double sal;
    private String job;
}
