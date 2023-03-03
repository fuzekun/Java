package model;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Order  {
    private String id; // 订单编号
    private double money; // 订单总价
    private String receiverAddress; // 送货地址
    private String receiverName; // 收货人姓名
    private String receiverPhone; // 收货人电话
    private int paystate; // 订单状态
    private Date ordertime; // 下单时间
    private int userId;//用户的id

    private List<OrderItem>items;

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getId() {
        return id;
    }

    public double getMoney() {
        return money;
    }

    public String getReceiverAddress() {
        return receiverAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public int getPaystate() {
        return paystate;
    }

    public Date getOrdertime() {
        return ordertime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void setReceiverAddress(String receiverAddress) {
        this.receiverAddress = receiverAddress;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public void setPaystate(int paystate) {
        this.paystate = paystate;
    }

    public void setOrdertime(Date ordertime) {
        this.ordertime = ordertime;
    }

    @Override
    public String toString(){
        return "Order{" +
                "id='" + id + '\'' +
                ", money=" + money +
                ", receiverAddress='" + receiverAddress + '\'' +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", paystate=" + paystate +
                ", ordertime=" + ordertime +
                '}';
    }
}
