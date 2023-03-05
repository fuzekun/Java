package servlet;

import model.Order;
import model.User;
import service.OrderServiece;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FindOrderServlet",urlPatterns = "/findOrder")
public class FindOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取用户id
        User user = (User)request.getSession().getAttribute("user");
        if(user == null){
            response.getWriter().write("非法访问");
            return ;
        }

        //2.调用service
        OrderServiece orderServiece = new OrderServiece();
        List<Order>list = orderServiece.findOrderByUserId(user.getId());

        //3.存入数据
        request.setAttribute("orders",list);

        //4.跳转
        request.getRequestDispatcher("/orderlist.jsp").forward(request,response);
    }
}
