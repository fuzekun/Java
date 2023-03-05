package com.example.demo.Dao;

import com.example.demo.model.Article;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface AdminDao {


    //user
    @Select({"select * from user"})
    public List<User> findAllUser();

    //article
    @Select({"select * from article"})
    public List<Article>findAllArticle();

    //comment
    @Select({"select * from comment"})
    public List<Comment>findAllComment();
}
