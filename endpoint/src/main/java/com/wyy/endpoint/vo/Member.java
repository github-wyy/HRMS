package com.wyy.endpoint.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("member")
public class Member {
    @TableId
    private String mid;
    private String name;
    private String password;
    private int flag;
}
