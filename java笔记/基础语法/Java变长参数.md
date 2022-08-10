# Java变长参数



## 1. 是什么

使用

`...` 修饰的参数

```java
package compiler;

/*
*
*   java中变长参数的实现
*
* */
public class ChangeLenArgs {

    private static void print(String ... args) {
        for (int i = 0; i < args.length; i++) {
            System.out.println(args[i]);
        }
    }

    public static void main(String[] args) {
        print("你好");
        print("你也好", "小明");
    }
}

```



## 2. 如何实现的

反编译之后，就会发现就是使用数组实现的。