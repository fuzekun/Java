# Java中的字符串



## 字符串的不可变性

> 总结来说就是：对于变量的操作，不会修改堆区中的字符串，而是重新创建一个字符串。

## 编码

1. uft-8和utf-16都是采用的边长的编码方式，所以单个字符和字符串的长度是不一样的。

2. java中**内码（运行内存）中的char使用UTF16的方式编码**，一个char占用两个字节，但是某些字符需要两个char来表示。所以，一个字符会占用2个或4个字节。

3. java中外码（字节码文件）中**char使用UTF8的方式编码**，一个字符占用1～6个字节。

4. UTF16编码中，英文字符占两个字节；**绝大多数汉字（尤其是常用汉字）占用两个字节**，**个别汉字**（在后期加入unicode编码的汉字，一般是极少用到的生僻字）**占用四个字节**。

5. UTF8编码中，英文字符占用一个字节；**绝大多数汉字占用三个字节，个别汉字占用四个字节**。



```java
   @Test
    public void test1() throws Exception{
        String china = "钓鱼岛是中国的";

        for (int i = 0; i < china.length(); i++) {
            char ch = china.charAt(i);
            StringBuilder t = new StringBuilder();
            t.append(ch);
            String ss = t.toString();
            System.out.println(ss.getBytes("utf-16").length);
        }
        System.out.println(china.getBytes("utf-16").length);
        System.out.println(china.getBytes().length);
        System.out.println(china.getBytes("utf-8").length);
    }
```



但是就是说byte数组的长度和**utf-8**的**长度是一样的**，如果采用的是**utf-16**的编码方式，应该是和utf-16一样的。所以内码的编码方式是utf-8;



![image-20220401113324496](D:\blgs\source\imgs\image-20220401113324496.png)



## 参考文献



[编码问题](https://www.cnblogs.com/louiswong/p/6062417.html)



