package com.example.demo.Controller;
import com.example.demo.Service.LikeService;
import com.example.demo.model.HostHolder;
import com.example.demo.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by tuzhenyu on 17-7-25.
 * @author tuzhenyu
 */
@Controller
@Api(description = "点赞差评接口")
public class LikeController {
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private LikeService likeService;


    @ApiOperation("点赞接口")
    @RequestMapping(value = {"/mylike/{articleId}"}, method = RequestMethod.GET)
    public String like(@PathVariable("articleId")int articleId){
        User user = hostHolder.getUser();
        if(user != null)System.out.println("mylike:user!=null username = " +  user.getName());
        if (user==null){
            System.out.println("mylike:没有权限,进入login");
            return "login";
        }
        likeService.like(user.getId(),articleId);
        return "redirect:/article/"+articleId;
    }

    @ApiOperation("差评接口")
    @RequestMapping(value = {"/dislike/{articleId}"}, method = RequestMethod.GET)
    public String dislike(@PathVariable("articleId")int articleId){
        User user = hostHolder.getUser();
        if (user==null){
            return "redirect:/in?next=/article/"+articleId;
        }
        likeService.dislike(user.getId(),articleId);
        return "redirect:/article/"+articleId;
    }
}
