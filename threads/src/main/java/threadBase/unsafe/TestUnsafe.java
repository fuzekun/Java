package threadBase.unsafe;


import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 9:58
 * @Description: 并不是线程不安全的， 而是操作比较底层，容易出现问题。
 */
public class TestUnsafe {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        Field f = Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        // 因为是静态的，所以直接使用null，不获取对象的字段，获取类的字段
        Unsafe unsafe = (Unsafe) f.get(null);   // obj是要获得obj对象的这个字段

        // 1. 获取域的偏移地址
        long nameOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("name"));
        long ageOffset = unsafe.objectFieldOffset(Person.class.getDeclaredField("age"));

        Person p = new TestUnsafe().new Person();
        // 2.执行cas操作
        unsafe.compareAndSwapInt(p, ageOffset, 0, 1);
        unsafe.compareAndSwapObject(p, nameOffset, null, "张三");

        System.out.println(p);
    }

    class Person {
        private String name;
        private int age;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        @Override
        public String toString() {
            return "[name = " + name + ", age = " + age + "]";
        }
    }
}
