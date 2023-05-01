package com.wyy.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wyy.common.dto.ActionDTO;
import com.wyy.common.service.abs.AbstractService;
import com.wyy.endpoint.dao.IActionDAO;
import com.wyy.endpoint.service.IActionService;
import com.wyy.endpoint.vo.Action;
import com.wyy.util.bean.DeepBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ActionServiceImpl extends AbstractService implements IActionService {
    @Autowired
    private IActionDAO actionDAO; // 权限数据层接口

    @Override
    public List<ActionDTO> listByRole(String rid) {
        QueryWrapper<Action> wrapper = new QueryWrapper<>();
        wrapper.eq("rid", rid); // 设置rid查询条件
        List<ActionDTO> allActions = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                this.actionDAO.selectList(wrapper), ActionDTO::new); // 查询全部数据
        return allActions;
    }
}
