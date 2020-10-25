package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.SysRole;
import org.example.model.SysUser;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;

public class UserMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = userMapper.selectById(1L);
            Assert.assertNotNull(user);
            Assert.assertEquals("admin", user.getUserName());
        }
    }

    @Test
    public void testSelectAll() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<SysUser> userList = userMapper.selectAll();
            Assert.assertNotNull(userList);
            Assert.assertTrue(userList.size() > 0);
        }
    }

    @Test
    public void testSelectRolesByUserId() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<SysRole> roleList = userMapper.selectRolesByUserId(1L);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        }
    }

    @Test
    public void testInsert() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.org");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insert(user);
            Assert.assertEquals(1, result);
            Assert.assertNull(user.getId()); // id 为 null，没有给 id 赋值，并且没有配置回写 id 的值
        } finally {
            session.rollback(); // 为了不影响其他测试，这里选择回滚
            session.close();
        }
    }

    @Test
    public void testInsert2() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.org");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insert2(user);
            Assert.assertEquals(1, result);
            Assert.assertNotNull(user.getId());
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testInsert3() {
        SqlSession session = getSqlSession();
        try {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            SysUser user = new SysUser();
            user.setUserName("test1");
            user.setUserPassword("123456");
            user.setUserEmail("test@mybatis.org");
            user.setUserInfo("test info");
            user.setHeadImg(new byte[]{1, 2, 3});
            user.setCreateTime(new Date());
            int result = userMapper.insert3(user);
            Assert.assertEquals(1, result);
            Assert.assertNotNull(user.getId());
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
            SysUser user = userMapper.selectById(1L);
            Assert.assertEquals("admin", user.getUserName());
            user.setUserName("admin_test");
            user.setUserEmail("test@mybatis.org");
            int result = userMapper.updateById(user);
            Assert.assertEquals(1, result);
            user = userMapper.selectById(1L);
            Assert.assertEquals("admin_test", user.getUserName());
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
            SysUser user1 = userMapper.selectById(1L);
            Assert.assertNotNull(user1);
            Assert.assertEquals(1, userMapper.deleteById(1L));
            Assert.assertNull(userMapper.selectById(1L));

            SysUser user2 = userMapper.selectById(1001L);
            Assert.assertNotNull(user2);
            Assert.assertEquals(1, userMapper.deleteById(user2));
            Assert.assertNull(userMapper.selectById(1001L));
        } finally {
            session.rollback();
            session.close();
        }
    }

    @Test
    public void testSelectRolesByUserIdAndRoleEnabled() {
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            List<SysRole> roleList = userMapper.selectRolesByUserIdAndRoleEnabled(1L, 1);
            Assert.assertNotNull(roleList);
            Assert.assertTrue(roleList.size() > 0);
        }
    }

}
