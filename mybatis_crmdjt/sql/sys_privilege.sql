CREATE TABLE sys_privilege (
    id BIGINT NOT NULL AUTO_INCREMENT COMMENT '权限id',
    privilege_name VARCHAR(50) COMMENT '权限名称',
    privilege_url VARCHAR(200) COMMENT '权限URL',
    PRIMARY KEY (id)
);

ALTER TABLE sys_privilege COMMENT '权限表';

INSERT INTo sys_privilege (id, privilege_name, privilege_url) VALUES (1, '用户管理', '/users');
INSERT INTo sys_privilege (id, privilege_name, privilege_url) VALUES (2, '角色管理', '/roles');
INSERT INTo sys_privilege (id, privilege_name, privilege_url) VALUES (3, '系统日志', '/logs');
INSERT INTo sys_privilege (id, privilege_name, privilege_url) VALUES (4, '人员维护', '/persons');
INSERT INTo sys_privilege (id, privilege_name, privilege_url) VALUES (5, '单位维护', '/companies');