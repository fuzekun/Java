package com.springboot.entity.bookStore;

import lombok.Data;

import java.util.Date;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 16:14
 * @Description:
 */
@Data
public class User {

    private int id;
    private String username;
    private String PASSWORD;
    private String gender;
    private String email;
    private String telephone;
    private String introduce;
    private String activeCode;
    private int state;
    private String role;
    private Date registTime;

}
