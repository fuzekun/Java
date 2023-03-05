package com.fuzekun.demo1.controller;

import com.fuzekun.demo1.entity.community.DiscussPost;
import com.fuzekun.demo1.entity.community.Page;
import com.fuzekun.demo1.service.DiscussPostService;
import com.fuzekun.demo1.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: Zekun Fu
 * @date: 2022/9/15 21:46
 * @Description:
 */
@Controller
public class IndexController {


    @Autowired
    DiscussPostService postService;
    @Autowired
    UserService userService;


    @RequestMapping(path = "/index", method = RequestMethod.GET)
    public String indexMap(Model model, Page page) {

        // springMVC会自动把page实例化，并且把page放入到Model中
        page.setRows(postService.selectDiscusPostRows(0));
        page.setPath("/index");

        List<DiscussPost>posts = postService.selectDiscusPosts(0, page.getOffSet(), page.getLimit());
        List<Map<String, Object>>discussPosts = new ArrayList<>();
        if (posts != null) {
            for (DiscussPost post: posts) {
                Map<String, Object> mp = new HashMap<>();
                mp.put("post", post);
                mp.put("user", userService.findById(post.getUserId()));
                discussPosts.add(mp);
            }
        }
        model.addAttribute("discussPosts", discussPosts);
        return "index";
    }



}
