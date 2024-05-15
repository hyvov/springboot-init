package com.yu.springbootinit.constant;

/**
 * 用户相关常量接口
 *
 * 该接口定义了与用户相关的常量信息，包括用户登录态键和用户角色等信息。
 */
public interface UserConstant {

    /**
     * 用户登录态键，用于存储用户登录状态的键名
     */
    String USER_LOGIN_STATE = "user_login";

    //  region 权限

    /**
     * 默认角色，表示普通用户的角色
     */
    String DEFAULT_ROLE = "user";

    /**
     * 管理员角色，表示具有管理员权限的角色
     */
    String ADMIN_ROLE = "admin";

    /**
     * 被封号角色，表示被封禁用户的角色
     */
    String BAN_ROLE = "ban";

    // endregion
}