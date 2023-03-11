package servlet;
/**
* 更改购物车数量
 *
* */

import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "ChangeNumServlet",urlPatterns = "/changeNum")
public class ChangeNumServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String id = request.getParameter("id");
        String num  = request.getParameter("num");

        //3.判断参数是否安全
        int number = Integer.parseInt(num);
        //3.1数量小于0
        if(number < 0){
            String a1 = "&nbsp &nbsp <a href = \""+ request.getContextPath()+"\">返回主页</a>";
            String a2 = "&nbsp &nbsp <a href = \"" + request.getContextPath() + "/cart.jsp\">查看购物车</a>";
            response.getWriter().write("数量出错，小于库存" + a1 + a2);
            return ;
        }
        //2.更新cart
        //2.1找到商品
        ProductService productService = new ProductService();
        Product product = productService.findBook(id);
        //3.2数量大于库存
        if(number > product.getPnum()){
            String a1 = "&nbsp &nbsp <a href = \""+ request.getContextPath()+"\">返回主页</a>";
            String a2 = "&nbsp &nbsp <a href = \"" + request.getContextPath() + "/cart.jsp\">查看购物车</a>";
            response.getWriter().write("参数出错，大于库存" + a1 + a2);
            return;
        }

        //2.2找到session数据
        Map<Product,Integer>cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");

        //2.3替换或移除
        if(cart.containsKey(product)){
            if("0".equals(num)){
                cart.remove(product);
            }else{
                cart.put(product,Integer.parseInt(num));
            }
        }
        //2.4存入session
        request.getSession().setAttribute("cart",cart);
        request.getRequestDispatcher("/cart.jsp").forward(request,response);
    }
}
