# redis



lamp架构： linux + apache + mysql + php

lnmp架构：linux + nginx + mysql + php

专注于运维



下星期









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



参考文章

[centOS8安装](https://www.4spaces.org/centos-8-install-redis/)

[出现流错误STO的解法](https://stackoverflow.com/questions/70963985/error-failed-to-download-metadata-for-repo-appstream-cannot-prepare-internal)





安装`/etc/redis.conf`进行匹配



## 连接redis

```shell
redis-cli -h node2
auth 密码
```









## 高级



### 1. 持久化

1. rdb持久化
2. aof持久化



![image-20220910164031894](D:\blgs\source\imgs\image-20220910164031894.png)

![image-20220910170700617](D:\blgs\source\imgs\image-20220910170700617.png)

![image-20220910170720749](D:\blgs\source\imgs\image-20220910170720749.png)



### 2. 主从复制，读写分离



### 3. 分片集群



### 4. 哨兵



