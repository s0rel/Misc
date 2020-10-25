package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.model.SysRole;
import org.example.model.SysUser;

import java.util.List;

public interface UserMapper {
    SysUser selectById(Long id);

    List<SysUser> selectAll();

    List<SysRole> selectRolesByUserId(Long userId);

    int insert(SysUser user);

    int insert2(SysUser user); // 使用 JDBC 方式返回主键自增的值

    int insert3(SysUser user); // 使用 JDBC 方式返回主键自增的值

    int updateById(SysUser user);

    int deleteById(Long id);

    int deleteById(SysUser user);

    List<SysRole> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId, @Param("enabled") Integer enabled); // 传递多个参数
}
