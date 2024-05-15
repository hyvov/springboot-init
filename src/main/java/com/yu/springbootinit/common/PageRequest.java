package com.yu.springbootinit.common;

import com.yu.springbootinit.constant.CommonConstant;
import lombok.Data;

/**
 * 分页请求类
 *
 * 该类用于表示分页查询的请求对象，包含了当前页号、页面大小、排序字段和排序顺序等信息。
 */
@Data // Lombok 注解，自动生成 getter、setter 等方法
public class PageRequest {

    /**
     * 当前页号，默认为第一页
     */
    private int current = 1;

    /**
     * 页面大小，默认为每页显示 10 条记录
     */
    private int pageSize = 10;

    /**
     * 排序字段，用于指定查询结果的排序依据
     */
    private String sortField;

    /**
     * 排序顺序，可选值为 CommonConstant.SORT_ORDER_ASC（升序）或 CommonConstant.SORT_ORDER_DESC（降序），默认为升序
     */
    private String sortOrder = CommonConstant.SORT_ORDER_ASC;
}