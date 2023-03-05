package com.example.demo.Controller;


import com.example.demo.Dao.AdminDao;
import com.example.demo.Dao.ArticleDao;
import com.example.demo.Dao.CommentDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Service.AdminService;
import com.example.demo.model.Article;
import com.example.demo.model.BaseResponse;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;

    @ResponseBody
    @RequestMapping(value = "/getAllArticles", method = RequestMethod.GET)
    public BaseResponse getAllArticles(){
        Map<String, Object> maps = new ModelMap();
        BaseResponse<List<Article>>baseResponse = new BaseResponse<>();
        List<Article> data = adminService.getAllArticles();
        baseResponse.setData(data);
        if(data != null){
            baseResponse.setCode(200);
            baseResponse.setMsg("查找成功");
        }else {
            baseResponse.setCode(205);
            baseResponse.setMsg("数据为空");
        }
        return baseResponse;
    }
    @ResponseBody
    @RequestMapping(value = "/getAllComments", method = RequestMethod.GET)
    public BaseResponse getAllComments(){
        Map<String, Object> maps = new ModelMap();
        BaseResponse<List<Comment>>baseResponse = new BaseResponse<>();
        List<Comment> data = adminService.getAllComments();
        baseResponse.setData(data);
        if(data != null){
            baseResponse.setCode(200);
            baseResponse.setMsg("查找成功");
        }else {
            baseResponse.setCode(205);
            baseResponse.setMsg("数据为空");
        }
        return baseResponse;
    }

    @ResponseBody
    @RequestMapping(value = "/getAllUsers", method = RequestMethod.GET)
    public BaseResponse getAllUsers(){
        Map<String, Object> maps = new ModelMap();
        BaseResponse<List<User>>baseResponse = new BaseResponse<>();
        List<User> data = adminService.getAllUsers();
        baseResponse.setData(data);
        if(data != null){
            baseResponse.setCode(200);
            baseResponse.setMsg("查找成功");
        }else {
            baseResponse.setCode(205);
            baseResponse.setMsg("数据为空");
        }
        return baseResponse;
    }
}
