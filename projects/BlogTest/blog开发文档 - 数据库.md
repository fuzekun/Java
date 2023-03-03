

# blog开发文档 - 数据库



- 创建数据库名为 blogtest

  ```mysql
  create database blogtset;
  ```



## 一.表的创建以及完整性

### 1.用户表的创建



```mysql
drop table if exists `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```



### 2.文章表的创建

```mysql
 drop table if exists `article`;
 CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(100) DEFAULT NULL,
  `comment_count` int(11) NOT NULL,
  `content` text,
  `created_date` datetime DEFAULT NULL,
  `describes` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `authorid` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `categoryIndex` (`category`),
  KEY `authorid` (`authorid`)
);
```



### 3.标签表的创建

```mysql
drop table if exists `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);
```



### 4.评论表的创建

```mysql
 drop table if exists `comment`;
 CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  foreign key (`article_id`) references article(`id`),
  foreign key (`user_id`) references user(`id`)
);
```

### 5.文章标签表

```mysql
 drop table if exists `article_tag`;
 CREATE TABLE `article_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  foreign key(`article_id`) references article(`id`),
  foreign key(`tag_id`) references tag(`id`)   
);
```

### 6.成就表

```mysql
drop table if exists `archive`;
 CREATE TABLE `archive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `article_title` varchar(255) DEFAULT NULL,
  `month` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`),
   foreign key (`article_id`) references article(`id`) 
);
```

筛选出每月点击量最高的文章放入表中。

### 7.登陆表

- 记录登陆的时间,放入登陆口令，用于免密登录

```mysql
CREATE TABLE `login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expired` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `ticket` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
   foreign key (`user_id`) references user(`id`)
);
```



## 二.数据增删查改及功能

### 1.文章查找

#### (1)用户查找自己的文章

```mysql
select * from article where author_id = @id;
```

#### (2)根据文章id查找文章

```mysql
select * from aritlce where id = @id;
```

#### (3)根据文章类型查找文章

```mysql
select * from article where category = @article_category;
```

#### (4）根据文章时间查找文章

```mysql
select * from article where created_date like %@date%
```

### 2.文章增加

```mysql
insert into article values ();
```

### 3.用户查找

#### (1)根据用户id

```mysql
select * from user where id = id;
```

#### (2)根据用户的名称

```mysql
select * from user where name = @name
```

### 4.用户登陆表增加

```mysql
insert into login_ticket values();
```

### 5.用户增加

```mysql
insert into user values();
```

### 6.用户删除

```mysql
delete from user where id = @id;
```

### 7.文章删除

#### (1)根据id

```mysql
delete from article where id = ?
```

#### (2)根据类型

```mysql
delete from article where category like %%
```

### 8.其他

#### (1)标签表

#### (2)成就文章表







## 三.索引的创建

### 1.表默认主外键自动创建索引

- 创建表的时候默认有根据主键的索引和根据外键的索引

  例如:

```mysql
show index from artcile
```

![image-20191212182617114](C:\Users\付泽坤\AppData\Roaming\Typora\typora-user-images\image-20191212182617114.png)

可以看出，根据主键和id 和外键authorid创建了索引，同时NOT NULL 的category也创建了索引。

### 2.文章根据时间创建索引

- 由于频繁根据文章类型查找文章所以建议category索引。
- 由于会经常备份文章所以需要建立文章时间的索引。

```mysql
create index tm on article(created_date);
create index ct on article(category);
```

### 3.创建时间和id的符合索引

- 由于每月会进行人们文章归档

```mysql
create index comp on article(id, created_date);
```

### 4.创建类型和id的复合索引

- 由于会根据文章类型查看文章，并且会根据id进行分页显示，所以创建了如下的索引.

```mysql
create index compt on article(id, category);
```





## 四.视图的创建

- 由于博客系统中文章的属性大部分人可见，其实建立视图意义不大
- 另外视图解析的两种算法，都会带来额外的开销
- 权限并不多级

所以本系统视图建立完全浪费，但是必须有组件，就只能画蛇添足。



### 1.文章视图

```mysql
drop view if EXISTS artcl;
create view artcl as 
	select category, content, created_date,
	describes, title
	from article;
```

### 2.文章标签视图

```mysql

#创建用于显示tag和文章信息的视图
drop view if EXISTS articleTag;
create view articleTag AS
	select category, content, created_date,
	describes, title, tag.name
	from article, article_tag, tag
  where article.id = article_tag.article_id 
		and tag.id = article_tag.tag_id;
```



## 五.触发器

触发器有一下特点

- 触发器开销大，可能一条插入语句会更新张表
- 触发器容易出错，由于触发器对用于隐藏，所以可能会出现莫名奇妙的禁止操作

所以应该少建立触发器，尽量不建立触发器。



### 1.删除文章的时候级联更新标签和评论(标签数目减一，评论级联删除)

```mysql
CREATE TRIGGER delArticle AFTER DELETE 
ON article FOR EACH ROW
BEGIN
 DELETE FROM `comment` WHERE article_id = OLD.id;#更新评论表
 SELECT tag_id FROM article_tag WHERE article_id = OLD.id INTO @tag_id;#先选出文章标签表中标签的id
 DELETE FROM article_tag WHERE article_id = OLD.id; #更新文章-标签表
 UPDATE tag SET tag.count = tag.count - 1 WHERE id = @tag_id; #更新标签表;
 DELETE FROM tag WHERE count = 0;											 			#如果标签数量为0就该删除了
END;
```

### 2.自动存入时间触发器

- 由于java的时间类型和mysql的时间类型存在转化问题，所以这里使用触发器，来解决时间问题

  ```mysql
  drop TRIGGER if EXISTS trig3;
  create trigger trig3 before insert
  on article for each row
  set NEW.created_date = NOW();
  ```

  



## 六.存储过程和函数

### 1.用户登陆的存储过程

```mysql

#用户登陆的producer
drop procedure if EXISTS login_test;

create procedure login_test(in username varchar(255),in psw varchar(255), in tkt VARCHAR(255), out st int)

BEGIN
 declare state int default 0;
 declare s_id int default 0; 
 select id, COUNT(1) into s_id, state from user where name = username and password = psw;
 if state > 0 THEN
 insert into login_ticket(expired, user_id, status, ticket) values (NOW(), s_id, state, tkt);
 select id into st from login_ticket where ticket = tkt;
 ELSE
 set st = 0;
 end IF;
end;

```



### 2.检查是否有评论标签和文章不匹配的触发器

```mysql
DROP PROCEDURE if EXISTS mdftag;
CREATE PROCEDURE mdftag()
BEGIN
DECLARE t_id, cnt int;
DECLARE done int DEFAULT 0;
DECLARE f CURSOR FOR SELECT COUNT(tag_id) AS count, tag_id FROM article_tag GROUP BY tag_id;#游标中存储数量和id
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;
#先删除article_tag表中文章已经被删掉的行
 DELETE FROM article_tag WHERE article_tag.id not in (SELECT tmp.id FROM(
 	SELECT ag.id FROM article_tag AS ag, article AS a WHERE a.id = ag.article_id
 	)as tmp
);
#同时修改comment的数量
DELETE FROM `comment` WHERE `comment`.article_id not in (SELECT id from article);
#然后修改tag表中的数量
OPEN f;																						#打开游标
while done != 1 DO
	FETCH f INTO cnt, t_id;													#获取游标中信息
	UPDATE tag SET tag.count = cnt where id = t_id; #更新表
end WHILE;
CLOSE f;
#最后删除tag中已经为0的标签的数量
DELETE FROM tag WHERE tag.id not in (SELECT tag_id FROM article_tag);

end;

```



### 3.备份用户存储过程

- 解释见备份

```mysql
drop PROCEDURE if EXISTS copyUser;
create procedure copyUser()
	begin
	 set @sql=concat("select * from user into outfile 'D:/blogtestdata/user",curdate(),".txt'");
	 prepare execsql from @sql;
	 execute execsql;
	end;

call copyUser;
```

### 4.备份文章存储过程

- 解释见备份

```mysql
drop PROCEDURE if EXISTS copyArticle;
#保存某月的文章
create procedure copyArticle(in mon varchar(255)) #传入的参数格式为 年-月
	begin
	 set @sql=concat("select * from article where created_date like '%", mon, "%'", " into outfile 'D:/blogtestdata/tmp",curdate(),".txt'");
	 select @sql;
	 prepare execsql from @sql;
	 execute execsql;
	end;

call copyArticle('2019-12');
```



### 5.将每月排名前三的文章放入archive中

```mysql
drop PROCEDURE if EXISTS saveHot;
/*
输入参数:month, year
将这个月的文章排名第一的保存到数据库中.
*/
create PROCEDURE saveHot(in mon varchar(255), in yer varchar(255))
	BEGIN
		set @sql=concat("select id, title into @article_id, @article_title from article where created_date like '%", yer, "-", mon,
		"%' order by id limit 3");
		PREPARE execsql from @sql;
		EXECUTE execsql;
		insert into archive(`article_id`, `article_title`, `month`, `year`) values (@article_id, @article_title, mon, yer);
	end;
call saveHot('12','2019');
```



## 七.用户以及权限管理

### 创建管理员

```mysql
 CREATE USER `admin`@`localhost` IDENTIFIED BY '1230';  #创建一个本地超级管理员
 create user `fuzekun` IDENTIFIED BY '1230'; 		    #创建一个远程管理员管理文章
 create user `prgmng` IDENTIFIED BY '1230';			    #创建一个远程管理员管理存储过程
 create user `logmng` IDENTIFIED BY '1230'; 		    #创建一个远程管理员进行登陆表的管理
 create user `tbmng`@`localhost` IDENTIFIED BY '1230';  #创建一个本用户管理员
 create user `viewmng` IDENTIFIED BY '1230';			#创建一个远程用于进行视图管理
```





### 1.总管理员

- 赋予管理员全部的权限

```mysql
grant all privileges on `blogtest`.* to `admin`@`localhost`;
```

### 2.管理文章评论的权限

```mysql
grant all privileges on `blogtest`.`article` to`fuzekun`@`locahost`;
grant all privileges on `blogtest`.`comment` to `fuzekun`@`locahost`;
```



### 3.只能执行存储过程的权限

- 为了保证数据库的完整性，写了检查文章和评论的触发器
- 为了保证数据库的安全性，写了保证文章和用户备份的触发器
- 可以有一个专门的用户管理员进行这些操作，每个月进行一次更新

```mysql
grant EXECUTE on `blogtest`.* to `prgmng`;
grant all privileges on `blogtest`.* to `prgmng`;
```

### 4.可以创建和修改表结构的权限

- 进行表结构的修改
- 进行增加新的表
- 进行废弃表的删除
- 可以让他创建存储过程进行处理以上错误

```mysql
grant create, alter, drop on `blogtest`.* to `tbmng`@`localhost`;
```



### 5.进行登陆和用户的管理

- 定时删除登陆表中的内容，防止占用内存
- 同时进行用户的删除和更新管理

```mysql
grant delete on `blogtest`.`login_ticket` to `logmng`;
grant delete, update on `blogtest`.`user` to `logmng`;
```

### 6.创建一个用户进行视图的管理

- 视图可以进行文章和标签的管理
- 这个用户可以专门管理文章和标签还有

```mysql
grant all privileges on `blogtest`.`articleTag` to `viewmng`;
grant all privileges on `blogtest`.`artcl` to `viewmng`;
grant all privileges on `blogtest`.`article` to `viewmng`;
grant all privileges on `blogtest`.`tag` to `viewmng`;
```



## 八.数据库的备份

### 1.文章备份:

- 使用存储过程将@time以前的文章备份到d:\blogtestdata文件夹下，以tmp+当前日期作为命名

- 只备份本月的文章

- 可以使用事务调度，一月调度一次存储过程

  ```mysql
  drop PROCEDURE if EXISTS copyArticle;
  #保存某月的文章
  create procedure copyArticle(in mon varchar(255)) #传入的参数格式为 年-月
  	begin
  	 set @sql=concat("select * from article where created_date like '%", mon, "%'", " into outfile 'D:/blogtestdata/article",curdate(),".txt'");
  	 select @sql;
  	 prepare execsql from @sql;
  	 execute execsql;
  	end;
  
  call copyArticle('2019-12');
  ```

  

### 2.用户备份

- 使用存储过程备份用户,将用户被分到d:\blogtestdata文件夹下，以当前的日期作+user为备份的命名规则
- 管理员可以手动定期删除以前的用户备份
- 事务调度这个存储过程，一个月调度一次

```mysql
drop PROCEDURE if EXISTS copyUser;
create procedure copyUser()
	begin
	 set @sql=concat("select * from user into outfile 'D:/blogtestdata/user",curdate(),".txt'");
	 prepare execsql from @sql;
	 execute execsql;
	end;

call copyUser;
```



### 3.物理备份

- 在电脑上有两个数据库
- 配置相同
- 数据schema一致
- 触发器和存储过程相同
- 具体重要数据就是用户和文章时常备份

### 4.恢复

- schema恢复

- 重要代码恢复

- 数据恢复

- 配置文件恢复

- 日志恢复

  