package service;

import dao.Imp.UserDao;
import exception.MySqlException;
import exception.UserException;
import model.User;
import utils.SendJMail;

import java.sql.SQLException;

public class UserService {
    UserDao userDao = new UserDao();
    public void register(User user) throws UserException{
        try {
            userDao.addUser(user);

            //注册成功提醒注册
            /*
            * 项目发布后，改成域名
            * */
            String link = "http:localhost:8080/bookStore/active?activeCode=" + user.getActiveCode();
            String html  = "<a href = \"" + link + "\">欢迎注册网上书城账号，点此激活</a>";
            System.out.println(html);
            // 但是如果一旦这个线程失败了，用户就不能使用这个邮箱再进行注册了。
            //可以开启一个新的线程进行邮件的发送，或者使用消息队列进行邮件的发送
            new Thread(()->{
                SendJMail.sendMail(user.getEmail(), html);
            }).start();
        }catch (Exception e){
            e.printStackTrace();
            throw new UserException("sql执行出错,用户注册失败");
        }
    }
    public void activeUser(String activeCode)throws UserException{
        User user = null;

        try{
            //1.查询激活码的用户是否存在
            user = userDao.findUserByActiveCode(activeCode);
            if(user == null){
                throw new UserException("激活失败，用户不存在...");
            }else if(user.getState() == 1){
                throw new UserException("激活失败，用户已激活...");
            }
            //2.激活用户
            userDao.updateStat(activeCode);
        }catch (SQLException e){
            e.printStackTrace();
            throw new UserException("激活失败");
        }
        //第一种办法是接收之后抛出
        //第二种解决办法就是只接收sql异常
    }
    public User login(String username,String password)throws UserException{
        User user = null;
        try {
            //1.查询
            user = userDao.findUserByUsernameAndPassword(username, password);
            //2.判断
            if(user == null){
                throw new UserException("用户名或密码错误");
            }else if(user.getState() == 0){
                throw new UserException("用户未激活，请先激活");
            }
            return user;
        }catch (SQLException e){
            e.printStackTrace();
            throw new UserException("登陆失败");
        }
    }
    public User findById(String userid)throws MySqlException{
        User user = null;
        try {
            user = userDao.findUserById(userid);
            return user;
        }catch (SQLException e){
            throw new MySqlException("系统繁忙，请稍后重试");
        }
    }
    public void updateUser(User user)throws MySqlException{
        try{
            userDao.updateUser(user);
        }catch (SQLException e){
            throw new MySqlException("系统繁忙，请稍后重试");
        }
    }
}
