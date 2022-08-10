# gradle编译spring源码



1. 修改gradle.build文件中的镜像
2. 修改gradle.build文件的kitlion的版本信息。改成1.3。
3. 跳过test模块

```java
test {
	enable = false
}
```

4. checkStyle删除

```xml
src下的模块删除
```



5. 依赖写的不对 。
   - 直接复制粘贴别人的依赖。
   - 比如spring-test下面的依赖





![image-20220504140005407](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504140005407.png)

![image-20220504143431174](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504143431174.png)



6. -Werror选项。
   - 仍旧是在build.gradle文件中，搜索删除就行了。







## 下载源码



## 2. 删除



## 各种错误总结

### 80%的编译错误都是由于依赖出错导致的

 解决的核心思路就是：

- 修改gradle文件
- 删除不必要的类
- 查看依赖是否下载



### 其他的错误

1. 不跳过test模块出错
2. 不跳过生成文档模块出错
3. 进程占用，无法删除旧的输出内容。
4. 编译好了之后，发现某些类找不到，但是能够跳转到这些类上面。

5. kotlin中的类找不到：还是修改gradle文件，把compileOnly改成compile就行了。