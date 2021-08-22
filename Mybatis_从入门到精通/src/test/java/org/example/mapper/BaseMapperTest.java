package org.example.mapper;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.Reader;

public class BaseMapperTest {
    private static SqlSessionFactory sqlSessionFactory;

    @BeforeAll
    public static void init() {
        try {
            Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
            // 创建 SqlSessionFactory 对象的过程中，首先解析 mybatis-config.xml 配置文件，读取配置文件中的 mappers 配置后会读取
            // 全部的 Mapper.xml 进行具体方法的解析，解析完成后 SqlSessionFactory对 象就包含了所有的属性配置和执行 SQL 的信息
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SqlSession getSqlSession() {
        // openSession 方法设置为 true，事务将自动提交到数据库
        return sqlSessionFactory.openSession(false);
    }
}
