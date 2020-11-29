package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.model.Role;
import org.example.model.User;

import java.util.List;

public interface UserMapper {
    User selectById(Long id);

    List<User> selectAll();

    List<Role> selectRolesByUserId(Long userId);

    int insert(User user);

    /**
     * 使用 JDBC 方式返回主键自增的值
     */
    int insertByUseGeneratedKeys(User user);

    /**
     * 使用 selectKey 返回主键的值
     */
    int insertBySelectKey(User user);

    int updateById(User user);

    int deleteById(Long id);

    int deleteById(User user);

    // 传递多个参数
    // 给参数配置 @Param 注解后，MyBatis 会自动将参数封装成 Map 类型，@Param 注解值会作为 Map 中的 key
    List<Role> selectRolesByUserIdAndRoleEnabled(@Param("userId") Long userId, @Param("enabled") Integer enabled);
}
