package filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class AutoLoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest  request = (HttpServletRequest)req;

        //当前已经登陆
        if(request.getSession().getAttribute("user") != null){
            chain.doFilter(request,resp);
        }
//        else {
//            for(Cookie cook: request.getCookies()){
//                //cookie没过时
//                if(cook.getName().equals("user")){
//                    chain.doFilter(req,resp);
//                }else {
//                    //重新登陆
//                    request.getRequestDispatcher("/login.jsp").forward(request,resp);
//                }
//            }
//        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
