package threadBase.unsafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 17:49
 * @Description:    测试原子更新器
 * 1. volatile才可以修改
 * 2. 私有属性不可修改
 */
public class TestUpdate {



    public static void main(String[] args) {
        Person person = new TestUpdate().new Person();
        AtomicReferenceFieldUpdater
                nameupdate = AtomicReferenceFieldUpdater.newUpdater(Person.class,
                String.class, "name");
        AtomicIntegerFieldUpdater
                ageupdate = AtomicIntegerFieldUpdater.newUpdater(Person.class, "age");
        System.out.println(ageupdate.compareAndSet(person, 0, 1));
        System.out.println(nameupdate.compareAndSet(person,null, "zhangsan"));
        System.out.println(person);
    }
    class Person {
        volatile String name;
        volatile int age;

        public void setAge(int age) {
            this.age = age;
        }

        public int getAge() {
            return age;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {

            return "[name = " + name + ", age = " +  age + "]";
        }
    }

}


