package com.example.demo.Controller;


import com.example.demo.Dao.UserDao;
import com.example.demo.Repositry.LoginTicketRepository;
import com.example.demo.Service.ArticleService;
import com.example.demo.Service.JedisService;
import com.example.demo.Service.TagService;
import com.example.demo.Service.UserService;
import com.example.demo.Utils.JblogUtil;
import com.example.demo.Utils.RedisKeyUntil;
import com.example.demo.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ViewResolver;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.View;
import java.util.*;

@Api(value = "IndexController",description = "主页面接口")
@Controller
@EnableSwagger2
public class IndexCtroller {
    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private TagService tagService;

    @Autowired
    private ArticleService articleService;


    //直接越级用了
    @Autowired
    private LoginTicketRepository loginTicketRepository;
    @Autowired
    private UserDao userDao;

    /*
    *
    * 主页显示的主要有前四页文章的id,titile,tag，
    * 所有的标签，文章的所有分类
    * 文章的分页
    * 文章是不是可以创造
    *
    * */

    @ApiOperation(value = "注销")
    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().removeAttribute("user");
        hostHolder.clear();
        //if(hostHolder.getUser() == null) System.out.println("注销:hostHolder:删除成功");
        Cookie cookie = new Cookie("ticket",null);
        cookie.setPath("/");
        cookie.setMaxAge(0);//删除cookie
        response.addCookie(cookie);
        return "redirect:/index";
    }
    @ApiOperation(value = "用户密码修改")
    @RequestMapping(value = {"modifyUser"}, method = RequestMethod.POST)
    public String modifyUser(@RequestParam("username")String username,
                             @RequestParam("password")String password,
                            Map map){
        ViewObject viewObject = new ViewObject();
        //获取通过md5加密后的密码
        User user = userDao.seletByName(username);
        if(user == null) {                                  //更新失败，跳转到result
            viewObject.set("msg", "更新失败, 用户不存在!!!");
            map.put("vo", viewObject);
        }else{                                              //跟新成功，跳转到index,注销
           // System.out.println("老密码:" + user.getPassword());
            user.setPassword(JblogUtil.MD5(password+user.getSalt()));
            //更新
            //System.out.println("新密码:" + user.getPassword());
            userDao.update(username, user.getPassword());
            return "redirect:/logout";
        }
        return "result";
    }

    @ApiOperation(value = "主页")
    @RequestMapping(value = {"","/index"},method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request){

        //传递文章信息，文章的标签，点击量，文章本身
        List<ViewObject>vos = new ArrayList<>();
        List<Article>articles = articleService.getLatestArticles(0,4);
        for(Article article :articles){
            ViewObject viewObject = new ViewObject();
            List<Tag>tags = tagService.getByArticleId(article.getId());
            String aclickCount = jedisService.get(RedisKeyUntil.getClickCountKey(article.getId() + ""));
            if(aclickCount == null){
                aclickCount = "0";
            }
            viewObject.set("aclickCount",aclickCount);
            viewObject.set("article",article);
            viewObject.set("tags",tags);
            vos.add(viewObject);
        }
        model.addAttribute("vos",vos);//传到前端的参数之前四篇文章

        //传递文章的所有标签
        List<Tag>tags = tagService.getAllTags();
        model.addAttribute("tags",tags);

        //传递是不是有创建文章的权限，以及当前文章的页数
        ViewObject pagination = new ViewObject();
        int count = articleService.getArticleCount();
        User user = hostHolder.getUser();
        if(user != null){ //如果是管理员
            request.getSession().setAttribute("user", user.getName());
        }
        else{
            request.getSession().setAttribute("user", "游客");
        }
        pagination.set("current",1);
        pagination.set("nextPage",2);
        pagination.set("lastPage",count % 4 == 0 ? count / 4 : count/4+1);
        model.addAttribute("pagination",pagination);

        //传递全部文章的类别文章的数量
        ViewObject categoryCount = new ViewObject();
        for(String category: JblogUtil.categorys){
            String categoryKey = RedisKeyUntil.getCategoryKey(category);
            String num = jedisService.get(categoryKey);
            if(num != null){
                categoryCount.set(JblogUtil.categoryMap.get(category),num);
            }
            else categoryCount.set(JblogUtil.categoryMap.get(categoryKey),0);
        }
        model.addAttribute("categeryCount",categoryCount);

        //向前台传递点击量，主要有两个，一个是全部的点击量，另一个是本页的点击量
        ViewObject clikCount = new ViewObject();
        String count1 = jedisService.get(RedisKeyUntil.getClickCountKey("/"));
        String count2 = jedisService.get(RedisKeyUntil.getClickCountKey("/index"));
        String count3 = jedisService.get(RedisKeyUntil.getClickCountKey("page/1"));
        String currentPage = String.valueOf(Integer.parseInt(count1 == null?"0":count1)
                +Integer.parseInt(count2==null?"0":count2)+Integer.parseInt(count3 == null?"0":count3));
        clikCount.set("currentPage",currentPage);
        String sumPage = jedisService.get(RedisKeyUntil.getClickCountKey("SUM"));
        clikCount.set("sumPage",sumPage);
        System.out.println(sumPage);
        model.addAttribute("clickCount",clikCount);

        //传递排名前四的文章
        List<Article>hotArticles = new ArrayList<>();
        Set<String>set = jedisService.zrevrange("hotArticles",0,3);
        for(String str:set){
            if(Character.isDigit(str.split(":")[1].charAt(0))){
                int articleId = Integer.parseInt(str.split(":")[1]);
                Article article = articleService.getArticleById(articleId);
                hotArticles.add(article);
            }
        }
        model.addAttribute("hotAticles",hotArticles);
        return "index";
    }

    @ApiOperation(value = "管理员(用户)的注册")
    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public String register(Model model, HttpServletResponse httpResponse,
                           @RequestParam String username, @RequestParam String password
            ,@RequestParam(value = "next",required = false)String next){
        Map<String,String> map = userService.register(username,password);
        if (map.containsKey("ticket")){//注册成功
            //加入cookie
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            if (next != null && next != "") {
                //注册成功并且有访问其他页面的请求
                System.out.println("有访问其他页面的请求");
                System.out.println("next:"+next);
                return "redirect:/" + next;
            }
            else {
                //注册成功并且没有访问其他页面
                System.out.println("返回主页");
                return "redirect:/index";
            }
        }else {//注册失败，转到登陆页面
            model.addAttribute("msg",map.get("msg"));
            return "login";
        }
    }
    @ApiOperation("用户(管理员)登陆")
    @RequestMapping(value = {"/login"},method = RequestMethod.POST)
    public String login(Model model, HttpServletResponse httpResponse,
                        @RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(value = "next",required = false)String next){
        Map<String,String> map = userService.login(username,password);
        if (map.containsKey("ticket")) {//自动登陆
            Cookie cookie = new Cookie("ticket",map.get("ticket"));
            cookie.setPath("/");
            httpResponse.addCookie(cookie);

            //如果是管理员
            if(username.equals("admin")){
                return "redirect:admins/index";
            }
            //有跳转到其他页面的请求
            if (next!=null && next != "") {
                return "redirect:" + next;
            }
            else {
                //System.out.println("直接跳转到主页");
                return "redirect:/index";//跳转到主页,redirect:/应该是在配置文件中加入默认的访问页面为index.html
           }
        }else {//失效，重新登陆
            System.out.println("没有ticket");
            System.out.println(map.get("msg"));
            model.addAttribute("msg", map.get("msg"));
            return "login";
        }
    }

    @ApiOperation("用来进入博客主页面")//如果不是管理员或者用户没有登陆就会返回主页面，没法评论，无法进行创建文章
    @RequestMapping(value = {"/in"},method = RequestMethod.GET)
    public String in(Model model, @RequestParam(value = "next",required = false)String next){
        model.addAttribute("next",next);
        System.out.println("应该跳转的next:"+next);
        return "redirect:/login";
    }

    //管理员可以创建文章，就是用户本身！！！
    @ApiOperation(value = "创建文章")
    @RequestMapping(value = {"/create"},method = RequestMethod.GET)
    public String create(Model model){
            return "create";//由于使用了loginRequestInteception所以没必要在这写逻辑了
    }

    @ApiOperation(value = "返回结果")
    @RequestMapping(value="/params", method = RequestMethod.GET)
    /* 后台用Map方法向前端传递参数
     * 传递的参数为message1: String, message2: String, user: User对象
     */
    public String passParam(Map <String, Object> map) {

        map.put("message1", "Hello, Spring Boot!");

        map.put("message2", "Hello, Spring Boot!");

        User user = new User();

        user.setName("fa");

        user.setPassword("da");

        map.put("user", user);

        return "result";
    }
    @ApiOperation(value = "管理员界面")
    @RequestMapping(value = "/adin", method = RequestMethod.GET)
    public String admin(){
        return "admin";
    }

}
