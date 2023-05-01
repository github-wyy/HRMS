package com.wyy.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wyy.common.dto.DeptDTO;
import com.wyy.common.dto.EmpDTO;
import com.wyy.common.dto.RatingDTO;
import com.wyy.common.service.abs.AbstractService;
import com.wyy.common.type.EmpResponseType;
import com.wyy.common.type.RecordOperateType;
import com.wyy.common.type.TableName;
import com.wyy.endpoint.component.RecordSender;
import com.wyy.endpoint.service.IEmpService;
import com.wyy.endpoint.dao.IDeptDAO;
import com.wyy.endpoint.dao.IEmpDAO;
import com.wyy.endpoint.dao.IRatingDAO;
import com.wyy.endpoint.dao.IRecordDAO;
import com.wyy.endpoint.vo.Dept;
import com.wyy.endpoint.vo.Emp;
import com.wyy.endpoint.vo.Rating;
import com.wyy.endpoint.vo.Record;
import com.wyy.util.bean.DeepBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpServiceImpl extends AbstractService implements IEmpService {
    @Autowired
    private IEmpDAO empDAO; // 获取雇员数据层接口实例
    @Autowired
    private IDeptDAO deptDAO; // 部门数据层实例
    @Autowired
    private IRatingDAO ratingDAO; // 等级数据层实例
    @Autowired
    private ObjectMapper objectMapper; // Jackson数据处理对象实例
    @Autowired
    private IRecordDAO recordDAO; // 操作记录对象实例
    @Autowired
    private RecordSender recordSender; // 消息发送对象实例

    @Override
    public Map<String, Object> split(long currentPage, long lineSize, String column, String keyword) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<Emp> wrapper = new QueryWrapper<>(); // 获取查询包装器
        if (super.isLike(column, keyword)) { // 判断是否需要模糊匹配
            wrapper.like(column, keyword); // 设置模糊查询列
        }
        Integer count = this.empDAO.selectCount(wrapper); // 数据查询
        result.put("count", count); // 保存查询统计结果
        Page<Emp> empPage = new Page<>(currentPage, lineSize, count); // 创建分页器
        IPage<Emp> resultPage = this.empDAO.selectPage(empPage, wrapper); // 数据查询
        List<EmpDTO> allEmps = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                resultPage.getRecords(), EmpDTO::new); // 查询全部数据
        result.put("data", allEmps); // 获取查询结果
        result.put("totalPage", resultPage.getTotal());
        return result;
    }

    @Override
    public EmpResponseType add(EmpDTO emp, String mid, String name) {
        // 1、由于雇员编号为手工配置，所以在数据增加前首先判断要增加雇员的编号是否重复
        if (this.empDAO.selectById(emp.getEmpno()) != null) { // 雇员编号存在
            return EmpResponseType.EMPNO_EXISTS; // 返回响应结果
        }
        // 2、判断当前所处的部门是否有空余名额
        Dept dept = this.deptDAO.selectById(emp.getDeptno()); // 获取部门编号
        if (dept == null) { // 部门不存在
            return EmpResponseType.FAILURE; // 返回响应结果
        }
        if (dept.getCurrent() >= dept.getBound()) { // 人数已经满员
            return EmpResponseType.DEPT_FULL; // 返回响应结果
        }
        // 3、判断工资范围
        Rating rating = this.ratingDAO.selectById(emp.getRtid()); // 获取职位信息
        if (emp.getSal() < rating.getLow() || emp.getSal() > rating.getHigh()) {    // 工资范围错误
            return EmpResponseType.RATING_ERROR; // 返回响应结果
        }
        // 4、将DTO数据拷贝到VO对象实例之中，以便进行数据层更新操作
        Emp empVO = new Emp(); // 实例化VO对象
        DeepBeanUtils.copyProperties(emp, empVO); // 对象属性拷贝
        // 5、设置雇佣日期为当前日期
        empVO.setHiredate(new Date());
        // 6、执行数据更新操作
        int count = this.empDAO.insert(empVO);
        // 7、执行部门人数自增处理
        if (count > 0) {    // 数据增加成功
            this.deptDAO.doIncrementCurrent(empVO.getDeptno()); // 8、更新部门中的已有雇员数量
        }
        // 8、执行数据记录，正规的做法是做一个分布式的消息处理
        Record record = new Record(); // 实例化记录操作对象实例
        record.setMid(mid); // 用户ID
        record.setName(name); // 用户姓名
        record.setOperate(RecordOperateType.ADD); // 设置操作类型
        record.setTab(TableName.EMP); // 设置操作表名称
        record.setUdate(new Date()); // 记录存储日期
        try {
            record.setData(this.objectMapper.writeValueAsString(empVO)); // 操作数据转为json保存
        } catch (JsonProcessingException e) {}

        recordSender.sendMessage(record);// 发送记录消息
        return EmpResponseType.SUCCESS;
    }

    @Override
    public Map<String, Object> preAdd() {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();	// 查询包装器
        List<DeptDTO> allDepts = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                this.deptDAO.selectList(deptWrapper), DeptDTO::new); // 查询全部数据
        result.put("depts", allDepts); // 保存全部部门数据
        QueryWrapper<Rating> ratingWrapper = new QueryWrapper<>();	// 查询包装器
        List<RatingDTO> allRatings = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                this.ratingDAO.selectList(ratingWrapper), RatingDTO::new); // 查询全部数据
        result.put("ratings", allRatings); // 保存全部等级数据
        return result;
    }

    @Override
    public Map<String, Object> preEdit(long empno) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<Dept> deptWrapper = new QueryWrapper<>();    // 查询包装器
        List<DeptDTO> allDepts = DeepBeanUtils.copyListProperties(    // 集合数据拷贝
                this.deptDAO.selectList(deptWrapper), DeptDTO::new); // 查询全部数据
        result.put("depts", allDepts); // 保存全部部门数据
        QueryWrapper<Rating> ratingWrapper = new QueryWrapper<>();    // 查询包装器
        List<RatingDTO> allRatings = DeepBeanUtils.copyListProperties(    // 集合数据拷贝
                this.ratingDAO.selectList(ratingWrapper), RatingDTO::new); // 查询全部数据
        result.put("ratings", allRatings); // 保存全部等级数据
        Emp empVO = this.empDAO.selectById(empno); // 查询雇员信息
        if (empVO != null) {
            EmpDTO empDTO = new EmpDTO(); // 实例化DTO对象
            DeepBeanUtils.copyProperties(empVO, empDTO); // 属性拷贝
            result.put("emp", empDTO);  // 保存当前雇员数据
        }
        return result;
    }

    @Override
    public EmpResponseType edit(EmpDTO emp, String mid, String name) { // 部门数据更新
        // 1、查询原始雇员数据，主要是确认本次更新是否引起了部门信息的更新
        Emp originEmp = this.empDAO.selectById(emp.getEmpno()); // 查询雇员数据

        // 2、判断当前的雇员是否为部门领导，如果是则不允许轻易更新
        QueryWrapper<Dept> deptMgrWrapper = new QueryWrapper<>();
        deptMgrWrapper.eq("empno", emp.getEmpno()); // 查询是否有领导编号
        deptMgrWrapper.eq("deptno", originEmp.getDeptno()); // 不是本部门
        Dept mgrDept = this.deptDAO.selectOne(deptMgrWrapper); // 部门查询
        System.err.println(mgrDept);
        if (mgrDept != null) {  // 雇员是领导
            return EmpResponseType.DEPT_MGR_ERROR;
        }

        if (!originEmp.getDeptno().equals(emp.getDeptno())) { // 部门发生改变
            // 3、判断新部门是否有空余位置
            Dept dept = this.deptDAO.selectById(emp.getDeptno()); // 获取部门编号
            if (dept.getCurrent() >= dept.getBound()) { // 人数已经满员
                return EmpResponseType.DEPT_FULL; // 返回响应结果
            }
        }
        // 4、判断工资范围
        Rating rating = this.ratingDAO.selectById(emp.getRtid()); // 获取职位信息
        if (emp.getSal() < rating.getLow() || emp.getSal() > rating.getHigh()) {    // 工资范围错误
            return EmpResponseType.RATING_ERROR; // 返回响应结果
        }
        // 5、将DTO数据拷贝到VO对象实例之中，以便进行数据层更新操作
        Emp empVO = new Emp(); // 实例化VO对象
        DeepBeanUtils.copyProperties(emp, empVO); // 对象属性拷贝
        // 6、执行数据更新操作
        int count = this.empDAO.updateById(empVO);
        // 7、部门修改判断
        if (!originEmp.getDeptno().equals(emp.getDeptno())) { // 部门编号发生改变
            this.deptDAO.doDecrementCurrent(originEmp.getDeptno()); // 减少原始部门的人数
            this.deptDAO.doIncrementCurrent(emp.getDeptno()); // 增加新部门的人数
        }
        // 8、执行数据记录，正规的做法是做一个分布式的消息处理
        Record record = new Record(); // 实例化记录操作对象实例
        record.setMid(mid); // 用户ID
        record.setName(name); // 用户姓名
        record.setOperate(RecordOperateType.EDIT); // 设置操作类型
        record.setTab(TableName.EMP); // 设置操作表名称
        record.setUdate(new Date()); // 记录存储日期
        try {
            record.setData(this.objectMapper.writeValueAsString(empVO)); // 操作数据转为json保存
        } catch (JsonProcessingException e) {}
        recordSender.sendMessage(record);// 发送记录消息 保存操作记录
        // 9、判断要更新的雇员信息是否为部门领导，如果是则需要更新部门表中的领导姓名
        QueryWrapper<Dept> deptQuery = new QueryWrapper<>();
        deptQuery.eq("deptno", emp.getDeptno()); // 部门编号判断
        deptQuery.eq("empno", emp.getEmpno()); // 领导编号判断
        Dept dept = this.deptDAO.selectOne(deptQuery); // 部门查询
        if (dept != null) { // 该雇员是领导，更新雇员姓名
            this.deptDAO.doEditMgr(Map.of("deptno", emp.getDeptno(), "empno", emp.getEmpno(), "mname", emp.getEname()));
            Record record2 = new Record(); // 实例化记录操作对象实例
            record2.setMid(mid); // 用户ID
            record2.setName(name); // 用户姓名
            record2.setOperate(RecordOperateType.EDIT); // 设置操作类型
            record2.setTab(TableName.DEPT); // 设置操作表名称
            record2.setUdate(new Date()); // 记录存储日期
            try {
                record2.setData(this.objectMapper.writeValueAsString(dept)); // 操作数据转为json保存
            } catch (JsonProcessingException e) {}
            recordSender.sendMessage(record2);// 发送记录消息 保存操作记录
        }
        return EmpResponseType.SUCCESS;
    }

    @Override
    public EmpResponseType remove(long empno, String mid, String name) {
        // 1、如果要想进行部门数据的更新，则首先应该查询出要删除雇员的原始信息
        Emp emp = this.empDAO.selectById(empno); // 查询出已有的用户信息
        if (emp == null) { // 没有对应的雇员信息
            return EmpResponseType.FAILURE;
        }
        // 2、查询部门数据
        Dept dept = this.deptDAO.selectById(emp.getDeptno());
        if (dept.getEmpno() != null && dept.getEmpno().equals(empno)) { // 雇员为部门领导
            // 3、更新部门中的领导信息，此时如果要删除的雇员是部门领导，则会清除部门表中指定行的部分字段信息，如果不是则不会清除
            this.deptDAO.doClearMgr(emp.getDeptno()); // 清除领导信息
        }
        // 4、由于删除了雇员信息，需要减少对应部门中的当前人数
        this.deptDAO.doDecrementCurrent(emp.getDeptno()); // 减少部门当前人数
        // 5、删除当前雇员信息
        this.empDAO.deleteById(empno);
        // 6、进行雇员信息记录
        Record empRecord = new Record(); // 实例化记录操作对象实例
        empRecord.setMid(mid); // 用户ID
        empRecord.setName(name); // 用户姓名
        empRecord.setOperate(RecordOperateType.REMOVE); // 设置操作类型
        empRecord.setTab(TableName.EMP); // 设置操作表名称
        empRecord.setUdate(new Date()); // 记录存储日期
        try {
            empRecord.setData(this.objectMapper.writeValueAsString(emp)); // 操作数据转为json保存
        } catch (JsonProcessingException e) {}
        this.recordDAO.insert(empRecord); // 保存操作记录
        if (dept.getEmpno().equals(empno)) {
            // 7、进行部门信息记录
            Record deptRecord = new Record(); // 实例化记录操作对象实例
            deptRecord.setMid(mid); // 用户ID
            deptRecord.setName(name); // 用户姓名
            deptRecord.setOperate(RecordOperateType.EDIT); // 设置操作类型
            deptRecord.setTab(TableName.DEPT); // 设置操作表名称
            deptRecord.setUdate(new Date()); // 记录存储日期
            try {
                deptRecord.setData(this.objectMapper.writeValueAsString(dept)); // 操作数据转为json保存
            } catch (JsonProcessingException e) {
            }
            recordSender.sendMessage(deptRecord);// 发送记录消息 保存操作记录
        }
        return EmpResponseType.SUCCESS;
    }
}
