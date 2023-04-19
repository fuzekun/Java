package com.springboot.dto.test;

import com.springboot.entity.test.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2023/4/10 17:16
 * @Description:
 */

@Mapper
public interface UserMapper {

    public List<User> findAllUsers();
}
