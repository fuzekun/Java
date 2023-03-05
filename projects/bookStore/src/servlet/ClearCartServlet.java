package servlet;

import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ClearCartServlet",urlPatterns = "/clearCart")
public class ClearCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.判断是否为本人操作
        ProductService productService = new ProductService();
        if(request.getSession().getAttribute("user") == null){
            response.getWriter().write("请先登录");
        }

        //2.删除商品
        Map<Product,Integer>cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
        String prId = (String)request.getParameter("prId");
        for(Map.Entry<Product,Integer> entry:cart.entrySet()){
            if(entry.getKey().getId().equals(prId)){
                cart.remove(productService.findBook(prId));
                request.getSession().setAttribute("cart",cart);
            }
        }
        //3.跳转到本页面
        response.sendRedirect(request.getContextPath()+"/cart.jsp");

    }
}
