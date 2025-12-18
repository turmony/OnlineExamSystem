# 在线考试系统测试文档

## 测试概述

本文档与 DEVELOPMENT.md 中的开发步骤一一对应，采用**边开发边测试**的策略，确保每个功能模块的质量。

### 测试工具
| 类型 | 工具 | 说明 | 优先级 |
|------|------|------|--------|
| **后端单元测试** | JUnit5 + Mockito | Service层测试 | **高** |
| **后端集成测试** | SpringBootTest | Controller层测试 | **高** |
| **接口测试** | Apifox/Postman | API测试 | **高** |
| 前端手动测试 | 浏览器 | 页面功能测试 | 中 |

> **说明**: 本项目后端为主，前端测试以手动测试为主，重点保证后端接口的测试覆盖率。

---

## TEST-1：环境搭建测试

### 1.1 后端项目启动测试
**测试目标**: 验证 Spring Boot 项目正常启动

**测试步骤**:
1. 执行 `mvn clean compile`
2. 执行 `mvn spring-boot:run`
3. 访问 `http://localhost:8080/actuator/health`

**预期结果**:
```json
{"status": "UP"}
```

**检查清单**:
- [ ] 项目编译无错误
- [ ] 项目启动无异常
- [ ] 健康检查接口返回 UP

---

### 1.2 前端项目启动测试
**测试目标**: 验证 Vue3 项目正常启动

**测试步骤**:
1. 进入 frontend 目录
2. 执行 `npm install`
3. 执行 `npm run dev`
4. 访问 `http://localhost:5173`

**预期结果**: 页面正常显示登录界面

**检查清单**:
- [ ] 依赖安装成功
- [ ] 开发服务器启动成功
- [ ] 页面正常显示

---

### 1.3 Docker 环境测试
**测试目标**: 验证 Docker 容器正常运行

**测试步骤**:
1. 执行 `docker-compose up -d`
2. 检查容器状态 `docker-compose ps`
3. 连接 MySQL: `mysql -h127.0.0.1 -uroot -p`
4. 连接 Redis: `redis-cli ping`

**预期结果**:
- MySQL 容器运行中，可正常连接
- Redis 容器运行中，返回 PONG

**检查清单**:
- [ ] MySQL 容器运行正常
- [ ] Redis 容器运行正常
- [ ] 数据库可连接
- [ ] Redis 可连接

---

### 1.4 数据库初始化测试
**测试目标**: 验证数据库表创建成功

**测试步骤**:
1. 执行 `sql/schema.sql`
2. 执行 `sql/data.sql`
3. 查询表结构和初始数据

**SQL测试**:
```sql
-- 检查表是否创建
SHOW TABLES;

-- 检查初始角色
SELECT * FROM sys_role;

-- 检查管理员账号
SELECT * FROM sys_user WHERE username = 'admin';
```

**预期结果**: 13张表创建成功，初始数据插入成功

**检查清单**:
- [ ] 所有表创建成功
- [ ] 3个角色数据存在
- [ ] 管理员账号存在

---

## TEST-2：基础架构测试

### 2.1 统一返回结果测试

**单元测试代码**:
```java
@Test
void testResultSuccess() {
    Result<String> result = Result.success("test data");
    assertEquals(200, result.getCode());
    assertEquals("success", result.getMessage());
    assertEquals("test data", result.getData());
}

@Test
void testResultError() {
    Result<Void> result = Result.error(400, "参数错误");
    assertEquals(400, result.getCode());
    assertEquals("参数错误", result.getMessage());
}
```

**检查清单**:
- [ ] Result.success() 测试通过
- [ ] Result.error() 测试通过
- [ ] 分页结果测试通过

---

### 2.2 全局异常处理测试

**测试用例**:
| 测试场景 | 触发方式 | 预期返回 |
|----------|----------|----------|
| 业务异常 | 抛出 BusinessException | 对应错误码和消息 |
| 参数校验失败 | @Validated 校验失败 | 400 + 具体字段错误 |
| 未认证 | 无Token访问 | 401 |
| 无权限 | 权限不足 | 403 |
| 资源不存在 | 查询不存在的ID | 404 |

**检查清单**:
- [ ] BusinessException 正确捕获
- [ ] 参数校验异常正确捕获
- [ ] 401/403/404 正确返回

---

### 2.3 JWT认证测试

**单元测试代码**:
```java
@Test
void testGenerateToken() {
    String token = jwtUtils.generateToken("admin", 1L, List.of("ADMIN"));
    assertNotNull(token);
    assertTrue(token.startsWith("eyJ"));
}

@Test
void testValidateToken() {
    String token = jwtUtils.generateToken("admin", 1L, List.of("ADMIN"));
    assertTrue(jwtUtils.validateToken(token));
}

@Test
void testGetUserIdFromToken() {
    String token = jwtUtils.generateToken("admin", 1L, List.of("ADMIN"));
    Long userId = jwtUtils.getUserIdFromToken(token);
    assertEquals(1L, userId);
}

@Test
void testExpiredToken() {
    // 使用过期的token
    String expiredToken = "eyJhbGciOiJIUzI1NiJ9...";
    assertFalse(jwtUtils.validateToken(expiredToken));
}
```

**检查清单**:
- [ ] Token生成测试通过
- [ ] Token验证测试通过
- [ ] Token解析测试通过
- [ ] 过期Token测试通过

---

### 2.4 Knife4j接口文档测试

**测试步骤**:
1. 启动后端服务
2. 访问 `http://localhost:8080/doc.html`

**检查清单**:
- [ ] 文档页面正常显示
- [ ] 接口分组正确
- [ ] 接口参数显示正确

---

### 2.5 前端Axios封装测试（手动测试）

**测试步骤**:
1. 打开浏览器开发者工具 Network 面板
2. 执行登录操作，检查请求头是否携带 Token
3. 模拟 Token 过期，检查是否自动跳转登录页

**检查清单**:
- [ ] 请求自动携带 Authorization 头
- [ ] 401 响应自动跳转登录页
- [ ] 错误响应显示提示信息

---

## TEST-3：用户管理模块测试

### 3.1 登录接口测试

**测试用例**:
| 用例ID | 测试场景 | 输入 | 预期结果 |
|--------|----------|------|----------|
| TC-3.1.1 | 正常登录 | admin/admin123 | 返回token和用户信息 |
| TC-3.1.2 | 用户名错误 | wrong/admin123 | 401 用户名或密码错误 |
| TC-3.1.3 | 密码错误 | admin/wrong | 401 用户名或密码错误 |
| TC-3.1.4 | 用户名为空 | /admin123 | 400 用户名不能为空 |
| TC-3.1.5 | 账号被禁用 | disabled/123456 | 403 账号已被禁用 |

**接口测试**:
```
POST /api/auth/login
Content-Type: application/json

{
    "username": "admin",
    "password": "admin123"
}
```

**预期响应**:
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiJ9...",
        "tokenType": "Bearer",
        "expiresIn": 86400,
        "userInfo": {
            "id": 1,
            "username": "admin",
            "realName": "系统管理员",
            "roles": ["ADMIN"]
        }
    }
}
```

**检查清单**:
- [ ] TC-3.1.1 通过
- [ ] TC-3.1.2 通过
- [ ] TC-3.1.3 通过
- [ ] TC-3.1.4 通过
- [ ] TC-3.1.5 通过

---

### 3.2 注册接口测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-3.2.1 | 正常注册 | 200 注册成功 |
| TC-3.2.2 | 用户名已存在 | 400 用户名已存在 |
| TC-3.2.3 | 密码过短 | 400 密码长度不能少于6位 |
| TC-3.2.4 | 邮箱格式错误 | 400 邮箱格式不正确 |

**检查清单**:
- [ ] TC-3.2.1 通过
- [ ] TC-3.2.2 通过
- [ ] TC-3.2.3 通过
- [ ] TC-3.2.4 通过

---

### 3.3 用户CRUD接口测试

**测试用例**:
| 用例ID | 接口 | 测试场景 | 预期结果 |
|--------|------|----------|----------|
| TC-3.3.1 | GET /api/user/page | 分页查询 | 返回用户列表 |
| TC-3.3.2 | GET /api/user/{id} | 获取详情 | 返回用户信息 |
| TC-3.3.3 | POST /api/user | 创建用户 | 201 创建成功 |
| TC-3.3.4 | PUT /api/user/{id} | 更新用户 | 200 更新成功 |
| TC-3.3.5 | DELETE /api/user/{id} | 删除用户 | 200 删除成功 |
| TC-3.3.6 | PUT /api/user/{id}/status | 禁用用户 | 200 状态更新成功 |

**Service层单元测试**:
```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserServiceImpl userService;
    
    @Test
    void testGetById() {
        User user = new User();
        user.setId(1L);
        user.setUsername("admin");
        
        when(userMapper.selectById(1L)).thenReturn(user);
        
        UserVO result = userService.getById(1L);
        
        assertNotNull(result);
        assertEquals("admin", result.getUsername());
    }
    
    @Test
    void testCreate() {
        UserDTO dto = new UserDTO();
        dto.setUsername("newuser");
        dto.setPassword("123456");
        
        when(userMapper.insert(any(User.class))).thenReturn(1);
        
        assertDoesNotThrow(() -> userService.create(dto));
    }
}
```

**检查清单**:
- [ ] 分页查询测试通过
- [ ] 获取详情测试通过
- [ ] 创建用户测试通过
- [ ] 更新用户测试通过
- [ ] 删除用户测试通过
- [ ] 状态更新测试通过

---

### 3.4 前端登录页面测试（手动测试）

**测试步骤**:
1. 访问登录页面
2. 不输入任何内容点击登录，检查验证提示
3. 输入错误的用户名密码，检查错误提示
4. 输入正确的用户名密码，检查是否跳转

**检查清单**:
- [ ] 页面正常显示
- [ ] 空表单验证提示正确
- [ ] 登录失败显示错误信息
- [ ] 登录成功跳转到首页

---

## TEST-4：班级科目管理测试

### 4.1 班级管理接口测试

**测试用例**:
| 用例ID | 接口 | 测试场景 | 预期结果 |
|--------|------|----------|----------|
| TC-4.1.1 | GET /api/class/page | 分页查询 | 返回班级列表 |
| TC-4.1.2 | GET /api/class/list | 获取全部 | 返回所有班级 |
| TC-4.1.3 | POST /api/class | 创建班级 | 201 创建成功 |
| TC-4.1.4 | PUT /api/class/{id} | 更新班级 | 200 更新成功 |
| TC-4.1.5 | DELETE /api/class/{id} | 删除班级 | 200 删除成功 |
| TC-4.1.6 | DELETE (有学生) | 删除关联班级 | 400 班级下存在学生 |

**检查清单**:
- [ ] 班级CRUD测试全部通过
- [ ] 班级关联学生删除保护测试通过

---

### 4.2 科目管理接口测试

**测试用例**:
| 用例ID | 接口 | 测试场景 | 预期结果 |
|--------|------|----------|----------|
| TC-4.2.1 | GET /api/subject/page | 分页查询 | 返回科目列表 |
| TC-4.2.2 | POST /api/subject | 创建科目 | 201 创建成功 |
| TC-4.2.3 | DELETE (有题目) | 删除关联科目 | 400 科目下存在题目 |

**检查清单**:
- [ ] 科目CRUD测试全部通过
- [ ] 科目关联题目删除保护测试通过

---

## TEST-5：题库管理模块测试

### 5.1 题目创建测试

**测试用例 - 单选题**:
```
POST /api/question
{
    "subjectId": 1,
    "questionType": 1,
    "content": "以下哪个是Java的基本数据类型？",
    "difficulty": 2,
    "score": 5,
    "options": [
        {"optionCode": "A", "optionContent": "String", "isCorrect": 0},
        {"optionCode": "B", "optionContent": "Integer", "isCorrect": 0},
        {"optionCode": "C", "optionContent": "int", "isCorrect": 1},
        {"optionCode": "D", "optionContent": "Array", "isCorrect": 0}
    ],
    "answer": "C"
}
```

**测试用例矩阵**:
| 用例ID | 题型 | 测试场景 | 预期结果 |
|--------|------|----------|----------|
| TC-5.1.1 | 单选 | 正常创建 | 201 创建成功 |
| TC-5.1.2 | 单选 | 无正确选项 | 400 必须有一个正确选项 |
| TC-5.1.3 | 多选 | 正常创建 | 201 创建成功 |
| TC-5.1.4 | 多选 | 只有一个正确选项 | 400 多选题至少两个正确选项 |
| TC-5.1.5 | 判断 | 正常创建 | 201 创建成功 |
| TC-5.1.6 | 填空 | 正常创建 | 201 创建成功 |
| TC-5.1.7 | 简答 | 正常创建 | 201 创建成功 |
| TC-5.1.8 | 所有 | 内容为空 | 400 题目内容不能为空 |

**Service层单元测试**:
```java
@Test
void testCreateSingleChoiceQuestion() {
    QuestionDTO dto = new QuestionDTO();
    dto.setSubjectId(1L);
    dto.setQuestionType(1);
    dto.setContent("测试单选题");
    dto.setAnswer("A");
    
    List<QuestionOptionDTO> options = new ArrayList<>();
    options.add(new QuestionOptionDTO("A", "选项A", 1));
    options.add(new QuestionOptionDTO("B", "选项B", 0));
    dto.setOptions(options);
    
    when(questionMapper.insert(any())).thenReturn(1);
    
    assertDoesNotThrow(() -> questionService.create(dto));
}

@Test
void testCreateQuestionWithoutCorrectOption() {
    QuestionDTO dto = new QuestionDTO();
    dto.setQuestionType(1);
    // 所有选项 isCorrect 都为 0
    
    assertThrows(BusinessException.class, () -> questionService.create(dto));
}
```

**检查清单**:
- [ ] 单选题创建测试通过
- [ ] 多选题创建测试通过
- [ ] 判断题创建测试通过
- [ ] 填空题创建测试通过
- [ ] 简答题创建测试通过
- [ ] 异常场景测试通过

---

### 5.2 题目查询测试

**测试用例**:
| 用例ID | 测试场景 | 请求参数 | 预期结果 |
|--------|----------|----------|----------|
| TC-5.2.1 | 按科目筛选 | subjectId=1 | 返回该科目题目 |
| TC-5.2.2 | 按题型筛选 | questionType=1 | 返回单选题 |
| TC-5.2.3 | 按难度筛选 | difficulty=3 | 返回中等难度 |
| TC-5.2.4 | 关键词搜索 | keyword=Java | 返回包含Java的题目 |
| TC-5.2.5 | 组合筛选 | 多条件 | 返回符合条件的题目 |

**检查清单**:
- [ ] 分页查询测试通过
- [ ] 各类筛选条件测试通过
- [ ] 组合筛选测试通过

---

### 5.3 题目导入测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-5.3.1 | 正常导入 | 返回导入成功数量 |
| TC-5.3.2 | 文件格式错误 | 400 仅支持xlsx格式 |
| TC-5.3.3 | 数据格式错误 | 返回错误行信息 |
| TC-5.3.4 | 空文件 | 400 文件内容为空 |

**检查清单**:
- [ ] 正常导入测试通过
- [ ] 异常文件处理测试通过
- [ ] 导入结果反馈正确

---

### 5.4 前端题目页面测试（手动测试）

**测试步骤**:
1. 访问题目列表页面，检查列表显示
2. 点击新建，选择不同题型，检查表单变化
3. 创建各类型题目，检查是否成功

**检查清单**:
- [ ] 题目列表正常显示
- [ ] 各题型表单显示正确
- [ ] 创建题目成功

---

## TEST-6：试卷管理模块测试

### 6.1 手动组卷测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-6.1.1 | 正常创建 | 201 创建成功 |
| TC-6.1.2 | 无题目 | 400 试卷必须包含题目 |
| TC-6.1.3 | 总分计算 | 自动计算总分 |
| TC-6.1.4 | 题目排序 | 按sortOrder排序 |

**接口测试**:
```
POST /api/paper
{
    "paperName": "数据结构期末考试",
    "subjectId": 1,
    "duration": 120,
    "passScore": 60,
    "questions": [
        {"questionId": 1, "score": 5, "sortOrder": 1},
        {"questionId": 2, "score": 10, "sortOrder": 2}
    ]
}
```

**检查清单**:
- [ ] 手动组卷测试通过
- [ ] 总分自动计算测试通过
- [ ] 题目排序测试通过

---

### 6.2 随机组卷测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-6.2.1 | 正常随机组卷 | 返回符合规则的试卷 |
| TC-6.2.2 | 题目不足 | 400 题库题目不足 |
| TC-6.2.3 | 多次组卷 | 题目不重复 |

**接口测试**:
```
POST /api/paper/random
{
    "paperName": "随机测试卷",
    "subjectId": 1,
    "duration": 60,
    "rules": [
        {"questionType": 1, "count": 10, "scorePerQuestion": 2},
        {"questionType": 3, "count": 10, "scorePerQuestion": 1}
    ]
}
```

**检查清单**:
- [ ] 随机组卷测试通过
- [ ] 题目数量符合规则
- [ ] 题型分布符合规则

---

## TEST-7：考试管理模块测试

### 7.1 考试创建测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-7.1.1 | 正常创建 | 201 创建成功 |
| TC-7.1.2 | 结束时间早于开始时间 | 400 时间设置错误 |
| TC-7.1.3 | 试卷未发布 | 400 试卷未发布 |
| TC-7.1.4 | 考试时长超过时间范围 | 400 考试时长设置错误 |

**检查清单**:
- [ ] 考试创建测试通过
- [ ] 时间验证测试通过
- [ ] 试卷状态验证测试通过

---

### 7.2 开始考试测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-7.2.1 | 正常开始 | 返回试题和考试信息 |
| TC-7.2.2 | 考试未开始 | 400 考试尚未开始 |
| TC-7.2.3 | 考试已结束 | 400 考试已结束 |
| TC-7.2.4 | 非参考班级 | 403 无权参加此考试 |
| TC-7.2.5 | 已超过最大次数 | 400 已用完考试次数 |
| TC-7.2.6 | 断点续考 | 返回上次进度 |

**Service层测试**:
```java
@Test
void testStartExam() {
    // Mock考试信息
    Exam exam = new Exam();
    exam.setId(1L);
    exam.setStartTime(LocalDateTime.now().minusHours(1));
    exam.setEndTime(LocalDateTime.now().plusHours(1));
    exam.setIsPublished(1);
    
    when(examMapper.selectById(1L)).thenReturn(exam);
    
    ExamDetailVO result = examService.startExam(1L, 100L);
    
    assertNotNull(result);
    assertNotNull(result.getQuestions());
}

@Test
void testStartExamNotInTime() {
    Exam exam = new Exam();
    exam.setStartTime(LocalDateTime.now().plusHours(1)); // 未开始
    
    when(examMapper.selectById(1L)).thenReturn(exam);
    
    assertThrows(BusinessException.class, 
        () -> examService.startExam(1L, 100L));
}
```

**检查清单**:
- [ ] 正常开始考试测试通过
- [ ] 时间验证测试通过
- [ ] 权限验证测试通过
- [ ] 断点续考测试通过

---

## TEST-8：在线考试模块测试

### 8.1 答案保存测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-8.1.1 | 保存单选答案 | 200 保存成功 |
| TC-8.1.2 | 保存多选答案 | 200 保存成功 |
| TC-8.1.3 | 修改答案 | 200 更新成功 |
| TC-8.1.4 | 考试已结束 | 400 考试已结束 |

**检查清单**:
- [ ] 各题型答案保存测试通过
- [ ] 答案修改测试通过
- [ ] 考试状态验证测试通过

---

### 8.2 提交试卷测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-8.2.1 | 正常提交 | 200 提交成功，返回成绩 |
| TC-8.2.2 | 超时自动提交 | 自动提交并评分 |
| TC-8.2.3 | 客观题自动评分 | 分数计算正确 |
| TC-8.2.4 | 重复提交 | 400 已提交过 |

**自动评分测试**:
```java
@Test
void testAutoGrading() {
    // 准备测试数据
    List<Answer> answers = new ArrayList<>();
    
    // 单选题 - 正确
    Answer a1 = new Answer();
    a1.setQuestionId(1L);
    a1.setAnswerContent("C");
    answers.add(a1);
    
    // 多选题 - 部分正确
    Answer a2 = new Answer();
    a2.setQuestionId(2L);
    a2.setAnswerContent("A,B");  // 正确答案 A,B,C
    answers.add(a2);
    
    // 执行评分
    gradeService.autoGrade(recordId, answers);
    
    // 验证评分结果
    // ...
}
```

**检查清单**:
- [ ] 提交试卷测试通过
- [ ] 自动评分测试通过
- [ ] 超时处理测试通过

---

### 8.3 防作弊功能测试（手动测试）

**测试步骤**:
1. 进入考试界面
2. 切换到其他标签页，检查是否弹出警告
3. 尝试复制粘贴，检查是否被阻止
4. 查看后端作弊日志表，确认记录写入

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-8.3.1 | 切换标签页 | 弹出警告，后端记录日志 |
| TC-8.3.2 | 复制文本 | 操作被阻止 |
| TC-8.3.3 | 粘贴文本 | 操作被阻止 |

**检查清单**:
- [ ] 切屏检测正常
- [ ] 复制粘贴禁用正常
- [ ] 后端日志记录正常

---

### 8.4 考试界面测试（手动测试）

**测试步骤**:
1. 以学生身份登录，进入考试列表
2. 点击开始考试，检查考试界面
3. 检查倒计时是否正常运行
4. 点击答题卡题号，检查题目切换
5. 选择答案，检查是否自动保存（刷新页面验证）
6. 点击提交，检查是否显示成绩

**检查清单**:
- [ ] 倒计时显示正确
- [ ] 答题卡点击切换题目
- [ ] 答案自动保存
- [ ] 提交后显示成绩

---

## TEST-9：成绩管理模块测试

### 9.1 成绩查询测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-9.1.1 | 教师查询班级成绩 | 返回成绩列表 |
| TC-9.1.2 | 学生查询个人成绩 | 返回个人成绩 |
| TC-9.1.3 | 成绩详情 | 返回每题得分 |
| TC-9.1.4 | 权限验证 | 学生只能看自己成绩 |

**检查清单**:
- [ ] 成绩查询测试通过
- [ ] 权限验证测试通过

---

### 9.2 主观题批改测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-9.2.1 | 正常批改 | 200 批改成功 |
| TC-9.2.2 | 分数超过题目分值 | 400 分数不能超过题目分值 |
| TC-9.2.3 | 批改完成 | 更新记录状态为已批改 |

**检查清单**:
- [ ] 主观题批改测试通过
- [ ] 分数验证测试通过
- [ ] 状态更新测试通过

---

### 9.3 成绩导出测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-9.3.1 | 导出Excel | 下载成绩Excel文件 |
| TC-9.3.2 | 数据完整性 | 所有字段导出正确 |

**检查清单**:
- [ ] 成绩导出测试通过
- [ ] 文件格式正确
- [ ] 数据完整

---

## TEST-10：统计分析模块测试

### 10.1 仪表盘统计测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-10.1.1 | 获取统计数据 | 返回各项统计 |
| TC-10.1.2 | 数据准确性 | 统计数据与实际一致 |

**检查清单**:
- [ ] 仪表盘数据测试通过
- [ ] 数据准确性验证通过

---

### 10.2 考试统计测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-10.2.1 | 考试统计 | 返回参与人数、通过率等 |
| TC-10.2.2 | 成绩分布 | 返回各分数段人数 |
| TC-10.2.3 | 题目正确率 | 返回每题正确率 |

**检查清单**:
- [ ] 考试统计测试通过
- [ ] 图表数据正确

---

## TEST-11：系统优化测试

### 11.1 缓存测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-11.1.1 | 缓存命中 | 第二次查询走缓存 |
| TC-11.1.2 | 缓存失效 | 更新后缓存清除 |
| TC-11.1.3 | 缓存穿透防护 | 空值也缓存 |

**检查清单**:
- [ ] 缓存功能测试通过
- [ ] 缓存一致性测试通过

---

### 11.2 安全测试

**测试用例**:
| 用例ID | 测试场景 | 预期结果 |
|--------|----------|----------|
| TC-11.2.1 | SQL注入 | 参数化查询，无注入 |
| TC-11.2.2 | XSS攻击 | 输出转义，无XSS |
| TC-11.2.3 | 越权访问 | 403 无权限 |
| TC-11.2.4 | 密码加密 | 数据库存储加密 |

**检查清单**:
- [ ] SQL注入测试通过
- [ ] XSS防护测试通过
- [ ] 权限控制测试通过
- [ ] 敏感数据加密测试通过

---

## TEST-12：部署测试

### 12.1 Docker部署测试

**测试步骤**:
1. 执行 `docker-compose build`
2. 执行 `docker-compose up -d`
3. 检查所有容器状态
4. 访问前端页面
5. 访问后端接口
6. 测试数据库连接

**检查清单**:
- [ ] 镜像构建成功
- [ ] 容器启动成功
- [ ] 服务间通信正常
- [ ] 数据持久化正常

---

### 12.2 集成测试

**测试场景**: 完整业务流程测试

**测试步骤**:
1. 管理员登录
2. 创建班级和科目
3. 创建教师和学生账号
4. 教师登录，创建题目
5. 教师创建试卷
6. 教师发布考试
7. 学生登录，参加考试
8. 学生提交试卷
9. 系统自动评分客观题
10. 教师批改主观题
11. 学生查看成绩
12. 查看统计分析

**检查清单**:
- [ ] 用户管理流程通过
- [ ] 题库管理流程通过
- [ ] 试卷管理流程通过
- [ ] 考试流程通过
- [ ] 成绩管理流程通过
- [ ] 统计分析流程通过

---

## 测试覆盖率要求

| 模块 | 后端覆盖率目标 | 前端测试方式 |
|------|----------------|--------------|
| 认证模块 | > 90% | 手动测试 |
| 用户管理 | > 80% | 手动测试 |
| 题库管理 | > 85% | 手动测试 |
| 试卷管理 | > 85% | 手动测试 |
| 考试模块 | > 90% | 手动测试 |
| 成绩管理 | > 85% | 手动测试 |
| 统计分析 | > 80% | 手动测试 |

> **说明**: 前端采用手动测试方式，重点保证后端代码的测试覆盖率。

---

## 测试报告模板

### 测试概况
- 测试时间：
- 测试人员：
- 测试环境：

### 后端测试结果汇总
| 模块 | 用例数 | 通过 | 失败 | 覆盖率 |
|------|--------|------|------|--------|
| 认证模块 | | | | |
| 用户管理 | | | | |
| 题库管理 | | | | |
| 试卷管理 | | | | |
| 考试管理 | | | | |
| 成绩管理 | | | | |
| 统计分析 | | | | |

### 前端手动测试结果
| 页面 | 测试结果 | 备注 |
|------|----------|------|
| 登录页面 | | |
| 用户管理 | | |
| 题库管理 | | |
| 试卷管理 | | |
| 考试界面 | | |
| 成绩管理 | | |

### 缺陷统计
| 严重程度 | 数量 | 已修复 | 待修复 |
|----------|------|--------|--------|
| 致命 | | | |
| 严重 | | | |
| 一般 | | | |
| 轻微 | | | |

### 问题与建议
1. ...
2. ...

