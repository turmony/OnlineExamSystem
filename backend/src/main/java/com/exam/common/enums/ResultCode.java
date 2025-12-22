package com.exam.common.enums;

/**
 * 响应状态码枚举
 *
 * @author Exam System
 * @since 2024-01-01
 */
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "success"),

    /**
     * 请求参数错误
     */
    BAD_REQUEST(400, "请求参数错误"),

    /**
     * 未认证
     */
    UNAUTHORIZED(401, "未认证"),

    /**
     * 无权限
     */
    FORBIDDEN(403, "无权限"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 服务器错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器错误"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(1000, "业务异常"),

    /**
     * 用户名或密码错误
     */
    LOGIN_ERROR(1001, "用户名或密码错误"),

    /**
     * Token已过期
     */
    TOKEN_EXPIRED(1002, "Token已过期"),

    /**
     * Token无效
     */
    TOKEN_INVALID(1003, "Token无效"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(1004, "用户不存在"),

    /**
     * 用户已存在
     */
    USER_ALREADY_EXISTS(1005, "用户已存在"),

    /**
     * 用户已被禁用
     */
    USER_DISABLED(1006, "用户已被禁用"),

    /**
     * 数据不存在
     */
    DATA_NOT_FOUND(1007, "数据不存在"),

    /**
     * 数据已存在
     */
    DATA_ALREADY_EXISTS(1008, "数据已存在"),

    /**
     * 操作失败
     */
    OPERATION_FAILED(1009, "操作失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

