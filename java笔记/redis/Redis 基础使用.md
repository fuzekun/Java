# Redis 基础使用



## Redis简介

了解 Redis 开源项目



1. redis的数据结构以及Java语言接口
2. redis的高级特性。持久化，主从复制，分片集群，读写分离等。
3. redis的高级使用

Redis 是一个开源（BSD 许可）的内存**数据结构存储**，用作数据库、缓存、消息代理和流引擎。Redis 提供[数据结构](https://redis.io/docs/data-types/)，例如 [字符串](https://redis.io/docs/data-types/strings/)、[散列](https://redis.io/docs/data-types/hashes/)、[列表](https://redis.io/docs/data-types/lists/)、[集合](https://redis.io/docs/data-types/lists/)、带范围查询的[排序集合、](https://redis.io/docs/data-types/sorted-sets/)[位图](https://redis.io/docs/data-types/bitmaps/)、[超日志](https://redis.io/docs/data-types/hyperloglogs/)、[地理空间索引](https://redis.io/docs/data-types/geospatial/)和[流](https://redis.io/docs/data-types/streams/)。Redis 内置了[复制](https://redis.io/topics/replication)、[Lua 脚本](https://redis.io/commands/eval)、[LRU 驱逐](https://redis.io/topics/lru-cache)、[事务](https://redis.io/topics/transactions)和不同级别的[磁盘持久性](https://redis.io/topics/persistence)，并通过[Redis Sentinel](https://redis.io/topics/sentinel)和[Redis Cluster](https://redis.io/topics/cluster-tutorial)自动分区提供高可用性。

您可以 对这些类型运行**原子操作，例如**[附加到字符串](https://redis.io/commands/append)； [增加哈希值](https://redis.io/commands/hincrby)；[将元素推入列表](https://redis.io/commands/lpush)；[计算集交](https://redis.io/commands/sinter)[、](https://redis.io/commands/sunion)并 、[差](https://redis.io/commands/sdiff)；或[获取排序集中排名最高的成员](https://redis.io/commands/zrange)。

为了达到最佳性能，Redis 使用 **内存中的数据集**。根据您的用例，Redis 可以通过定期[将数据集转储到磁盘](https://redis.io/topics/persistence#snapshotting) 或[将每个命令附加到基于磁盘的日志](https://redis.io/topics/persistence#append-only-file)来持久化您的数据。如果您只需要一个功能丰富的网络内存缓存，您也可以禁用持久性。

Redis 支持[异步复制](https://redis.io/topics/replication)，具有快速非阻塞同步和自动重新连接以及网络拆分上的部分重新同步。

Redis 还包括：

- [交易](https://redis.io/topics/transactions)
- [发布/订阅](https://redis.io/topics/pubsub)
- [Lua 脚本](https://redis.io/commands/eval)
- [生命周期有限的密钥](https://redis.io/commands/expire)
- [LRU 驱逐密钥](https://redis.io/topics/lru-cache)
- [自动故障转移](https://redis.io/topics/sentinel)

[您可以从大多数编程语言](https://redis.io/clients)中使用 Redis 。



## 安装redis



**方法一:直接使用yum 安装**

参考文章

[centOS8安装](https://www.4spaces.org/centos-8-install-redis/)

[出现流错误STO的解法](https://stackoverflow.com/questions/70963985/error-failed-to-download-metadata-for-repo-appstream-cannot-prepare-internal)

[yum无法更新] Failed to download metadata for repo ‘AppStream‘: xxxx

```
cd /etc/yum.repos.d/
sed -i 's/mirrorlist/#mirrorlist/g' /etc/yum.repos.d/CentOS-*
sed -i 's|#baseurl=http://mirror.centos.org|baseurl=http://vault.centos.org|g' /etc/yum.repos.d/CentOS-*
yum update -y

等待之后

wget -O /etc/yum.repos.d/CentOS-Base.repo https://mirrors.aliyun.com/repo/Centos-vault-8.5.2111.repo
yum clean all
yum makecache
```

安装`/etc/redis.conf`进行匹配



**方法二:源码安装**

[redis安装官方文档](https://redis.io/docs/getting-started/installation/install-redis-from-source/)

1. 下载源文件
2. 编译安装
3. **配置软连接(不必要)**
4. **使用systemctl进行redis控制启停(很有必要)**

首先，新建文件`/etc/systemd/system/redis.service`里面放入下面的内容

```xml
[Unit]
Description=Redis
After=network.target

[Service]
Type=simple														
ExecStart=redis-server的地址 redis.conf的地址
ExecReload=/usr/local/bin/redis-server -s reload
ExecStop=/usr/local/bin/redis-server -s stop
PrivateTmp=true

[Install]
WantedBy=multi-user.target
```

其次，运行`systemctl daemon-reload`

最后，systemctl start redis， systemctl enable redis 设置开机自启动。



## 配置redis

1. 配置密码	`requirepass`
2. 配置备份
3. 配置远程连接

## 启动redis

1. 默认启动`redis-server`
2. 使用配置文件启动`redis-server 配置文件名称`
3. 开机自启动`systemctl`



## 连接redis

```
redis-cli -h node2
auth 密码
```







# 基础语法







# 事务







# Spring 整合Redis



[spring redis官方文档](https://spring.io/projects/spring-data-redis)



1. 所谓整合，也就是写一些代码，将一些其他组件的功能可以被java调用。
2. 所谓redis整合，就是把redis提供的接口，键值的获取、持久化等功能使用spring代码调用，相当于jdbc，用于java来访问数据库这种。
3. 想要深入了解一门语言，那么这些访问其他组件的代码，是很有必要进行学习的，这就是所谓的源码阅读。





## demo



1. 改pom, 加入redis依赖,版本可以不需要写，由spring的父工程控制。

```xml
<!-- https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-redis -->
<!--整合redis-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>

```

2. 配配置类

```java
package com.fuzekun.demo1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();
        return template;
    }

}

```



3. 做测试

```java
package com.fuzekun.demo1.utils;

import com.fuzekun.demo1.Demo1Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = Demo1Application.class)
public class RedisTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testStrings() {
        String redisKey = "test:count";

        redisTemplate.opsForValue().set(redisKey, 1);

        System.out.println(redisTemplate.opsForValue().get(redisKey));
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    @Test
    public void testHashes() {
        String redisKey = "test:user";

        redisTemplate.opsForHash().put(redisKey, "id", 1);
        redisTemplate.opsForHash().put(redisKey, "username", "zhangsan");

        System.out.println(redisTemplate.opsForHash().get(redisKey, "id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey, "username"));
    }

    @Test
    public void testLists() {
        String redisKey = "test:ids";

        redisTemplate.opsForList().leftPush(redisKey, 101);
        redisTemplate.opsForList().leftPush(redisKey, 102);
        redisTemplate.opsForList().leftPush(redisKey, 103);

        System.out.println(redisTemplate.opsForList().size(redisKey));
        System.out.println(redisTemplate.opsForList().index(redisKey, 0));
        System.out.println(redisTemplate.opsForList().range(redisKey, 0, 2));

        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
    }

    @Test
    public void testSets() {
        String redisKey = "test:teachers";

        redisTemplate.opsForSet().add(redisKey, "刘备", "关羽", "张飞", "赵云", "诸葛亮");

        System.out.println(redisTemplate.opsForSet().size(redisKey));
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }

    @Test
    public void testSortedSets() {
        String redisKey = "test:students";

        redisTemplate.opsForZSet().add(redisKey, "唐僧", 80);
        redisTemplate.opsForZSet().add(redisKey, "悟空", 90);
        redisTemplate.opsForZSet().add(redisKey, "八戒", 50);
        redisTemplate.opsForZSet().add(redisKey, "沙僧", 70);
        redisTemplate.opsForZSet().add(redisKey, "白龙马", 60);

        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        System.out.println(redisTemplate.opsForZSet().score(redisKey, "八戒"));
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey, "八戒"));
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey, 0, 2));
    }

    @Test
    public void testKeys() {
        redisTemplate.delete("test:user");

        System.out.println(redisTemplate.hasKey("test:user"));

        redisTemplate.expire("test:students", 10, TimeUnit.SECONDS);
    }

    // 多次访问同一个key
    @Test
    public void testBoundOperations() {
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    // 编程式事务
    @Test
    public void testTransactional() {
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                String redisKey = "test:tx";

                operations.multi();

                operations.opsForSet().add(redisKey, "zhangsan");
                operations.opsForSet().add(redisKey, "lisi");
                operations.opsForSet().add(redisKey, "wangwu");
                
                // 事务完成之前进行询问会查询不到
                System.out.println(operations.opsForSet().members(redisKey));

                return operations.exec();
            }
        });
        System.out.println(obj);
    }

}

```

![image-20221121203720148](http://qiniu.fuzekun.top/image-20221121203720148.png)





## 写功能

> **具体的功能请参照，牛客博客教程。**

## 常用配置



配置登录的结点和登录的端口以及登录密码。

使用`RedisConnectionFactory`创建`RedisConnection`，然后把这个连接传入template 中方便后续的使用。

**官方文档上说使用工厂创建的连接线程安全，并且可以被实例共享。那么就是说，是线程安全的单例模式(个人猜测)。**



1. 导入jedis依赖

```xml
<dependencies>

  <!-- other dependency elements omitted -->

  <dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>4.3.1</version>
  </dependency>

</dependencies>
```



2. 编写配置类，获取连接工厂

```java
@Configuration
class RedisConfiguration {

  @Bean
  public JedisConnectionFactory redisConnectionFactory() {
															// 设置域名和端口
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("server", 6379);
    config.setPassword("fdafda");							// 设置密码
    return new JedisConnectionFactory(config);
  }
}
```



3. 将工厂创建的连接注入到模板配置中。仍旧是上面的配置类

```java
package com.fuzekun.demo1.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
public class RedisConfig {


    /**
     * Jedis
     */
//    @Bean
//    public RedisConnectionFactory jedisConnectionFactory() {
//        RedisSentinelConfiguration sentinelConfig = new RedisSentinelConfiguration()
//                .sentinel("node0", 6379);
//        sentinelConfig.setPassword("1230");
//        return new JedisConnectionFactory(sentinelConfig);
//    }

    // 创建工厂
    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("node0", 6379);
        config.setPassword("1230");
        return new JedisConnectionFactory(config);
    }
    // 将工厂注入到factory中
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);

        // 设置key的序列化方式
        template.setKeySerializer(RedisSerializer.string());
        // 设置value的序列化方式
        template.setValueSerializer(RedisSerializer.json());
        // 设置hash的key的序列化方式
        template.setHashKeySerializer(RedisSerializer.string());
        // 设置hash的value的序列化方式
        template.setHashValueSerializer(RedisSerializer.json());

        template.afterPropertiesSet();
        return template;
    }

}

```



关于工厂模式的思考：为什么要用工厂模式？

1. 控制对象创建的工序，方便对象的管理。
2. 将对象和控制解耦，增加了灵活性，方便对象类的扩展。
3. 使用配置文件，解决了对象的选择问题。

为什么使用抽象工厂模式? 定义了产品一族。





# Redis面试问题总结<一>



1. redis常用的场景：异步，削峰，消息队列。
2. redis的功能举例：点赞，关注，评论，
3. http缓存、验证码缓存、cookie的缓存。比如用户凭证的缓存，用户信息的缓存(cookies缓存)。
3. **spring整合redis，spring连接redis的过程，spring关于redis的各种是如何设计的？**
3. **spring整合mybatis，以及spring 整合jdbc的流程。**

