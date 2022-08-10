# Java对象序列化



1. 实现了序列化接口
2. 创建一个输入流
3. 创建调用流的写对象





```java
package memento;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Test1 {

    private static List<Integer>a = new ArrayList<>();
    static {
        a.add(1);
        a.add(2);
        a.add(5);
    }
    public static void main(String[] args) {

        // 说明list实现了Seriablizable接口
        System.out.println(a instanceof Serializable);
        try (OutputStream out = new FileOutputStream(new File("./src/data/2.txt")); ObjectOutputStream wo = new ObjectOutputStream(out)) {
            wo.writeObject(a);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try (InputStream in = new FileInputStream(new File("./src/data/2.txt")); ObjectInput io = new ObjectInputStream(in)) {
            List<Integer> b = (List<Integer>) io.readObject();
            System.out.println(b.get(0) + " " + b.get(2));
        } catch (Exception e) {

        }



    }
}

```

