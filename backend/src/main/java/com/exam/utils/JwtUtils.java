package com.exam.utils;

import com.exam.config.JwtProperties;
import com.exam.common.enums.ResultCode;
import com.exam.exception.BusinessException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.crypto.SecretKey;

/**
 * JWT 工具类
 *
 * <p>负责生成、解析和校验 Token。</p>
 *
 * @author Exam System
 * @since 2024-01-01
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtils {

    private final JwtProperties jwtProperties;

    /**
     * 签名密钥
     */
    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 Token
     *
     * @param username 用户名
     * @param userId   用户ID
     * @param roles    角色列表
     * @return Token 字符串
     */
    public String generateToken(String username, Long userId, List<String> roles) {
        Instant now = Instant.now();
        Instant expireAt = now.plusSeconds(jwtProperties.getExpiration());

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expireAt))
                .claims(Map.of(
                        "userId", userId,
                        "roles", roles
                ))
                .signWith(key)
                .compact();
    }

    /**
     * 校验 Token 是否有效
     *
     * @param token Token 字符串
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.warn("Token 已过期: {}", e.getMessage());
            return false;
        } catch (Exception e) {
            log.warn("Token 校验失败", e);
            return false;
        }
    }

    /**
     * 从 Token 中获取用户ID
     *
     * @param token Token 字符串
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        try {
            Jws<Claims> jws = parseToken(token);
            Object userId = jws.getPayload().get("userId");
            if (userId instanceof Number number) {
                return number.longValue();
            }
            if (userId instanceof String str) {
                return Long.parseLong(str);
            }
            throw new BusinessException(ResultCode.TOKEN_INVALID, "Token 中用户ID无效");
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ResultCode.TOKEN_EXPIRED);
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.error("解析 Token 中的用户ID失败", e);
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }
    }

    /**
     * 从 Token 中获取用户名
     *
     * @param token Token 字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        try {
            return parseToken(token).getPayload().getSubject();
        } catch (ExpiredJwtException e) {
            throw new BusinessException(ResultCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.error("解析 Token 中的用户名失败", e);
            throw new BusinessException(ResultCode.TOKEN_INVALID);
        }
    }

    /**
     * 解析 Token
     *
     * @param token Token 字符串
     * @return 解析结果
     */
    private Jws<Claims> parseToken(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
    }
}


