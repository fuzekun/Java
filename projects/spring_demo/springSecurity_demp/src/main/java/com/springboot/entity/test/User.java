package com.springboot.entity.test;

import lombok.Data;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 16:12
 * @Description:
 */

@Data
public class User {

    private int id;
    private String name;
    private String email;
    private String password;
}
