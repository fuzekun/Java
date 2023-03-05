package com.example.demo.Service;

import com.example.demo.Dao.ArticleTagDao;
import com.example.demo.Dao.TagDao;
import com.example.demo.model.ArticleTag;
import com.example.demo.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    TagDao tagDao;

    @Autowired
    ArticleTagDao articleTagDao;

    public Tag selectByName(String name){
        return tagDao.selectByName(name);
    }

    public List<Tag>getAllTags(){
        return tagDao.selectAll();
    }

    public List<Tag>getByArticleId(int articleId){
        return tagDao.selectByArticleId(articleId);
    }

    public int addTag(Tag tag){
        return tagDao.insertTag(tag);//返回的如果不是tag的id就会出错
    }

    public int addArticleTag(ArticleTag articleTag){
        return articleTagDao.insertArticleTag(articleTag);
    }

    public void updateCount(int getId,int count){
        tagDao.updateCount(getId,
                count);
    }
}


