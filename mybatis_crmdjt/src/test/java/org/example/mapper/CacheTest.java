package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.Role;
import org.example.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CacheTest extends BaseMapperTest {
    /**
     * MyBatis 的一级缓存存在于 SqlSession 的生命周期中，在同一个 SqlSession 中查询时，MyBatis 会把执行
     * 的方法和参数通过算法生成缓存的键值，将键值和查询结果存入到一个 Map 对象中。如果同一个 SqlSession 中
     * 执行的方法和参数完全一致，那么通过算法会生成相同的键值，当 Map 缓存对象中已经存在该键值时，则会返回
     * 缓存中的对象。在 <select></select> 中配置 flushCache="true" 可以清空一级缓存。此外，任何的 INSERT、
     * UPDATE 和 DELETE 操作都会清空一级缓存。
     */
    @Test
    public void testL1Cache() {
        User user1 = null;
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            user1 = userMapper.selectById(1L);
            user1.setUserName("New Name");
            User user2 = userMapper.selectById(1L);
            Assertions.assertEquals("New Name", user2.getUserName());
            Assertions.assertEquals(user1, user2);
        }
        System.out.println("开启新的 SQLSession");
        try (SqlSession session = getSqlSession()) {
            UserMapper userMapper = session.getMapper(UserMapper.class);
            User user2 = userMapper.selectById(1L);
            Assertions.assertNotEquals("New Name", user2.getUserName());
            Assertions.assertNotEquals(user1, user2);
            userMapper.deleteById(2L);
            User user3 = userMapper.selectById(1L);
            Assertions.assertNotEquals(user2, user3);
        }
    }

    @Test
    public void testL2Cache() {
        Role role1 = null;
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            role1 = roleMapper.selectById(1L);
            role1.setRoleName("New Name");
            Role role2 = roleMapper.selectById(1L);
            Assertions.assertEquals("New Name", role2.getRoleName());
            Assertions.assertEquals(role1, role2);
        }
        System.out.println("开启新的 SQLSession");
        try (SqlSession session = getSqlSession()) {
            RoleMapper roleMapper = session.getMapper(RoleMapper.class);
            Role role2 = roleMapper.selectById(1L);
            Assertions.assertEquals("New Name", role2.getRoleName());
            Assertions.assertNotEquals(role1, role2);
            Role role3 = roleMapper.selectById(1L);
            Assertions.assertNotEquals(role2, role3);
        }
    }
}
