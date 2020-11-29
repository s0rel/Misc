package org.example.mapper;


import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.example.model.Role;

import java.util.List;

public interface RoleMapper {
    @Select("SELECT id, role_name, enabled, create_by, create_time FROM role WHERE id = #{id}")
    Role selectById(Long id);

    @Results(id = "roleResultMap", value = {
            @Result(property = "id", column = "id", id = true),
            @Result(property = "roleName", column = "role_name"),
            @Result(property = "enabled", column = "enabled"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "createTime", column = "create_time")})
    @Select("SELECT id, role_name, enabled, create_by, create_time FROM role WHERE id = #{id}")
    Role selectByIdByResultMap(Long id);

    @ResultMap("roleResultMap")
    @Select("SELECT * FROM role")
    List<Role> selectAll();

    @Insert("INSERT INTO role(id, role_name, enabled, create_by, create_time) VALUES (#{id}, #{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType = TIMESTAMP})")
    int insert(Role role);

    /**
     * 使用 JDBC 方式返回主键自增的值
     */
    @Insert("INSERT INTO role(role_name, enabled, create_by, create_time) VALUES (#{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType = TIMESTAMP})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insertByUseGeneratedKeys(Role role);

    /**
     * 使用 selectKey 返回主键的值
     */
    @Insert("INSERT INTO role(role_name, enabled, create_by, create_time) VALUES (#{roleName}, #{enabled}, #{createBy}, #{createTime, jdbcType = TIMESTAMP})")
    @SelectKey(statement = "SELECT LAST_INSERT_ID()",
            keyProperty = "id",
            resultType = Long.class,
            before = false)
    int insertBySelectKey(Role role);

    @Update("UPDATE role set role_name = #{roleName}, enabled = #{enabled}, create_by = #{createBy}, create_time = #{createTime, jdbcType = TIMESTAMP} WHERE id = #{id}")
    int updateById(Role role);

    @Delete("DELETE FROM role WHERE id = #{id}")
    int deleteById(Long id);
}
