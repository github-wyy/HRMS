package com.wyy.endpoint.service;

import com.wyy.common.dto.ActionDTO;

import java.util.List;

public interface IActionService {
    public List<ActionDTO> listByRole(String rid); // 根据角色ID查询权限数据
}
