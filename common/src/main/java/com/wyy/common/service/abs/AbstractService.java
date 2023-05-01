package com.wyy.common.service.abs;

public abstract class AbstractService {
    /**
     * 判断当前的业务查询是否需要进行数据模糊匹配
     * @param column 当前列
     * @param keyword 查询关键字
     * @return 如果需要模糊匹配返回true，否则返回false
     */
    public boolean isLike(String column, String keyword) {
        return column != null && !column.equals("") && keyword != null && !keyword.equals("");
    }
}
