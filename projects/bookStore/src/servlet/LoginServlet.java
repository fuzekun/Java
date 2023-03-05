package servlet;

import exception.UserException;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import service.UserService;
import utils.C3P0Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

@WebServlet(name = "LoginServlet",urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        UserService userService= new UserService();

        //获取参数
        String username = (String)request.getParameter("username");
        String password = (String)request.getParameter("password");
        String checkbox = (String)request.getParameter("checkbox");

        //登陆
        try{
            user = userService.login(username,password);
            //注册成功

            //存入session
            request.getSession().setAttribute("user",user);
            //跳转
            //request.getRequestDispatcher("/index.jsp").forward(request,response);
//
//            防止重复提交
//            response.sendRedirect(request.getContextPath() + "/index.jsp");
            //自动登陆
            if(checkbox != null){
                Cookie cookie = new Cookie("user",user.toString());//这是永久的自动登陆吧
            }

            if("admin".equals(user.getRole())){
                response.sendRedirect(request.getContextPath()+"/admin/login/home.jsp");
            }else{
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        }catch (UserException e){
            //注册失败
            request.setAttribute("login_err",e.getMessage());
            request.getRequestDispatcher("/login.jsp").forward(request,response);
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
