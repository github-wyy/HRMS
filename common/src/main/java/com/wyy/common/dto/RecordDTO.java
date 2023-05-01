package com.wyy.common.dto;

import lombok.Data;

import java.util.Date;

@Data
public class RecordDTO {
    private Long recid;
    private Date udate;
    private String mid;
    private String name;
    private String operate;
    private String data;
    private String tab;
}
