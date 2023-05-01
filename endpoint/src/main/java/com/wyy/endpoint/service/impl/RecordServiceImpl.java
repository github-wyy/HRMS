package com.wyy.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyy.common.dto.RecordDTO;
import com.wyy.common.service.abs.AbstractService;
import com.wyy.endpoint.service.IRecordService;
import com.wyy.endpoint.dao.IRecordDAO;
import com.wyy.endpoint.vo.Record;
import com.wyy.util.bean.DeepBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RecordServiceImpl extends AbstractService implements IRecordService {
    @Autowired
    private IRecordDAO recordDAO; // 注入记录数据层实例
    @Override
    public Map<String, Object> split(long currentPage, long lineSize, String column, String keyword) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<Record> wrapper = new QueryWrapper<>(); // 获取查询包装器
        if (super.isLike(column, keyword)) { // 判断是否需要模糊匹配
            wrapper.like(column, "%" + keyword + "%"); // 设置模糊查询列
        }
        Integer count = this.recordDAO.selectCount(wrapper); // 数据查询
        result.put("count", count); // 保存查询统计结果
        Page<Record> empPage = new Page<>(currentPage, lineSize, count); // 创建分页器
        IPage<Record> resultPage = this.recordDAO.selectPage(empPage, wrapper); // 数据查询
        List<RecordDTO> allRecords = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                resultPage.getRecords(), RecordDTO::new); // 查询全部数据
        result.put("data", allRecords); // 获取查询结果
        result.put("totalPage", resultPage.getTotal());
        return result;
    }
}
