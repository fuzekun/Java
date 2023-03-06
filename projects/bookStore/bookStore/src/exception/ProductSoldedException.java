package exception;
/**
 * 商品数量超出已有的库存报错
 * msg
 * */
public class ProductSoldedException extends Exception{
    public ProductSoldedException(String msg){
        super(msg);
    }
}
