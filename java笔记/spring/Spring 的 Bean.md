# Spring 的 Bean

- ApplicationContext是beanFactory的高级。是装饰器模式的中的装饰后的类。
- 具有更多的内容



![image-20220711185652679](D:\blgs\source\imgs\image-20220711185652679.png)

## ApplicationContext的作用：

### 1. 作用

- **是用户bean工厂的工厂**，负责构建用户的beanfactory的factory。

- 定义bean的数据结构，相当于**类加载**。最后的结果是给容器中提供一个Map:<类名，BeanDefination>
- 初始化beanFactory，这个factory用于创建bean的实例，同时会**限定bean创建的过程**，**是一个模板方法**。

有了bean工厂之后，就可以创建bean了。

- 使用getBean()，其实是调用的beanFactory.getBean()方法。



### 2. 核心方法

1. register(cofigration): 定义一个bean的数据结构。相当于类加载的过程。
2. refresh()：
   - 定义一个beanFactory的, 把上一步处理的BeanDenation方到beanFactory()里面。
   - 同时定义bean创建的前后处理。
   - **是一个模板方法**
3. getBean(）: 相当于对象的创建的过程，因为最后调用了`AbstractAutowireCapableBeanFactory.doCreateBean`这个方法。
4. finalizeBeanFactory(beanFactory) : 调用了getBean()方法。



**注意在初始化context的时候，就进行了bean的对象创建。因为初始化的时候就调用了getBean方法。**

- 对于单例对象，getBean()就是对象的唯一创建的过程。
- 一般创建的都是单例的，如下图所示。

![image-20220504201316475](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504201316475.png)

P

![image-20220711185644129](D:\blgs\source\imgs\image-20220711185644129.png)



## bean的生名周期

略，还没学到..



## spring中上下文获取bean的过程

1. 通过Context.register(配置信息)定义bean的数据结构信息。
2. 通过Context.refresh(),定义bean工厂，里面规定了bean的创建过程。包括了beanFactory的前后处理都在这里。
3. 通过beanFactory.getBean(类名)方法创建了bean对象。这个getBean()是在初始化Context的时候进行的。
4. 用户通过Context.getBean()获取beanFactory()创建的单例对象。





## 使用设计模式

两个设计模式：灵活，扩展，复用，维护

- 工厂模式，是为了面向切面编程，也就是在创建对象之前，注册前置处理器和后置处理器。
  - 可以控制对象的生产流程
  - 也就是面向切面编程。
- 模板方法，创建BeanFactory的时候，规定了bean创建的过程，规定了应该怎么组装各种处理和bean的关系。
  - 步骤是死的，但是每一步的细节处理，是有区别的，具体来说，监听和前后处理是具体顺序，但是每一步的应该怎么处理，是由用户决定的。这样更容易扩展，更加灵活。
  - 举个例子来说，就是做饭，有三步是共同的，都是把先处理，在做熟，最后盛出来。但是不同的菜有不同的做法。
  - 这样就是实现了抽象和实现的解耦。更加容易复用和维护，也更灵活。
- 单例模式
  - 减少了大对象的创建，提高了性能。
  - 更加容易管理和维护，对象归容器处理，不会因为用户的错误导致大量没法回收的垃圾。