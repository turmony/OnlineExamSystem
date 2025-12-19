-- ============================================
-- 在线考试系统初始数据脚本
-- ============================================

-- 选择数据库
USE exam;

-- 初始化角色
INSERT INTO `sys_role` (`role_code`, `role_name`, `description`) VALUES
('ADMIN', '管理员', '系统管理员，拥有所有权限'),
('TEACHER', '教师', '可以管理题库、试卷、考试、阅卷'),
('STUDENT', '学生', '可以参加考试、查看成绩');

-- 初始化管理员账号 (密码: admin123)
-- 密码使用 BCrypt 加密，对应明文密码为 admin123
INSERT INTO `sys_user` (`username`, `password`, `real_name`, `status`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iuctErT.ggylkTtFa7Ue9n7x3xpe', '系统管理员', 1);

-- 为管理员分配管理员角色
INSERT INTO `sys_user_role` (`user_id`, `role_id`) VALUES (1, 1);

