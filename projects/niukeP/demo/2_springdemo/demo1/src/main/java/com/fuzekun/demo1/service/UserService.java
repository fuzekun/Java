package com.fuzekun.demo1.service;

import com.fuzekun.demo1.entity.community.User;
import com.fuzekun.demo1.mapper.community.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 21:52
 * @Description:
 */

@Service
public class UserService {

    @Autowired
    UserMapper2 userMapper2;

    public User findById(long id) {
        return userMapper2.findById(id);
    }

    public Map<String, Object> register(User user) {
        return null;
    }

    public int activation(int userid, String code) {
        return 1;
    }
}
