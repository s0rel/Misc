package org.example.mapper;

import org.apache.ibatis.annotations.Param;
import org.example.model.Role;
import org.example.model.User;

import java.util.List;
import java.util.Map;

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

    // 根据动态条件查询用户信息
    List<User> selectByUser(User user);

    // 根据主键更新
    int updateByIdSelective(User user);

    User selectByIdOrUserName(User user);

    List<User> selectByIdList(List<Long> idList);

    int insertList(List<User> userList);

    int updateByMap(Map<String, Object> map);

    /**
     * 使用 MyBatis 的一对一映射处理一对一关系
     */
    User selectUserAndRoleById(Long id);

    /**
     * 使用 resultMap 处理一对一映射关系
     */
    User selectUserAndRoleById2(Long id);

    /**
     * 使用 resultMap 的 association 标签处理一对一映射关系
     */
    User selectUserAndRoleById3(Long id);

    /**
     * 使用 resultMap 的 association 标签实现嵌套查询
     */
    User selectUserAndRoleByIdSelect(Long id);

    List<User> selectAllUserAndRoles();

    User selectAllUserAndRolesSelect(Long id);
}
