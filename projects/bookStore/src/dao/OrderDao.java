package dao;

import model.Order;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    public void createOrder(Order order)throws SQLException;
    public List<Order> findOrderByUId(int id)throws SQLException;
    public Order findOrderById(String orderId)throws SQLException;
    public void deleteOrder(String orderId)throws SQLException;
    public void changeOrderState(String orderId)throws SQLException;
}
