package com.fuzekun.demo1.controller;

import com.fuzekun.demo1.entity.community.User;
import com.fuzekun.demo1.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 22:13
 * @Description:
 */

@Controller
public class UserController {

    @Autowired
    UserService service;
    @RequestMapping("/getUserById")
    @ResponseBody
    public User getUserById(@PathParam("userId") int userId) {
        return service.findById(userId);
    }
}
