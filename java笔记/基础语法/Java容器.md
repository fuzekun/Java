# Java容器

## list

- 所有的变量名称只不过是指针。
- list初始化initCapcity只是初始化了Obect[]的大小，并没有初始化size，所以如果直接访问，还是会报IndexOutOfBoundery异常。
- 如果想要初始化大小，应该使用Arrays.asList(new Integer[5]);
- 此时，还是没有赋值。因为数组赋值也需要进行new Integer操作。

### list初始化问题

```java
   
   List<Integer>list = new ArrayList<>(Arrays.asList(new Integer[5]));
    int[]a = new int[10];


TestGenericDao t = new TestGenericDao();
    List<Integer> list2 = new ArrayList<>();
    list2.add(4);
    list2.add(2);
    list2.add(3);
    System.out.println(t.list.size());
    Collections.copy(t.list, list2); // 可以使用这种进行，就不是同一个了
    //        t.list = list2;               // 如果直接赋值，就是指向了同一个
    Collections.sort(list2);
    for (int x : t.list) {      // 指向的是同一个list，其中的list和list2都是指针
        System.out.println(x);
    }
	// 正确的访问方式
	int n = list2.size();
	for (int i = 0; i < n; i++) {
        System.out.println(t.list.get(i));
    }
}


```





### list的声明大小(size)

- 使用新生成对象的方式进行，相当于创建了五个大小的空数组，然后使用**工具Arrays包装**一下。

```java
  
   List<Integer>list = new ArrayList<>(Arrays.asList(new Integer[5]));
```





### list初始化的几种方式



- 使用匿名内部类

```java
List<String> stringList = new LinkedList<String>(){{
    add("a");
    add("b");
    add("c");
}};
```

- 使用Arrays类

```java
List<String> stringList = Arrays.asList("a", "b", "c");
```



- 使用Collection类 --- **不可变，用处不大**

```java
List<String> apples = Collections.nCopies(3, "apple");
System.out.println(apples);
```



- 使用Stream

```java
List<String> list = Stream.of("a", "b", "c").collect(Collectors.toList());
```



- 使用Lists(jdk9)

```java
List<String> list = Lists.newArrayList("a", "b", "c");
```





