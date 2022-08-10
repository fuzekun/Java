package DataBase;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import com.mysql.cj.jdbc.*;

public class Connect {

    public static void main(String[] args) throws Exception{
//        Class.forName("com.mysql.cj.jdbc.Driver"); // 注册一个驱动，创建一个mysql的驱动
        String driverName = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/yshop2";
        String pw = "1230";
        String uname = "root";

        Driver d = (Driver) Class.forName(driverName).newInstance(); // 创建一个Driver
        DriverManager.registerDriver(d);    // 还是手动创建一个Driver比较舒服
        Connection cnct = DriverManager.getConnection(url, uname, pw);


    }
}
