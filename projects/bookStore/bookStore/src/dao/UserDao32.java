package dao;



import exception.UserException;
import model.User;

import java.sql.SQLException;

public interface UserDao32 {
    public void addUser(User user) throws SQLException;
    public User findUserByActiveCode(String activecode)throws SQLException;
    public void updateStat(String activeCode) throws SQLException;
    public User findUserByUsernameAndPassword(String username,String password) throws SQLException;
    public User findUserById(String id)throws SQLException;
    public void updateUser(User user)throws SQLException;
}
