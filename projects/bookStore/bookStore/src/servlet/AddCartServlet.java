package servlet;

import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "AddCartServlet",urlPatterns = "/addCart")
public class AddCartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.获取参数
        String id = (String)request.getParameter("id");

        //2.调用service
        ProductService productService = new ProductService();
        Product product = productService.findBook(id);



        //3.放入购物测Map
        Map<Product,Integer>cart = (Map<Product, Integer>) request.getSession().getAttribute("cart");
        //3.1如果没有数据就创建
        if(cart == null){
            cart = new HashMap<Product,Integer>();
            cart.put(product,1);
        }else{
            //3.2判断map中是否有当前想购的商品
            /**
             * 默认情况key是根据地址判断的，重写equal方法
             */
            if(cart.containsKey(product)){

                //先判断是否超过了数目，如果超过库存就不让操作
                if(cart.get(product) + 1 >= product.getPnum()){
                    //6.1返回主页
                    String a1 = "&nbsp &nbsp <a href = \""+ request.getContextPath()+"\">返回主页</a>";

                    //6.2查看购物车
                    String a2 = "&nbsp &nbsp <a href = \"" + request.getContextPath() + "/cart.jsp\">查看购物车</a>";
                    response.getWriter().write("对不起，书已销完" + a1 + a2);
                    return;
                }

              cart.put(product,cart.get(product)+1);
            }else{
                cart.put(product,1);
            }
        }
//        //4.打印购物车的数据
//        for(Map.Entry<Product,Integer>entry : cart.entrySet()){
//            System.out.println(entry.getKey() + "的数量是:" + entry.getValue());
//        }
        //5.存入session
        request.getSession().setAttribute("cart",cart);

        //6.响应客户端
        //6.1返回主页
        String a1 = "&nbsp &nbsp <a href = \""+ request.getContextPath()+"\">返回主页</a>";

        //6.2查看购物车
        String a2 = "&nbsp &nbsp <a href = \"" + request.getContextPath() + "/cart.jsp\">查看购物车</a>";
//        System.out.println(a2);
        response.getWriter().write("购买成功"+a1 + a2);
        //request.getRequestDispatcher("cart.jsp").forward(request,response);
    }
}
