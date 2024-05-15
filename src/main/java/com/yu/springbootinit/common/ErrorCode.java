package com.yu.springbootinit.common;

/**
 * 自定义错误码枚举
 *
 * 该枚举类定义了一系列常见的错误码以及对应的错误信息，用于表示在应用中可能出现的各种错误情况。
 */
public enum ErrorCode {

    SUCCESS(0, "ok"),
    PARAMS_ERROR(40000, "请求参数错误"),
    NOT_LOGIN_ERROR(40100, "未登录"),
    NO_AUTH_ERROR(40101, "无权限"),
    NOT_FOUND_ERROR(40400, "请求数据不存在"),
    FORBIDDEN_ERROR(40300, "禁止访问"),
    SYSTEM_ERROR(50000, "系统内部异常"),
    OPERATION_ERROR(50001, "操作失败");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;

    /**
     * 构造方法，用于创建一个 ErrorCode 枚举对象
     *
     * @param code 状态码
     * @param message 错误信息
     */
    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取错误码
     *
     * @return 返回枚举对象的状态码
     */
    public int getCode() {
        return code;
    }

    /**
     * 获取错误信息
     *
     * @return 返回枚举对象的错误信息
     */
    public String getMessage() {
        return message;
    }
}