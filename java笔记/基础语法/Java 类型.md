# Java 类型

Integer类型重写了equals 方法，比较的都是数值的大小。但是首先判断是同一类型的才行。但是 == 判断的就是对象了。

```java
public boolean equals(Object obj) {
    if (obj instanceof Integer) {
        return value == ((Integer)obj).intValue();
    }
    return false;
}
```