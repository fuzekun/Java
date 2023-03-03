package com.example.demo.Interceptor;

import com.example.demo.Dao.LoginTicketDao;
import com.example.demo.Dao.UserDao;
import com.example.demo.Repositry.LoginTicketRepository;
import com.example.demo.Service.JedisService;
import com.example.demo.Utils.RedisKeyUntil;
import com.example.demo.model.HostHolder;
import com.example.demo.model.LoginTicket;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
/*
* 实现自动登陆
*
* */

@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private LoginTicketDao loginTicketDao;

    @Autowired
    private LoginTicketRepository loginTicketRepository;

    @Autowired
    private UserDao userDao;

    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private JedisService jedisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = null;
        if (request.getCookies()!=null){
            for (Cookie cookie : request.getCookies()){
                if ("ticket".equals(cookie.getName())){
                    System.out.println("tickey :" + cookie.getValue());
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if (ticket!=null){
            LoginTicket loginTicket = loginTicketRepository.getByTicket(ticket);
            if (loginTicket==null||loginTicket.getExpired().before(new Date())||loginTicket.getStatus()!=0){
                return false;
            }
            User user = userDao.seletById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }
        //点击量计数
        String uri = request.getServletPath();
        String uriKey = RedisKeyUntil.getClickCountKey(uri);
        String sumKey = RedisKeyUntil.getClickCountKey("SUM");
        jedisService.incr(uriKey);
        jedisService.incr(sumKey);

        return true;
    }
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
