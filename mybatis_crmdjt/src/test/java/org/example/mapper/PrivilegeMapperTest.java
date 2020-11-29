package org.example.mapper;

import org.apache.ibatis.session.SqlSession;
import org.example.model.Privilege;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PrivilegeMapperTest extends BaseMapperTest {
    @Test
    public void testSelectById() {
        try (SqlSession sqlSession = getSqlSession()) {
            PrivilegeMapper privilegeMapper = sqlSession.getMapper(PrivilegeMapper.class);
            Privilege privilege = privilegeMapper.selectById(1L);
            Assertions.assertNotNull(privilege);
            Assertions.assertEquals("用户管理", privilege.getPrivilegeName());
        }
    }
}
