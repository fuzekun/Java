package servlet;

import exception.UserException;
import model.User;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import service.UserService;
import utils.C3P0Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

@WebServlet(name = "RegisterServlet",urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = new User();
        UserService userService= new UserService();

        //验证码进行验证
        //失败返回错误信息
        String checkcode_clict = request.getParameter("checkcode");
        String checkcode_session = (String)request.getSession().getAttribute("checkcode_session");
        System.out.println("checkcode_clict:"+checkcode_clict);
        System.out.println("checkcode_session:"+checkcode_session);

        if(!checkcode_clict.equals(checkcode_session)){

            request.setAttribute("checkcode_err","验证码错误");
            request.getRequestDispatcher("register.jsp").forward(request,response);
            return ;
        }


        //给用户赋值
        try
        {
            BeanUtils.populate(user ,request.getParameterMap());
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(user);
        user.setRole("user");
        user.setActiveCode(UUID.randomUUID().toString());
        System.out.println(user);

        //注册
        try{
            userService.register(user);

            //注册成功
            request.getRequestDispatcher("/registersuccess.jsp").forward(request,response);


        }catch (UserException e){

            //注册失败
            request.setAttribute("regist_err","用户名重复");
            request.getRequestDispatcher("/register.jsp").forward(request,response);
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
