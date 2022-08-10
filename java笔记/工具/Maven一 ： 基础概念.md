# Maven<一> ： 基础概念

## Maven POM

1. 项目的配置文件
   - 项目的构建信息
   - 

## Maven Plugin

maven执行，最后都是

- 项目的





## IDEA构建项目时候的初始化



1. 使用spring initializer意味着，你创建项目使用的**目录格式**是spring类型的。如果你要修改你项目的依赖，只需要修改pom.xml文件，不用担心依赖都是spring官网的。因为你可以自己修改。
2. 创建的时候可以不用加入依赖，然后然后自己修改，加上自己写的工厂、项目的依赖就行了。
3. spring initilizer的网址。`https:\\start.springboot.io`或者`https:\\start.aliyun.com`
4. 也可以手动创建，IDEA打开。网址`https:\\start.spring.io` 或者`https:\\start.springboot.io`
5. 当然自己也可以根据maven项目进行手动添加
   - 依赖项
   - 包的命名，主启动类在默认文件夹下







## 实战

### 编译springboot源码

- **删除以前的.m库**，一定要删除，因为有版本问题，还有就是如果你以前下载的库如果不完全，一定会导致编译出错。

- 导入idea

- `clean install -DskipTests -Pfast`从idead中执行以下代码, 点击下图然后输入,最后点击执行。

  ![image-20220503092303970](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220503092303970.png)

1. 出现`Failed to execute goal org.apache.maven.plugins:maven-compiler-plugin:3.8.1:testCompile `错误
   - 加入以下代码

```xml
<plugin>
    			<groupId>org.apache.maven.plugins</groupId>
    			<artifactId>maven-compiler-plugin</artifactId>
    			<version>3.8.1</version>
    			<configuration>
        			<source>1.8</source>
        			<target>1.8</target>
    			</configuration>
			</plugin>

```

2. 删除spring-tools/configration/test/java文件夹
3. 文件名称限制，文件名称太长，无法解压缩，应该从操作系统层面进行设置。
4. 出现graddle下载失败。**不是网速的原因，自己下载没问题，直接配置环境变量，不用maven自己下载了**
   - 自己下载对应的gradle版本
   - 解压
   - 配置全局的环境变量
   - cmd中测试使用gradle可以成功。



