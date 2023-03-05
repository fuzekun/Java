package servlet;

import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ProductInfoServlet" ,urlPatterns = "/productInfo")
public class ProductInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取id
         String id = request.getParameter("id");

        //调用service
        ProductService productService = new ProductService();
        Product product = productService.findBook(id);

        //存入request
        request.setAttribute("product",product);
        request.getRequestDispatcher("/product_info.jsp").forward(request,response);
    }
}
