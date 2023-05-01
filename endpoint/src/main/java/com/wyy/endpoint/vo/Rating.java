package com.wyy.endpoint.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("rating")
public class Rating {
    @TableId
    private String rtid;
    private String name;
    private Double low;
    private Double high;
    private String note;
}
