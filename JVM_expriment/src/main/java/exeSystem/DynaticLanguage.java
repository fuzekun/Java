package exeSystem;

import java.io.FileWriter;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;


/*
*
*   java的动态类型语言
*   就是说：java的类型检查是在运行时期而不是在编译时期。只有在解释执行的过程中才会触发。
* */
public class DynaticLanguage {

    public static void main(String[] args) {
        Father f = new Son();
        GrandFather fa = new Father();
        f.thinking();
        fa.thinking();
    }

}

class GrandFather {
    void thinking() {
        System.out.println("I am grandfa");
    }
}
class Father extends GrandFather {
    void thinking() {
        System.out.println("I am father");
    }
}

class Son extends Father {
    void thinking() {
        try {
            MethodType mt = MethodType.methodType(void.class);
//                Field  lookupImp = MethodHandle.Lookup.class.getDeclaredField("IMPL_LOOKUP");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

