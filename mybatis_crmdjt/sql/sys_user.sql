CREATE TABLE sys_user (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户id',
    user_name VARCHAR(50) COMMENT '用户名',
    user_password VARCHAR(50) COMMENT '密码',
    user_email VARCHAR(50) COMMENT '邮箱',
    user_info TEXT COMMENT '简介',
    head_img BLOB COMMENT '头像',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
);

ALTER TABLE sys_user COMMENT '用户表';

INSERT INTO sys_user(id, user_name, user_password, user_email, user_info, head_img, create_time)
VALUES (1, 'admin', '123456', 'admin@mybatis.org', '管理员', NULL, '2020-10-22 08:55:00');
INSERT INTO sys_user(id, user_name, user_password, user_email, user_info, head_img, create_time)
VALUES (1001, 'test', '123456', 'test@mybatis.org', '测试用户', NULL, '2020-10-22 08:56:00');

