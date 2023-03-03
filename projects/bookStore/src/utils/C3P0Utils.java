package utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;


import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;

public class C3P0Utils {

	//整一个c3p0的连接池
	private static ComboPooledDataSource source  = new ComboPooledDataSource();

	//提供连接池访问接口
	public static DataSource getDataSource(){
		try {
			source.setDriverClass("com.mysql.cj.jdbc.Driver");
		}catch (Exception e) {
			e.printStackTrace();
		}
		source.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/bookStore?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false");
		source.setUser("root");
		source.setPassword("1230");
		return source;
	}

	//提供连接池的连接
	public static Connection getConnection(){
		try {
			return source.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RuntimeException("服务器错误");
		}
	}

	//关闭方法，可以不写
	public static void closeAll(Connection conn,Statement statement,ResultSet resultSet){
		
		if(resultSet != null){
			try {
				resultSet.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			resultSet = null;
		}
		
		if(statement != null){
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			statement = null;
		}
		
		if(conn != null){
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				conn = null;
			}
		}
	}
}
