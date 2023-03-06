package model;

import java.io.Serializable;

public class OrderItem  {
    private Order order;//订单
    private Product p; //商品
    private int buynum; //购物数量

    public Order getOrder() {
        return order;
    }

    public Product getP() {
        return p;
    }

    public int getBuynum() {
        return buynum;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setP(Product p) {
        this.p = p;
    }

    public void setBuynum(int buynum) {
        this.buynum = buynum;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
                "order=" + order +
                ", p=" + p +
                ", buynum=" + buynum +
                '}';
    }
}
