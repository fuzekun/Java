# maven实战



## 一、 maven编译springboot源码

### 0. 环境

- jdk1.8
- maven 3.6.0
- gradle 4.10.3 (建议下载，后面说为什么)[下载地址](https://downloads.gradle-dn.com/distributions/gradle-4.10.3-bin.zip)
- IDEA 2019专业版
- Win10 + 机械硬盘(后面说为什么)
- springboot源码2.2.5-release版本

### 1. 源码国内下载

- 下载源码，建议使用gitee进行下载，建议下载压缩包。[下载地址](https://gitee.com/humaolin2021/spring-boot.git)
  在下面选择`2.2.5-release`版本进行下载zip文件



![image-20220503233137022](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220503233137022.png)

- 当然可以使用命令行。新建一个文件，然后命令行`git clone https://gitee.com/humaolin2021/spring-boot.git`

### 2. 配置maven



#### 2.1 配置国内镜像



`maven->config->settings.xml`文件夹，搜索`mirrors`，复制下面代码进去。

- 这些是国内的镜像，还有rep的中心仓库，足够编译springboot源码了。


```java
<!--配置阿里镜像-->
   <mirror>
     <id>nexus-aliyun</id>
     <mirrorOf>central</mirrorOf>
     <name>Nexus aliyun</name>
     <url>http://maven.aliyun.com/nexus/content/groups/public</url>
    </mirror>

    <mirror>
      <id>huaweicloud</id>
      <name>华为云 maven</name>
      <mirrorOf>*</mirrorOf>
      <url>https://mirrors.huaweicloud.com/repository/maven/</url>
    </mirror>

    <mirror>
      <id>nexus-tencentyun</id>
      <mirrorOf>*</mirrorOf>
      <name>腾讯云</name>
      <url>http://mirrors.cloud.tencent.com/nexus/repository/maven-public/</url>
    </mirror>
    <mirror>
      <id>repo2</id>
      <mirrorOf>central</mirrorOf>
      <name>中心仓库</name>
      <url>http://repo2.maven.org/maven2/](http://repo2.maven.org/maven2/</url>
    </mirror>
    <mirror>
      <id>ui</id>
      <mirrorOf>central</mirrorOf>
      <name>ui</name>
      <url>http://uk.maven.org/maven2/](http://uk.maven.org/maven2/</url>
  </mirror>

```

#### 2.2 配置.m文件的位置

默认的.m文件是放在c盘的，修改成别的盘，因为放在c盘容易把c盘给占满，不是特别好。

`config->setting.xml`搜索`repository`修改成其他的位置比如:D:/.m/repository。



### 3. 配置gradle

- 配置gradle的环境变量

> 这个完全是因为下载过程中会出现超时错误，所以使用全局的gradle进行编译就不会因为maven下载不了gradle而出现错误。



### 4. 编译springboot源码

#### 4.1 删除原来的.m库

**删除以前的.m库**，一定要删除，因为有版本问题，还有就是如果你以前下载的库如果不完全，一定会导致编译出错。

#### 4.2 导入idea

- 关闭原来的项目
- 如下图导入下载好的springboot，选好jdk的版本，其他默认就行了。

![image-20220503232355456](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220503232355456.png)



#### 4.3 修改idea的maven



- idea配置自己的maven。
  - `setting`中搜索`maven`
  - 然后修改下面这三项

![image-20220503233053536](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220503233053536.png)

- 修改maven的参数信息
  - maven的jdk对应的版本要正确
  - 还有`Xmx:`设置的是占用内存，可以尽可能大一点，方便快点编译，官网上说建议512m。如果内存够用设置1024m。

![image-20220503233021492](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220503233021492.png)





#### 4.4 修改配置文件

- 修改配置文件, 在根目录的`pom.xml`中把`properties`修改成下面的内容

```xml
<!--下面的内容是为了跳过test而设置的，如果不跳过，很可能报错-->
<!--另外修改版本号，完全是为了方便引入自己的项目的时候，不和官方的冲突-->
	<properties>
		<revision>2.2.5.RELEASE-MY</revision>
		<main.basedir>${basedir}</main.basedir>
		<disable.checks>true</disable.checks>
		<skipTests>true</skipTests>
	</properties>
```

- 修改项目的jdk版本,**一定要去掉这个对号，看看后面的内容，jdk9+**



![在这里插入图片描述](https://img-blog.csdnimg.cn/e518de1301dc4a1d915d6bfb554c7ed4.png)



#### 4.5 执行编译命令  

- `clean install -DskipTests -Pfast`从idea中执行以下代码, 点击下图然后输入,最后点击执行。



  ![image-20220503092303970](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/c3c83fe57fb32b7fa1700d5621911687.png)



- 最后，等待几分钟，就成功了。



![在这里插入图片描述](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/6f435e557d9a481c84e82cf199860891.png)







### 5. 测试验证

#### 5.1 创建项目

1. 创建一个新的spring initializer项目 [从这创建](https://start.springboot.io/)

![image-20220504083852579](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504083852579.png)



最好不要使用idea创建，因为使用idea很慢，并且很容易失败。就是demo.zip下载不下来，下载下来一个.mvn文件和src文件。



还有有的博客说，直接创建model，非常非常不建议。因为一旦model创建失败，可能会把原来编译好的东西搞毁。





#### 5.2 导入项目

- 先关闭原来的

![image-20220504084300565](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504084300565.png)





- 然后直接导入



![image-20220504084332445](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504084332445.png)







#### 5.3 项目导入自己编译好的springboot源码

- maven引入源码，按照`maven-> + -> springboot根目录的 -> pom.xml -> ok` 即可

![image-20220504084455649](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504084455649.png)





- 配置刚创建项目的`pom.xml`文件, 把出现sprinboot的地方都加上version，然后version改成自己的修改配置文件的名字。

![image-20220504084707065](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504084707065.png)



#### 5.4 测试

- 然后测试可不可以主启动

![image-20220504085133138](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504085133138.png)

- 在使用`ctrl + 点击`跳转源码，就可以自己修改源代码了。

![image-20220504085234134](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504085234134.png)





### 6. 错误总结

我发生了以下几类错误

- 系统错误
- maven导入依赖错误
- jdk的版本错误
- 配置文件错误
- 验证的时候出现的问题

#### 6.1 系统错误

1. 文件名称限制，文件名称太长，无法解压缩，应该从操作系统层面进行设置，暂时没找到解决办法，可能是因为**硬盘的限制**，所以建议使用机械因硬盘。因为使用的固态，没法解压。也可能是**Win11的系统限制**
2. 网速问题，配置了国内镜像，一般下载依赖很快的，所以**强烈建立以上步骤不要省略**。

3. Exception in thread "main" java.net.ConnectException: Connection timed out: connect...错误，就是依赖下载失败，找个网速好的地方，重新下载、

#### 6.2 maven依赖错误

1. 出现graddle下载失败。**不是网速的原因，自己下载没问题，直接配置环境变量，不用maven自己下载了**
   - 自己下载对应的gradle版本
   - 解压
   - 配置全局的环境变量
   - cmd中测试使用gradle可以成功。


3. 各种org.类找不到。
   - 兄弟别为难自己了，直接删除.m库，重新下载吧。
   - 网上各种删文件夹，加入依赖项，都没什么道理的。就是因为你的仓库不对，所以才会报这种错误。修改写好的项目代码，真的很容易出各种问题。

#### 6.3 jdk版本错误

就是报的什么compiler错误。看其他博客说在pom中添加pugin指定编译器。确实有用，但是有用一次。所以还是使用IDEA老老实实的指定全局的吧。



#### 6.4 配置文件错误

修改pom.xml文件，因为跳过test，在发布的时候是默认false的，而不跳过就很容易出错。



#### 6.5 创建spring 项目错误



错误原因已经说了，就是IDEA不行。所以建议手动创建。当然如果页面房访问不了，可以创建maven项目，手动修改成spring的类型就行了。













## 二gradle 编译spring 源码



gradle构建好spring源码

![image-20220504095848356](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220504095848356.png)































