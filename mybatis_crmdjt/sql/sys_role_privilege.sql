CREATE TABLE sys_role_privilege (
    role_id BIGINT COMMENT '角色id',
    privilege_id BIGINT COMMENT '权限id'
);

ALTER TABLE sys_user COMMENT '角色权限关联表';

INSERT INTO sys_role_privilege (role_id, privilege_id) VALUES (1, 1);
INSERT INTO sys_role_privilege (role_id, privilege_id) VALUES (1, 3);
INSERT INTO sys_role_privilege (role_id, privilege_id) VALUES (1, 2);
INSERT INTO sys_role_privilege (role_id, privilege_id) VALUES (2, 4);
INSERT INTO sys_role_privilege (role_id, privilege_id) VALUES (2, 5);