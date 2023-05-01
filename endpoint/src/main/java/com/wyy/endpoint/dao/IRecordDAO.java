package com.wyy.endpoint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyy.endpoint.vo.Record;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IRecordDAO extends BaseMapper<Record> {
}
