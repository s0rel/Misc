CREATE DATABASE IF NOT EXISTS mybatis DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE mybatis;

CREATE TABLE user_role (
    user_id BIGINT(20) COMMENT '用户id',
    role_id BIGINT(20) COMMENT '角色id'
);

ALTER TABLE user COMMENT '用户角色关联表';

INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO user_role (user_id, role_id) VALUES (1001, 2);