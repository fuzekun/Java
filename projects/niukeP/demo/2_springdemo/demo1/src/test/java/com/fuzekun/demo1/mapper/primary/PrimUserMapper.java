package com.fuzekun.demo1.mapper.primary;

import com.fuzekun.demo1.Demo1Application;
import com.fuzekun.demo1.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 14:01
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@ContextConfiguration(classes = Demo1Application.class)
public class PrimUserMapper {

    @Autowired
    private UserMapper userMapper;
    @Test
    public void testGetUser() {
        if (userMapper == null) {
            System.out.println("自动装配失败!!");
            return;
        }
        User user = userMapper.findById(1);
        System.out.println(user);
    }
}
