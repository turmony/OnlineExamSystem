-- ============================================
-- 在线考试系统数据库建表脚本
-- ============================================

-- 1. 系统用户表
CREATE TABLE IF NOT EXISTS `sys_user` (
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

-- 2. 角色表
CREATE TABLE IF NOT EXISTS `sys_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色ID',
    `role_code` VARCHAR(50) NOT NULL COMMENT '角色编码',
    `role_name` VARCHAR(50) NOT NULL COMMENT '角色名称',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '角色描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_role_code` (`role_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色表';

-- 3. 用户角色关联表
CREATE TABLE IF NOT EXISTS `sys_user_role` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role_id` BIGINT NOT NULL COMMENT '角色ID',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_user_role` (`user_id`, `role_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_role_id` (`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户角色关联表';

-- 4. 班级表
CREATE TABLE IF NOT EXISTS `sys_class` (
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

-- 5. 科目表
CREATE TABLE IF NOT EXISTS `exam_subject` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '科目ID',
    `subject_name` VARCHAR(100) NOT NULL COMMENT '科目名称',
    `subject_code` VARCHAR(50) DEFAULT NULL COMMENT '科目编码',
    `description` VARCHAR(255) DEFAULT NULL COMMENT '科目描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='科目表';

-- 6. 题目表
CREATE TABLE IF NOT EXISTS `exam_question` (
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

-- 7. 题目选项表
CREATE TABLE IF NOT EXISTS `exam_question_option` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '选项ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `option_code` VARCHAR(10) NOT NULL COMMENT '选项编码(A/B/C/D)',
    `option_content` TEXT NOT NULL COMMENT '选项内容',
    `is_correct` TINYINT DEFAULT 0 COMMENT '是否正确答案: 0-否 1-是',
    `sort_order` INT DEFAULT 0 COMMENT '排序',
    PRIMARY KEY (`id`),
    KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题目选项表';

-- 8. 试卷表
CREATE TABLE IF NOT EXISTS `exam_paper` (
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

-- 9. 试卷题目关联表
CREATE TABLE IF NOT EXISTS `exam_paper_question` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `paper_id` BIGINT NOT NULL COMMENT '试卷ID',
    `question_id` BIGINT NOT NULL COMMENT '题目ID',
    `score` DECIMAL(5,2) NOT NULL COMMENT '题目分值',
    `sort_order` INT DEFAULT 0 COMMENT '题目顺序',
    PRIMARY KEY (`id`),
    KEY `idx_paper_id` (`paper_id`),
    KEY `idx_question_id` (`question_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='试卷题目关联表';

-- 10. 考试表
CREATE TABLE IF NOT EXISTS `exam_exam` (
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

-- 11. 考试记录表
CREATE TABLE IF NOT EXISTS `exam_record` (
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

-- 12. 答题记录表
CREATE TABLE IF NOT EXISTS `exam_answer` (
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

-- 13. 作弊日志表
CREATE TABLE IF NOT EXISTS `exam_cheat_log` (
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

