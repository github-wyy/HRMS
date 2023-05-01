package com.wyy.endpoint.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("dept")
public class Dept {
    @TableId
    private Long deptno;
    private String dname;
    private Integer bound;
    private Integer current;
    private Long empno;
    private String mname;
}
