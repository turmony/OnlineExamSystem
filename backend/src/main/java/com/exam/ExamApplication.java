package com.exam;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 在线考试系统启动类
 *
 * @author Exam System
 * @since 2024-01-01
 */
@SpringBootApplication
@MapperScan("com.exam.mapper")
public class ExamApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExamApplication.class, args);
    }
}

