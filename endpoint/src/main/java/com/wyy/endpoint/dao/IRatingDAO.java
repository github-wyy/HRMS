package com.wyy.endpoint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyy.endpoint.vo.Rating;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRatingDAO extends BaseMapper<Rating> {
}
