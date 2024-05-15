package com.yu.springbootinit.common;

import java.io.Serializable;
import lombok.Data;

/**
 * 通用返回结果类
 *
 * @param <T> 返回数据的类型
 */
@Data // Lombok 注解，自动生成 getter、setter 等方法
public class BaseResponse<T> implements Serializable {

    /**
     * 返回码
     */
    private int code;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 构造方法，用于创建一个通用返回结果对象
     *
     * @param code 返回码
     * @param data 返回数据
     * @param message 返回消息
     */
    public BaseResponse(int code, T data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    /**
     * 构造方法，用于创建一个通用返回结果对象（不带消息）
     *
     * @param code 返回码
     * @param data 返回数据
     */
    public BaseResponse(int code, T data) {
        this(code, data, "");
    }

    /**
     * 构造方法，用于创建一个通用返回结果对象（根据错误码）
     *
     * @param errorCode 错误码对象
     */
    public BaseResponse(ErrorCode errorCode) {
        this(errorCode.getCode(), null, errorCode.getMessage());
    }
}

