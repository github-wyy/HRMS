package com.wyy.endpoint.service;

import com.wyy.common.dto.MemberDTO;

import java.util.Map;

public interface IMemberService {
    /**
     * 实现用户登录处理，用户登录时根据传入的用户名和密码进行登录认证，并获取用户对应的角色以及权限数据
     * @param dto 保存用户认证信息的对象
     * @return 用户数据，如果认证失败则返回null，如果成功则返回如下数据内容：
     * 1、key = mid：用户id；
     * 2、key = name：用户真实姓名；
     * 3、key = flag：管理员标记；
     * 4、key = roles：返回用户对应的角色数据；
     * 5、key = actions：返回用户对应的权限数据。
     */
    public Map<String, Object> login(MemberDTO dto);
    /**
     * 用户信息列表显示
     * @param currentPage 当前所在页
     * @param lineSize 每页显示数据行数
     * @param column 模糊查询列
     * @param keyword 查询关键字
     * @return 返回结果包含有用户数据集合和行数统计，使用Map存储，包含的数据项信息如下：
     * key = count：数据行数统计
     * key = data：用户数据集合
     */
    public Map<String, Object> split(long currentPage, long lineSize, String column, String keyword);
}
