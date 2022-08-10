package clone;

import java.util.Scanner;

/**
 * @author: Zekun Fu
 * @date: 2022/5/20 20:38
 * @Description: 深克隆需要递归克隆，所有的引用对象都需要进行深克隆，StringBuilder也不例外。
 *                  但是String一般不需要进行，因为String是指向常量池的。
 *                  除非newString，让String再堆里面
 */
public class StirngBuilderClone {

    private class Person implements Cloneable{
        public StringBuilder sb;
        public Person (StringBuilder sb) {
            this.sb = sb;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException{
        Person p = new StirngBuilderClone().new Person(new StringBuilder("北京"));
        Person p2 = (Person) p.clone();
        p2.sb.append("腾讯");
        System.out.println(p.sb);
    }
}
