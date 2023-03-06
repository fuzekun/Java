package servlet;

import model.PageResult;
import model.Product;
import service.ProductService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ShowProductByPageServlet",urlPatterns = "/showProductByPage")
public class ShowProductByPageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductService ps = new ProductService();
        //1.获取参数
        String category = (String)request.getParameter("category");
        String pageStr = (String)request.getParameter("page");
        int page = 1;
        if(pageStr != null && !pageStr.equals("")){
            page = Integer.parseInt(pageStr);
        }

        //2.调用service
        PageResult<Product>productPageResult = ps.findBooks(category,page,0); //使用默认的pageSize

        //3.存入request
        request.setAttribute("pageResult",productPageResult);
        request.setAttribute("category",category);

        //4.跳转页面
        request.getRequestDispatcher("product_list.jsp").forward(request,response);
    }
}
