
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import utils.C3P0Utils;
import utils.JdbcUtils;
import utils.SendJMail;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

public class test {


    static void addUser() throws Exception{
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setDriverClass("com.mysql.cj.jdbc.Driver");
        source.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/bookStore?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8");
        source.setUser("root");
        source.setPassword("1230");
        source.getConnection();
        QueryRunner queryRunner2 = new QueryRunner();

        Connection connection = JdbcUtils.getConnection();
        String sql = "insert into test(username,psd) values (?,?)";
        QueryRunner qr = new QueryRunner(source);


        //sql语句
        sql = "INSERT INTO user " +
                "(username,PASSWORD,gender,email,telephone,introduce,activeCode,state,role,registTime) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?)";

        //执行
        qr.update(sql,"fa","fa","男",
                "fd","15","fd",
                "fd",0,"user",new Date());
    }

    public static void lilv(){
        double x = 187.5;
        double [] ans = new double[2];
        ans[0] = x;
        for(int i = 0;i < 30;i++){
            ans[0] *= (1 + 0.0005);
        }
        System.out.printf("一个月总钱数为%.2f\n",ans[0]);
        System.out.printf("月利率为%.2f\n",(ans[0] - x) / x);
        System.out.printf("测试pow月利率%.2f\n",Math.pow(1.0005,30) - 1);
        System.out.printf("年利率为%.2f\n",(x * Math.pow(1.0005,365) - x) / x);
        System.out.printf("测试double类型的数%.2f\n",Math.pow(1.0005,365) - 1);
        for(int i = 0;i < 365;i++){
            x *= (1 + 0.0005);
        }
        System.out.printf("一年的总应还钱数是%.2f\n",x);
        x = 12.0;
        System.out.printf("借呗的利率%.2f\n",(x * Math.pow(1.0073,12) - x));
    }

    void sendmail(){
        // 收件人电子邮箱
        String to = "1078682405@qq.com";

        // 发件人电子邮箱
        String from = "1078682405@qq.com";

        // 指定发送邮件的主机为 localhost
        String host = "localhost";

        // 获取系统属性
        Properties properties = System.getProperties();

        // 设置邮件服务器
        properties.setProperty("mail.smtp.host", host);

        // 获取默认session对象
        Session session = Session.getDefaultInstance(properties);

        try{
            // 创建默认的 MimeMessage 对象
            MimeMessage message = new MimeMessage(session);

            // Set From: 头部头字段
            message.setFrom(new InternetAddress(from));

            // Set To: 头部头字段
            message.addRecipient(Message.RecipientType.TO,
                    new InternetAddress(to));

            // Set Subject: 头部头字段
            message.setSubject("This is the Subject Line!");

            // 设置消息体
            message.setText("This is actual message");

            // 发送消息
            Transport.send(message);
            System.out.println("Sent message successfully....");
        }catch (MessagingException mex) {
            mex.printStackTrace();
        }
    }
    public static void main(String[] args) {
        String link = "http:localhost:8080/bookStore/active?activeCode=" + UUID.randomUUID().toString();
        String html  = "<a href = \"" + link + "\">欢迎注册网上书城账号，点此激活</a>";
        SendJMail.sendMail("fuzekun255@163.com",html);
    }
}
