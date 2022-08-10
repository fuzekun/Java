package exeSystem;

import static java.lang.invoke.MethodHandles.lookup;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
/*
*
* IMPL_LOOKUP这个Field本身就是一个Lookup对象。这个对象在MethodHandlers类里面。
* static final Lookup IMPL_LOOKUP = new Lookup(Object.class, TRUSTED);
*   所以可以通过, Class对象的一个Field，来获取到，并且设置它的可见性。
* 而Field中有get()方法，返回的是这个默认值。
* */


public class Test {
    class GrandFa {
        void thinking() {
            System.out.println("GrandFa is thinking.");
        }
    }

    class Fa extends GrandFa {
        @Override
        void thinking() {
            System.out.println("Father is thinking.");
        }
    }

    class SSon extends Fa {
        @Override
        void thinking() {
            try {
                MethodType mt = MethodType.methodType(void.class);
                Field lookupImp = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
                lookupImp.setAccessible(true);
                ((MethodHandles.Lookup) lookupImp.get(null))
                        .findSpecial(GrandFa.class, "thinking", mt, GrandFa.class)
                        .bindTo(this)
                        .invoke();
            } catch (Throwable e) {

            }
        }
    }

    public static void main(String[] args) {
        (new Test().new SSon()).thinking();
    }
}
