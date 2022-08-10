package clone;

/**
 * @author: Zekun Fu
 * @date: 2022/5/20 20:23
 * @Description: 测试String类型是否是“深克隆”,如果不使用new，那么就不用深拷贝。
 *                  但是如果使用了new,也很大可能不需要。
 *                  因为String是一个不可变的对象，没法对String的属性进行修改，
 *                  一旦修改，就意味着新的String对象产生了。
 *                  那么拷贝的那个对象就不指向原来的地址了。
 *
 *                  总结：String类型一般不需要深拷贝。
 *                      1. 如果不new，指向常量驰。
 *                      2. 如果new了，由于不可变性，也不会改变原型对象的String。
 */
public class StringClone {

    private class Person implements Cloneable{
        public String address;
        public StringBuilder sb;
        public Person(String address, StringBuilder sb) {
            this.address = address;     // 放在了堆里面,但是指向常量池中的addres
            this.sb = sb;
        }

        @Override
        protected Object clone() throws CloneNotSupportedException {
            return super.clone();
        }
    }

    public static void main(String[] args) throws CloneNotSupportedException{
        Person p = new StringClone().new Person(new String("北京"), new StringBuilder("北京"));
        Person p2 = (Person) p.clone();
//        p2.address += "上海";             // 因为p2 += 创建了一个新的StringBuilder对象，然后创建了新的String，所以引动的地址直接就改变了。
        p2.sb.append("腾讯");             // 如果直接是
        System.out.println(p.address);
        System.out.println(p.sb);
    }
}
