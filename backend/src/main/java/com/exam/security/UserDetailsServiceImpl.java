package com.exam.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 用户详情服务
 *
 * <p>当前阶段仅提供占位实现，后续在用户模块开发完成后，
 * 会通过数据库加载真实用户信息。</p>
 *
 * @author Exam System
 * @since 2024-01-01
 */
@Slf4j
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.warn("UserDetailsServiceImpl 为占位实现，请在用户模块完成后进行完善，当前用户名: {}", username);
        throw new UsernameNotFoundException("用户不存在");
    }
}


