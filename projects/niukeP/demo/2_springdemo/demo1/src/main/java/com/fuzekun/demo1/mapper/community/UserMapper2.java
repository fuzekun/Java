package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.entity.community.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 21:55
 * @Description:
 */
@Mapper
public interface UserMapper2 {
    public User findById(long id);
}
