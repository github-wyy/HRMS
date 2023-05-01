package com.wyy.endpoint.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("action")
public class Action {
    @TableId
    private String aid;
    private String rid;
    private String name;
    private String note;
}
