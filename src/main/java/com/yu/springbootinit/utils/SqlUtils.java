package com.yu.springbootinit.utils;

import org.apache.commons.lang3.StringUtils;



/**
 * SQL 工具类，提供 SQL 相关的辅助方法
 */
public class SqlUtils {

    /**
     * 校验排序字段是否合法（防止 SQL 注入）
     *
     * @param sortField 待校验的排序字段
     * @return 如果排序字段合法返回 true，否则返回 false
     */
    public static boolean validSortField(String sortField) {
        // 如果排序字段为空，则认为不合法
        if (StringUtils.isBlank(sortField)) {
            return false;
        }
        // 判断排序字段中是否包含 SQL 注入可能的关键字符
        return !StringUtils.containsAny(sortField, "=", "(", ")", " ");
    }
}