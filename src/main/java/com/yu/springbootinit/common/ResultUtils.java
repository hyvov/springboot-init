package com.yu.springbootinit.common;

/**
 * 返回结果工具类
 *
 * 该工具类提供了一系列静态方法，用于生成接口返回的结果对象。
 */
public class ResultUtils {

    /**
     * 成功返回结果
     *
     * @param data 成功时返回的数据对象
     * @param <T> 数据对象的类型
     * @return 返回一个成功的结果对象，包含指定的数据对象
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败返回结果（根据错误码）
     *
     * @param errorCode 错误码枚举对象
     * @return 返回一个失败的结果对象，包含指定的错误码信息
     */
    public static BaseResponse error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败返回结果（自定义错误码和错误信息）
     *
     * @param code 自定义的错误码
     * @param message 自定义的错误信息
     * @return 返回一个失败的结果对象，包含指定的错误码和错误信息
     */
    public static BaseResponse error(int code, String message) {
        return new BaseResponse(code, null, message);
    }

    /**
     * 失败返回结果（根据错误码和自定义错误信息）
     *
     * @param errorCode 错误码枚举对象
     * @param message 自定义的错误信息
     * @return 返回一个失败的结果对象，包含指定的错误码和错误信息
     */
    public static BaseResponse error(ErrorCode errorCode, String message) {
        return new BaseResponse(errorCode.getCode(), null, message);
    }
}