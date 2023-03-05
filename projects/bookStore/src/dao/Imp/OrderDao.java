package dao.Imp;

import model.Order;
import model.OrderItem;
import model.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.C3P0Utils;
import utils.ManageThreadLocal;

import javax.xml.transform.Result;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDao implements dao.OrderDao {
    @Override
    public void createOrder(Order order)throws SQLException{

        //1.sql
        String sql = "insert into orders values(?,?,?,?,?,?,?,?)";

        //3.参数
        List<Object>params = new ArrayList<>();
        params.add(order.getId());
        params.add(order.getMoney());
        params.add(order.getReceiverAddress());
        params.add(order.getReceiverName());
        params.add(order.getReceiverPhone());
        params.add(order.getPaystate());
        params.add(order.getOrdertime());
        params.add(order.getUserId());

        //3.执行
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(ManageThreadLocal.getConnection(),sql,params.toArray());
    }

    @Override
    public List<Order> findOrderByUId(int uid)throws SQLException{
        String sql = "select * from orders where user_id = ?";
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        return queryRunner.query(sql,new BeanListHandler<Order>(Order.class),uid);
    }

    @Override
    public Order findOrderById(String orderId)throws SQLException{

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select * from orders where id = ?";
        String sql2 = "select o.*,p.name,p.price from orderitem o,products p where o.product_id = p.id and o.order_id = ?";

        List<OrderItem> myItems = queryRunner.query(sql2, new ResultSetHandler<List<OrderItem>>(){
            @Override
            public List<OrderItem>handle(ResultSet rs)throws SQLException{
                //1.创建集合对象
                List<OrderItem>items = new ArrayList<>();

                //2.变量
                while(rs.next()){
                    //创建一个OrderItem对象
                    OrderItem item = new OrderItem();
                    item.setBuynum(rs.getInt("buynum"));

                    //创建product对象
                    Product product = new Product();
                    product.setId(rs.getString("product_id"));
                    product.setName(rs.getString("name"));
                    product.setPrice(rs.getDouble("price"));

                    //把product放入item
                    item.setP(product);

                    //把item放在items中
                    items.add(item);
                }
                //3.返回变量
                return items;
            }
        },orderId);
        //1.得到order
        Order order = queryRunner.query(sql,new BeanHandler<Order>(Order.class),orderId);
        //2.封装item
        order.setItems(myItems);
        return order;
    }


    /**
    * 删除订单
    * */
    @Override
    public void deleteOrder(String orderId)throws SQLException{
        String sql = "delete from orders where Id = ?";
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.update(ManageThreadLocal.getConnection(),sql,orderId);
    }
    /**
     * 修改订单状态
     *
    * */
    @Override
    public void changeOrderState(String orderId)throws SQLException{
        String sql = "update orders set paystate = ? where id = ?";
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
        queryRunner.update(sql,1,orderId);
    }

    public static void main(String[] args) {
        OrderDao orderDao = new OrderDao();
        try {
            Order order = orderDao.findOrderById("21e02eff-3fe3-442a-8fba-516591673dae");
            System.out.println(order);
            System.out.println("商品详情");
            for(OrderItem item:order.getItems()){
                System.out.println("数量:" + item.getBuynum());
                System.out.println("名称:" + item.getP().getName());
                System.out.println("价格" + item.getP().getPrice());
            }
            orderDao.changeOrderState("21e02eff-3fe3-442a-8fba-516591673dae");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
