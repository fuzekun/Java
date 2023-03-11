package dao.Imp;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mysql.cj.jdbc.JdbcConnection;
import dao.UserDao32;
import model.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.tomcat.jdbc.pool.DataSource;
import utils.C3P0Utils;
import utils.JdbcUtils;

import java.sql.*;
import java.util.Date;

public class UserDao implements UserDao32 {

    public void addUser(User user) throws SQLException {

        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());


        //sql语句
        String sql = "INSERT INTO user " +
                "(username,PASSWORD,gender,email,telephone,introduce,activeCode,state,role,registTime) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        //执行
        qr.update(sql,user.getUsername(),
                user.getPassword(),
                user.getGender(),
                user.getEmail(),
                user.getTelephone(),
                user.getIntroduce(),
                user.getActiveCode(),
                user.getState(),
                user.getRole(),
                user.getRegistTime());
    }
    @Override
    public User findUserByActiveCode(String activecode)throws SQLException{
        //1.QuaryRunner
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        //2.sql
        String sql = "select * from user where activeCode = ?";

        //3.访问
        return qr.query(sql,new BeanHandler<User>(User.class),activecode);
    }

    @Override
    public void updateStat(String activecode)throws SQLException{
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        //2.sql
        String sql = "update user set state = 1 where activecode = ?";

        //3.更改
        qr.update(sql,activecode);
    }

    @Override
    public User findUserByUsernameAndPassword(String username,String password) throws SQLException{
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select * from user where username = ? and PASSWORD = ?";

        return qr.query(sql,new BeanHandler<User>(User.class),username,password);
    }
    @Override
    public User findUserById(String id)throws SQLException{
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select * from user where id = ?";

        return qr.query(sql,new BeanHandler<User>(User.class),id);
    }

    @Override
    public void updateUser(User user)throws SQLException{
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        System.out.println(user);

        String sql = "update user set PASSWORD = ?, telephone = ?, gender = ? where id = ?";

        queryRunner.update(sql,user.getPassword(),user.getTelephone(),user.getGender(),user.getId());
    }
}
