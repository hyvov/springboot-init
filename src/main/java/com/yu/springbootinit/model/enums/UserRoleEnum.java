package com.yu.springbootinit.model.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.ObjectUtils;

/**
 * 用户角色枚举
 *
 * 该枚举类定义了用户角色的枚举值，包括用户、管理员和被封号三种角色。
 */
public enum UserRoleEnum {

    USER("用户", "user"), // 用户角色枚举值
    ADMIN("管理员", "admin"), // 管理员角色枚举值
    BAN("被封号", "ban"); // 被封号角色枚举值

    /**
     * 角色描述文本
     */
    private final String text;

    /**
     * 角色值
     */
    private final String value;

    /**
     * 枚举构造方法
     *
     * @param text 角色描述文本
     * @param value 角色值
     */
    UserRoleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 获取所有枚举值的列表
     *
     * @return 返回包含所有枚举值的列表
     */
    public static List<String> getValues() {
        return Arrays.stream(values()).map(item -> item.value).collect(Collectors.toList());
    }

    /**
     * 根据角色值获取对应的枚举对象
     *
     * @param value 角色值
     * @return 返回与角色值对应的枚举对象，如果找不到则返回 null
     */
    public static UserRoleEnum getEnumByValue(String value) {
        if (ObjectUtils.isEmpty(value)) {
            return null;
        }
        for (UserRoleEnum anEnum : UserRoleEnum.values()) {
            if (anEnum.value.equals(value)) {
                return anEnum;
            }
        }
        return null;
    }

    /**
     * 获取角色值
     *
     * @return 返回枚举对象的角色值
     */
    public String getValue() {
        return value;
    }

    /**
     * 获取角色描述文本
     *
     * @return 返回枚举对象的角色描述文本
     */
    public String getText() {
        return text;
    }
}