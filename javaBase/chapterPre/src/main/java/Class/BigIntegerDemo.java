package Class;

import java.math.BigInteger;  
import java.util.Random;

public class BigIntegerDemo {

	private String text;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		System.out.println("��������BigInteger����: ");  
        //BigInteger(int numBits, Random rnd)   
        //����һ��������ɵ� BigInteger�������� 0 �� (2^numBits - 1)����������Χ�ھ��ȷֲ���ֵ  
        BigInteger bi1 =  new BigInteger(55,new Random());  
        System.out.println("bi1 = " + bi1);  
          
        //BigInteger(byte[] val)   
        //������ BigInteger �Ķ����Ʋ����ʾ��ʽ�� byte ����ת��Ϊ BigInteger��  
        BigInteger bi2 = new BigInteger(new byte[]{3,2,3});  
        System.out.println("bi2 = " + bi2);  
          
        //��  
        System.out.println("bi1 + bi2 = " + bi1.add(bi2));  
        //��  
        System.out.println("bi1 - bi2 = " + bi1.subtract(bi2));  
        //��  
        System.out.println("bi1 * bi2 = " + bi1.multiply(bi2));  
        //ָ������  
        System.out.println("bi1��2�η� = " + bi1.pow(2));  
        //������  
        System.out.println("bi1/bi2��������: " + bi1.divide(bi2));  
        //����  
        System.out.println("bi1/bi2������: " + bi1.remainder(bi2));  
        //������+����  
        System.out.println("bi1 / bi2 = " + bi1.divideAndRemainder(bi2)[0] +   
                "--" + bi1.divideAndRemainder(bi2)[1]);  
        System.out.println("bi1 + bi2 = " + bi1.add(bi2));  
        //�Ƚϴ�С,Ҳ������max()��min()  
        if(bi1.compareTo(bi2) > 0)  
  
               System.out.println("bd1 is greater than bd2");  
  
           else if(bi1.compareTo(bi2) == 0)  
  
               System.out.println("bd1 is equal to bd2");  
  
           else if(bi1.compareTo(bi2) < 0)  
  
               System.out.println("bd1 is lower than bd2");  
        //�����෴��  
        BigInteger bi3 = bi1.negate();  
        System.out.println("bi1���෴��: " + bi3);  
        //���ؾ���ֵ  
        System.out.println("bi1�ľ���ֵ:  " + bi3.abs());  
        
        //��װ��ͻ�������֮��Ļ���ת��
        int x = new Integer(3);
        System.out.println(x);
        x = new Integer(3) + new Integer(4);
        System.out.println(x);
        
        //����һ������
        //void���Ͳ������
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
