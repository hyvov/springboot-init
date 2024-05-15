package com.yu.springbootinit.exception;

import com.yu.springbootinit.common.ErrorCode;

/**
 * 抛异常工具类
 *
 * 该工具类提供了一组静态方法，用于根据条件抛出异常。
 */
public class ThrowUtils {

    /**
     * 如果条件成立，则抛出指定的运行时异常
     *
     * @param condition 条件表达式，如果为 true 则抛出异常
     * @param runtimeException 要抛出的运行时异常对象
     */
    public static void throwIf(boolean condition, RuntimeException runtimeException) {
        if (condition) {
            throw runtimeException;
        }
    }

    /**
     * 如果条件成立，则抛出指定错误码的业务异常
     *
     * @param condition 条件表达式，如果为 true 则抛出异常
     * @param errorCode 要抛出的业务异常的错误码对象
     */
    public static void throwIf(boolean condition, ErrorCode errorCode) {
        throwIf(condition, new BusinessException(errorCode));
    }

    /**
     * 如果条件成立，则抛出指定错误码和错误消息的业务异常
     *
     * @param condition 条件表达式，如果为 true 则抛出异常
     * @param errorCode 要抛出的业务异常的错误码对象
     * @param message 错误消息
     */
    public static void throwIf(boolean condition, ErrorCode errorCode, String message) {
        throwIf(condition, new BusinessException(errorCode, message));
    }
}
