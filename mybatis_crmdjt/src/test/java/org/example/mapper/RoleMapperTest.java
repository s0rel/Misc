package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.Privilege;
import org.example.model.Role;
import org.example.type.Enabled;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

public class RoleMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role result = roleMapper.selectById(1L);
            Assertions.assertNotNull(result);
            Assertions.assertEquals("管理员", result.getRoleName());
        }
    }

    @Test
    public void testSelectByIdByResultMap() {
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role result = roleMapper.selectByIdByResultMap(1L);
            Assertions.assertNotNull(result);
            Assertions.assertEquals("管理员", result.getRoleName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            List<Role> result = roleMapper.selectAll();
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.size() > 0);
            Assertions.assertNotNull(result.get(0).getRoleName());
        }
    }

    @Test
    public void testInsert() {
        SqlSession session = getSqlSession();
        try {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role role = new Role();
            role.setRoleName("test1");
            // 插入当前时间
            role.setCreateTime(new Date());
            role.setEnabled(Enabled.enabled);
            role.setCreateBy(1L);
            // 返回值是执行 SQL 影响的行数
            int result = roleMapper.insert(role);
            Assertions.assertEquals(1, result);
            // id 为 null，没有给 id 赋值，并且没有配置回写 id 的值
            Assertions.assertNull(role.getId());
        } finally {
            // 为了不影响其他测试，这里选择回滚
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testInsertByUseGeneratedKeys() {
        SqlSession session = getSqlSession();
        try {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role role = new Role();
            role.setRoleName("test1");
            // 插入当前时间
            role.setCreateTime(new Date());
            role.setEnabled(Enabled.enabled);
            role.setCreateBy(1L);
            int result = roleMapper.insertByUseGeneratedKeys(role);
            Assertions.assertEquals(1, result);
            Assertions.assertNotNull(role.getId());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testInsertBySelectKey() {
        SqlSession session = getSqlSession();
        try {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role role = new Role();
            role.setRoleName("test1");
            // 插入当前时间
            role.setCreateTime(new Date());
            role.setEnabled(Enabled.enabled);
            role.setCreateBy(1L);
            int result = roleMapper.insertBySelectKey(role);
            Assertions.assertEquals(1, result);
            Assertions.assertNotNull(role.getId());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession session = getSqlSession();
        try {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role role = roleMapper.selectById(1L);
            Assertions.assertEquals("管理员", role.getRoleName());
            role.setRoleName("admin_test");
            int result = roleMapper.updateById(role);
            Assertions.assertEquals(1, result);
            role = roleMapper.selectById(1L);
            Assertions.assertEquals("admin_test", role.getRoleName());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testDeleteById() {
        SqlSession session = getSqlSession();
        try {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role result = roleMapper.selectById(1L);
            Assertions.assertNotNull(result);
            Assertions.assertEquals(1, roleMapper.deleteById(1L));
            Assertions.assertNull(roleMapper.selectById(1L));
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectAllRoleAndPrivileges() {
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            List<Role> result = roleMapper.selectAllRoleAndPrivileges();
            for (Role role : result) {
                System.out.println("角色名：" + role.getRoleName());
                for (Privilege privilege : role.getPrivilegeList()) {
                    System.out.println("权限名：" + privilege.getPrivilegeName());
                }
            }
        }
    }

    @Test
    public void testSelectRoleByUserIdChoose() {
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role role = roleMapper.selectById(2L);
            role.setEnabled(Enabled.disabled);
            roleMapper.updateById(role);
            List<Role> result = roleMapper.selectRoleByUserIdChoose(1L);
            for (Role r : result) {
                System.out.println("角色名：" + r.getRoleName());
                if (r.getId().equals(1L)) {
                    Assertions.assertNotNull(r.getPrivilegeList());
                } else {
                    Assertions.assertNull(r.getPrivilegeList());
                    continue;
                }
                for (Privilege privilege : r.getPrivilegeList()) {
                    System.out.println("权限名：" + privilege.getPrivilegeName());
                }
            }
        }
    }

    @Test
    public void testUpdateById2() {
        SqlSession session = getSqlSession();
        try {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role result = roleMapper.selectById(2L);
            Assertions.assertEquals(Enabled.enabled, result.getEnabled());
            result.setEnabled(Enabled.disabled);
            roleMapper.updateById(result);
        } finally {
            session.rollback();
            session.close();
        }
    }
}
