package com.wyy.common.dto;

import lombok.Data;

@Data
public class RatingDTO {
    private String rtid;
    private String name;
    private Double low;
    private Double high;
    private String note;
}
