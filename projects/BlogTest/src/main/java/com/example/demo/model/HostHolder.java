package com.example.demo.model;

import lombok.Data;
import org.springframework.stereotype.Component;


/*
*
* 用来记录多线程中用户的信息，进行用户的存储，从线程池中获取用户
*
* */

@Component
@Data
public class HostHolder {
    private static ThreadLocal<User> users = new ThreadLocal<>();

    public User getUser(){
        return users.get();
    }

    public void setUser(User user){
        users.set(user);
    }

    public void clear(){
        users.remove();
    }
}
