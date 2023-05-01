package com.wyy.util.bean;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class DeepBeanUtils extends BeanUtils {// 继承BeanUtils工具类
    private DeepBeanUtils() {
    } // 构造方法私有化

    /**
     * 实现List集合的拷贝处理
     *
     * @param sources 拷贝的原List集合
     * @param target  目标类型
     * @param <S>     源对象类型
     * @param <T>     目标对象类型
     * @return 拷贝后的List集合
     */
    public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
        List<T> list = new ArrayList<>(sources.size());    // 创建List集合
        for (S source : sources) {  // 源集合迭代
            T obj = target.get(); // 获取数据
            copyProperties(source, obj); // 属性拷贝
            list.add(obj); // 添加数据
        }
        return list;  // 集合返回
    }
}

