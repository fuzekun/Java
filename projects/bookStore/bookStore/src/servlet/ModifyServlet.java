package servlet;

import model.User;
import org.apache.commons.beanutils.BeanUtils;
import service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ModifyServlet",urlPatterns = "/modify")
public class ModifyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserService us = new UserService();
        User user = new User();

        try{
            //获取参数
            BeanUtils.populate(user,request.getParameterMap());

            //更新成功
            us.updateUser(user);
            response.sendRedirect(request.getContextPath()+"/modifyUserInfoSuccess.jsp");//不需要传递什么数据所以..
        }catch (Exception e){
            //更新失败
            //可以回去，也可以response.getWrite.write();
            request.setAttribute("msg",e.getMessage());
            request.getRequestDispatcher("/modifyuserinfo.jsp").forward(request,response);//回去应该跳转
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
