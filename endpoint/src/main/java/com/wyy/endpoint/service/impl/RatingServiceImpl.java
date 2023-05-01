package com.wyy.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyy.common.dto.RatingDTO;
import com.wyy.common.service.abs.AbstractService;
import com.wyy.endpoint.service.IRatingService;
import com.wyy.endpoint.dao.IRatingDAO;
import com.wyy.endpoint.vo.Rating;
import com.wyy.util.bean.DeepBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RatingServiceImpl extends AbstractService implements IRatingService {
    @Autowired
    private IRatingDAO ratingDAO; // 等级数据层实例
    @Override
    public List<RatingDTO> list() {
        QueryWrapper<Rating> wrapper = new QueryWrapper<>();	// 查询包装器
        List<RatingDTO> allRatings = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                this.ratingDAO.selectList(wrapper), RatingDTO::new); // 查询全部数据
        return allRatings;
    }
}
