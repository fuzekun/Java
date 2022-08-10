# 责任链模式



## 1. 背景

- 需求: 实现过滤
- 需求2:   直接写好，不用修改的那种的过滤，就是可以从配置文件中读取的过滤器，然后加入到过滤器队列中。
- 需求3：可以在决定在哪里停止过滤，不用进行下一步的过滤。
- 需求4：在去的时候进行一个过滤，在回来的时候进行另一个过滤。



## 2.实现



[代码请看具体的四个文件](https://gitee.com/tobewin3/java-base/tree/master/deisinModel/src/chainOfResponsibility)



## 3. 实际应用

### 1. 过滤器



- 实现1: 使用数组实现顺序表
  - 使用递归调用
  - fc可以说是next指针，指向下一个需要执行的filter。
  - 此处的fc是使用index进行实现的
- 优点分析
  - 控制的流程放在了filterChain里面。
  - 这样就算用户没有设置next,filterChain也可以保证安全。
- 细节
  - index--；实现可以重复使用过滤器
  - 把filterChain改成 filter next更容易理解

```java
package chainOfResponsibility;


import java.util.ArrayList;
import java.util.List;

/*
*
*   经典servlet的实现版本
* */
public class FilterChainIV {

    public static void main(String[] args) {
        MyFilterChain fc = new MyFilterChain();
        fc.add(new MyHtmlFilter()).add(new MySensitiveFilter());
        MyResponse res = new MyResponse();
        MyRequest req = new MyRequest();
        fc.doFilter(req, res, fc);
        System.out.println("req = " + req.msg);
        System.out.println("resp = " + res.msg);
        fc.doFilter(req, res, fc);
        System.out.println("req = " + req.msg);			// 进行二次过滤，或者进行别的过滤。
        System.out.println("resp = " + res.msg);
    }
}

class MyRequest {
    String msg = "";
}

class MyResponse {
    String msg = "";
}

interface MyFilter {
    public void doFilter(MyRequest req, MyResponse resp, MyFilter next);
}

class MyFilterChain implements MyFilter{
    List<MyFilter>filters = new ArrayList<>();
    int index = 0;

    public MyFilterChain add(MyFilter filter) {
        filters.add(filter);
        return this;
    }

    @Override
    public void doFilter(MyRequest req, MyResponse resp, MyFilter next) {
        if (index >= filters.size()) return ;
        MyFilter filter = filters.get(index);
        index++;
        filter.doFilter(req, resp, next);
        index--;                    // 进行回溯，否则没法进行重新过滤了
    }
}

class MyHtmlFilter implements MyFilter{

    @Override
    public void doFilter(MyRequest req, MyResponse resp,  MyFilter next) {
        req.msg += "Req.htmlFilter-->";
        next.doFilter(req, resp, next);           // 如果从这里不进行递归调用，就不进行下一步处理了
        resp.msg += "Resp.htmlFilter-->";
    }
}
class MySensitiveFilter implements MyFilter{
    @Override
    public void doFilter(MyRequest req, MyResponse resp, MyFilter next) {
        req.msg += "Req.sensitiveFilter-->";
        next.doFilter(req, resp, next);             // 有用户自己决定是否进行下一步的调用
        resp.msg += "Resp.sensitiveFilter-->";
    }
}

```





- 实现2：使用链表实现顺序表

```java
package chainOfResponsibility.v5;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;

/*
*
*   使用链表直接实现FilterChain
*
* 属性, Filter next
*   除了RuntimeException与其子类，以及错误（Error），其他的都是检查异常（绝对的大家族）。
*
*
* */
public class FilterChianI {

    public static void main(String[] args) {
        Filter htmlFilter = new HtmlFitler();
        htmlFilter.setNext(new SesitiveFilter());
        Requeset req = new Requeset();
        Response res = new Response();
        htmlFilter.doFilter(req, res);
        System.out.println(req.msg);
        System.out.println(res.msg);
    }
}


abstract class Filter {
    private Filter next;
    public Filter setNext(Filter next){
        this.next = next;
        return this;
    }
    public abstract void doFilter(Requeset req, Response resp);
    public Filter getNext() throws NullPointerException{
        if (this.next != null) return this.next;
        else throw new NullPointerException("没有设置next!!!请设置在调用");
    }
}
class Requeset {
    String msg = "";
}
class Response {
    String msg = "";
}

class HtmlFitler extends Filter {

    @Override
    public void doFilter(Requeset req, Response resp) {
        req.msg += "Req.htmlFilter->";
        Filter nx = super.getNext();
        nx.doFilter(req, resp);
        resp.msg += "Resp.htmlFilter->";
    }
}

class SesitiveFilter extends Filter {
    @Override
    public void doFilter(Requeset req, Response resp) {
        req.msg += "Req.sensitiveFilter->";
        //getNext().doFilter(req, resp);      // 报错
        resp.msg += "Resp.sensitiveFilter->";
    }
}
```

- 此处设置如果没有设置next就抛出一个空指针异常，提醒用户只有在设置了之后才可以进行next的调用。
- 把Filter设置成一个链表，next就是Filter类型
- Fitler是一个抽象类。



3. **类图**

![image-20220515210028155](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220515210028155.png)





### 2. 日志记录

需求: 我们创建抽象类 *AbstractLogger*，带有详细的日志记录级别。然后我们创建三种类型的记录器，都扩展了 *AbstractLogger*。每个记录器消息的级别是否属于自己的级别，如果是则相应地打印出来，否则将不打印并把消息传给下一个记录器。



- [菜鸟教程示例](https://www.runoob.com/design-pattern/chain-of-responsibility-pattern.html)和第二种实现过滤器完全相同的思路，就不重复代码了。

- 类图（来自菜鸟教程）
- ![image-20220515210201477](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220515210201477.png)



## 4. 责任链模式分析

### 1. 定义 

顾名思义，责任链模式（Chain of Responsibility Pattern）为请求创建了一个接收者对象的链。这种模式给予请求的类型，对请求的发送者和接收者进行解耦。这种类型的设计模式属于行为型模式。

在这种模式中，通常每个接收者都包含对另一个接收者的引用。如果一个对象不能处理该请求，那么它会把相同的请求传给下一个接收者，依此类推。

### 2. 类图

### 3. 优缺点分析

- 优点
  - 允许动态的增加和删除职责，且由用户决定是否执行某种操作。
  - 实现了被操作对象和操作的解耦。
- 缺点
  - 系统复杂度提高，容易出错
  - 影响性能，出现递归调用，容易造成栈溢出

### 4. 适用场景

- 由用户确定实现怎样的处理的流程。
- 不确定是否需要添加这种处理的情况。
- 把任务分解，大的任务分解成小步进行处理。


