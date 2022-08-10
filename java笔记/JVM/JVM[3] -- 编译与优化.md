# JVM -- 编译与优化









## 及时编译器

![image-20220501171535744](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501171535744.png)

### 背景

### 实现

#### **分层编译与解释**

1. 两种运行模式
   - client
   - server

![image-20220501161928318](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501161928318.png)

2. 三种运行模式

   - 解释器模式
   - 编译模式
   - 混合模式

3. 分层编译

   - 0层：解释执行
   - 1层：直接简单编译
   - 2层：**混合模式下,执行一段时间**, 性能监控，启动回边和方法计数器
   - 3层：**代码足够热后**,客户端编译器编译，开启全部性能监控，比如虚方法和分支跳转。
   - 4层：**代码足够热后,**服务器编译器编译.

   > 以上是不是固定升级的,但是默认是这种升级方式.并且这种升级方式是自动的,是**代码足够热**之后启动的升级过程.
   
   ![image-20220501173149455](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501173149455.png)
   
4. 上图可以看到0层可以直接干到4层，也可以有3层返回道1层。并且代码可能会经过多次的编译。

#### **编译的时机**

上面的分层编译体现了，字节码是如何一步一步走向编译的。

#### **热点代码与探测**

编译热点代码

- 被多次调用的方法
- 被多次执行的循环体 -- 称为**栈上替换**
- 分别使用了方法计数器和回边计数器进行热点代码的探测，**两种计数器是客户端模式的性能监控工具**。

![image-20220501173332210](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501173332210.png)

![image-20220501173422362](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501173422362.png)

- 循环成为热点代码的时候，会编译整个方法，但是只是循环部分执行编译的代码。此时在方法在栈上，所以称之为**栈上替换**。

![image-20220501174657851](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501174657851.png)

#### **编译过程**

![image-20220501173048549](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501173048549.png)



![image-20220501173538575](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501173538575.png)

- 可以注意到，这里说的是客户端的编译过程，而服务器的编译过程会更加激进。

### 特性

- 分层
- 适合长时间运行
- 平台独立性
- **激进优化**策略
- **链接优化**

![image-20220501172736919](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501172736919.png)

### 参数



#### **三种模式**

- 默认是混合模式
- `-Xint`使用的是interpreted 模式
- `-Xcomp` 使用编译模式

![image-20220501151718359](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501151718359.png)



1. 使用命令行参数进行
2. 不是永久设置的

****

#### 回边计数器设置

- -XX:OnStackReplacePercentage         # 栈上替换的阈值，默认是933，对应着回边计数器的阈值为933 * 1500 / 100 = 13995
- 服务器端的计算方式: (10000*140 - 33) / 100 = 10700其中140就是这个参数，InterpreterProfilePercentage=33

#### 方法计数器设置

- -XX:CompileThreshold=默认1500次
- -XX:-UseCounterDecay          # 关闭热度衰减
- -XX:CounterHalfLifeTime=5   # 半衰期时间，单位是秒

#### 运行模式

- `-client`
- `-server`







### 实践



```java
package compiler.JIT;
/*
*
*   参数:-XX:+UnlockDiagnosticVMOptions -XX:+PrintCompilation -XX:+PrintInlining
*
*   -XX:+PrintAssembly -XX:+UnlockDiagnosticVMOptions.
* */
public class testJIT {
    private static final int NUM = 15000;
    public static int doubleValue(int i) {
        for (int j = 0; j < 100000; j++);
        return i *= 2;
    }

    public static long calSum() {
        long sum = 0;
        for (int i = 1; i <= 100; i++) {
            sum += doubleValue(i);
        }
        return sum;
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUM;i++) {
            calSum();
        }
    }
}

```





![image-20220501181410425](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220501181410425.png)







2. 空循环不进行，因为给直接编译优化掉了。





## 提前编译器

### 两种实现方式

- 静态编译编译。
- 即时编译缓存。





## 后端编译优化



![image-20220430172034446](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220430172034446.png)



**数组边界检查消除**



c++ 执行以下代码

```c++
#include <iostream>
using namespace std;

int main() {
    
    int arr[100];
    cout << arr[101] << endl;
    return 0;
}
```



![image-20220430162205664](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220430162205664.png)

并不会报错，但是实际上，数组已经越界了。



java会有数组越界的检查，但是每一次访问都需要进行一次这样的判断。







## 前端编译

1. 异常机制在虚拟机层面是怎么实现的？
