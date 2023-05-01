package com.wyy.endpoint.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
@Data
@TableName("record")
public class Record {
    @TableId
    private Long recid;
    private Date udate;
    private String mid;
    private String name;
    private String operate;
    private String data;
    private String tab;
}
