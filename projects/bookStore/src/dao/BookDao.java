package dao;

import model.OrderItem;
import model.Product;

import java.sql.SQLException;
import java.util.List;

public interface BookDao {
    public long count(String category) throws SQLException;//计算总数
    public List<Product>findBook(String category,int page,int pageSize) throws  SQLException;//根据类别和当前页数返回当前页的所有书籍信息
    public Product findBookById(String id) throws SQLException;
    public void updateBookNum(String p_id,int num) throws SQLException;
}
