package service;

import dao.Imp.BookDao;
import exception.ProductSoldedException;
import model.PageResult;
import model.Product;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductService {
    BookDao bookDao = new BookDao();
    /**
     * @param category 类别;
     * @param  page 当前页
     * @param pageSize 一页的数目
     * @return null 表示出错
     * @return !null 正确结果
     * */
    public PageResult<Product>findBooks(String category,int page,int pageSize){
        //创建模型
        PageResult<Product>pr = new PageResult<>();
        long totalCount = 0;

        try {
            //设置总记录数
            totalCount = bookDao.count(category);
            pr.setTotalcnt(totalCount);

            //设置一页显示的数目
            if(pageSize == 0){
                pageSize = 4;
            }
            pr.setPageSize(4);

            //设置总页数
            int totcalPage = (int)totalCount / pageSize;
            if(totalCount % pageSize != 0){//上取整
                totcalPage++;
            }
            pr.setTotalpage(totcalPage);

            //设置当前页数
            pr.setCurrenpage(page);

            //设置数据list
            List<Product>list = bookDao.findBook(category,page,pageSize);
            pr.setList(list);

            //正确返回正确数据
            return pr;
        }catch (Exception e){
            e.printStackTrace();

            //错误返回空
            return null;
        }
    }
    public Product findBook(String id){
        try{
            return bookDao.findBookById(id);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 验证equal方法是否重写
     * */
    public static void main(String[] args) {
        Map<Product, Integer> cart = new HashMap<>();
        ProductService productService = new ProductService();
        Product p1 = productService.findBook("2");
        cart.put(p1, 1);
        Product p2 = productService.findBook("2");
        if (cart.containsKey(p2)) {
            System.out.println("已经存在");
        } else {
            System.out.println("不存在");
        }
    }
}
