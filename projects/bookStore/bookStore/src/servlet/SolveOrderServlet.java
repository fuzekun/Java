package servlet;

import model.Order;
import model.OrderItem;
import model.Product;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import service.OrderServiece;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "SolveOrderServlet",urlPatterns = "/solveOrder")
public class SolveOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.接受参数
        //1.把数据封装好
        Order order = new Order();
        try{
            //1.1获取sessoin的user
            User user = (User)request.getSession().getAttribute("user");

            if(user == null){
                response.getWriter().write("非法访问.....");
                return ;
            }

            //1.2取购物车
            Map<Product,Integer>cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
            if(cart == null || cart.size() == 0){
                response.getWriter().write("购物车没东西");
                return;
            }

            //1.3封装order
            BeanUtils.populate(order,request.getParameterMap());
            order.setId((UUID.randomUUID()).toString());//随机生成的Id会不会重复??
            order.setOrdertime(new Date());
            order.setUserId(user.getId());


            //1.4封装order_items(订单中有n个商品)
            List<OrderItem>items = new ArrayList<OrderItem>();

            int totalprice = 0;
            for(Map.Entry<Product,Integer>entry :cart.entrySet()){
                OrderItem item = new OrderItem();
                //设置购物数目
                item.setBuynum(entry.getValue());
                item.setP(entry.getKey());
                item.setOrder(order);
                totalprice += entry.getValue() * entry.getKey().getPrice();
                items.add(item);
            }
            order.setMoney(totalprice);
            order.setItems(items);
//            System.out.println("订单");
//            System.out.println(order);
            for(OrderItem item : items){
                System.out.println("商品:" + item.getP() + " " + "数目:" + item.getBuynum());
            }
            //2.调用service
            OrderServiece orderServiece = new OrderServiece();
            orderServiece.solveOrder(order);
            //3.移除session的数据
            request.getSession().removeAttribute("cart");//移除的是cart而不是order
            //4.响应
            String a1 = "&nbsp &nbsp <a href = \""+ request.getContextPath()+"\">返回主页</a>";
            response.getWriter().write("下单成功"+a1);
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
