package servlet;

import exception.MySqlException;
import model.User;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "FindByIdServlet",urlPatterns = "/findById")
public class FindByIdServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userid = request.getParameter("id");
        UserService userService = new UserService();
        try{
            //查找成功
            User user = userService.findById(userid);
            request.getSession().setAttribute("user",user);
            request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request,response);//这里需要使用requst因为user还用得着
        }catch (MySqlException e){
            request.setAttribute("find_err",e.getMessage());
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
