package com.yu.springbootinit.exception;

import com.yu.springbootinit.common.ErrorCode;

/**
 * 自定义业务异常类
 *
 * 该异常类继承自 RuntimeException，用于表示业务逻辑中的异常情况。
 */
public class BusinessException extends RuntimeException {

    /**
     * 错误码，用于标识异常的具体错误类型
     */
    private final int code;

    /**
     * 构造方法，根据错误码和错误消息创建异常对象
     *
     * @param code 错误码
     * @param message 错误消息
     */
    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * 构造方法，根据 ErrorCode 对象创建异常对象
     *
     * @param errorCode 错误码枚举对象
     */
    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    /**
     * 构造方法，根据 ErrorCode 对象和错误消息创建异常对象
     *
     * @param errorCode 错误码枚举对象
     * @param message 错误消息
     */
    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    /**
     * 获取错误码
     *
     * @return 返回异常对象的错误码
     */
    public int getCode() {
        return code;
    }
}
