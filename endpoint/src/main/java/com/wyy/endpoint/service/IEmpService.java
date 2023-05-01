package com.wyy.endpoint.service;

import com.wyy.common.dto.EmpDTO;
import com.wyy.common.type.EmpResponseType;

import java.util.Map;

public interface IEmpService {
    /**
     * 雇员信息列表显示
     * @param currentPage 当前所在页
     * @param lineSize 每页显示数据行数
     * @param column 模糊查询列
     * @param keyword 查询关键字
     * @return 返回结果包含有雇员数据集合和行数统计，使用Map存储，包含的数据项信息如下：
     * key = count：数据行数统计
     * key = data：雇员数据集合
     */
    public Map<String, Object> split(long currentPage, long lineSize, String column, String keyword);

    /**
     * 增加新雇员，该雇员增加操作需要进行如下的业务处理：
     * 1、判断当前增加雇员的部门人数是否已经满员，如果满员则无法增加
     * 2、雇员信息增加时需要判断工资与待遇等级是否匹配
     * 3、雇员信息增加完成后需要修改部门中的当前雇员数量
     * @param emp 新增雇员信息
     * @param mid 操作用户id
     * @param name 操作用户姓名
     * @return 根据雇员存储状态返回相应的结果
     */
    public EmpResponseType add(EmpDTO emp, String mid, String name);

    /**
     * 雇员信息增加前的数据查询
     * @return 需要返回部门与员工等级信息，包含数据如下：
     * key = depts：所有的部门数据
     * key = ratings：所有的等级数据
     */
    public Map<String, Object> preAdd();

    /**
     * 雇员更新前数据查询操作
     * @param empno 要修改的雇员编号
     * @return 等级数据、部门数据以及指定ID的雇员数据
     * 1、key = emp：要更新的雇员信息；
     * 2、key = depts：所有部门数据
     * 3、key = ratings：所有等级数据
     */
    public Map<String, Object> preEdit(long empno);

    /**
     * 更新雇员信息，该操作需要考虑如下的业务需要：
     * 1、考虑部门变更的因素，如果部门变更则需要检查目标部门人数是否符合要求；
     * 2、检查工资范围是否符合工资标准
     * 3、部门当前人员数量的加减处理
     * 4、操作记录
     * @param emp 要更新的雇员信息
     * @param mid 操作者用户id
     * @param name 操作者姓名
     * @return 根据更新状态返回相应结果
     */
    public EmpResponseType edit(EmpDTO emp, String mid, String name);

    /**
     * 雇员数据删除操作，删除时需要考虑到如下的操作：
     * 1、如果删除的雇员是部门领导，则需要先更新部门表中的相应部门的数据；
     * 2、雇员删除时需要更新部门中的已有人员数量
     * 3、根据编号删除雇员表中的数据内容
     * 4、操作记录
     * @param empno 要删除雇员id
     * @param mid 操作者用户id
     * @param name 操作者姓名
     * @return 根据删除状态返回相应结果
     */
    public EmpResponseType remove(long empno, String mid, String name);
}
