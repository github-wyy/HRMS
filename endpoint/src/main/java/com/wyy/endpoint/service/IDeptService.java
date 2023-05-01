package com.wyy.endpoint.service;

import com.wyy.common.dto.DeptDTO;
import com.wyy.common.type.DeptResponseType;

import java.util.List;

public interface IDeptService {
    public List<DeptDTO> list(); // 查询全部部门数据

    /**
     * 更新部门对应的领导信息：
     * 1、查询部门当前领导是否为要操作的雇员信息；
     * 2、更新部门对应的领导信息
     * @param dto 部门领导信息
     * @param mid 操作者ID
     * @param name 操作者姓名
     * @return 返回更新状态
     */
    public DeptResponseType editMgr(DeptDTO dto, String mid, String name);

    /**
     * 增加部门信息
     * @param dto 部门数据
     * @param mid 操作用户id
     * @param name 操作用户姓名
     * @return 返回更新状态
     */
    public DeptResponseType add(DeptDTO dto, String mid, String name);

    /**
     * 更新部门数据
     * @param dto 修改后的部门数据
     * @param mid 操作用户id
     * @param name 操作用户姓名
     * @return 返回更新状态
     */
    public DeptResponseType edit(DeptDTO dto, String mid, String name);

    /**
     * 删除部门信息
     * @param deptno 部门id
     * @param mid 操作用户id
     * @param name 操作用户姓名
     * @return 返回更新状态
     */
    public DeptResponseType remove(long deptno, String mid, String name);

    /**
     * 根据部门ID查看部门信息
     * @param deptno 部门编号
     * @return 部门完整数据
     */
    public DeptDTO get(long deptno);
}
