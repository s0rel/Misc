package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.Role;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


import java.util.Date;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user = userMapper.selectById(1L);
            Assertions.assertNotNull(user);
            Assertions.assertEquals("admin", user.getUserName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<User> userList = userMapper.selectAll();
            Assertions.assertNotNull(userList);
            Assertions.assertTrue(userList.size() > 0);
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<Role> roleList = userMapper.selectRolesByUserId(1L);
            Assertions.assertNotNull(roleList);
            Assertions.assertTrue(roleList.size() > 0);
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
            user.setUserEmail("test@mybatis.org");
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
            User user1 = userMapper.selectById(1L);
            Assertions.assertNotNull(user1);
            Assertions.assertEquals(1, userMapper.deleteById(1L));
            Assertions.assertNull(userMapper.selectById(1L));

            User user2 = userMapper.selectById(1001L);
            Assertions.assertNotNull(user2);
            Assertions.assertEquals(1, userMapper.deleteById(user2));
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
            List<Role> roleList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
            Assertions.assertNotNull(roleList);
            Assertions.assertTrue(roleList.size() > 0);
        }
    }
}
