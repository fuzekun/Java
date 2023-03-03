package utils;

import java.sql.Connection;
import java.sql.SQLException;
/**
 * 保证原子性
 * */
public class ManageThreadLocal {
    private static ThreadLocal<Connection> tl = new ThreadLocal<>();

    public static Connection getConnection() {
        Connection conn = tl.get();
        try {
            if (conn == null) {

                conn = C3P0Utils.getConnection();
                tl.set(conn);
                System.out.println("第一次从ThreadLocal获取Connection对象:" + conn);
            } else {
                System.out.println("第n次从ThreadLocal中获取Connection对象");
            }
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void startTransaction() {
        try {
            ManageThreadLocal.getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void rollbackTransaction() {
        try {
            ManageThreadLocal.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void commitTransaction(){
        try {
            ManageThreadLocal.getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            ManageThreadLocal.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}