package com.fuzekun.demo1.mapper.second;

import com.fuzekun.demo1.Demo1Application;
import com.fuzekun.demo1.entity.Book;
import com.fuzekun.demo1.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Zekun Fu
 * @date: 2022/9/16 15:58
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class SecondUserMapper {

    @Autowired
    private BookMapper bookMapper;
    @Test
    public void testGetUser() {
        if (bookMapper == null) {
            System.out.println("自动装配失败!!");
            return;
        }
        Book book = bookMapper.findById(1);
        System.out.println(book);
    }
}
