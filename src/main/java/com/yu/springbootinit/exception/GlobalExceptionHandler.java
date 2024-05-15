package com.yu.springbootinit.exception;

import com.yu.springbootinit.common.BaseResponse;
import com.yu.springbootinit.common.ErrorCode;
import com.yu.springbootinit.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * 该类用于处理全局的异常情况，通过 @RestControllerAdvice 注解标识为全局异常处理器。
 */
@RestControllerAdvice // 注解标识为全局异常处理器
@Slf4j // 使用 Lombok 提供的日志注解
public class GlobalExceptionHandler {

    /**
     * 处理业务异常
     *
     * @param e BusinessException 异常对象
     * @return 返回包含错误信息的 BaseResponse 对象
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e); // 记录异常日志
        return ResultUtils.error(e.getCode(), e.getMessage()); // 返回包含错误信息的 BaseResponse 对象
    }

    /**
     * 处理运行时异常
     *
     * @param e RuntimeException 异常对象
     * @return 返回包含系统错误信息的 BaseResponse 对象
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e); // 记录异常日志
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "系统错误"); // 返回包含系统错误信息的 BaseResponse 对象
    }
}