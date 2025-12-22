package com.exam.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Knife4j配置类
 *
 * @author Exam System
 * @since 2024-01-01
 */
@Configuration
public class Knife4jConfig {

    /**
     * OpenAPI配置
     *
     * @return OpenAPI
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("在线考试系统API文档")
                        .description("在线考试系统接口文档")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Exam System")));
    }
}

