# maven 2 -- maven基本使用



## Maven 配置



### IDEA配置默认Maven



- 打开idea
- `file->other settings->settings for new projects`

![在这里插入图片描述](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/0c067067d84244b0bee84587e572e0a1.png)

- 修改下面三项
![在这里插入图片描述](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/e219d831ca9d4c7cb98c9252be65eb4d.png)



### Maven 仓库配置

#### 配置国内镜像

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

#### 配置.m文件的位置

默认的.m文件是放在c盘的，修改成别的盘，因为放在c盘容易把c盘给占满，不是特别好。

`config->setting.xml`搜索`repository`修改成其他的位置比如:D:/.m/repository。

