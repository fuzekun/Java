package servlet;

import model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SettleAccountServlet",urlPatterns = "/settleAccount")
public class SettleAccountServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.是否登陆
        User user = (User)request.getSession().getAttribute("user");
        if(user != null){
            //1.1登陆结账
            request.getRequestDispatcher("/order.jsp").forward(request,response);
        }else{
            response.sendRedirect("login.jsp");
        }
    }
}
