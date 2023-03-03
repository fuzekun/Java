package com.example.demo.Controller;

import com.example.demo.Service.ArticleService;
import com.example.demo.Service.CommentService;
import com.example.demo.aync.EventModel;
import com.example.demo.aync.EventProducer;
import com.example.demo.aync.EventType;
import com.example.demo.model.Comment;
import com.example.demo.model.HostHolder;
import com.example.demo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
@Api(description = "评论接口")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private EventProducer eventProducer;

    @ApiOperation("增加评论")
    @RequestMapping(value = "/addComment/{articleId}",method = RequestMethod.GET)
    public String addComment(@PathVariable("articleId")int articleId, @RequestParam("content")String content
                             ){
        System.out.println("进入添加评论页面");
        Comment comment = new Comment();
        User user = hostHolder.getUser();
        if(user == null){
            return "redirect:/in?next=/article/"+articleId;
        }
        else
            comment.setUserId(user.getId());
        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setArticleId(articleId);
        comment.setStatus(0);
        commentService.addComment(comment);

        int count = commentService.getCommentCount(articleId);
        articleService.updateCommentCount(articleId,count);

        eventProducer.fireEvent(new EventModel().setType(EventType.COMMENT)
                .setActorId(user.getId()).setExts("username",user.getName())
                .setExts("email","1078682405@qq.com")
                .setExts("articleId",String.valueOf(articleId)));
        return "redirect:/article/"+articleId;
    }
}
