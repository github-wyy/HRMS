package com.wyy.endpoint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyy.endpoint.vo.Action;
import org.apache.ibatis.annotations.Mapper;

import java.util.Set;

@Mapper
public interface IActionDAO extends BaseMapper<Action> {
    public Set<String> findAllByMember(String mid); // 获取用户权限
}
