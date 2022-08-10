package Bridge;

import java.sql.Connection;
import java.sql.DriverManager;

public class TestJDBC {


    public static void main(String[] args) throws Exception {

        /*
        *
        *   需要注意的点
        * 1. url已经改变了，需要添加时去，才能访访问
        * 2. 驱动器也由原本的版本，升级到了cj版本
        * 3. 需要引入这个类才可以进行访问
        * */
        String DB_URL = "jdbc:mysql://localhost:3306/test?serverTimezone=Asia/Shanghai";
        String user = "root";
        String pass = "1230";
        Class.forName("com.mysql.cj.jdbc.Driver"); // mysql数据库的jdbc的驱动
        Connection conn=DriverManager.getConnection(DB_URL,user,pass);//建立连接

    }
}
