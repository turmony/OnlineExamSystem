package com.exam.exception;

import com.exam.common.enums.ResultCode;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 全局异常处理器测试
 *
 * 对应 TESTING.md -> TEST-2 -> 2.2 全局异常处理测试
 *
 * @author Exam System
 * @since 2024-01-01
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class GlobalExceptionHandlerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("业务异常应返回自定义错误码和消息")
    void testHandleBusinessException() throws Exception {
        String json = mockMvc.perform(get("/api/test/exception/business"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(json);
        assertThat(node.get("code").asInt()).isEqualTo(1000);
        assertThat(node.get("message").asText()).isNotBlank();
    }

    @Test
    @DisplayName("非法参数异常应返回400和对应消息")
    void testHandleIllegalArgumentException() throws Exception {
        String json = mockMvc.perform(get("/api/test/exception/illegal-argument"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(json);
        assertThat(node.get("code").asInt()).isEqualTo(ResultCode.BAD_REQUEST.getCode());
        assertThat(node.get("message").asText()).isNotBlank();
    }

    @Test
    @DisplayName("系统异常应返回500和统一提示")
    void testHandleSystemException() throws Exception {
        String json = mockMvc.perform(get("/api/test/exception/system"))
                .andExpect(status().isInternalServerError())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(json);
        assertThat(node.get("code").asInt()).isEqualTo(ResultCode.INTERNAL_SERVER_ERROR.getCode());
        assertThat(node.get("message").asText()).isNotBlank();
    }

    @Test
    @DisplayName("@RequestBody 参数校验失败应返回400和字段错误信息")
    void testHandleMethodArgumentNotValidException() throws Exception {
        // username 为空，触发 @NotBlank
        String body = "{\"username\":\"\"}";

        String json = mockMvc.perform(post("/api/test/exception/method-argument-not-valid")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(json);
        assertThat(node.get("code").asInt()).isEqualTo(ResultCode.BAD_REQUEST.getCode());
        assertThat(node.get("message").asText()).isNotBlank();
    }

    @Test
    @DisplayName("普通参数校验失败应返回400和字段错误信息")
    void testHandleBindException() throws Exception {
        // 不传任何参数，触发 BindException
        String json = mockMvc.perform(get("/api/test/exception/bind-exception"))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResponse()
                .getContentAsString();

        JsonNode node = objectMapper.readTree(json);
        assertThat(node.get("code").asInt()).isEqualTo(ResultCode.BAD_REQUEST.getCode());
        assertThat(node.get("message").asText()).isNotBlank();
    }
}


