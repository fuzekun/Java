package Class;

import java.math.BigInteger;  
import java.util.Random;

public class BigIntegerDemo {

	private String text;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("构造两个BigInteger对象: ");  
        //BigInteger(int numBits, Random rnd)   
        //构造一个随机生成的 BigInteger，它是在 0 到 (2^numBits - 1)（包括）范围内均匀分布的值  
        BigInteger bi1 =  new BigInteger(55,new Random());  
        System.out.println("bi1 = " + bi1);  
          
        //BigInteger(byte[] val)   
        //将包含 BigInteger 的二进制补码表示形式的 byte 数组转换为 BigInteger。  
        BigInteger bi2 = new BigInteger(new byte[]{3,2,3});  
        System.out.println("bi2 = " + bi2);  
          
        //加  
        System.out.println("bi1 + bi2 = " + bi1.add(bi2));  
        //减  
        System.out.println("bi1 - bi2 = " + bi1.subtract(bi2));  
        //乘  
        System.out.println("bi1 * bi2 = " + bi1.multiply(bi2));  
        //指数运算  
        System.out.println("bi1的2次方 = " + bi1.pow(2));  
        //整数商  
        System.out.println("bi1/bi2的整数商: " + bi1.divide(bi2));  
        //余数  
        System.out.println("bi1/bi2的余数: " + bi1.remainder(bi2));  
        //整数商+余数  
        System.out.println("bi1 / bi2 = " + bi1.divideAndRemainder(bi2)[0] +   
                "--" + bi1.divideAndRemainder(bi2)[1]);  
        System.out.println("bi1 + bi2 = " + bi1.add(bi2));  
        //比较大小,也可以用max()和min()  
        if(bi1.compareTo(bi2) > 0)  
  
               System.out.println("bd1 is greater than bd2");  
  
           else if(bi1.compareTo(bi2) == 0)  
  
               System.out.println("bd1 is equal to bd2");  
  
           else if(bi1.compareTo(bi2) < 0)  
  
               System.out.println("bd1 is lower than bd2");  
        //返回相反数  
        BigInteger bi3 = bi1.negate();  
        System.out.println("bi1的相反数: " + bi3);  
        //返回绝对值  
        System.out.println("bi1的绝对值:  " + bi3.abs());  
        
        //包装类和基本类型之间的互相转化
        int x = new Integer(3);
        System.out.println(x);
        x = new Integer(3) + new Integer(4);
        System.out.println(x);
        
        //测试一个错误
        //void类型不能输出
       // System.out.print(test("afa"));
        
	}
	public void test(String s) {
		this.setText(s);
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

}
