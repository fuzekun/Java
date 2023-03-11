package service;

import dao.Imp.BookDao;
import dao.Imp.OrderDao;
import dao.Imp.OrderItemsDao;
import dao.Imp.UserDao;
import exception.ProductSoldedException;
import model.Order;
import model.OrderItem;
import model.Product;
import org.apache.commons.dbutils.handlers.MapHandler;
import utils.ManageThreadLocal;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderServiece {
    OrderDao orderDao = new OrderDao();
    OrderItemsDao orderItemsDao = new OrderItemsDao();
    BookDao bookDao = new BookDao();
    public void solveOrder(Order order){
        try{
            //开启事务
            ManageThreadLocal.startTransaction();
            //1.插入订单表
            orderDao.createOrder(order);
            //2.插入订单详情表
            orderItemsDao.addOrderItems2(order.getItems());
            //3.修改商品的数量
            for(OrderItem item :order.getItems()){
                bookDao.updateBookNum(item.getP().getId(),item.getP().getPnum() - item.getBuynum());
            }

            //结束事务
            ManageThreadLocal.commitTransaction();

        }catch (SQLException e){
            System.out.println("creteOrder或者addOrderItem的SQL出错");

            //回滚事务
            ManageThreadLocal.rollbackTransaction();
            e.printStackTrace();
        }
    }
    public List<Order>findOrderByUserId(int uid){
        List<Order>list = null;
        try{
            list = orderDao.findOrderByUId(uid);
        }catch (SQLException e){
            System.out.println("查询订单sql出错");
            e.printStackTrace();
        }
        return list;
    }

    public Order findOrderById(String orderId){
        try{
            //1.获取订单信息
            Order order = new Order();
            order = orderDao.findOrderById(orderId);
            //2.返回信息
            return order;
        }catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }
    public void changeOrderState(String orderId){
        try{
            orderDao.changeOrderState(orderId);
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
