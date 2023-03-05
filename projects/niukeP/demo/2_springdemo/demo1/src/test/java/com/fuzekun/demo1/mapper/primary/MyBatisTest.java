package com.fuzekun.demo1.mapper.primary;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 10:14
 * @Description:
 */



import com.fuzekun.demo1.entity.User;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;


public class MyBatisTest {
    /** * MyBatis SqlSessionFactory * SqlSessionFactory????SqlSession????????????SqlSession??????????commit?rollback?close???? * @return */ private static SqlSessionFactory getSessionFactory() {
        SqlSessionFactory sessionFactory = null;
        String resource = "mybatisConfiguration.xml";
        try {
            // 默认的寻找路径是recources文件！！！
            sessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader(
                    resource));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sessionFactory;
    }

    public static void main(String[] args) {
        SqlSession sqlSession = getSessionFactory().openSession();
        UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
        User user = userMapper.findById(1);
        System.out.println(user.getUsername());
    }
}
