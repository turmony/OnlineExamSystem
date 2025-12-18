# 在线考试系统开发文档

## 项目概述

### 项目名称
在线考试系统 (Online Exam System)

### 项目描述
一个功能完善的在线考试系统，支持题库管理、智能组卷、在线考试、自动评分等功能，具备防作弊机制。

### 技术栈
| 层级 | 技术 | 版本 | 说明 |
|------|------|------|------|
| **后端框架** | Spring Boot | 3.2+ | **核心重点** |
| **安全框架** | Spring Security | 6.x | **核心重点** |
| **ORM框架** | MyBatis-Plus | 3.5+ | **核心重点** |
| **数据库** | MySQL | 8.0 | **核心重点** |
| **缓存** | Redis | 7.x | **核心重点** |
| API文档 | Knife4j | 4.x | 自动生成 |
| 前端框架 | Vue3 | 3.4+ | 简化使用 |
| UI组件库 | Arco Design Vue | 2.x | 开箱即用 |
| 构建工具 | Vite | 5.x | 快速构建 |
| 路由 | Vue Router | 4.x | 基础路由 |
| HTTP客户端 | Axios | 1.x | 请求封装 |
| 部署 | Docker + Nginx | - | 容器化 |

> **说明**: 本项目**后端为主**，前端采用简化方案，主要使用 Arco Design 现成组件快速搭建界面。

---

## 项目文件架构

```
OnlineExamSystem/
├── frontend/                           # 前端项目目录（简化版）
│   ├── public/                         # 静态资源
│   │   └── favicon.ico
│   ├── src/
│   │   ├── api/                        # API接口（按模块划分）
│   │   │   ├── auth.js                 # 认证接口
│   │   │   ├── user.js                 # 用户接口
│   │   │   ├── question.js             # 题目接口
│   │   │   ├── paper.js                # 试卷接口
│   │   │   ├── exam.js                 # 考试接口
│   │   │   └── statistics.js           # 统计接口
│   │   ├── assets/                     # 静态资源
│   │   │   └── logo.png
│   │   ├── components/                 # 公共组件（精简）
│   │   │   ├── QuestionItem.vue        # 题目展示组件
│   │   │   ├── ExamTimer.vue           # 考试倒计时
│   │   │   └── AnswerSheet.vue         # 答题卡
│   │   ├── layouts/                    # 布局组件
│   │   │   ├── MainLayout.vue          # 主布局（带侧边栏）
│   │   │   └── ExamLayout.vue          # 考试布局（全屏）
│   │   ├── router/                     # 路由配置
│   │   │   └── index.js
│   │   ├── stores/                     # 状态管理（简化）
│   │   │   └── user.js                 # 用户状态
│   │   ├── utils/                      # 工具函数
│   │   │   ├── request.js              # Axios封装
│   │   │   └── auth.js                 # Token管理
│   │   ├── views/                      # 页面组件
│   │   │   ├── Login.vue               # 登录页
│   │   │   ├── Register.vue            # 注册页
│   │   │   ├── Dashboard.vue           # 仪表盘
│   │   │   ├── UserList.vue            # 用户管理
│   │   │   ├── ClassList.vue           # 班级管理
│   │   │   ├── SubjectList.vue         # 科目管理
│   │   │   ├── QuestionList.vue        # 题库管理
│   │   │   ├── QuestionForm.vue        # 题目编辑
│   │   │   ├── PaperList.vue           # 试卷管理
│   │   │   ├── PaperForm.vue           # 试卷编辑
│   │   │   ├── ExamList.vue            # 考试列表
│   │   │   ├── ExamForm.vue            # 考试编辑
│   │   │   ├── ExamRoom.vue            # 考试界面（核心）
│   │   │   ├── ExamResult.vue          # 考试结果
│   │   │   ├── GradeList.vue           # 成绩列表
│   │   │   ├── GradeReview.vue         # 阅卷页面
│   │   │   └── Statistics.vue          # 统计分析
│   │   ├── App.vue
│   │   └── main.js
│   ├── index.html
│   ├── vite.config.js
│   └── package.json
│
├── backend/                            # 后端项目目录
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/exam/
│   │   │   │   ├── ExamApplication.java
│   │   │   │   ├── config/             # 配置类
│   │   │   │   │   ├── CorsConfig.java
│   │   │   │   │   ├── MybatisPlusConfig.java
│   │   │   │   │   ├── RedisConfig.java
│   │   │   │   │   ├── SecurityConfig.java
│   │   │   │   │   └── Knife4jConfig.java
│   │   │   │   ├── controller/         # 控制器
│   │   │   │   │   ├── AuthController.java
│   │   │   │   │   ├── UserController.java
│   │   │   │   │   ├── ClassController.java
│   │   │   │   │   ├── SubjectController.java
│   │   │   │   │   ├── QuestionController.java
│   │   │   │   │   ├── PaperController.java
│   │   │   │   │   ├── ExamController.java
│   │   │   │   │   ├── GradeController.java
│   │   │   │   │   └── StatisticsController.java
│   │   │   │   ├── service/            # 服务层
│   │   │   │   │   ├── AuthService.java
│   │   │   │   │   ├── UserService.java
│   │   │   │   │   ├── ClassService.java
│   │   │   │   │   ├── SubjectService.java
│   │   │   │   │   ├── QuestionService.java
│   │   │   │   │   ├── PaperService.java
│   │   │   │   │   ├── ExamService.java
│   │   │   │   │   ├── GradeService.java
│   │   │   │   │   ├── StatisticsService.java
│   │   │   │   │   └── impl/           # 服务实现
│   │   │   │   │       ├── AuthServiceImpl.java
│   │   │   │   │       ├── UserServiceImpl.java
│   │   │   │   │       ├── ClassServiceImpl.java
│   │   │   │   │       ├── SubjectServiceImpl.java
│   │   │   │   │       ├── QuestionServiceImpl.java
│   │   │   │   │       ├── PaperServiceImpl.java
│   │   │   │   │       ├── ExamServiceImpl.java
│   │   │   │   │       ├── GradeServiceImpl.java
│   │   │   │   │       └── StatisticsServiceImpl.java
│   │   │   │   ├── mapper/             # MyBatis Mapper
│   │   │   │   │   ├── UserMapper.java
│   │   │   │   │   ├── RoleMapper.java
│   │   │   │   │   ├── ClassMapper.java
│   │   │   │   │   ├── SubjectMapper.java
│   │   │   │   │   ├── QuestionMapper.java
│   │   │   │   │   ├── QuestionOptionMapper.java
│   │   │   │   │   ├── PaperMapper.java
│   │   │   │   │   ├── PaperQuestionMapper.java
│   │   │   │   │   ├── ExamMapper.java
│   │   │   │   │   ├── ExamRecordMapper.java
│   │   │   │   │   ├── AnswerMapper.java
│   │   │   │   │   └── CheatLogMapper.java
│   │   │   │   ├── entity/             # 实体类
│   │   │   │   │   ├── User.java
│   │   │   │   │   ├── Role.java
│   │   │   │   │   ├── UserRole.java
│   │   │   │   │   ├── Class.java
│   │   │   │   │   ├── Subject.java
│   │   │   │   │   ├── Question.java
│   │   │   │   │   ├── QuestionOption.java
│   │   │   │   │   ├── Paper.java
│   │   │   │   │   ├── PaperQuestion.java
│   │   │   │   │   ├── Exam.java
│   │   │   │   │   ├── ExamRecord.java
│   │   │   │   │   ├── Answer.java
│   │   │   │   │   └── CheatLog.java
│   │   │   │   ├── dto/                # 数据传输对象
│   │   │   │   │   ├── LoginDTO.java
│   │   │   │   │   ├── RegisterDTO.java
│   │   │   │   │   ├── UserDTO.java
│   │   │   │   │   ├── QuestionDTO.java
│   │   │   │   │   ├── PaperDTO.java
│   │   │   │   │   ├── ExamDTO.java
│   │   │   │   │   ├── AnswerDTO.java
│   │   │   │   │   └── GradeDTO.java
│   │   │   │   ├── vo/                 # 视图对象
│   │   │   │   │   ├── LoginVO.java
│   │   │   │   │   ├── UserVO.java
│   │   │   │   │   ├── QuestionVO.java
│   │   │   │   │   ├── PaperVO.java
│   │   │   │   │   ├── ExamVO.java
│   │   │   │   │   ├── ExamDetailVO.java
│   │   │   │   │   ├── GradeVO.java
│   │   │   │   │   └── StatisticsVO.java
│   │   │   │   ├── common/             # 公共类
│   │   │   │   │   ├── result/
│   │   │   │   │   │   ├── Result.java
│   │   │   │   │   │   └── PageResult.java
│   │   │   │   │   ├── constant/
│   │   │   │   │   │   ├── CommonConstant.java
│   │   │   │   │   │   ├── RedisConstant.java
│   │   │   │   │   │   └── SecurityConstant.java
│   │   │   │   │   └── enums/
│   │   │   │   │       ├── ResultCode.java
│   │   │   │   │       ├── UserRole.java
│   │   │   │   │       ├── QuestionType.java
│   │   │   │   │       ├── ExamStatus.java
│   │   │   │   │       └── CheatType.java
│   │   │   │   ├── exception/          # 异常处理
│   │   │   │   │   ├── BusinessException.java
│   │   │   │   │   └── GlobalExceptionHandler.java
│   │   │   │   ├── security/           # 安全相关
│   │   │   │   │   ├── JwtTokenProvider.java
│   │   │   │   │   ├── JwtAuthenticationFilter.java
│   │   │   │   │   ├── UserDetailsServiceImpl.java
│   │   │   │   │   └── SecurityUser.java
│   │   │   │   ├── utils/              # 工具类
│   │   │   │   │   ├── JwtUtils.java
│   │   │   │   │   ├── RedisUtils.java
│   │   │   │   │   ├── ExcelUtils.java
│   │   │   │   │   └── BeanUtils.java
│   │   │   │   └── aspect/             # 切面
│   │   │   │       └── LogAspect.java
│   │   │   └── resources/
│   │   │       ├── mapper/             # MyBatis XML
│   │   │       │   ├── UserMapper.xml
│   │   │       │   ├── QuestionMapper.xml
│   │   │       │   ├── PaperMapper.xml
│   │   │       │   ├── ExamMapper.xml
│   │   │       │   └── StatisticsMapper.xml
│   │   │       ├── application.yml
│   │   │       ├── application-dev.yml
│   │   │       └── application-prod.yml
│   │   └── test/                       # 后端测试目录
│   │       └── java/com/exam/
│   │           ├── controller/
│   │           │   ├── AuthControllerTest.java
│   │           │   ├── UserControllerTest.java
│   │           │   ├── QuestionControllerTest.java
│   │           │   ├── PaperControllerTest.java
│   │           │   └── ExamControllerTest.java
│   │           ├── service/
│   │           │   ├── UserServiceTest.java
│   │           │   ├── QuestionServiceTest.java
│   │           │   ├── PaperServiceTest.java
│   │           │   ├── ExamServiceTest.java
│   │           │   └── GradeServiceTest.java
│   │           └── mapper/
│   │               ├── UserMapperTest.java
│   │               ├── QuestionMapperTest.java
│   │               └── ExamMapperTest.java
│   ├── pom.xml
│   └── Dockerfile
│
├── docker/                             # Docker配置
│   ├── docker-compose.yml
│   ├── mysql/
│   │   ├── Dockerfile
│   │   ├── my.cnf
│   │   └── init/
│   │       └── init.sql
│   ├── redis/
│   │   └── redis.conf
│   └── nginx/
│       ├── nginx.conf
│       └── conf.d/
│           └── default.conf
│
├── sql/                                # SQL脚本
│   ├── schema.sql                      # 建表语句
│   └── data.sql                        # 初始数据
│
├── docs/                               # 项目文档
│   ├── api/                            # API文档
│   └── images/                         # 文档图片
│
├── .cursorrules                        # Cursor规则
├── DEVELOPMENT.md                      # 开发文档
├── TESTING.md                          # 测试文档
├── README.md                           # 项目说明
└── .gitignore
```

---

## 数据库设计

### ER图说明

系统包含以下核心实体：用户(User)、角色(Role)、班级(Class)、科目(Subject)、题目(Question)、试卷(Paper)、考试(Exam)、考试记录(ExamRecord)、答题记录(Answer)、作弊日志(CheatLog)

### 数据库表结构

#### 1. 系统用户表 (sys_user)
```sql
CREATE TABLE `sys_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `real_name` VARCHAR(50) NOT NULL COMMENT '真实姓名',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `gender` TINYINT DEFAULT 0 COMMENT '性别: 0-未知 1-男 2-女',
    `status` TINYINT DEFAULT 1 COMMENT '状态: 0-禁用 1-启用',
    `class_id` BIGINT DEFAULT NULL COMMENT '班级ID(学生)',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除: 0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    KEY `idx_class_id` (`class_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='系统用户表';
```

#### 2. 角色表 (sys_role)
```sql
CREATE TABLE `sys_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
    `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '角色描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';
```

#### 3. 用户角色关联表 (sys_user_role)
```sql
CREATE TABLE `sys_user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';
```

#### 4. 班级表 (sys_class)
```sql
CREATE TABLE `sys_class` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '班级ID',
    `class_name` VARCHAR(100) NOT NULL COMMENT '班级名称',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '班级描述',
    `teacher_id` BIGINT DEFAULT NULL COMMENT '班主任ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';
```

#### 5. 科目表 (exam_subject)
```sql
CREATE TABLE `exam_subject` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '科目ID',
    `subject_name` VARCHAR(100) NOT NULL COMMENT '科目名称',
    `subject_code` VARCHAR(50) DEFAULT NULL COMMENT '科目编码',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '科目描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科目表';
```

#### 6. 题目表 (exam_question)
```sql
CREATE TABLE `exam_question` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '题目ID',
    `subject_id` BIGINT NOT NULL COMMENT '科目ID',
    `question_type` TINYINT NOT NULL COMMENT '题目类型: 1-单选 2-多选 3-判断 4-填空 5-简答',
    `content` TEXT NOT NULL COMMENT '题目内容',
    `answer` TEXT NOT NULL COMMENT '正确答案',
    `analysis` TEXT DEFAULT NULL COMMENT '答案解析',
    `difficulty` TINYINT DEFAULT 3 COMMENT '难度: 1-简单 2-较易 3-中等 4-较难 5-困难',
    `score` DECIMAL(5,2) DEFAULT 0 COMMENT '默认分值',
    `creator_id` BIGINT NOT NULL COMMENT '创建人ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_subject_id` (`subject_id`),
    KEY `idx_question_type` (`question_type`),
    KEY `idx_difficulty` (`difficulty`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目表';
```

#### 7. 题目选项表 (exam_question_option)
```sql
CREATE TABLE `exam_question_option` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '选项ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `option_code` VARCHAR(10) NOT NULL COMMENT '选项编码(A/B/C/D)',
    `option_content` TEXT NOT NULL COMMENT '选项内容',
    `is_correct` TINYINT DEFAULT 0 COMMENT '是否正确答案: 0-否 1-是',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`),
    KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目选项表';
```

#### 8. 试卷表 (exam_paper)
```sql
CREATE TABLE `exam_paper` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '试卷ID',
    `paper_name` VARCHAR(200) NOT NULL COMMENT '试卷名称',
    `subject_id` BIGINT NOT NULL COMMENT '科目ID',
    `total_score` DECIMAL(5,2) NOT NULL COMMENT '总分',
    `pass_score` DECIMAL(5,2) DEFAULT 60 COMMENT '及格分',
    `duration` INT NOT NULL COMMENT '考试时长(分钟)',
    `question_count` INT DEFAULT 0 COMMENT '题目数量',
    `paper_type` TINYINT DEFAULT 1 COMMENT '组卷方式: 1-手动 2-随机',
    `status` TINYINT DEFAULT 0 COMMENT '状态: 0-草稿 1-已发布',
    `creator_id` BIGINT NOT NULL COMMENT '创建人ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_subject_id` (`subject_id`),
    KEY `idx_creator_id` (`creator_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷表';
```

#### 9. 试卷题目关联表 (exam_paper_question)
```sql
CREATE TABLE `exam_paper_question` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `paper_id` BIGINT NOT NULL COMMENT '试卷ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `score` DECIMAL(5,2) NOT NULL COMMENT '题目分值',
    `sort_order` INT DEFAULT 0 COMMENT '题目顺序',
    PRIMARY KEY (`id`),
    KEY `idx_paper_id` (`paper_id`),
    KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷题目关联表';
```

#### 10. 考试表 (exam_exam)
```sql
CREATE TABLE `exam_exam` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '考试ID',
    `exam_name` VARCHAR(200) NOT NULL COMMENT '考试名称',
    `paper_id` BIGINT NOT NULL COMMENT '试卷ID',
    `class_ids` VARCHAR(500) DEFAULT NULL COMMENT '参与班级ID(逗号分隔)',
    `start_time` DATETIME NOT NULL COMMENT '开始时间',
    `end_time` DATETIME NOT NULL COMMENT '结束时间',
    `duration` INT NOT NULL COMMENT '考试时长(分钟)',
    `max_attempts` INT DEFAULT 1 COMMENT '最大考试次数',
    `is_published` TINYINT DEFAULT 0 COMMENT '是否发布: 0-否 1-是',
    `anti_cheat_enabled` TINYINT DEFAULT 1 COMMENT '防作弊: 0-关 1-开',
    `show_result` TINYINT DEFAULT 1 COMMENT '交卷显示成绩: 0-否 1-是',
    `show_answer` TINYINT DEFAULT 0 COMMENT '交卷显示答案: 0-否 1-是',
    `creator_id` BIGINT NOT NULL COMMENT '创建人ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`),
    KEY `idx_paper_id` (`paper_id`),
    KEY `idx_start_time` (`start_time`),
    KEY `idx_end_time` (`end_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试表';
```

#### 11. 考试记录表 (exam_record)
```sql
CREATE TABLE `exam_record` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '记录ID',
    `exam_id` BIGINT NOT NULL COMMENT '考试ID',
    `user_id` BIGINT NOT NULL COMMENT '考生ID',
    `paper_id` BIGINT NOT NULL COMMENT '试卷ID',
    `start_time` DATETIME DEFAULT NULL COMMENT '开始答题时间',
    `submit_time` DATETIME DEFAULT NULL COMMENT '交卷时间',
    `total_score` DECIMAL(5,2) DEFAULT NULL COMMENT '总得分',
    `objective_score` DECIMAL(5,2) DEFAULT NULL COMMENT '客观题得分',
    `subjective_score` DECIMAL(5,2) DEFAULT NULL COMMENT '主观题得分',
    `status` TINYINT DEFAULT 0 COMMENT '状态: 0-未开始 1-进行中 2-已交卷 3-已批改',
    `cheat_count` INT DEFAULT 0 COMMENT '作弊次数',
    `is_passed` TINYINT DEFAULT NULL COMMENT '是否及格: 0-否 1-是',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_exam_user` (`exam_id`, `user_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='考试记录表';
```

#### 12. 答题记录表 (exam_answer)
```sql
CREATE TABLE `exam_answer` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '答题ID',
    `record_id` BIGINT NOT NULL COMMENT '考试记录ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `answer_content` TEXT DEFAULT NULL COMMENT '考生答案',
    `is_correct` TINYINT DEFAULT NULL COMMENT '是否正确: 0-错 1-对 NULL-待判',
    `score` DECIMAL(5,2) DEFAULT NULL COMMENT '得分',
    `review_comment` VARCHAR(500) DEFAULT NULL COMMENT '批改评语',
    `reviewer_id` BIGINT DEFAULT NULL COMMENT '批改人ID',
    `review_time` DATETIME DEFAULT NULL COMMENT '批改时间',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    KEY `idx_record_id` (`record_id`),
    KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='答题记录表';
```

#### 13. 作弊日志表 (exam_cheat_log)
```sql
CREATE TABLE `exam_cheat_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `record_id` BIGINT NOT NULL COMMENT '考试记录ID',
    `user_id` BIGINT NOT NULL COMMENT '考生ID',
    `exam_id` BIGINT NOT NULL COMMENT '考试ID',
    `cheat_type` TINYINT NOT NULL COMMENT '作弊类型: 1-切屏 2-复制 3-粘贴 4-退出全屏',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '描述',
    `ip_address` VARCHAR(50) DEFAULT NULL COMMENT 'IP地址',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '记录时间',
    PRIMARY KEY (`id`),
    KEY `idx_record_id` (`record_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_exam_id` (`exam_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='作弊日志表';
```

### 初始数据

```sql
-- 初始化角色
INSERT INTO `sys_role` (`role_code`, `role_name`, `description`) VALUES
('ADMIN', '管理员', '系统管理员，拥有所有权限'),
('TEACHER', '教师', '可以管理题库、试卷、考试、阅卷'),
('STUDENT', '学生', '可以参加考试、查看成绩');

-- 初始化管理员账号 (密码: admin123)
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `status`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iuctErT.ggylkTtFa7Ue9n7x3xpe', '系统管理员', 1);

INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);
```

---

## API设计

### 通用说明

#### 基础URL
```
开发环境: http://localhost:8080/api
生产环境: https://your-domain.com/api
```

#### 请求头
```
Content-Type: application/json
Authorization: Bearer <token>
```

#### 通用响应格式
```json
{
    "code": 200,
    "message": "success",
    "data": {}
}
```

#### 错误码定义
| 错误码 | 说明 |
|--------|------|
| 200 | 成功 |
| 400 | 请求参数错误 |
| 401 | 未认证 |
| 403 | 无权限 |
| 404 | 资源不存在 |
| 500 | 服务器错误 |

---

### 1. 认证模块 (Auth)

#### 1.1 用户登录
```
POST /api/auth/login
```
**请求参数:**
```json
{
    "username": "admin",
    "password": "admin123"
}
```
**响应:**
```json
{
    "code": 200,
    "message": "success",
    "data": {
        "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
        "tokenType": "Bearer",
        "expiresIn": 86400,
        "userInfo": {
            "id": 1,
            "username": "admin",
            "realName": "系统管理员",
            "avatar": null,
            "roles": ["ADMIN"]
        }
    }
}
```

#### 1.2 用户注册
```
POST /api/auth/register
```
**请求参数:**
```json
{
    "username": "student001",
    "password": "123456",
    "realName": "张三",
    "email": "zhangsan@email.com",
    "phone": "13800138000",
    "classId": 1
}
```

#### 1.3 退出登录
```
POST /api/auth/logout
```

#### 1.4 获取当前用户信息
```
GET /api/auth/info
```

#### 1.5 修改密码
```
PUT /api/auth/password
```
**请求参数:**
```json
{
    "oldPassword": "123456",
    "newPassword": "654321"
}
```

---

### 2. 用户管理模块 (User)

#### 2.1 分页查询用户列表
```
GET /api/user/page?current=1&size=10&username=&realName=&roleId=
```
**响应:**
```json
{
    "code": 200,
    "data": {
        "records": [
            {
                "id": 1,
                "username": "admin",
                "realName": "系统管理员",
                "email": "admin@email.com",
                "phone": "13800138000",
                "status": 1,
                "roles": ["ADMIN"],
                "createTime": "2024-01-01 00:00:00"
            }
        ],
        "total": 100,
        "current": 1,
        "size": 10
    }
}
```

#### 2.2 获取用户详情
```
GET /api/user/{id}
```

#### 2.3 创建用户
```
POST /api/user
```
**请求参数:**
```json
{
    "username": "teacher001",
    "password": "123456",
    "realName": "李老师",
    "email": "teacher@email.com",
    "phone": "13900139000",
    "gender": 1,
    "roleIds": [2]
}
```

#### 2.4 更新用户
```
PUT /api/user/{id}
```

#### 2.5 删除用户
```
DELETE /api/user/{id}
```

#### 2.6 启用/禁用用户
```
PUT /api/user/{id}/status?status=1
```

#### 2.7 重置密码
```
PUT /api/user/{id}/reset-password
```

---

### 3. 班级管理模块 (Class)

#### 3.1 分页查询班级列表
```
GET /api/class/page?current=1&size=10&className=
```

#### 3.2 获取所有班级(下拉选择)
```
GET /api/class/list
```

#### 3.3 获取班级详情
```
GET /api/class/{id}
```

#### 3.4 创建班级
```
POST /api/class
```
**请求参数:**
```json
{
    "className": "计算机2024级1班",
    "description": "计算机科学与技术专业",
    "teacherId": 2
}
```

#### 3.5 更新班级
```
PUT /api/class/{id}
```

#### 3.6 删除班级
```
DELETE /api/class/{id}
```

#### 3.7 获取班级学生列表
```
GET /api/class/{id}/students
```

---

### 4. 科目管理模块 (Subject)

#### 4.1 分页查询科目列表
```
GET /api/subject/page?current=1&size=10&subjectName=
```

#### 4.2 获取所有科目(下拉选择)
```
GET /api/subject/list
```

#### 4.3 创建科目
```
POST /api/subject
```
**请求参数:**
```json
{
    "subjectName": "数据结构",
    "subjectCode": "DS",
    "description": "计算机专业基础课程"
}
```

#### 4.4 更新科目
```
PUT /api/subject/{id}
```

#### 4.5 删除科目
```
DELETE /api/subject/{id}
```

---

### 5. 题目管理模块 (Question)

#### 5.1 分页查询题目列表
```
GET /api/question/page?current=1&size=10&subjectId=&questionType=&difficulty=&keyword=
```

#### 5.2 获取题目详情
```
GET /api/question/{id}
```

#### 5.3 创建题目
```
POST /api/question
```
**请求参数(单选题):**
```json
{
    "subjectId": 1,
    "questionType": 1,
    "content": "以下哪个是Java的基本数据类型？",
    "difficulty": 2,
    "score": 5,
    "analysis": "Java的基本数据类型包括byte, short, int, long, float, double, char, boolean",
    "options": [
        {"optionCode": "A", "optionContent": "String", "isCorrect": 0},
        {"optionCode": "B", "optionContent": "Integer", "isCorrect": 0},
        {"optionCode": "C", "optionContent": "int", "isCorrect": 1},
        {"optionCode": "D", "optionContent": "Array", "isCorrect": 0}
    ],
    "answer": "C"
}
```

**请求参数(多选题):**
```json
{
    "subjectId": 1,
    "questionType": 2,
    "content": "以下哪些是面向对象的特性？",
    "difficulty": 3,
    "score": 10,
    "options": [
        {"optionCode": "A", "optionContent": "封装", "isCorrect": 1},
        {"optionCode": "B", "optionContent": "继承", "isCorrect": 1},
        {"optionCode": "C", "optionContent": "多态", "isCorrect": 1},
        {"optionCode": "D", "optionContent": "递归", "isCorrect": 0}
    ],
    "answer": "A,B,C"
}
```

**请求参数(判断题):**
```json
{
    "subjectId": 1,
    "questionType": 3,
    "content": "Java是一种面向对象的编程语言。",
    "difficulty": 1,
    "score": 2,
    "answer": "1",
    "analysis": "Java确实是一种面向对象的编程语言"
}
```

**请求参数(填空题):**
```json
{
    "subjectId": 1,
    "questionType": 4,
    "content": "Java中，___关键字用于定义常量。",
    "difficulty": 2,
    "score": 5,
    "answer": "final"
}
```

**请求参数(简答题):**
```json
{
    "subjectId": 1,
    "questionType": 5,
    "content": "请简述Java中的垃圾回收机制。",
    "difficulty": 4,
    "score": 20,
    "answer": "Java的垃圾回收机制...(参考答案)"
}
```

#### 5.4 更新题目
```
PUT /api/question/{id}
```

#### 5.5 删除题目
```
DELETE /api/question/{id}
```

#### 5.6 批量导入题目
```
POST /api/question/import
Content-Type: multipart/form-data
```
**请求参数:**
- file: Excel文件

#### 5.7 导出题目模板
```
GET /api/question/template
```

---

### 6. 试卷管理模块 (Paper)

#### 6.1 分页查询试卷列表
```
GET /api/paper/page?current=1&size=10&subjectId=&paperName=&status=
```

#### 6.2 获取试卷详情
```
GET /api/paper/{id}
```

#### 6.3 创建试卷(手动组卷)
```
POST /api/paper
```
**请求参数:**
```json
{
    "paperName": "数据结构期末考试",
    "subjectId": 1,
    "duration": 120,
    "passScore": 60,
    "paperType": 1,
    "questions": [
        {"questionId": 1, "score": 5, "sortOrder": 1},
        {"questionId": 2, "score": 10, "sortOrder": 2}
    ]
}
```

#### 6.4 随机组卷
```
POST /api/paper/random
```
**请求参数:**
```json
{
    "paperName": "数据结构随机测试",
    "subjectId": 1,
    "duration": 60,
    "passScore": 60,
    "rules": [
        {"questionType": 1, "count": 10, "scorePerQuestion": 2, "difficulty": null},
        {"questionType": 2, "count": 5, "scorePerQuestion": 4, "difficulty": 3},
        {"questionType": 3, "count": 10, "scorePerQuestion": 2, "difficulty": null},
        {"questionType": 5, "count": 2, "scorePerQuestion": 20, "difficulty": 4}
    ]
}
```

#### 6.5 更新试卷
```
PUT /api/paper/{id}
```

#### 6.6 删除试卷
```
DELETE /api/paper/{id}
```

#### 6.7 发布试卷
```
PUT /api/paper/{id}/publish
```

#### 6.8 预览试卷
```
GET /api/paper/{id}/preview
```

---

### 7. 考试管理模块 (Exam)

#### 7.1 分页查询考试列表
```
GET /api/exam/page?current=1&size=10&examName=&status=
```

#### 7.2 获取考试详情
```
GET /api/exam/{id}
```

#### 7.3 创建考试
```
POST /api/exam
```
**请求参数:**
```json
{
    "examName": "数据结构2024年期末考试",
    "paperId": 1,
    "classIds": "1,2,3",
    "startTime": "2024-06-20 09:00:00",
    "endTime": "2024-06-20 11:00:00",
    "duration": 120,
    "maxAttempts": 1,
    "antiCheatEnabled": 1,
    "showResult": 1,
    "showAnswer": 0
}
```

#### 7.4 更新考试
```
PUT /api/exam/{id}
```

#### 7.5 删除考试
```
DELETE /api/exam/{id}
```

#### 7.6 发布考试
```
PUT /api/exam/{id}/publish
```

#### 7.7 获取学生可参加的考试列表
```
GET /api/exam/available
```

#### 7.8 开始考试
```
POST /api/exam/{id}/start
```
**响应:**
```json
{
    "code": 200,
    "data": {
        "recordId": 1,
        "examName": "数据结构期末考试",
        "duration": 120,
        "remainingTime": 7200,
        "questions": [
            {
                "id": 1,
                "questionType": 1,
                "content": "以下哪个是Java的基本数据类型？",
                "score": 5,
                "options": [
                    {"optionCode": "A", "optionContent": "String"},
                    {"optionCode": "B", "optionContent": "Integer"},
                    {"optionCode": "C", "optionContent": "int"},
                    {"optionCode": "D", "optionContent": "Array"}
                ]
            }
        ]
    }
}
```

#### 7.9 保存答案(自动保存)
```
POST /api/exam/answer
```
**请求参数:**
```json
{
    "recordId": 1,
    "questionId": 1,
    "answerContent": "C"
}
```

#### 7.10 提交试卷
```
POST /api/exam/{recordId}/submit
```

#### 7.11 获取考试结果
```
GET /api/exam/result/{recordId}
```

#### 7.12 记录作弊行为
```
POST /api/exam/cheat
```
**请求参数:**
```json
{
    "recordId": 1,
    "cheatType": 1,
    "description": "切换到其他页面"
}
```

---

### 8. 成绩管理模块 (Grade)

#### 8.1 分页查询成绩列表(教师)
```
GET /api/grade/page?current=1&size=10&examId=&classId=&studentName=
```

#### 8.2 获取学生成绩列表(学生)
```
GET /api/grade/my?current=1&size=10
```

#### 8.3 获取成绩详情
```
GET /api/grade/{recordId}
```

#### 8.4 批改主观题
```
PUT /api/grade/review
```
**请求参数:**
```json
{
    "answerId": 1,
    "score": 15,
    "reviewComment": "答案基本正确，但缺少具体例子"
}
```

#### 8.5 批量完成阅卷
```
PUT /api/grade/complete/{recordId}
```

#### 8.6 导出成绩
```
GET /api/grade/export?examId=1
```

---

### 9. 统计分析模块 (Statistics)

#### 9.1 获取仪表盘统计
```
GET /api/statistics/dashboard
```
**响应:**
```json
{
    "code": 200,
    "data": {
        "userCount": 100,
        "questionCount": 500,
        "paperCount": 20,
        "examCount": 15,
        "todayExamCount": 2
    }
}
```

#### 9.2 获取考试统计
```
GET /api/statistics/exam/{examId}
```
**响应:**
```json
{
    "code": 200,
    "data": {
        "examId": 1,
        "examName": "数据结构期末考试",
        "participantCount": 50,
        "submittedCount": 48,
        "passedCount": 40,
        "passRate": 83.3,
        "averageScore": 75.5,
        "maxScore": 98,
        "minScore": 32,
        "scoreDistribution": [
            {"range": "0-59", "count": 8},
            {"range": "60-69", "count": 10},
            {"range": "70-79", "count": 15},
            {"range": "80-89", "count": 10},
            {"range": "90-100", "count": 5}
        ]
    }
}
```

#### 9.3 获取题目正确率统计
```
GET /api/statistics/question?examId=1
```

---

## 详细开发步骤

### 测试文件保存目录

测试文件保存在项目根目录的 `tests/` 目录下。

---

### 阶段一：环境搭建 (预计2天)

#### 步骤 1.1：初始化后端项目
- 使用 Spring Initializr 创建项目
- 配置 pom.xml 依赖
- 配置 application.yml
- 创建包结构

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-1 - 1.1 后端项目启动测试]

#### 步骤 1.2：初始化前端项目（简化）
- 使用 Vite 创建 Vue3 项目（不使用TypeScript）
- 安装依赖：Arco Design Vue, Vue Router, Axios
- 配置 vite.config.js
- 创建简化目录结构

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-1 - 1.2 前端项目启动测试]

#### 步骤 1.3：Docker环境配置
- 创建 docker-compose.yml
- 配置 MySQL 容器
- 配置 Redis 容器
- 配置 Nginx 容器

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-1 - 1.3 Docker 环境测试]

#### 步骤 1.4：数据库初始化
- 执行建表SQL脚本
- 插入初始化数据

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-1 - 1.4 数据库初始化测试]

---

### 阶段二：基础架构开发 (预计3天)

#### 步骤 2.1：后端基础架构
- 创建统一返回结果类 Result
- 创建全局异常处理器
- 配置 MyBatis-Plus
- 配置 Redis
- 配置 Knife4j 接口文档

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-2 - 2.1 统一返回结果测试] [2.2 全局异常处理测试] [2.4 Knife4j接口文档测试]

#### 步骤 2.2：安全认证模块
- 配置 Spring Security
- 实现 JWT 工具类
- 实现 JWT 过滤器
- 实现 UserDetailsService

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-2 - 2.3 JWT认证测试]

#### 步骤 2.3：前端基础架构（简化）
- 封装 Axios 请求（request.js）
- 配置基础路由
- 创建主布局组件（直接使用Arco Layout）

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-2 - 2.5 前端Axios封装测试]（手动测试）

---

### 阶段三：用户管理模块 (预计2天)

#### 步骤 3.1：后端用户管理
- 创建 User 实体类
- 创建 UserMapper
- 创建 UserService 和实现类
- 创建 UserController
- 实现登录、注册、用户CRUD

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-3 - 3.1 登录接口测试] [3.2 注册接口测试] [3.3 用户CRUD接口测试]

#### 步骤 3.2：前端用户管理（简化）
- 实现登录页面（使用Arco Form）
- 实现用户列表页面（使用Arco Table）
- 实现用户弹窗表单（使用Arco Modal + Form）

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-3 - 3.4 前端登录页面测试]（手动测试）

---

### 阶段四：班级科目管理 (预计2天)

#### 步骤 4.1：后端班级科目管理
- 创建 Class, Subject 实体类
- 创建 Mapper, Service, Controller
- 实现 CRUD 接口

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-4 - 4.1 班级管理接口测试] [4.2 科目管理接口测试]

#### 步骤 4.2：前端班级科目管理（简化）
- 实现班级列表页面（复用Table组件模式）
- 实现科目列表页面（复用Table组件模式）

> ✅ **完成后测试**: 手动测试页面功能

---

### 阶段五：题库管理模块 (预计4天)

#### 步骤 5.1：后端题库管理
- 创建 Question, QuestionOption 实体类
- 创建 Mapper, Service, Controller
- 实现题目 CRUD 接口
- 实现题目批量导入
- 实现题目导出模板

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-5 - 5.1 题目创建测试] [5.2 题目查询测试] [5.3 题目导入测试]

#### 步骤 5.2：前端题库管理（简化）
- 实现题目列表页面（Arco Table + 筛选）
- 实现题目表单页面（根据题型动态渲染选项）

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-5 - 5.4 前端题目页面测试]（手动测试）

---

### 阶段六：试卷管理模块 (预计3天)

#### 步骤 6.1：后端试卷管理
- 创建 Paper, PaperQuestion 实体类
- 创建 Mapper, Service, Controller
- 实现手动组卷接口
- 实现随机组卷接口
- 实现试卷预览接口

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-6 - 6.1 手动组卷测试] [6.2 随机组卷测试]

#### 步骤 6.2：前端试卷管理（简化）
- 实现试卷列表页面
- 实现组卷页面（题目选择 + 分值设置）

> ✅ **完成后测试**: 手动测试页面功能

---

### 阶段七：考试管理模块 (预计4天)

#### 步骤 7.1：后端考试管理
- 创建 Exam, ExamRecord, Answer 实体类
- 创建 Mapper, Service, Controller
- 实现考试 CRUD 接口
- 实现开始考试接口
- 实现保存答案接口
- 实现提交试卷接口
- 实现自动评分逻辑

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-7 - 7.1 考试创建测试] [7.2 开始考试测试]

#### 步骤 7.2：前端考试管理（简化）
- 实现考试列表页面
- 实现考试表单弹窗

> ✅ **完成后测试**: 手动测试页面功能

---

### 阶段八：在线考试模块 (预计3天)

#### 步骤 8.1：考试界面开发（前端核心页面）
- 实现考试界面布局（左侧题目 + 右侧答题卡）
- 实现倒计时组件（简单计时器）
- 实现答题卡组件（题号导航）
- 实现自动保存功能（答题后调用接口）

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-8 - 8.1 答案保存测试] [8.2 提交试卷测试] [8.4 考试界面测试]

#### 步骤 8.2：防作弊功能（简化版）
- 实现切屏检测（visibilitychange事件）
- 实现复制粘贴禁用（阻止默认事件）
- 后端作弊行为记录接口

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-8 - 8.3 防作弊功能测试]（手动测试）

---

### 阶段九：成绩管理模块 (预计3天)

#### 步骤 9.1：后端成绩管理
- 创建 GradeController
- 实现成绩列表查询
- 实现主观题批改接口
- 实现成绩导出接口

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-9 - 9.1 成绩查询测试] [9.2 主观题批改测试] [9.3 成绩导出测试]

#### 步骤 9.2：前端成绩管理（简化）
- 实现成绩列表页面
- 实现阅卷页面（主观题评分表单）

> ✅ **完成后测试**: 手动测试页面功能

---

### 阶段十：统计分析模块 (预计2天)

#### 步骤 10.1：后端统计分析
- 实现仪表盘统计接口
- 实现考试统计分析接口
- 实现题目正确率统计

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-10 - 10.1 仪表盘统计测试] [10.2 考试统计测试]

#### 步骤 10.2：前端统计分析（简化）
- 实现仪表盘页面（使用Arco统计数值组件）
- 使用Arco Chart组件展示简单图表

> ✅ **完成后测试**: 手动测试页面功能

---

### 阶段十一：系统优化 (预计2天)

#### 步骤 11.1：性能优化（后端重点）
- 添加 Redis 缓存
- 数据库索引优化
- 接口响应优化

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-11 - 11.1 缓存测试]

#### 步骤 11.2：安全加固
- 接口权限校验
- XSS/CSRF 防护
- SQL注入防护
- 敏感数据加密

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-11 - 11.2 安全测试]

---

### 阶段十二：部署上线 (预计2天)

#### 步骤 12.1：Docker 部署
- 编写后端 Dockerfile
- 前端打包部署到 Nginx
- 配置 docker-compose
- 配置 Nginx 反向代理

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-12 - 12.1 Docker部署测试]

#### 步骤 12.2：项目文档
- 完善 README.md
- 编写部署文档
- 编写使用手册

> ✅ **完成后测试**: 参考 [TESTING.md - TEST-12 - 12.2 集成测试]（完整业务流程测试）

---

## 开发时间表

| 阶段 | 内容 | 预计时间 | 重点 | 测试文档对应章节 |
|------|------|----------|------|------------------|
| 阶段一 | 环境搭建 | 2天 | 后端 | TEST-1 |
| 阶段二 | 基础架构 | 3天 | **后端核心** | TEST-2 |
| 阶段三 | 用户管理 | 2天 | **后端核心** | TEST-3 |
| 阶段四 | 班级科目 | 1天 | 后端 | TEST-4 |
| 阶段五 | 题库管理 | 3天 | **后端核心** | TEST-5 |
| 阶段六 | 试卷管理 | 3天 | **后端核心** | TEST-6 |
| 阶段七 | 考试管理 | 3天 | **后端核心** | TEST-7 |
| 阶段八 | 在线考试 | 3天 | 前后端 | TEST-8 |
| 阶段九 | 成绩管理 | 2天 | **后端核心** | TEST-9 |
| 阶段十 | 统计分析 | 2天 | 后端 | TEST-10 |
| 阶段十一 | 系统优化 | 2天 | 后端 | TEST-11 |
| 阶段十二 | 部署上线 | 2天 | 运维 | TEST-12 |
| **总计** | - | **28天** | - | - |

> **说明**: 前端简化后，总开发时间从33天缩减到28天，后端开发占比约70%。

---

## 技术难点与解决方案

### 1. 防作弊机制
**问题**: 如何有效防止学生在考试中作弊
**解决方案**:
- 前端: 监听 `visibilitychange` 事件检测切屏
- 前端: 禁用右键菜单和复制粘贴快捷键
- 前端: 使用 Fullscreen API 强制全屏
- 后端: 记录所有异常行为日志
- 后端: 作弊次数超过阈值自动提交试卷

### 2. 考试时间同步
**问题**: 如何保证考试时间的准确性
**解决方案**:
- 开始考试时从服务器获取剩余时间
- 前端使用 `setInterval` 倒计时，定期与服务器同步
- 超时自动提交试卷

### 3. 答案自动保存
**问题**: 如何防止意外情况导致答案丢失
**解决方案**:
- 每道题作答后自动调用保存接口
- 使用防抖优化请求频率
- 本地 localStorage 备份
- 断点续考功能

### 4. 客观题自动评分
**问题**: 如何准确地自动评分
**解决方案**:
- 单选题: 答案完全匹配得满分
- 多选题: 可配置全对得分/部分得分
- 判断题: 答案匹配得满分
- 填空题: 支持多答案匹配

---

## 参考资源

- Vue3 官方文档: https://vuejs.org/
- Arco Design Vue: https://arco.design/vue
- Spring Boot 官方文档: https://spring.io/projects/spring-boot
- MyBatis-Plus: https://baomidou.com/
- Docker 官方文档: https://docs.docker.com/

