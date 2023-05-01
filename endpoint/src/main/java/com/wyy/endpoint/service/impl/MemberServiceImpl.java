package com.wyy.endpoint.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wyy.common.dto.MemberDTO;
import com.wyy.common.service.abs.AbstractService;
import com.wyy.endpoint.service.IMemberService;
import com.wyy.endpoint.dao.IActionDAO;
import com.wyy.endpoint.dao.IMemberDAO;
import com.wyy.endpoint.dao.IRoleDAO;
import com.wyy.endpoint.vo.Member;
import com.wyy.util.bean.DeepBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MemberServiceImpl extends AbstractService implements IMemberService {
    @Autowired
    private IMemberDAO memberDAO; // 注入DAO接口实例
    @Autowired
    private IRoleDAO roleDAO; // 注入DAO接口实例
    @Autowired
    private IActionDAO actionDAO; // 注入DAO接口实例

    @Override
    public Map<String, Object> login(MemberDTO dto) {
        Map<String, Object> result = new HashMap<>(); // 保存登录结果
        Member member = this.memberDAO.selectById(dto.getMid()); // 根据ID查询
        if (member != null && member.getPassword().equals(dto.getPassword())) { // 密码验证
            result.put("mid", member.getMid()); // 保存用户id
            result.put("name", member.getName()); // 保存真实姓名
            result.put("flag", member.getFlag()); // 保存管理员标记
            result.put("roles", this.roleDAO.findAllByMember(member.getMid())); // 保存用户授权信息
            result.put("actions", this.actionDAO.findAllByMember(member.getMid())); // 保存用户权限信息
        }
        return result;
    }
    @Override
    public Map<String, Object> split(long currentPage, long lineSize, String column, String keyword) {
        Map<String, Object> result = new HashMap<>();
        QueryWrapper<Member> wrapper = new QueryWrapper<>(); // 获取查询包装器
        if (super.isLike(column, keyword)) { // 判断是否需要模糊匹配
            wrapper.like(column, "%" + keyword + "%"); // 设置模糊查询列
        }
        Integer count = this.memberDAO.selectCount(wrapper); // 数据查询
        result.put("count", count); // 保存查询统计结果
        Page<Member> empPage = new Page<>(currentPage, lineSize, count); // 创建分页器
        IPage<Member> resultPage = this.memberDAO.selectPage(empPage, wrapper); // 数据查询
        List<MemberDTO> allMembers = DeepBeanUtils.copyListProperties( 	// 集合数据拷贝
                resultPage.getRecords(), MemberDTO::new); // 查询全部数据
        result.put("data", allMembers); // 获取查询结果
        result.put("totalPage", resultPage.getTotal());
        return result;
    }
}
