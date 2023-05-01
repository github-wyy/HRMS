package com.wyy.endpoint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyy.endpoint.vo.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Set;

@Mapper
public interface IRoleDAO extends BaseMapper<Role> {
    public Set<String> findAllByMember(String mid); // 获取用户角色
    public List<Role> findAllDetailsByMember(String mid); // 根据用户id查询对应角色
}
