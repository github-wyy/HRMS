package com.wyy.endpoint.service;

import com.wyy.common.dto.RoleDTO;

import java.util.List;

public interface IRoleService {
    public List<RoleDTO> list(); // 列出全部的角色信息
    public List<RoleDTO> listByMember(String mid); // 查看用户已有的角色信息
}
