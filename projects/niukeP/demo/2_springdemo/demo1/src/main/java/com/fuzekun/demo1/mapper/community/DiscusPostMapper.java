package com.fuzekun.demo1.mapper.community;

import com.fuzekun.demo1.entity.community.DiscussPost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: Zekun Fu
 * @date: 2022/9/18 19:46
 * @Description:
 */
@Mapper
public interface DiscusPostMapper {

    public List<DiscussPost> selectDiscusPosts(long userId, int offset, int limit) ;

    public int selectDiscusPostRows(@Param("userId") long userid);

    public DiscussPost findById(int id);

    public List<DiscussPost> selectAll();

    public DiscussPost selectById(@Param("id") int id);
}
