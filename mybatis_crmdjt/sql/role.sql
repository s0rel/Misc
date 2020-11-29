CREATE DATABASE IF NOT EXISTS mybatis DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE mybatis;

CREATE TABLE role (
    id BIGINT(20) NOT NULL AUTO_INCREMENT COMMENT '角色id',
    role_name VARCHAR(50) COMMENT '角色名',
    enabled INT(11) COMMENT '有效标志',
    create_by BIGINT(20) COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
);

ALTER TABLE role COMMENT '角色表';

INSERT INTO role (id, role_name, enabled, create_by, create_time) VALUES (1, '管理员', 1, 1, '2020-11-28 08:56:30');
INSERT INTO role (id, role_name, enabled, create_by, create_time) VALUES (2, '普通用户', 1, 1, '2020-11-28 08:56:40');