package servlet;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "MyAccountServlet",urlPatterns = "/myaccount")
public class MyAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //如果登陆进入mycount.jsp
        User user  = (User) request.getSession().getAttribute("user");
        if(user != null){
            response.sendRedirect(request.getContextPath()+"/myAccount.jsp");
        }else {
            //否则进入登陆页面
            response.sendRedirect(request.getContextPath()+"/login.jsp");
        }
    }
}
