package com.wyy.common.dto;

import lombok.Data;

@Data
public class DeptDTO {
    private Long deptno;
    private String dname;
    private Integer bound;
    private Integer current;
    private Long empno;
    private String mname;
}
