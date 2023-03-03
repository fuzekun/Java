package com.example.demo.Service;

import com.example.demo.Dao.AdminDao;
import com.example.demo.Dao.ArticleDao;
import com.example.demo.Dao.CommentDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.model.Article;
import com.example.demo.model.BaseResponse;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.mysql.cj.conf.PropertyKey.logger;


@Service
public class AdminService {

    @Autowired
    private AdminDao adminDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private CommentDao commentDao;

    public List<Article> getAllArticles(){
        List list = null;
        try{
            list = adminDao.findAllArticle();
        }catch ( Exception e){
            //应该使用日志记录，暂且使用输出
            System.out.println("出错啦!文章服务器没法找到文章");
       }
        return list;
    }


    public List<Comment> getAllComments(){
        List list = null;
        try{
            list = adminDao.findAllComment();
        }catch ( Exception e){
            //应该使用日志记录，暂且使用输出
            System.out.println("出错啦!评论服务器没法找到文章");
        }
        return list;
    }

    public List<User>getAllUsers(){
        List list = null;
        try{
            list = adminDao.findAllUser();
        }catch ( Exception e){
            //应该使用日志记录，暂且使用输出
            System.out.println("出错啦!用户服务器没法找到文章");
        }
        return list;
    }


}
