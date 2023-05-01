package com.wyy.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyy.common.dto.DeptDTO;
import com.wyy.common.service.abs.AbstractService;
import com.wyy.common.type.DeptResponseType;
import com.wyy.common.type.RecordOperateType;
import com.wyy.common.type.TableName;
import com.wyy.endpoint.component.RecordSender;
import com.wyy.endpoint.service.IDeptService;
import com.wyy.endpoint.dao.IDeptDAO;
import com.wyy.endpoint.dao.IEmpDAO;
import com.wyy.endpoint.dao.IRecordDAO;
import com.wyy.endpoint.vo.Dept;
import com.wyy.endpoint.vo.Emp;
import com.wyy.endpoint.vo.Record;
import com.wyy.util.bean.DeepBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class DeptServiceImpl extends AbstractService implements IDeptService {
    @Autowired
    private IDeptDAO deptDAO; // 注入部门数据层实例
    @Autowired
    private IEmpDAO empDAO;// 注入部门数据层实例
    @Autowired
    private IRecordDAO recordDAO; // 注入记录数据层实例
    @Autowired
    private ObjectMapper objectMapper; // JSON转换
    @Autowired
    private RecordSender recordSender; // 消息发送对象实例
    @Override
    public List<DeptDTO> list() {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();	// 查询包装器
        List<DeptDTO> allDepts = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                this.deptDAO.selectList(wrapper), DeptDTO::new); // 查询全部数据
        return allDepts;
    }

    @Override
    public DeptResponseType editMgr(DeptDTO dto, String mid, String name) {
        // 1、查询部门信息，便于判断当前设置的领导信息是否重复
        Dept dept = this.deptDAO.selectById(dto.getDeptno()); // 查询部门信息
        // 2、判断当前部门领导
        if (dept.getEmpno() == null || !dept.getEmpno().equals(dto.getEmpno())) { // 当前未变更领导信息
            // 3、判断当前的雇员是否为本部门的员工，如果不是则不允许更新
            Emp emp = this.empDAO.selectById(dto.getEmpno()); // 查询雇员信息
            if (emp == null) { // 雇员不存在
                return DeptResponseType.UNKNOW_EMP;
            }
            if (!emp.getDeptno().equals(dto.getDeptno())) { // 雇员所在部门不匹配
                return DeptResponseType.DEPT_INVALIDATE; // 返回错误信息
            }
            // 4、判断员工级别
            if (emp.getRtid().compareTo("YOOTK-5") < 0) { // 错误的级别
                return DeptResponseType.RATING_INVALIDATE; // 返回错误信息
            }
            // 5、更新部门领导信息
            this.deptDAO.doEditMgr(Map.of("deptno", dto.getDeptno(), "empno", dto.getEmpno(), "mname", emp.getEname()));
        } else { // 当前需要变更领导信息
            return DeptResponseType.FAILURE;
        }
        Record record = new Record(); // 实例化记录操作对象实例
        record.setMid(mid); // 用户ID
        record.setName(name); // 用户姓名
        record.setOperate(RecordOperateType.EDIT); // 设置操作类型
        record.setTab(TableName.DEPT); // 设置操作表名称
        record.setUdate(new Date()); // 记录存储日期
        try {
            record.setData(this.objectMapper.writeValueAsString(dept)); // 操作数据转为json保存
        } catch (JsonProcessingException e) {}
        recordSender.sendMessage(record);// 发送记录消息 保存操作记录
        return DeptResponseType.SUCCESS;
    }

    @Override
    public DeptResponseType add(DeptDTO dto, String mid, String name) {
        Dept dept = new Dept(); // 实例化VO对象
        DeepBeanUtils.copyProperties(dto, dept); // 对象数据拷贝
        dept.setCurrent(0); // 新部门的雇员人数为0
        if (this.deptDAO.insert(dept) > 0) { // 部门数据增加成功
            return DeptResponseType.SUCCESS; // 增加成功
        }
        Record record = new Record(); // 实例化记录操作对象实例
        record.setMid(mid); // 用户ID
        record.setName(name); // 用户姓名
        record.setOperate(RecordOperateType.ADD); // 设置操作类型
        record.setTab(TableName.DEPT); // 设置操作表名称
        record.setUdate(new Date()); // 记录存储日期
        try {
            record.setData(this.objectMapper.writeValueAsString(dept)); // 操作数据转为json保存
        } catch (JsonProcessingException e) {}
        recordSender.sendMessage(record);// 发送记录消息 保存操作记录
        return DeptResponseType.FAILURE; // 增加失败
    }

    @Override
    public DeptResponseType edit(DeptDTO dto, String mid, String name) {
        // 1、查询雇员人数
        Integer count = this.empDAO.selectCount(new QueryWrapper<Emp>().eq("deptno", dto.getDeptno()));
        // 2、人数信息判断
        if (dto.getBound() < count) { // 人数错误
            return DeptResponseType.DEPT_MORE_NUMBER; // 返回错误状态
        }
        // 3、如果人数满足要求，则直接更新部门信息
        Dept dept = new Dept(); // 实例化VO对象
        DeepBeanUtils.copyProperties(dto, dept); // 属性拷贝

        this.deptDAO.doUpdateBase(dept);// 数据更新

        Record record = new Record(); // 实例化记录操作对象实例
        record.setMid(mid); // 用户ID
        record.setName(name); // 用户姓名
        record.setOperate(RecordOperateType.EDIT); // 设置操作类型
        record.setTab(TableName.DEPT); // 设置操作表名称
        record.setUdate(new Date()); // 记录存储日期
        try {
            record.setData(this.objectMapper.writeValueAsString(dept)); // 操作数据转为json保存
        } catch (JsonProcessingException e) {}
        recordSender.sendMessage(record);// 发送记录消息 保存操作记录
        return DeptResponseType.SUCCESS; // 更新成功
    }

    @Override
    public DeptResponseType remove(long deptno, String mid, String name) {
        // 1、查询部门数据，为便于进行操作记录
        Dept dept = this.deptDAO.selectById(deptno);
        // 2、删除该部门对应的雇员信息
        if (dept.getCurrent() > 0) { // 该部门有数据
            this.empDAO.doEditDeptno(deptno); // 清空部门雇员对应信息
        }
        // 3、删除部门数据
        this.deptDAO.deleteById(deptno); // 根据id删除数据
        Record record = new Record(); // 实例化记录操作对象实例
        record.setMid(mid); // 用户ID
        record.setName(name); // 用户姓名
        record.setOperate(RecordOperateType.REMOVE); // 设置操作类型
        record.setTab(TableName.DEPT); // 设置操作表名称
        record.setUdate(new Date()); // 记录存储日期
        try {
            record.setData(this.objectMapper.writeValueAsString(dept)); // 操作数据转为json保存
        } catch (JsonProcessingException e) {}
        recordSender.sendMessage(record);// 发送记录消息 保存操作记录
        return DeptResponseType.SUCCESS;
    }

    @Override
    public DeptDTO get(long deptno) {
        Dept dept = this.deptDAO.selectById(deptno); // 根据id查看用户信息
        DeptDTO dto = new DeptDTO(); // 实例化DTO对象
        DeepBeanUtils.copyProperties(dept, dto); // 属性拷贝
        return dto;
    }
}
