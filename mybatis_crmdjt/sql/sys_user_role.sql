CREATE TABLE sys_user_role (
    user_id BIGINT COMMENT '用户id',
    role_id BIGINT COMMENT '角色id'
);

ALTER TABLE sys_user COMMENT '用户角色关联表';

INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 1);
INSERT INTO sys_user_role (user_id, role_id) VALUES (1, 2);
INSERT INTO sys_user_role (user_id, role_id) VALUES (1001, 2);