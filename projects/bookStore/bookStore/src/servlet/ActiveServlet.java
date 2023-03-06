package servlet;

import dao.Imp.UserDao;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ActiveServlet",urlPatterns = "/active")
public class ActiveServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setHeader("content-type","text/html;charset=utf-8");
        //response.setContentType("UTF-8");
        //1.获取参数
        String activeCode = (String)request.getParameter("activeCode");

        UserService userService= new UserService();
        //2.激活
        try {
            //激活成功
            userService.activeUser(activeCode);
            response.getWriter().write("激活成功");
        }catch (Exception e){
            //激活失败
            e.printStackTrace();
            response.getWriter().write(e.getMessage());
        }

    }
}
