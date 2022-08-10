package first_memory.memeryLeak;

import exeSystem.Test;

import java.util.HashSet;

/**
 * @author: Zekun Fu
 * @date: 2022/5/26 10:55
 * @Description:测试一下看看仅仅修改属性，不改变hash值会不会导致内存泄漏
 */
public class TestHashLeak {

    class Person {
        int id;
        String name;
        public Person(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public int hashCode() {     // 重写了方法和某个属性相关，如果修改属性，hash值就会被修改
            return id;
        }

        @Override
        public String toString() {
            String s = "{id:" + this.id + ", name:" + this.name + "}";
            return s;
        }
    }

    public static void main(String[] args) {
        Person p1 = new TestHashLeak().new Person(1, "test1");
        Person p2 = new TestHashLeak().new Person(2, "test2");
        HashSet<Person>s = new HashSet<>();
        s.add(p2);
        s.add(p1);
        p1.setId(2);                    // 修改了对象的hash值。
        // 此时p1挂在了hash值为1的链表上，但是p1的hash值是2, 就永远没法删除了。
        System.out.println(s.remove(p1));
        for (Person p : s) {
            System.out.println(p);
        }
    }
}
