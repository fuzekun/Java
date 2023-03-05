package com.example.demo.Controller;

import com.example.demo.Service.*;
import com.example.demo.Utils.JblogUtil;
import com.example.demo.Utils.RedisKeyUntil;
import com.example.demo.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@EnableSwagger2
@Api(value = "ArticleController",description = "文章接口")
@Controller
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Autowired
    private TagService tagService;

    @Autowired
    private JedisService jedisService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    //文章的分页显示
    @ApiOperation(value = "文章的分页显示")
    @RequestMapping(value = {"/page/{pageId}"}, method = RequestMethod.GET)
    public String article(Model model, @PathVariable("pageId")int pageId){
        List<ViewObject>vos = new ArrayList<>();
        List<Article>articles = articleService.getLatestArticles((pageId - 1) * 4,4);
        //传递四个article的点击量和标签
        for(Article article :articles){
            ViewObject vo = new ViewObject();
            List<Tag>tags = tagService.getByArticleId(article.getId());
            String aclickCount = jedisService.get(RedisKeyUntil.getClickCountKey(+article.getId() + ""));
            if(aclickCount == null){
                aclickCount = "0";
            }
            vo.set("aclickCount",aclickCount);
            vo.set("article",article);
            vos.add(vo);
        }
        //通过ViewObject将文章传递到前端
        model.addAttribute("vos",vos);


        //初始化文章
        ViewObject pagination = new ViewObject();
        int count = articleService.getArticleCount();
        pagination.set("current",pageId);
        pagination.set("nextPage",pageId+1);
        pagination.set("prePage",pageId-1);
        //因为是四篇文章在页面上显示，所以总共会有多少页
        pagination.set("lastPage",count % 4 == 0 ? count / 4 : count/4+1);

        User user = hostHolder.getUser();
        if(user == null || "admin".equals(user.getRole())){///
            model.addAttribute("create",1);
        }else{
            model.addAttribute("create",0);
        }
        model.addAttribute("pagination",pagination);
        //传递文章的标签
        List<Tag>tags = tagService.getAllTags();
        model.addAttribute("tags",tags);
        jedisService.put(RedisKeyUntil.getCategoryKey("Web"),"0");

        ViewObject categoryCount = new ViewObject();
        for(String category:JblogUtil.categorys){
            String categoryKey = RedisKeyUntil.getCategoryKey(category);
            String num = jedisService.get(categoryKey);

            if(num != null){
                categoryCount.set(JblogUtil.categoryMap.get(category),num);
            }
            else {
                categoryCount.set(JblogUtil.categoryMap.get(category), 0);
            }
        }
        model.addAttribute("categeryCount",categoryCount);

        ViewObject clickCount = new ViewObject();
        String currentPage = jedisService.get(RedisKeyUntil.getClickCountKey("/page/"+pageId));
        String sumPage = jedisService.get(RedisKeyUntil.getClickCountKey("SUM"));
        clickCount.set("currentPage",currentPage);
        clickCount.set("sumPage",sumPage);
        model.addAttribute("clickCount",clickCount);

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

    @RequestMapping(value = "/articleAdd",method = RequestMethod.POST)
    public String addArticle(@RequestParam("title")String title,@RequestParam("category")String category,
                             @RequestParam("tag")String tag,@RequestParam("describe")String describe,
                             @RequestParam("content")String content) {
        int authorid = 3;                          //默认fuzekun写的
        try{
            authorid = hostHolder.getUser().getId();
        }catch (NullPointerException e){
            System.out.println("用户不存在,权限不够");
        }

        //文章不需要转化成html格式，这样在进行修改的时候更容易。只在前台显示的时候进行一次修改就行了
        Article article = new Article(title, describe, content,  new Date(), 0, category,authorid);
        articleService.addArticle(article);//插入成功会返回本文章的ID
        int articleId = article.getId();

        String[] tags = tag.split(",");

        for (String t : tags) {
            Tag tag1 = tagService.selectByName(t);
            if (tag1 == null) {//新标签
                Tag tag2 = new Tag();
                tag2.setName(t);
                tag2.setCount(1);
                tagService.addTag(tag2);//插入成功会返回本标签的id
                int tagId = tag2.getId();

                //插入两个库
                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(tagId);
                articleTag.setArticleId(article.getId());
                tagService.addArticleTag(articleTag);
            } else {//旧标签
                //一个更新，一个插入
                tagService.updateCount(tag1.getId(), tag1.getCount() + 1);

                ArticleTag articleTag = new ArticleTag();
                articleTag.setArticleId(articleId);
                tagService.addArticleTag(articleTag);
            }
        }
        String categoryKey = RedisKeyUntil.getCategoryKey(category);
        jedisService.incr(categoryKey);
        return "redirect:/";
    }

    @ApiOperation("文章显示")
    @RequestMapping(value = "/article/{articleId}", method = RequestMethod.GET)//绑定文章的ID，用于被人的容易跳转的实现
    public String singleArticle(Model model,@PathVariable("articleId")int articleId){
        //返回文章信息
       Article article = articleService.getArticleById(articleId);
        List<Tag>tags = tagService.getByArticleId(article.getId());
        model.addAttribute("article",article);
        model.addAttribute("tags",tags);

        //放回当前的点击量和总的点击量
        ViewObject clickCount = new ViewObject();
        String currentPage = jedisService.get(RedisKeyUntil.getClickCountKey("/article")+articleId);
        String sumPage = jedisService.get(RedisKeyUntil.getClickCountKey("SUM"));
        clickCount.set("current",currentPage);
        clickCount.set("sumPage",sumPage);
        model.addAttribute("clickCount",clickCount);

        //返回好评差评数目
        model.addAttribute("likeCount",likeService.getLikeCount(articleId));
        model.addAttribute("dislikeCount",likeService.getDisLikeCount(articleId));

        //返回评论
        List<Comment>comments = commentService.getCommentByArticleId(articleId);
        List<ViewObject>vos = new ArrayList<>();
        for(Comment comment:comments){
            ViewObject vo = new ViewObject();
            vo.set("comment",comment);
            vo.set("username",userService.getUser(comment.getUserId()).getName());
            vos.add(vo);
        }
        model.addAttribute("vos",vos);
        model.addAttribute("commetCount",comments.size());
        model.addAttribute("articleId",articleId);

        //返给本文浏览量
        String uriKey = RedisKeyUntil.getClickCountKey(article.getId()+"");
        System.out.println(uriKey);
        String articleClickCount = jedisService.get(uriKey);
        model.addAttribute("articleClickCount",articleClickCount);

        return "article";

    }

    @ApiOperation(value = "点赞的数量统计")
    @RequestMapping(value = "/like/{articleId}",method = RequestMethod.GET)
    public void getLike(Model model ,@PathVariable(value = "aticleId")int articleId){
        User user = hostHolder.getUser();
        likeService.like(user.getId(),articleId);
    }

    @ApiOperation("删除文章")
    @RequestMapping(value = "/articleDel/{articleId}", method = RequestMethod.GET)
    public String del(@PathVariable(value = "articleId")int articleId, Model model){
        ViewObject vo = new ViewObject();
        String msg = "";
        Article article = articleService.getArticleById(articleId);
        if(hostHolder.getUser() == null || hostHolder.getUser().getId() != article.getAuthorid()) {
            msg = "删除失败,权限不够";
        }else {
            //从数据库中级联删除文章,包括删除标签表和标签文章表以及评论表
            articleService.delArticle(articleId);
            //从redis中删除热门文章，文章的类型的数目减少一
            jedisService.zrem("hotArticles",RedisKeyUntil.getClickCountKey(articleId + ""));
            jedisService.put(RedisKeyUntil.getCategoryKey(article.getCategory()),
                    (Integer.parseInt(jedisService.get(RedisKeyUntil.getCategoryKey(article.getCategory()))) - 1) + "");
            msg = "删除成功";
        }
        vo.set("msg",msg);
        model.addAttribute("vo",vo);
        return "result";
    }

    @ApiOperation("跳转到更新文章界面")
    @RequestMapping(value = "/modifyArticlePre/{articleId}", method = RequestMethod.GET)
    public String modifyArticlPre(Model model, @PathVariable(value = "articleId")int articleId){

        //首先进行权限验证
        ViewObject vo = new ViewObject();
        String msg = "";
        Article article = articleService.getArticleById(articleId);
        if(hostHolder.getUser() == null || hostHolder.getUser().getId() != article.getAuthorid()){//没有权限
            msg = "修改失败,权限不够";
            vo.set("msg", msg);
            model.addAttribute("vo", vo);
            return "result";
        }
        List<Tag> tags = tagService.getByArticleId(articleId);
        if(article == null){
           msg = "没找到文章，或者持久层出错";
            vo.set("msg", msg);
            model.addAttribute("vo", vo);
           model.addAttribute("msg",msg);
           return "result";
        }
        model.addAttribute("article", article);
        model.addAttribute("articleId", articleId);
        Tag tag = new Tag();
        for( Tag tagt : tags ){
            System.out.println(tagt.getName());
            tag = tagt;
        }
        if(tag.getName() != null && tag.getName() != ""){}
        else {
            tag.setName("没有标签");
        }
        model.addAttribute("tag", tag);
        return "modifyArticle";
    }

    @ApiOperation(value = "更改文章")
    @RequestMapping(value = "/modifyArticle", method = RequestMethod.POST)
    public String modifyArticle(Model model, @RequestParam("title")String title,
                                @RequestParam("articleId")String articleId,
                                @RequestParam("category")String category,
                                @RequestParam("tag")String tag,@RequestParam("describe")String describe,
                                @RequestParam("content")String content){

        ViewObject vo = new ViewObject();
        String msg = "";
        Article article = articleService.getArticleById(Integer.parseInt(articleId));
        if(hostHolder.getUser() == null || hostHolder.getUser().getId() != article.getAuthorid()){//没有权限

            msg = "修改失败,权限不够";
        }else{//执行修改的操作
            //修改jedis中的文章类型
            if(category != article.getCategory()) {
                //原来的文章类型减少一
                jedisService.decr(RedisKeyUntil.getCategoryKey(article.getCategory()));
                //现在的文章类型增加一
                jedisService.incr(RedisKeyUntil.getCategoryKey(category));
            }

            //修改文章
            article.setTitle(title);
            article.setContent(content);
            article.setCategory(category);
            article.setDescribes(describe);
            articleService.updateArtcle(Integer.parseInt(articleId), article);
            msg = "修改成功";
        }
        vo.set("msg",msg);
        model.addAttribute("vo",vo);
        return "result";
    }


    @ApiOperation(value = "根据分类查找文章")
    @RequestMapping(value = "/getKindArticle/{category}", method = RequestMethod.GET)
    public String getArticleByCategory(Model model, @PathVariable(name = "category") String category,
                                       @RequestParam(name = "pageId")int pageId){

        //传递文章类型
        model.addAttribute("category",category);

        List<ViewObject>vos = new ArrayList<>();
        List<Article>articles = articleService.findByCategory(category, (pageId - 1) * 4, 4);
        //传递文章和点击量
        for(Article article : articles){
            ViewObject vo = new ViewObject();
            String aclickCount = jedisService.get(RedisKeyUntil.getClickCountKey(+article.getId() + ""));
            if(aclickCount == null){
                aclickCount = "0";
            }
            vo.set("aclickCount",aclickCount);
            vo.set("article",article);
            vos.add(vo);
        }
        model.addAttribute("vos",vos);


        //初始化下标
        ViewObject pagination = new ViewObject();
        int count = articleService.getArticleCountByCategory(category);
        pagination.set("current",pageId);
        pagination.set("nextPage",pageId+1);
        pagination.set("prePage",pageId-1);
        //因为是四篇文章在页面上显示，所以总共会有多少页
        pagination.set("lastPage",count % 4 == 0 ? count / 4 : count/4+1);
        User user = hostHolder.getUser();
        if(user == null || "admin".equals(user.getRole())){///
            model.addAttribute("create",1);
        }else{
            model.addAttribute("create",0);
        }
        model.addAttribute("pagination",pagination);
        //传递文章的标签
        List<Tag>tags = tagService.getAllTags();
        model.addAttribute("tags",tags);


        //传递文章类型以及数量
        ViewObject categoryCount = new ViewObject();
        for(String category2:JblogUtil.categorys){
            String categoryKey = RedisKeyUntil.getCategoryKey(category2);
            String num = jedisService.get(categoryKey);
            if(num != null){
                categoryCount.set(JblogUtil.categoryMap.get(category2),num);
            }
            else
                categoryCount.set(JblogUtil.categoryMap.get(category),0);
            model.addAttribute("categeryCount",categoryCount);
        }

        //传递点击量
        ViewObject clickCount = new ViewObject();
        String currentPage = jedisService.get(RedisKeyUntil.getClickCountKey("/getKindArticle/"+category));
        String sumPage = jedisService.get(RedisKeyUntil.getClickCountKey("SUM"));
        clickCount.set("currentPage",currentPage);
        clickCount.set("sumPage",sumPage);
        model.addAttribute("clickCount",clickCount);

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
        return "showArticleByCategory";

    }
}
