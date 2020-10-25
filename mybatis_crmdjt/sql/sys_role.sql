CREATE TABLE sys_role (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '角色id',
    role_name VARCHAR(50) COMMENT '角色名',
    enabled INT COMMENT '有效标志',
    create_by BIGINT COMMENT '创建人',
    create_time DATETIME COMMENT '创建时间',
    PRIMARY KEY (id)
);

ALTER TABLE sys_role COMMENT '角色表';

INSERT INTO sys_role (id, role_name, enabled, create_by, create_time) VALUES (1, '管理员', 1, 1, '2020-10-22 08:56:30');
INSERT INTO sys_role (id, role_name, enabled, create_by, create_time) VALUES (2, '普通用户', 1, 1, '2020-10-22 08:56:40');