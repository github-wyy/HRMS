package com.wyy.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Data
public class MemberDTO {
    @NotBlank
    private String mid;
    private String name;
    @NotBlank
    private String password;
    private int flag;
    private List<RoleDTO> roles = new ArrayList<>();
}
