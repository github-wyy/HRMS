package com.wyy.endpoint.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wyy.endpoint.vo.Dept;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface IDeptDAO extends BaseMapper<Dept> {
    /**
     * 部门已有人数自增1
     * @param deptno 要自增处理的部门编号
     * @return 自增成功返回true，否则返回false
     */
    public boolean doIncrementCurrent(Long deptno);

    /**
     * 部门已有人数自减1
     * @param deptno 要自减处理的部门编号
     * @return 自减成功返回true，否则返回false
     */
    public boolean doDecrementCurrent(Long deptno);

    /**
     * 更新部门领导信息
     * @param deptno 更新的部门编号
     * @return 更新成功返回true，否则返回false
     */
    public boolean doClearMgr(Long deptno);

    /**
     * 更新部门领导信息
     * @param params 更新参数，包含如下内容项：
     * 1、key = deptno：部门编号
     * 2、key = empno：领导-雇员编号
     * 3、key = mname：领导姓名
     * @return 更新成功返回true，否则返回false
     */
    public boolean doEditMgr(Map<String, Object> params);

    /**
     * 更新部门基础信息，不包括领导者信息
     * @param dept 包含部门基础信息
     * @return 更新成功返回true，否则返回false
     */
    public boolean doUpdateBase(Dept dept);
}
