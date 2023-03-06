package com.example.demo.dao;

import com.example.demo.Dao.UserDao;
import com.example.demo.Service.UserService;
import com.example.demo.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author: Zekun Fu
 * @date: 2023/3/5 19:46
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserDaoTest {

    @Autowired
    private UserService service;

    @Test
    public void insertTest() {
        service.register("fzk", "1230");
    }
}
