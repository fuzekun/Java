# Java Web 概述



## 1.  Java Web是什么

1. java web使用java语言，开发服务器端的开发
2. 可以开发一个旅游网站 + 书城网站 + 各种其他的系统
3. 数据库 + 前端开发 + 服务器端开发 + 运维
4. 后续框架 SSM + springboot + springcloud 
5. 中间件 + 容器 + 业务解决方案
   1. redis
   2. kafka
   3. es
   4. docker
6. 基础：
   1. 网络基础
   2. css + js + html
   3. servlet + jsp + filter + listenner
   4. 数据库基础



## 2. 进阶篇

1. 业务解决方案
   1. 权限系统
   2. 报表系统
   3. 秒杀系统
   4. 工作流的系统
2. 实际企业中使用知识去落地
3. 思想层面的东西 ：
   1. 计算机底层
   2. 高级的架构





## 3. JQuery



1. 是一个js框架，简化js的文档处理 + 事件处理 + 动画处理 + ajax交互

2. 最简单的、自定义的jQuery框架，优化dom操作

```js
function get(id) {
    var obj = document.getElementById(id);
    return obj;
}

下面调用
<script>
	var div1 = get("div");
	alert(div1);
</script>>
```



## 4. ajax

1. ASynchornized Javascript And XML 异步的javascript和XML。
2. 什么是异步呢？对于IO的异步阻塞使用的是什么意思呢？
3. 怎么实现异步。