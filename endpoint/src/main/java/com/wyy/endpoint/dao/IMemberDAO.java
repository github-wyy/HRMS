package com.wyy.endpoint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyy.endpoint.vo.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IMemberDAO extends BaseMapper<Member> {
}
