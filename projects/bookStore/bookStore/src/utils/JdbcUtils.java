package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JdbcUtils {
    public static Connection getConnection(){
        //加载驱动器
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (Exception e){
            e.printStackTrace();
        }
        String url="jdbc:mysql://localhost:3306/bookStore2?serverTimezone=UTC";
        String username = "root";
        String password = "1230";
        Connection connection = null;
        //获取连接
        try{
            connection = DriverManager.getConnection(url, username, password);
        }catch (Exception e){
            e.printStackTrace();
        }
        return connection;
    }
}
