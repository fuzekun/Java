package dao.Imp;

import model.Order;
import model.OrderItem;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import utils.C3P0Utils;
import utils.ManageThreadLocal;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderItemsDao implements dao.OrderItemsDao {

    @Override
    public void addOrderItems(List<OrderItem>items)throws SQLException {
        String sql = "insert into orderitem values(?,?,?)";

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        for(OrderItem orderItem : items){
            queryRunner.update(sql,orderItem.getOrder().getId(),orderItem.getP().getId(),orderItem.getBuynum());
        }
    }


    /**
     * 这个速度更快，使用批处理
     * */
    @Override
    public void addOrderItems2(List<OrderItem>items) throws SQLException{


        //1.首先创建二维数组
        Object[][] params  = new Object[items.size()][];
        for(int i = 0;i < items.size();i++){
            OrderItem item = items.get(i);
            params[i] = new Object[]{item.getOrder().getId(),item.getP().getId(),item.getBuynum()};
        }
        //2.sql
        String sql = "insert into orderitem values(?,?,?)";

        //3.使用批处理执行sql
        QueryRunner queryRunner = new QueryRunner();
        queryRunner.batch(ManageThreadLocal.getConnection(),sql,params);
    }
}
