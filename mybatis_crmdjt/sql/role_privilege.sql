CREATE DATABASE IF NOT EXISTS mybatis DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE mybatis;

CREATE TABLE role_privilege (
    role_id BIGINT(20) COMMENT '角色id',
    privilege_id BIGINT(20) COMMENT '权限id'
);

ALTER TABLE user COMMENT '角色权限关联表';

INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 3);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 4);
INSERT INTO role_privilege (role_id, privilege_id) VALUES (2, 5);