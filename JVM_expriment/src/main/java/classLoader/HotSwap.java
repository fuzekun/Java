package classLoader;
/*
*
*   java热部署的实现测试
* */

import java.io.IOException;
import java.io.InputStream;
import java.io.File;
import java.lang.reflect.Method;
/**
 * 自定义类加载器，并override findClass方法
 */
class MyClassLoader extends ClassLoader{
    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException{
        try{
            String fileName = name.substring(name.lastIndexOf("." )+1) + ".class" ;
            InputStream is = this.getClass().getResourceAsStream(fileName);
            byte[] b = new byte[is.available()];
            is.read(b);
            return defineClass(name, b, 0, b. length);
        } catch(IOException e){
            throw new ClassNotFoundException(name);
        }
    }
}



public class HotSwap {
    public static void main(String[] args) throws Exception{
        loadHelloWorld();
        // 回收资源,释放HelloWorld.class文件，使之可以被替换
        System.gc();
        Thread.sleep(1000);// 等待资源被回收
        File fileV2 = new File( "HelloWorld.class");
        File fileV1 = new File(
            "D:\\projects\\java\\JVM_expriment\\out\\production\\JVM_expriment\\classLoader\\HelloWorld.class" );
        fileV1.delete(); //删除V1版本
        fileV2.renameTo(fileV1); //更新V2版本
        System. out.println( "Update success!");
        loadHelloWorld();
}

    public static void loadHelloWorld() throws Exception {
        MyClassLoader myLoader = new MyClassLoader(); //自定义类加载器
        Class<?> class1 = myLoader
                .findClass( "classLoader.HelloWorld");//类实例
        Object obj1 = class1.newInstance(); //生成新的对象
        Method method = class1.getMethod( "say");
        method.invoke(obj1); //执行方法say
        System. out.println(obj1.getClass()); //对象
        System. out.println(obj1.getClass().getClassLoader()); //对象的类加载器
    }
}
