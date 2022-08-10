# Git





## git 基础概念

- 工作树

- 分支 [分支的基本概念](https://blog.csdn.net/sxhelijian/article/details/122990084)

  - 分支就是一个副本，主要就是通过**拷贝和粘贴**实现的分工合作。
  - 分支切换就是切换工作目录
  - 分支合并，需要解决冲突
  - 注意不同层级的branch中的作用。

  ![image-20220430120822834](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220430120822834.png)

  

  - 下面的不同层级是逻辑上的概念，不是物理上真实存在的。是一种文件夹的命名规则。

  ![img](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/c54e71fd87b344e8bb8725329bd09147.png)

  - 分支采用了分治思想，初始的时候是一个空文件夹master，最后由不同的程序员逐渐完善之后，在进行合并，成为一个程序。

- 协作

  有了分支这个必杀技之后，就可以快乐的进行版本控制了。

  1. 首先创建一个开发分支。
  2. 在开发分支中写自己的代码，完成测试后，合并到主分支。
  3. 如果代码检测不合格，那么怎么办？删除分支，重新重原来的分支上进行操作。此时原分支相当于一个backup。
  4. 而git上面的master分支就是一个一个的版本。拉去不同的版本，也可以创建不同的master
  
  



## git基本语法



### 1. 帮助命令

帮助`git --help 命令名称`

比如`git --help remote`就会进入以下的帮助文档页面

![image-20220430113940967](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220430113940967.png)



- 直接使用git 列出常用的一些命令

![image-20220430114119940](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220430114119940.png)



### 2. 开启工作区域

- `git clone + 地址`：下载仓库到本地 
- `git init`: 将本地的仓库初始，也就是创建一个.git文件,如果没有可能被隐藏了。



### 3. 关联远程仓库

- `git remote add 别名 master 仓库地址`： 把本地文件夹和远程仓库进行关联
- `git remote -v` : 列出所关联的仓库地址
- `git remote rm 别名` ：删除所关联的仓库地址

### 4. 通知git新增文件

- `git add .`			： 将所有新增的文件添加
- `git status`      ：产看新增文件的状态
- `git remove .` : 将所有新增的文件去掉
- `git restore` :  没搞懂啥意思
- `git mv 原文件名 目标文件名` :文件重命名

### 5.提交 -- git 将文件加入目录树

- `git commit`：将新增文件进行提交
- `git merge` :合并提交



### 6. 上传、下载

- `git push 远程仓库别名 master` :将所有的文件同步到远程仓库中
- `git pull 远程仓库别名 master`: 从远程仓库里拉取内容，并和远程仓库一起协作
- `git fetch 远程仓库的别名`: 只是从远程仓库中拉去内容，不进行协作。

### 7. 文件的分支操作

具体分支的讲解请看

 [分支的基本概念](https://blog.csdn.net/sxhelijian/article/details/122990084)



### 8. git 配置

- git config 
  - git config core.longpaths true # 允许使用长文件名。 Filename too long“问题解决





## IDEA配置git

### 1. IDEA使用分支操作

1. 如下图所示

![image-20220505091324869](https://gitee.com/tobewin3/picgo-home/raw/master/imgs/image-20220505091324869.png)





2. 选择之后有一个checkout操作。



## git贡献自己的源码





## 使用github / gitee实战

- 创建JavaBase:
  - JVM
  - 多线程
  - java语法基础
  - 设计模式
  - 坦克大战源码
- 创建JavaWEB仓库
  - 书城
  - 博客系统
  - 智能监控系统
- 创建Algorithm：
  - leetcode
  - acwing
  - 牛客

- 搭建博客网站



