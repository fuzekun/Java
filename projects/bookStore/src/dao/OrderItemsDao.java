package dao;

import model.OrderItem;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface OrderItemsDao {
    public void addOrderItems(List<OrderItem> items)throws SQLException;
    public void addOrderItems2(List<OrderItem>items) throws SQLException;
}
