package com.wyy.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyy.common.dto.RoleDTO;
import com.wyy.common.service.abs.AbstractService;
import com.wyy.endpoint.service.IRoleService;
import com.wyy.endpoint.dao.IRoleDAO;
import com.wyy.endpoint.vo.Role;
import com.wyy.util.bean.DeepBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl extends AbstractService implements IRoleService {
    @Autowired
    private IRoleDAO roleDAO;

    @Override
    public List<RoleDTO> list() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();	// 查询包装器
        List<RoleDTO> allRoles = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                this.roleDAO.selectList(wrapper), RoleDTO::new); // 查询全部数据
        return allRoles;
    }

    @Override
    public List<RoleDTO> listByMember(String mid) {
        List<RoleDTO> allRoles = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                this.roleDAO.findAllDetailsByMember(mid), RoleDTO::new); // 查询全部数据
        return allRoles;
    }
}
