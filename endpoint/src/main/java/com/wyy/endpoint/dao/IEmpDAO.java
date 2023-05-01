package com.wyy.endpoint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyy.endpoint.vo.Emp;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IEmpDAO extends BaseMapper<Emp> {
    /**
     * 部门信息删除时，删除对应雇员的部门编号
     * @param deptno 要部门编号
     * @return 更新状态
     */
    public boolean doEditDeptno(Long deptno);
}
