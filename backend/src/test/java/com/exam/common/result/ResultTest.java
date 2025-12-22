package com.exam.common.result;

import com.exam.common.enums.ResultCode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 统一返回结果测试
 *
 * @author Exam System
 * @since 2024-01-01
 */
class ResultTest {

    @Test
    void testResultSuccess() {
        Result<String> result = Result.success("test data");
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertEquals("test data", result.getData());
    }

    @Test
    void testResultSuccessWithoutData() {
        Result<Void> result = Result.success();
        assertEquals(200, result.getCode());
        assertEquals("success", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testResultSuccessWithMessage() {
        Result<String> result = Result.success("操作成功", "test data");
        assertEquals(200, result.getCode());
        assertEquals("操作成功", result.getMessage());
        assertEquals("test data", result.getData());
    }

    @Test
    void testResultError() {
        Result<Void> result = Result.error(400, "参数错误");
        assertEquals(400, result.getCode());
        assertEquals("参数错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testResultErrorWithMessage() {
        Result<Void> result = Result.error("操作失败");
        assertEquals(500, result.getCode());
        assertEquals("操作失败", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testResultErrorWithoutParams() {
        Result<Void> result = Result.error();
        assertEquals(500, result.getCode());
        assertEquals("服务器错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testResultErrorWithResultCode() {
        Result<Void> result = Result.error(ResultCode.BAD_REQUEST);
        assertEquals(400, result.getCode());
        assertEquals("请求参数错误", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testResultErrorWithResultCodeAndMessage() {
        Result<Void> result = Result.error(ResultCode.BAD_REQUEST, "用户名不能为空");
        assertEquals(400, result.getCode());
        assertEquals("用户名不能为空", result.getMessage());
        assertNull(result.getData());
    }

    @Test
    void testPageResult() {
        List<String> records = new ArrayList<>();
        records.add("item1");
        records.add("item2");
        
        PageResult<String> pageResult = PageResult.of(records, 100L, 1L, 10L);
        
        assertNotNull(pageResult);
        assertEquals(2, pageResult.getRecords().size());
        assertEquals(100L, pageResult.getTotal());
        assertEquals(1L, pageResult.getCurrent());
        assertEquals(10L, pageResult.getSize());
        assertEquals("item1", pageResult.getRecords().get(0));
        assertEquals("item2", pageResult.getRecords().get(1));
    }

    @Test
    void testPageResultWithEmptyList() {
        List<String> records = new ArrayList<>();
        
        PageResult<String> pageResult = PageResult.of(records, 0L, 1L, 10L);
        
        assertNotNull(pageResult);
        assertTrue(pageResult.getRecords().isEmpty());
        assertEquals(0L, pageResult.getTotal());
        assertEquals(1L, pageResult.getCurrent());
        assertEquals(10L, pageResult.getSize());
    }

    @Test
    void testResultWithDifferentDataTypes() {
        // 测试字符串类型
        Result<String> stringResult = Result.success("string data");
        assertEquals("string data", stringResult.getData());

        // 测试整数类型
        Result<Integer> intResult = Result.success(123);
        assertEquals(123, intResult.getData());

        // 测试对象类型
        TestData testData = new TestData("test", 100);
        Result<TestData> objectResult = Result.success(testData);
        assertNotNull(objectResult.getData());
        assertEquals("test", objectResult.getData().getName());
        assertEquals(100, objectResult.getData().getValue());
    }

    /**
     * 测试数据类
     */
    static class TestData {
        private String name;
        private Integer value;

        public TestData(String name, Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }
    }
}

