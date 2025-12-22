package com.exam.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * JWT 配置属性
 *
 * @author Exam System
 * @since 2024-01-01
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * 签名密钥
     */
    private String secret;

    /**
     * 过期时间（秒）
     */
    private Long expiration;
}


