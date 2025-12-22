package com.exam.common.result;

import com.exam.common.enums.ResultCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 统一返回结果
 *
 * @author Exam System
 * @since 2024-01-01
 */
@Data
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public Result() {
    }

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    /**
     * 成功返回
     *
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), null);
    }

    /**
     * 成功返回
     *
     * @param data 数据
     * @param <T>  数据类型
     * @return Result
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
    }

    /**
     * 成功返回
     *
     * @param message 消息
     * @param data    数据
     * @param <T>     数据类型
     * @return Result
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(ResultCode.SUCCESS.getCode(), message, data);
    }

    /**
     * 失败返回
     *
     * @param <T> 数据类型
     * @return Result
     */
    public static <T> Result<T> error() {
        return new Result<>(ResultCode.INTERNAL_SERVER_ERROR.getCode(), ResultCode.INTERNAL_SERVER_ERROR.getMessage(), null);
    }

    /**
     * 失败返回
     *
     * @param message 消息
     * @param <T>     数据类型
     * @return Result
     */
    public static <T> Result<T> error(String message) {
        return new Result<>(ResultCode.INTERNAL_SERVER_ERROR.getCode(), message, null);
    }

    /**
     * 失败返回
     *
     * @param code    状态码
     * @param message 消息
     * @param <T>     数据类型
     * @return Result
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null);
    }

    /**
     * 失败返回
     *
     * @param resultCode 结果码
     * @param <T>        数据类型
     * @return Result
     */
    public static <T> Result<T> error(ResultCode resultCode) {
        return new Result<>(resultCode.getCode(), resultCode.getMessage(), null);
    }

    /**
     * 失败返回
     *
     * @param resultCode 结果码
     * @param message    消息
     * @param <T>        数据类型
     * @return Result
     */
    public static <T> Result<T> error(ResultCode resultCode, String message) {
        return new Result<>(resultCode.getCode(), message, null);
    }
}

