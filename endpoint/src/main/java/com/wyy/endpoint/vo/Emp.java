package com.wyy.endpoint.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("emp")
public class Emp {
    @TableId
    private Long empno;
    private Long deptno;
    private String rtid;
    private String ename;
    private Date hiredate;
    private Double sal;
    private String job;
}
