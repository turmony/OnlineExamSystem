package com.exam.exception;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 异常测试控制器
 *
 * <p>仅用于测试全局异常处理器，不会参与正式业务。</p>
 *
 * @author Exam System
 * @since 2024-01-01
 */
@RestController
@RequestMapping("/api/test/exception")
@Validated
public class TestExceptionController {

    /**
     * 触发业务异常
     */
    @GetMapping("/business")
    public void business() {
        throw new BusinessException(1000, "业务异常测试");
    }

    /**
     * 触发非法参数异常
     */
    @GetMapping("/illegal-argument")
    public void illegalArgument() {
        throw new IllegalArgumentException("参数非法测试");
    }

    /**
     * 触发系统异常
     */
    @GetMapping("/system")
    public void system() {
        throw new RuntimeException("系统异常测试");
    }

    /**
     * 触发 MethodArgumentNotValidException（@RequestBody + 校验失败）
     *
     * @param request 请求体
     */
    @PostMapping("/method-argument-not-valid")
    public void methodArgumentNotValid(@RequestBody @Valid TestRequest request) {
        // 正常情况下不会进入这里
    }

    /**
     * 触发 BindException（对象参数 + 校验失败）
     *
     * @param request 请求参数
     */
    @GetMapping("/bind-exception")
    public void bindException(@Valid TestRequest request) {
        // 正常情况下不会进入这里
    }

    /**
     * 测试请求体
     */
    public static class TestRequest {

        @NotBlank(message = "用户名不能为空")
        @Size(min = 3, max = 20, message = "用户名长度必须在3-20之间")
        private String username;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }
    }
}


