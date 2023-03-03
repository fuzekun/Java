package com.example.demo.Interceptor;

import com.example.demo.Service.JedisService;
import com.example.demo.Utils.RedisKeyUntil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * Created by tuzhenyu on 17-7-20.
 * @author tuzhenyu
 */
@Component
public class ArticleClickInterceptor implements HandlerInterceptor {
    @Autowired
    private JedisService jedisService;

    public void init(String articleClickCount,String uriKey){
        //初始化cilickCount 和 hotArticles集合
        //1.初始化集合
        jedisService.zrem("hotArticles",uriKey);
        //2.初始化clickCount
        articleClickCount = null;
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String uri = httpServletRequest.getServletPath().split("/")[2];
        String uriKey = RedisKeyUntil.getClickCountKey(uri);
        String articleClickCount = jedisService.get(uriKey);
        if(articleClickCount == null) {
            //现在是0但是已经有人点了，就是1了
            articleClickCount = "1";
            jedisService.put(RedisKeyUntil.getClickCountKey(uri),articleClickCount);
        }
        else {
            int cnt = (Integer.parseInt(articleClickCount) + 1);
            articleClickCount = cnt + "";
            jedisService.put(RedisKeyUntil.getClickCountKey(uri),articleClickCount);
        }
        jedisService.zincrby("hotArticles",uriKey);
        /*用来测试使用zincrby函数是不是可以增加成员
        * 结果是可以的
        * */
//        jedisService.zincrby("hotArticles","test");
//        Set<String> set = jedisService.zrevrange("hotArticles",0,20);
//        for(String s :set){
//            System.out.println(s);
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
