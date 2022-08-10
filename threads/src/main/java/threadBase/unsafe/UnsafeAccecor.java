package threadBase.unsafe;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author: Zekun Fu
 * @date: 2022/6/5 18:21
 * @Description:    工具类获取一个反射的对象
 */
public class UnsafeAccecor {

    public static Unsafe getUnsafe() {

        Field f = null;
        try {
            f = Unsafe.class.getDeclaredField("theUnsafe");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        f.setAccessible(true);
        // 因为是静态的，所以直接使用null，不获取对象的字段，获取类的字段
        Unsafe unsafe = null;   // obj是要获得obj对象的这个字段
        try {
            unsafe = (Unsafe) f.get(null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return unsafe;
    }
}
