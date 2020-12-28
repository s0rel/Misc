package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.Privilege;
import org.example.model.Role;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User result = userMapper.selectById(1L);
            Assertions.assertNotNull(result);
            Assertions.assertEquals("admin", result.getUserName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<User> result = userMapper.selectAll();
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.size() > 0);
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<Role> result = userMapper.selectRolesByUserId(1L);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.size() > 0);
        }
    }

    @Test
    public void testInsert() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            // 这里不需要设置 id 的值，写入数据库时会自动生成
            User user = new User();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.org");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1, 2, 3});
            // 插入当前时间
            user.setCreateTime(new Date());
            // 返回值是执行 SQL 影响的行数
            int result = userMapper.insert(user);
            Assertions.assertEquals(1, result);
            // id 为 null，没有给 id 赋值，并且没有配置回写 id 的值
            Assertions.assertNull(user.getId());
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
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insertByUseGeneratedKeys(user);
            Assertions.assertEquals(1, result);
            Assertions.assertNotNull(user.getId());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testInsertBySelectKey() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.org");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insertBySelectKey(user);
            Assertions.assertEquals(1, result);
            Assertions.assertNotNull(user.getId());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testUpdateById() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.selectById(1L);
            Assertions.assertEquals("admin", user.getUserName());
            user.setUserName("admin_test");
            user.setUserEmail("test@mybatis.org");
            int result = userMapper.updateById(user);
            Assertions.assertEquals(1, result);
            user = userMapper.selectById(1L);
            Assertions.assertEquals("admin_test", user.getUserName());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testDeleteById() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User result1 = userMapper.selectById(1L);
            Assertions.assertNotNull(result1);
            Assertions.assertEquals(1, userMapper.deleteById(1L));
            Assertions.assertNull(userMapper.selectById(1L));

            User result2 = userMapper.selectById(1001L);
            Assertions.assertNotNull(result2);
            Assertions.assertEquals(1, userMapper.deleteById(result2));
            Assertions.assertNull(userMapper.selectById(1001L));
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<Role> result = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
            Assertions.assertNotNull(result);
            Assertions.assertTrue(result.size() > 0);
        }
    }

    @Test
    public void testSelectByUser() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            // 只查询用户名时
            User user = new User();
            user.setUserName("ad");
            List<User> result = userMapper.selectByUser(user);
            Assertions.assertTrue(result.size() > 0);
            // 只查询用户邮箱时
            user = new User();
            user.setUserEmail("test@mybatis.org");
            result = userMapper.selectByUser(user);
            Assertions.assertTrue(result.size() > 0);
            // 当同时查询用户名和邮箱时
            user = new User();
            user.setUserName("ad");
            user.setUserEmail("test@mybatis.org");
            result = userMapper.selectByUser(user);
            Assertions.assertEquals(result.size(), 0);
        }
    }

    @Test
    public void testUpdateByIdSelective() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = new User();
            user.setId(1L);
            user.setUserEmail("test@mybatis.org");
            int result = userMapper.updateByIdSelective(user);
            Assertions.assertEquals(1, result);
            user = userMapper.selectById(1L);
            Assertions.assertEquals("admin", user.getUserName());
            Assertions.assertEquals("test@mybatis.org", user.getUserEmail());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectByIdOrUserName() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            // 只查询用户名时
            User user = new User();
            user.setId(1L);
            user.setUserName("admin");
            User result = userMapper.selectByIdOrUserName(user);
            Assertions.assertNotNull(result);
            // 当没有 id 时
            user.setId(null);
            result = userMapper.selectByIdOrUserName(user);
            Assertions.assertNotNull(result);
            // 当 id 和用户名都为 null 时
            user.setUserName(null);
            result = userMapper.selectByIdOrUserName(user);
            Assertions.assertNull(result);
        }
    }

    @Test
    public void testSelectByIdList() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<Long> idList = new ArrayList<>();
            idList.add(1L);
            idList.add(1001L);
            List<User> result = userMapper.selectByIdList(idList);
            Assertions.assertEquals(2, result.size());
        }
    }

    @Test
    public void testInsertList() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<User> userList = new ArrayList<>();
            for (int i = 0; i < 2; i++) {
                User user = new User();
                user.setUserName("test" + i);
                user.setUserPassword("123456");
                user.setUserEmail("test@mybatis.org");
                userList.add(user);
            }
            int result = userMapper.insertList(userList);
            Assertions.assertEquals(2, result);
            for (User user : userList) {
                System.out.println(user.getId());
            }
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testUpdateByMap() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("id", 1L);
            map.put("user_email", "test@mybatis.org");
            map.put("user_password", "12345678");
            userMapper.updateByMap(map);
            User result = userMapper.selectById(1L);
            Assertions.assertEquals("test@mybatis.org", result.getUserEmail());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectUserAndRoleById() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User result = userMapper.selectUserAndRoleById(1001L);
            Assertions.assertNotNull(result);
            Assertions.assertNotNull(result.getRole());
        }
    }

    @Test
    public void testSelectUserAndRoleById2() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User result = userMapper.selectUserAndRoleById2(1001L);
            Assertions.assertNotNull(result);
            Assertions.assertNotNull(result.getRole());
        }
    }

    @Test
    public void testSelectUserAndRoleById3() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User result = userMapper.selectUserAndRoleById3(1001L);
            Assertions.assertNotNull(result);
            Assertions.assertNotNull(result.getRole());
        }
    }

    @Test
    public void testSelectUserAndRoleByIdSelect() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User result = userMapper.selectUserAndRoleByIdSelect(1001L);
            Assertions.assertNotNull(result);
            System.out.println("调用 result.getRole()");
            Assertions.assertNotNull(result.getRole());
        }
    }

    @Test
    public void testSelectAllUserAndRoles() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<User> result = userMapper.selectAllUserAndRoles();
            for (User user : result) {
                System.out.println("用户名：" + user.getUserName());
                for (Role role : user.getRoleList()) {
                    System.out.println("角色名：" + role.getRoleName());
                    for (Privilege privilege : role.getPrivilegeList()) {
                        System.out.println("权限名：" + privilege.getPrivilegeName());
                    }
                }
            }
        }
    }


    @Test
    public void testSelectAllUserAndRolesSelect() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User result = userMapper.selectAllUserAndRolesSelect(1L);
            System.out.println("用户名：" + result.getUserName());
            for (Role role : result.getRoleList()) {
                System.out.println("角色名：" + role.getRoleName());
                for (Privilege privilege : role.getPrivilegeList()) {
                    System.out.println("权限名：" + privilege.getPrivilegeName());
                }
            }
        }
    }
}
