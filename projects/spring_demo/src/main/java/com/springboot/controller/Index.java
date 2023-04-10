package com.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 14:55
 * @Description:
 */

@RestController
public class Index {

    @RequestMapping("/index")
    public String toIndex() {
        return "你好";
    }

}
