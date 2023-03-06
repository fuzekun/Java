package dao.Imp;

import com.mchange.v2.c3p0.cfg.C3P0Config;
import model.Order;
import model.OrderItem;
import model.Product;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import utils.C3P0Utils;
import utils.ManageThreadLocal;

import java.io.ObjectInput;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookDao implements dao.BookDao {
    /**
    * 计算产品总数
    * @param category 书籍类型
    *
    * */
    public long count(String category) throws SQLException {
        long cnt = 0;

        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        String sql = "select count(*) from products where 1=1";

        if(category != null && !"".equals(category)){
            sql += " and category = ?";
            cnt = (long)queryRunner.query(sql,new ScalarHandler(),category);
        }else {
            cnt = (long)queryRunner.query(sql,new ScalarHandler());
        }

        return cnt;
    }
    /**
     * @param category 类型
     * @param page 当前页
     * @param pageSize 页总数
     * */

    @Override
    public List<Product> findBook (String category, int page,int pageSize) throws  SQLException{
        List<Object>params = new ArrayList<>();
        String sql = "select * from products where 1=1";
        if(category != null && !"".equals(category)){
            sql += " and category = ?";
            params.add(category);
        }
        int start = (page - 1) * pageSize;
        sql += " limit ?,?";
        params.add(start);
        params.add(pageSize);

        //执行
        QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());

        return queryRunner.query(sql,new BeanListHandler<Product>(Product.class),params.toArray());
    }


    public static void main(String[] args) throws SQLException{
        BookDao dao = new BookDao();
        long cnt = dao.count("文学");
        String category = "文学";
        System.out.println(cnt);
        List<Product>list = dao.findBook(category,1,4);
        for(Product p :list) {
            System.out.println(p);
        }
    }

    @Override
    /**
     * @param id 商品id
     *
     * */
    public Product findBookById(String id) throws SQLException{
        QueryRunner qr = new QueryRunner(C3P0Utils.getDataSource());
        String sql = "select * from products where id = ?";
        return qr.query(sql,new BeanHandler<Product>(Product.class),id);
    }


    @Override
    public void updateBookNum(String p_id,int num) throws SQLException{
        QueryRunner queryRunner = new QueryRunner();
        String sql = "update products set pnum = ? where id = ?";
        queryRunner.update(ManageThreadLocal.getConnection(),sql,num,p_id);
    }
}
