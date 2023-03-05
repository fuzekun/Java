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

@WebServlet(name = "PayServlet",urlPatterns = "/pay")
public class PayServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  IOException {

        //1.获取参数
        String orderId = (String)request.getParameter("orderid");
        //System.out.println("orderId:"+orderId);

        //2.找到订单
        OrderServiece orderServiece = new OrderServiece();
        Order order = orderServiece.findOrderById(orderId);
        //3.判断是否支付
        if(order.getPaystate() == 1){
            response.getWriter().write("已经支付，请勿重复!");
            return ;
        }
        //4.没有支付，修改支付状态
        orderServiece.changeOrderState(orderId);

        //5.跳转到支付成功页面
        response.sendRedirect(request.getContextPath()+"/paysuccess.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
