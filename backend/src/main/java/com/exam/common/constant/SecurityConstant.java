package com.exam.common.constant;

/**
 * 安全相关常量
 *
 * @author Exam System
 * @since 2024-01-01
 */
public final class SecurityConstant {

    private SecurityConstant() {
    }

    /**
     * 请求头中存放 Token 的字段名称
     */
    public static final String AUTHORIZATION_HEADER = "Authorization";

    /**
     * Token 前缀
     */
    public static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 不需要认证的接口前缀
     */
    public static final String[] PERMIT_ALL_PATTERNS = {
            "/api/auth/login",
            "/api/auth/register",
            "/actuator/health",
            "/doc.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources/**"
    };
}


