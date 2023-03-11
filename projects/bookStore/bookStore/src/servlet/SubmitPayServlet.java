package servlet;

import model.Order;
import model.OrderItem;
import service.OrderServiece;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "SubmitPayServlet",urlPatterns = "/submitPay")
public class SubmitPayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    //1.获取参数
        String orderId = request.getParameter("orderId");
        //2.查找订单信息
        OrderServiece orderServiece = new OrderServiece();
        Order order = orderServiece.findOrderById(orderId);
        //3.计算总价钱
        double totalPrice = 0;
        for(OrderItem item :order.getItems()){
            totalPrice += item.getBuynum() * item.getP().getPrice();
        }
        //4.传递信息
        request.setAttribute("totalPrice",totalPrice);
        //5.跳转
        request.getRequestDispatcher("/pay.jsp").forward(request,response);
    }
}
