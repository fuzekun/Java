package com.springboot.controller;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 15:04
 * @Description: 跳转页面的controller
 */
@Controller
public class IndexController2 {

    Logger logger = LoggerFactory.getLogger(IndexController2.class);
    @RequestMapping("/test")
    public String toTest() {
        logger.debug("进入方法");
        return "testThymeleaf";
    }

    @RequestMapping("/login")
    public String toLogin() {
        return "/login";
    }
}
