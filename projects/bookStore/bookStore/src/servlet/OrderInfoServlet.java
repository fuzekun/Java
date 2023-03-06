package servlet;

import dao.Imp.OrderDao;
import model.Order;
import model.OrderItem;
import service.OrderServiece;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "OrderInfoServlet",urlPatterns = "/orderInfo")
public class OrderInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取参数
        String orderId = (String)request.getParameter("orderId");

        //2.找到订单以及其详细信息
        OrderServiece orderServiece = new OrderServiece();
        Order order = orderServiece.findOrderById(orderId);


        //3.保存到session,并且将钱转过去
        request.getSession().setAttribute("order",order);

        //4.跳转到订单详情
        request.getRequestDispatcher("/orderInfo.jsp").forward(request,response);
    }
}
