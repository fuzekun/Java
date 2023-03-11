package com.fuzekun.demo1.service;

import com.fuzekun.demo1.entity.community.DiscussPost;
import com.fuzekun.demo1.mapper.community.DiscusPostMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 22:59
 * @Description:
 */

@Service
public class DiscussPostService {
    @Autowired
    DiscusPostMapper mapper;

    public List<DiscussPost> selectDiscusPosts(int userId, int offset, int limit) {
        return mapper.selectDiscusPosts(userId, offset, limit);
    }

    public int selectDiscusPostRows(int userId) {
        return mapper.selectDiscusPostRows(userId);
    }
}
