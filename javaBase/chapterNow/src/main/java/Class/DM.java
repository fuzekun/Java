package Class;

import java.util.Date;
import java.util.EnumSet;

public class DM {

    public static class Employee implements Comparable<Employee>{
        /*
        *
        * 正常来说，Date的装填应该只是由Employee这个类生成的对象进行改变，
        * 但是由于Date生成的对象，本身就是一个可变的对象，
        * 所以
        * */
        private Date date;              // 由于date是可变的，所以就算这里使用了private 那么他也是会被改变的

        private Date getDate() {
            return date;            // 如果直接返回就会破坏封装性
        }
        private Date get() {
            return (Date)date.clone();  // 正确做法应该是返回一个新的对象。
        }

        @Override
        public boolean equals(Object obj) {
            Employee other = (Employee) obj;    // 重写方法还得经过一步强制转换
            return this.date.equals(other.date);
        }

        @Override
        public int compareTo(Employee o) {
            return o.date.compareTo(o.date);    // 使用o.date没什么问题，因为这个类可以访问任何一个对象的私有域。
        }

        private static void test() {
            Employee e = new Employee();
            e.date = new Date();
        }

    }

    public static void main(String[] args) {
        Date d = new Employee().getDate();
        double tenYearsInMilliSecond = 10 * 365.25 * 24 * 60 * 60 * 1000;
        d.setTime(d.getTime() - (long)tenYearsInMilliSecond);

    }

    static class Oclass {
        public static void main(String[] args) {
            Employee employee = new Employee();
            employee.date = new Date();     // 在同一个类种的任意方法可以访问任意私有域。
        }
    }
}
