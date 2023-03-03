/*
Navicat MySQL Data Transfer

Source Server         : locahost
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : blogtest

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-12-18 10:20:23
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for archive
-- ----------------------------
DROP TABLE IF EXISTS `archive`;
CREATE TABLE `archive` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `article_title` varchar(255) DEFAULT NULL,
  `month` int(11) NOT NULL,
  `year` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of archive
-- ----------------------------
INSERT INTO `archive` VALUES ('1', '27', '开发文档', '12', '2019');

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
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
  KEY `authorid` (`authorid`),
  KEY `compt` (`id`,`category`),
  KEY `comp` (`id`,`created_date`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('12', 'Java', '1', '- 请求参数\r\n\r\n|  名称  | 是否必须  | 备注 |\r\n|  ----  | ----  |   ----  |\r\n| username  | 是 |  用户名\r\n| 单元格  | 是 |  密码  |\r\n\r\n- 请求方式\r\n\r\n `post`\r\n\r\n- 返回错误实例\r\n\r\n```\r\n{\r\n	code:405,\r\n	msg:请完整填写信息,\r\n	result:{\r\n		username = null,\r\n		password = 123,\r\n	}\r\n}\r\n```\r\n\r\n- 返回正确实例\r\n\r\n```\r\n{\r\ncode:1,\r\nmsg:登陆成功,\r\ndata{\r\n	username = \"\",\r\n	passwprd = \"\",\r\n	gender = \"\",\r\n	email = \"\",\r\n	tel = \"\"\r\n}\r\n```\r\n- 返回参数\r\n\r\n| 参数名 | 类型 | 含义 |\r\n| -- | -- |\r\n| username | String | 姓名 |\r\n| password | String | 密码 |\r\n| gender | String | 性别 |\r\n| email | String | 邮箱 |\r\n| tel | String | 电话 |\r\n', '2019-08-22 17:01:30', '', '测试markdown', '3');
INSERT INTO `article` VALUES ('31', 'Java', '1', '谢谢坤老带飞', '2019-12-17 19:27:50', '孟晓龙first', '我也发布一个', '59');
INSERT INTO `article` VALUES ('16', 'Java', '0', '<h1>h1 标题</h1>\n<h2>h2 标题</h2>\n<h3>h3 标题</h3>\n<h4>h4 标题</h4>\n<h5>h5 标题</h5>\n<h6>h6 标题</h6>\n<h2>水平线</h2>\n<hr />\n<hr />\n<hr />\n<h2>文本样式</h2>\n<p><strong>This is bold text</strong></p>\n<p><strong>This is bold text</strong></p>\n<p><em>This is italic text</em></p>\n<p><em>This is italic text</em></p>\n<p>~~Strikethrough~~</p>\n<h2>列表</h2>\n<p>无序</p>\n<ul>\n<li>Create a list by starting a line with <code>+</code>, <code>-</code>, or <code>*</code></li>\n<li>Sub-lists are made by indenting 2 spaces:\n<ul>\n<li>Marker character change forces new list start:\n<ul>\n<li>Ac tristique libero volutpat at</li>\n</ul>\n<ul>\n<li>Facilisis in pretium nisl aliquet</li>\n</ul>\n<ul>\n<li>Nulla volutpat aliquam velit</li>\n</ul>\n</li>\n</ul>\n</li>\n<li>Very easy!</li>\n</ul>\n<p>有序</p>\n<ol>\n<li>\n<p>Lorem ipsum dolor sit amet</p>\n</li>\n<li>\n<p>Consectetur adipiscing elit</p>\n</li>\n<li>\n<p>Integer molestie lorem at massa</p>\n</li>\n<li>\n<p>You can use sequential numbers...</p>\n</li>\n<li>\n<p>...or keep all the numbers as <code>1.</code></p>\n</li>\n</ol>\n<p>Start numbering with offset:</p>\n<ol start=\"57\">\n<li>foo</li>\n<li>bar</li>\n</ol>\n<h2>代码</h2>\n<p>Inline <code>code</code></p>\n<p>Indented code</p>\n<pre><code>// Some comments\nline 1 of code\nline 2 of code\nline 3 of code\n</code></pre>\n<p>Block code &quot;fences&quot;</p>\n<pre><code>Sample text here...\n</code></pre>\n<p>Syntax highlighting</p>\n<pre><code class=\"language-js\">var foo = function (bar) {\n  return bar++;\n};\n\nconsole.log(foo(5));\n</code></pre>\n', '2019-11-14 01:44:41', '第一次写应该展示这个页面', 'markdown语法教程', '3');
INSERT INTO `article` VALUES ('22', 'Java', '0', '[TOC]\r\n\r\n#### Disabled options\r\n\r\n- TeX (Based on KaTeX);\r\n- Emoji;\r\n- Task lists;\r\n- HTML tags decode;\r\n- Flowchart and Sequence Diagram;\r\n\r\n#### Editor.md directory\r\n\r\n    editor.md/\r\n            lib/\r\n            css/\r\n            scss/\r\n            tests/\r\n            fonts/\r\n            images/\r\n            plugins/\r\n            examples/\r\n            languages/     \r\n            editormd.js\r\n            ...\r\n\r\n```html\r\n<!-- English -->\r\n<script src=\"../dist/js/languages/en.js\"></script>\r\n\r\n<!-- 繁體中文 -->\r\n<script src=\"../dist/js/languages/zh-tw.js\"></script>\r\n```\r\n', '2019-11-14 04:17:23', '上一个教程被转化成了html代码', '没有被转化后的教程', '3');
INSERT INTO `article` VALUES ('27', 'Java', '0', '# blog开发文档 - 数据库\r\n\r\n## 一.表的创建以及完整性\r\n\r\n### 1.用户表的创建\r\n\r\n\r\n\r\n```mysql\r\ndrop table if exists `user`;\r\nCREATE TABLE `user` (\r\n  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n  `head_url` varchar(255) DEFAULT NULL,\r\n  `name` varchar(255) DEFAULT NULL,\r\n  `password` varchar(255) DEFAULT NULL,\r\n  `role` varchar(255) DEFAULT NULL,\r\n  `salt` varchar(255) DEFAULT NULL,\r\n  `username` varchar(255) DEFAULT NULL,\r\n  PRIMARY KEY (`id`)\r\n);\r\n```\r\n\r\n\r\n\r\n### 2.文章表的创建\r\n\r\n```mysql\r\n drop table if exists `article`;\r\n CREATE TABLE `article` (\r\n  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n  `category` varchar(100) DEFAULT NULL,\r\n  `comment_count` int(11) NOT NULL,\r\n  `content` text,\r\n  `created_date` datetime DEFAULT NULL,\r\n  `describes` varchar(255) DEFAULT NULL,\r\n  `title` varchar(255) DEFAULT NULL,\r\n  `authorid` int(11) NOT NULL,\r\n  PRIMARY KEY (`id`),\r\n  KEY `categoryIndex` (`category`),\r\n  KEY `authorid` (`authorid`)\r\n);\r\n```\r\n\r\n\r\n\r\n### 3.标签表的创建\r\n\r\n```mysql\r\ndrop table if exists `tag`;\r\nCREATE TABLE `tag` (\r\n  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n  `count` int(11) NOT NULL,\r\n  `name` varchar(255) DEFAULT NULL,\r\n  PRIMARY KEY (`id`)\r\n);\r\n```\r\n\r\n\r\n\r\n### 4.评论表的创建\r\n\r\n```mysql\r\n drop table if exists `comment`;\r\n CREATE TABLE `comment` (\r\n  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n  `article_id` int(11) NOT NULL,\r\n  `content` varchar(255) DEFAULT NULL,\r\n  `created_date` datetime DEFAULT NULL,\r\n  `status` int(11) NOT NULL,\r\n  `user_id` int(11) NOT NULL,\r\n  PRIMARY KEY (`id`),\r\n  foreign key (`article_id`) references article(`id`),\r\n  foreign key (`user_id`) references user(`id`)\r\n);\r\n```\r\n\r\n### 5.文章标签表\r\n\r\n```mysql\r\n drop table if exists `article_tag`;\r\n CREATE TABLE `article_tag` (\r\n  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n  `article_id` int(11) NOT NULL,\r\n  `tag_id` int(11) NOT NULL,\r\n  PRIMARY KEY (`id`),\r\n  foreign key(`article_id`) references article(`id`),\r\n  foreign key(`tag_id`) references tag(`id`)   \r\n);\r\n```\r\n\r\n### 6.成就表\r\n\r\n```mysql\r\ndrop table if exists `archive`;\r\n CREATE TABLE `archive` (\r\n  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n  `article_id` int(11) NOT NULL,\r\n  `article_title` varchar(255) DEFAULT NULL,\r\n  `month` int(11) NOT NULL,\r\n  `year` int(11) NOT NULL,\r\n  PRIMARY KEY (`id`),\r\n   foreign key (`article_id`) references article(`id`) \r\n);\r\n```\r\n\r\n筛选出每月点击量最高的文章放入表中。\r\n\r\n### 7.登陆表\r\n\r\n- 记录登陆的时间,放入登陆口令，用于免密登录\r\n\r\n```mysql\r\nCREATE TABLE `login_ticket` (\r\n  `id` int(11) NOT NULL AUTO_INCREMENT,\r\n  `expired` datetime DEFAULT NULL,\r\n  `status` int(11) NOT NULL,\r\n  `ticket` varchar(255) DEFAULT NULL,\r\n  `user_id` int(11) NOT NULL,\r\n  PRIMARY KEY (`id`),\r\n   foreign key (`user_id`) references user(`id`)\r\n);\r\n```\r\n\r\n\r\n\r\n## 二.数据增删查改及功能\r\n\r\n\r\n\r\n## 三.索引的创建\r\n\r\n### 1.表默认主外键自动创建索引\r\n\r\n- 创建表的时候默认有根据主键的索引和根据外键的索引\r\n\r\n  例如:\r\n\r\n```mysql\r\nshow index from artcile\r\n```\r\n\r\n![image-20191212182617114](C:\\Users\\付泽坤\\AppData\\Roaming\\Typora\\typora-user-images\\image-20191212182617114.png)\r\n\r\n可以看出，根据主键和id 和外键authorid创建了索引，同时NOT NULL 的category也创建了索引。\r\n\r\n\r\n\r\n## 2.文章根据时间创建索引\r\n\r\n- 由于频繁根据文章类型查找文章所以建议category索引。\r\n- 由于会经常备份文章所以需要建立文章时间的索引。\r\n\r\n```mysql\r\ncreate index tm on article(created_date);\r\ncreate index ct on article(category);\r\n```\r\n\r\n\r\n\r\n## 四.视图的创建\r\n\r\n- 由于博客系统中文章的属性大部分人可见，其实建立视图意义不大\r\n- 另外视图解析的两种算法，都会带来额外的开销\r\n- 权限并不多级\r\n\r\n所以本系统视图建立完全浪费，但是必须有组件，就只能画蛇添足。\r\n\r\n\r\n\r\n1.文章视图\r\n\r\n```mysql\r\ndrop view if EXISTS artcl;\r\ncreate view artcl as \r\n	select category, content, created_date,\r\n	describes, title\r\n	from article;\r\n```\r\n\r\n2.文章标签视图\r\n\r\n```mysql\r\n#创建用于显示tag和文章信息的视图\r\ndrop view if EXISTS articleTag;\r\ncreate view articleTag AS\r\n	select category, content, created_date,\r\n	describes, title, tag.name\r\n	from article, article_tag, tag\r\n  where article.id = article_tag.article_id \r\n		and tag.id = article_tag.tag_id;\r\n```\r\n\r\n\r\n\r\n## 五.触发器\r\n\r\n触发器有一下特点\r\n\r\n- 触发器开销大，可能一条插入语句会更新张表\r\n- 触发器容易出错，由于触发器对用于隐藏，所以可能会出现莫名奇妙的禁止操作\r\n\r\n所以应该少建立触发器，尽量不建立触发器。\r\n\r\n\r\n\r\n### 1.删除文章的时候级联更新标签和评论(标签数目减一，评论级联删除)\r\n\r\n```mysql\r\nCREATE TRIGGER delArticle AFTER DELETE \r\nON article FOR EACH ROW\r\nBEGIN\r\n DELETE FROM `comment` WHERE article_id = OLD.id;#更新评论表\r\n SELECT tag_id FROM article_tag WHERE article_id = OLD.id INTO @tag_id;#先选出文章标签表中标签的id\r\n DELETE FROM article_tag WHERE article_id = OLD.id; #更新文章-标签表\r\n UPDATE tag SET tag.count = tag.count - 1 WHERE id = @tag_id; #更新标签表;\r\n DELETE FROM tag WHERE count = 0;											 			#如果标签数量为0就该删除了\r\nEND;\r\n```\r\n\r\n### 2.自动存入时间触发器\r\n\r\n- 由于java的时间类型和mysql的时间类型存在转化问题，所以这里使用触发器，来解决时间问题\r\n\r\n  ```mysql\r\n  drop TRIGGER if EXISTS trig3;\r\n  create trigger trig3 before insert\r\n  on article for each row\r\n  set NEW.created_date = NOW();\r\n  ```\r\n\r\n  \r\n\r\n\r\n\r\n## 六.存储过程和函数\r\n\r\n### 1.用户登陆的存储过程\r\n\r\n```mysql\r\n#用户登陆的producer\r\ndrop procedure if EXISTS login_test;\r\n\r\ncreate procedure login_test(in username varchar(255),in psw varchar(255), in tkt VARCHAR(255), out st int)\r\n\r\nBEGIN\r\n declare state int default 0;\r\n declare s_id int default 0; \r\n select id, COUNT(1) into s_id, state from user where name = username and password = psw;\r\n if state > 0 THEN\r\n insert into login_ticket(expired, user_id, status, ticket) values (NOW(), s_id, state, tkt);\r\n select id into st from login_ticket where ticket = tkt;\r\n ELSE\r\n set st = 0;\r\n end IF;\r\nend;\r\n\r\n```\r\n\r\n\r\n\r\n2.检查是否有评论标签和文章不匹配的触发器\r\n\r\n```mysql\r\nDROP PROCEDURE if EXISTS mdftag;\r\nCREATE PROCEDURE mdftag()\r\nBEGIN\r\nDECLARE t_id, cnt int;\r\nDECLARE done int DEFAULT 0;\r\nDECLARE f CURSOR FOR SELECT COUNT(tag_id) AS count, tag_id FROM article_tag GROUP BY tag_id;#游标中存储数量和id\r\nDECLARE CONTINUE HANDLER FOR NOT FOUND SET done = 1;\r\n#先删除article_tag表中文章已经被删掉的行\r\n DELETE FROM article_tag WHERE article_tag.id not in (SELECT tmp.id FROM(\r\n 	SELECT ag.id FROM article_tag AS ag, article AS a WHERE a.id = ag.article_id\r\n 	)as tmp\r\n);\r\n#同时修改comment的数量\r\nDELETE FROM `comment` WHERE `comment`.article_id not in (SELECT id from article);\r\n#然后修改tag表中的数量\r\nOPEN f;																						#打开游标\r\nwhile done != 1 DO\r\n	FETCH f INTO cnt, t_id;													#获取游标中信息\r\n	UPDATE tag SET tag.count = cnt where id = t_id; #更新表\r\nend WHILE;\r\nCLOSE f;\r\n#最后删除tag中已经为0的标签的数量\r\nDELETE FROM tag WHERE tag.id not in (SELECT tag_id FROM article_tag);\r\n\r\nend;\r\n\r\n```\r\n\r\n\r\n\r\n### 3.备份用户存储过程\r\n\r\n- 解释见备份\r\n\r\n```mysql\r\ndrop PROCEDURE if EXISTS copyUser;\r\ncreate procedure copyUser()\r\n	begin\r\n	 set @sql=concat(\"select * from user into outfile \'D:/blogtestdata/user\",curdate(),\".txt\'\");\r\n	 prepare execsql from @sql;\r\n	 execute execsql;\r\n	end;\r\n\r\ncall copyUser;\r\n```\r\n\r\n### 4.备份文章存储过程\r\n\r\n- 解释见备份\r\n\r\n```mysql\r\ndrop PROCEDURE if EXISTS copyArticle;\r\n#保存某月的文章\r\ncreate procedure copyArticle(in mon varchar(255)) #传入的参数格式为 年-月\r\n	begin\r\n	 set @sql=concat(\"select * from article where created_date like \'%\", mon, \"%\'\", \" into outfile \'D:/blogtestdata/tmp\",curdate(),\".txt\'\");\r\n	 select @sql;\r\n	 prepare execsql from @sql;\r\n	 execute execsql;\r\n	end;\r\n\r\ncall copyArticle(\'2019-12\');\r\n```\r\n\r\n\r\n\r\n## 七.用户以及权限管理\r\n\r\n\r\n\r\n\r\n\r\n## 八.数据库的备份\r\n\r\n### 1.文章备份:\r\n\r\n- 使用存储过程将@time以前的文章备份到d:\\blogtestdata文件夹下，以tmp+当前日期作为命名\r\n\r\n- 只备份本月的文章\r\n\r\n- 可以使用事务调度，一月调度一次存储过程\r\n\r\n  ```mysql\r\n  drop PROCEDURE if EXISTS copyArticle;\r\n  #保存某月的文章\r\n  create procedure copyArticle(in mon varchar(255)) #传入的参数格式为 年-月\r\n  	begin\r\n  	 set @sql=concat(\"select * from article where created_date like \'%\", mon, \"%\'\", \" into outfile \'D:/blogtestdata/tmp\",curdate(),\".txt\'\");\r\n  	 select @sql;\r\n  	 prepare execsql from @sql;\r\n  	 execute execsql;\r\n  	end;\r\n  \r\n  call copyArticle(\'2019-12\');\r\n  ```\r\n\r\n  \r\n\r\n### 2.用户备份\r\n\r\n- 使用存储过程备份用户,将用户被分到d:\\blogtestdata文件夹下，以当前的日期作+user为备份的命名规则\r\n- 管理员可以手动定期删除以前的用户备份\r\n- 事务调度这个存储过程，一个月调度一次\r\n\r\n```mysql\r\ndrop PROCEDURE if EXISTS copyUser;\r\ncreate procedure copyUser()\r\n	begin\r\n	 set @sql=concat(\"select * from user into outfile \'D:/blogtestdata/user\",curdate(),\".txt\'\");\r\n	 prepare execsql from @sql;\r\n	 execute execsql;\r\n	end;\r\n\r\ncall copyUser;\r\n```\r\n\r\n\r\n\r\n### 3.物理备份\r\n\r\n- 在电脑上有两个数据库\r\n- 配置相同\r\n- 数据schema一致\r\n- 触发器和存储过程相同\r\n- 具体重要数据就是用户和文章时常备份\r\n\r\n\r\n\r\n', '2019-12-14 13:38:34', '数据库开发篇', '开发文档', '57');
INSERT INTO `article` VALUES ('28', '数据库', '1', '好无聊啊', '2019-12-14 20:32:14', '没意思', '测试数据', '58');
INSERT INTO `article` VALUES ('30', '数据库', '0', '哈哈哈哈', '2019-12-17 19:24:24', '', '数据库', '59');

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of article_tag
-- ----------------------------
INSERT INTO `article_tag` VALUES ('29', '30', '0');
INSERT INTO `article_tag` VALUES ('30', '31', '0');
INSERT INTO `article_tag` VALUES ('26', '27', '0');
INSERT INTO `article_tag` VALUES ('27', '28', '0');
INSERT INTO `article_tag` VALUES ('12', '12', '11');
INSERT INTO `article_tag` VALUES ('16', '16', '11');
INSERT INTO `article_tag` VALUES ('22', '22', '11');

-- ----------------------------
-- Table structure for comment
-- ----------------------------
DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `content` varchar(255) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=22 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of comment
-- ----------------------------
INSERT INTO `comment` VALUES ('19', '12', '很棒', '2019-11-15 12:20:51', '0', '3');
INSERT INTO `comment` VALUES ('20', '28', '这个是用来测试能不能进行修改文章类型的吗', '2019-12-14 12:58:47', '0', '58');
INSERT INTO `comment` VALUES ('21', '31', '哈哈哈哈谢谢坤哥', '2019-12-17 11:33:57', '0', '59');

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES ('1');

-- ----------------------------
-- Table structure for login_ticket
-- ----------------------------
DROP TABLE IF EXISTS `login_ticket`;
CREATE TABLE `login_ticket` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `expired` datetime DEFAULT NULL,
  `status` int(11) NOT NULL,
  `ticket` varchar(255) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=114 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of login_ticket
-- ----------------------------
INSERT INTO `login_ticket` VALUES ('2', '2019-05-20 13:51:38', '0', '08a8d78647494c8a9b340533ca9fae6f', '3');
INSERT INTO `login_ticket` VALUES ('3', '2019-05-20 13:58:23', '0', 'ee4e9461c29142fd865ae72992161de0', '3');
INSERT INTO `login_ticket` VALUES ('4', '2019-05-20 13:58:53', '0', '9658cc1246d14c2898a75b76a7e77900', '3');
INSERT INTO `login_ticket` VALUES ('5', '2019-05-20 13:59:00', '0', '20a4ca5a69b24461a8df4292c04f0b85', '3');
INSERT INTO `login_ticket` VALUES ('6', '2019-05-20 13:59:02', '0', '279fc0dcb9044ebf8adbd57fe0f1cbed', '3');
INSERT INTO `login_ticket` VALUES ('9', '2019-05-20 14:39:23', '0', 'd54817b276004e31a8f8e2605f28fdba', '3');
INSERT INTO `login_ticket` VALUES ('8', '2019-05-20 14:34:49', '0', '96453575bb1c4926813202306eca8ca8', '3');
INSERT INTO `login_ticket` VALUES ('10', '2019-05-20 14:41:02', '0', '6386b57d0bd749bf885dcd07ba86060d', '3');
INSERT INTO `login_ticket` VALUES ('11', '2019-05-20 14:46:41', '0', 'fd032ea011e04b9f84735fe9bf0523f0', '3');
INSERT INTO `login_ticket` VALUES ('12', '2019-05-20 14:52:51', '0', 'bde50d3703764f4f8819cc811a43703c', '3');
INSERT INTO `login_ticket` VALUES ('13', '2019-05-20 14:56:19', '0', 'a124199768d74cbb88bfae3b9c8c3c7f', '3');
INSERT INTO `login_ticket` VALUES ('14', '2019-05-21 17:58:16', '0', '46ae384888374b739602b0fddbf923eb', '3');
INSERT INTO `login_ticket` VALUES ('15', '2019-05-22 13:06:30', '0', '8e93195c8b4b4afeacef2a19f8ab5672', '3');
INSERT INTO `login_ticket` VALUES ('16', '2019-05-22 13:56:10', '0', 'aaacac7d6ac74eb1b4d17e33be63f88c', '3');
INSERT INTO `login_ticket` VALUES ('17', '2019-05-22 14:52:32', '0', '26775c6388a04b039803ba7fe71940fe', '3');
INSERT INTO `login_ticket` VALUES ('18', '2019-05-22 15:08:04', '0', 'fe94dde7b2304648a3b00498a0ed0003', '3');
INSERT INTO `login_ticket` VALUES ('19', '2019-05-22 15:14:15', '0', '2212d09c5c7e40c2a43f83e52dc5f454', '3');
INSERT INTO `login_ticket` VALUES ('20', '2019-05-22 15:21:51', '0', 'd9aa57a6698344c6ab30a010c9fd72d6', '3');
INSERT INTO `login_ticket` VALUES ('21', '2019-05-22 15:26:58', '0', '4eb94fb87d294188a522319211c5744a', '3');
INSERT INTO `login_ticket` VALUES ('22', '2019-05-22 15:49:08', '0', 'fb205a9ae1204dcbbf27d581f1e62ca5', '3');
INSERT INTO `login_ticket` VALUES ('23', '2019-05-22 18:54:24', '0', '82ef12b66e654e56add9d2b6e9b90f86', '3');
INSERT INTO `login_ticket` VALUES ('24', '2019-05-22 18:57:13', '0', 'de508f6054bb455a95f09f30ad226e4a', '3');
INSERT INTO `login_ticket` VALUES ('25', '2019-05-23 19:17:54', '0', 'c6c2ebad9df848a8b29f583355de51f8', '3');
INSERT INTO `login_ticket` VALUES ('26', '2019-05-23 19:18:27', '0', '186e660c0fa54ffca550d0e8c0789199', '51');
INSERT INTO `login_ticket` VALUES ('27', '2019-05-24 17:04:37', '0', '6ceaf9b33d46456d8254f038affadf26', '51');
INSERT INTO `login_ticket` VALUES ('28', '2019-05-24 17:05:59', '0', '8bd2f5e15d044e07a90174e70315a255', '51');
INSERT INTO `login_ticket` VALUES ('29', '2019-05-24 17:06:01', '0', '3575a6ae3d554666adb99e4f4985d309', '51');
INSERT INTO `login_ticket` VALUES ('30', '2019-05-24 17:09:06', '0', '13511fe290bb4787baa2ae1c733791ea', '51');
INSERT INTO `login_ticket` VALUES ('31', '2019-05-24 17:09:20', '0', '1dca2ee067aa4b4bbde671eeff998c55', '51');
INSERT INTO `login_ticket` VALUES ('32', '2019-05-25 09:11:01', '0', '9363e0132ba34b1cb5ef3e1da4fa9a45', '51');
INSERT INTO `login_ticket` VALUES ('33', '2019-05-26 07:33:01', '0', '40f301cb047647ed8400fea86ff60426', '3');
INSERT INTO `login_ticket` VALUES ('34', '2019-05-26 07:34:25', '0', 'edd904e05bfb4283857f7d74ca819c6a', '3');
INSERT INTO `login_ticket` VALUES ('35', '2019-06-12 19:14:22', '0', 'd85da1b3131f4c5fab3a7765f3555d1e', '3');
INSERT INTO `login_ticket` VALUES ('36', '2019-06-12 19:15:08', '0', '8dd666ca26d44ad895fd6ee6b7f9c4eb', '3');
INSERT INTO `login_ticket` VALUES ('37', '2019-06-12 19:16:18', '0', '40620d2569bd4aa08c9424d9b39ae463', '3');
INSERT INTO `login_ticket` VALUES ('38', '2019-06-12 19:16:35', '0', 'ea586ec410124075b5ddebc638daf377', '52');
INSERT INTO `login_ticket` VALUES ('39', '2019-06-12 19:17:28', '0', '58e4508e2d46495d9b735bac82c0e16e', '52');
INSERT INTO `login_ticket` VALUES ('40', '2019-07-17 19:24:02', '0', '799a3b445a5c4a4ebe6a2b0651a0d757', '53');
INSERT INTO `login_ticket` VALUES ('41', '2019-07-17 20:25:30', '0', '7a60fcda71c2447b803979ceaf7c5b5a', '53');
INSERT INTO `login_ticket` VALUES ('42', '2019-07-18 07:56:29', '0', '94e097996406466ebb2e4e3098358342', '53');
INSERT INTO `login_ticket` VALUES ('43', '2019-07-18 07:58:34', '0', '4af41b19a4094b93bdbe2eb6ef161411', '53');
INSERT INTO `login_ticket` VALUES ('44', '2019-07-18 07:59:40', '0', 'f464dcbb844947a69927e0c1e06bcf4a', '53');
INSERT INTO `login_ticket` VALUES ('45', '2019-07-18 08:00:16', '0', '77ecf66b1cf34a508e4025dc83ca9c43', '53');
INSERT INTO `login_ticket` VALUES ('46', '2019-07-18 08:00:49', '0', '59beb31b707c4f4caba259badcfdf6c2', '53');
INSERT INTO `login_ticket` VALUES ('47', '2019-07-18 08:01:29', '0', 'dfdbbef05b0e4814b82ed4b4f7fd895b', '53');
INSERT INTO `login_ticket` VALUES ('48', '2019-07-18 08:04:49', '0', 'c8febf562aed499f83266128b55c19f3', '53');
INSERT INTO `login_ticket` VALUES ('49', '2019-07-18 08:09:55', '0', '8bb6b551b6fa467284bb3b57803f69d2', '53');
INSERT INTO `login_ticket` VALUES ('50', '2019-07-18 08:10:23', '0', '5877388e777a4da6bb6ec5c5acf850ad', '53');
INSERT INTO `login_ticket` VALUES ('51', '2019-07-18 08:11:40', '0', 'ce9dbefe3e2044c3b59aca312d00d279', '53');
INSERT INTO `login_ticket` VALUES ('52', '2019-07-18 08:12:53', '0', 'bc31330e77cd40dd82bad97632a52b94', '53');
INSERT INTO `login_ticket` VALUES ('53', '2019-07-18 08:19:29', '0', 'ddb35f6c4973483cbddc82fceffb0421', '53');
INSERT INTO `login_ticket` VALUES ('54', '2019-07-18 08:21:04', '0', '3ac79bd8ff904260b5276b9b3d5b2d5b', '53');
INSERT INTO `login_ticket` VALUES ('55', '2019-07-18 08:21:25', '0', 'abdd8eb5fb3f4436a1c77ea780279291', '53');
INSERT INTO `login_ticket` VALUES ('56', '2019-07-18 08:22:13', '0', 'bec4b85ae21c4f8289c486bbe0d4fd02', '53');
INSERT INTO `login_ticket` VALUES ('57', '2019-07-22 09:23:00', '0', '8c21f0f9aa874946a0ce50b59f338bbc', '53');
INSERT INTO `login_ticket` VALUES ('58', '2019-07-22 09:29:40', '0', 'd04c948da0c74a668bcc8c0e01a5c61a', '52');
INSERT INTO `login_ticket` VALUES ('59', '2019-07-22 20:02:13', '0', 'a8716f21715d44a8b89a6accaf9cc29a', '53');
INSERT INTO `login_ticket` VALUES ('60', '2019-07-22 20:14:46', '0', '2a1184862f474ef3a3b3acc57e910bb1', '53');
INSERT INTO `login_ticket` VALUES ('61', '2019-07-22 20:44:30', '0', 'e0775bd2f7b349e286b4e76eaa0e3bd8', '53');
INSERT INTO `login_ticket` VALUES ('62', '2019-07-22 20:44:47', '0', 'c6fbc3f0d47446b7810b295964d42700', '53');
INSERT INTO `login_ticket` VALUES ('63', '2019-07-22 20:47:02', '0', '3d60eb3e78524e97b058105b4831e2dc', '53');
INSERT INTO `login_ticket` VALUES ('64', '2019-07-22 20:49:12', '0', '013546732e3341039231092b12aa6f73', '53');
INSERT INTO `login_ticket` VALUES ('65', '2019-07-22 21:11:19', '0', '9922e9f3db224be49173c91c05f17bf4', '53');
INSERT INTO `login_ticket` VALUES ('66', '2019-08-23 21:39:57', '0', '14789fd739354d6cb737041dba3b71f2', '54');
INSERT INTO `login_ticket` VALUES ('67', '2019-08-23 21:41:34', '0', '82672d9e8f4b47c9994f9ecf7df76ae6', '54');
INSERT INTO `login_ticket` VALUES ('68', '2019-08-23 23:07:33', '0', '552e1ef5d52c41d796b2c245ba0710a8', '54');
INSERT INTO `login_ticket` VALUES ('69', '2019-09-20 20:20:15', '0', 'd1fb147bb9dd4d52831bea682f132d5b', '3');
INSERT INTO `login_ticket` VALUES ('70', '2019-09-22 15:41:17', '0', 'ddc2a6ab3fce4949ab55fc0cb83fc81d', '3');
INSERT INTO `login_ticket` VALUES ('71', '2019-11-14 14:16:08', '0', '9f2152ed03b34d358ab9299d2a17f5a3', '3');
INSERT INTO `login_ticket` VALUES ('72', '2019-11-15 07:25:38', '0', 'c126d0778ce247cbb9521729b0fe29ef', '3');
INSERT INTO `login_ticket` VALUES ('73', '2019-11-15 17:10:12', '0', '922a6346fecd4902ad2a674630c9ebbd', '3');
INSERT INTO `login_ticket` VALUES ('74', '2019-11-16 12:03:56', '0', '7b4cd895fa1d4d9290d1f3f6b7992bc5', '3');
INSERT INTO `login_ticket` VALUES ('75', '2019-11-16 18:20:43', '0', '59eafb9e5dc54cc6873b08bf4e102af9', '3');
INSERT INTO `login_ticket` VALUES ('76', '2019-11-16 18:32:53', '0', '86b0dd8b393144188266e4102d7dd3bd', '3');
INSERT INTO `login_ticket` VALUES ('77', '2019-11-16 18:33:09', '0', 'd11e5304277e423e9d1ab3716dcd4f12', '3');
INSERT INTO `login_ticket` VALUES ('78', '2019-11-16 18:39:27', '0', '69fa1d70f0c44cd9af1118d112be85b4', '53');
INSERT INTO `login_ticket` VALUES ('79', '2019-11-16 18:39:58', '0', '39c2f52d62f8443082c3bc77ef652996', '53');
INSERT INTO `login_ticket` VALUES ('80', '2019-11-18 12:32:58', '0', '411ac57ec92945e893f7fc7110eaefa6', '53');
INSERT INTO `login_ticket` VALUES ('81', '2019-11-19 13:51:39', '0', '98599ff92257467e99959cb1adef1f65', '3');
INSERT INTO `login_ticket` VALUES ('83', '2019-12-11 12:41:48', '1', '45fda', '50');
INSERT INTO `login_ticket` VALUES ('84', '2019-12-15 10:29:32', '0', '30b9cb1dc12a4c3ab0a843fbb4421dd8', '55');
INSERT INTO `login_ticket` VALUES ('85', '2019-12-15 10:30:15', '0', 'ead7e7083f9440a1995ded40db7a3c8c', '55');
INSERT INTO `login_ticket` VALUES ('86', '2019-12-15 10:31:20', '0', '2976bfb8abad44888bbe173039a05681', '56');
INSERT INTO `login_ticket` VALUES ('87', '2019-12-15 10:34:55', '0', '71efcb6db41d4958aabafbc78c5b4c87', '57');
INSERT INTO `login_ticket` VALUES ('88', '2019-12-15 14:43:34', '0', 'aa057ccfe9514af19c2bd44b5d916dec', '3');
INSERT INTO `login_ticket` VALUES ('89', '2019-12-15 15:24:29', '0', 'd13c0f714ec54faebc9bc3b5fc48b722', '3');
INSERT INTO `login_ticket` VALUES ('90', '2019-12-15 15:29:10', '0', '13a4d5a29e8241b6a981993cd7ce3fbf', '3');
INSERT INTO `login_ticket` VALUES ('91', '2019-12-15 16:00:38', '0', 'bbd70f0fc18c4c3987607f45bb07bb46', '58');
INSERT INTO `login_ticket` VALUES ('92', '2019-12-15 16:00:48', '0', '85d312f8c59541109df676ec189d9431', '58');
INSERT INTO `login_ticket` VALUES ('93', '2019-12-15 16:01:12', '0', 'f35fc9fd605f4a0aba86d5ac5bb2728a', '58');
INSERT INTO `login_ticket` VALUES ('94', '2019-12-15 16:01:14', '0', '46a25eebf21443d893902672f55c2afe', '58');
INSERT INTO `login_ticket` VALUES ('95', '2019-12-15 16:01:21', '0', 'f3fdb49603ab4406b5f25e8b101b10ea', '58');
INSERT INTO `login_ticket` VALUES ('96', '2019-12-15 16:05:58', '0', '8b13df5836b04eb6a001ba853b06295f', '58');
INSERT INTO `login_ticket` VALUES ('97', '2019-12-15 16:07:34', '0', '05f19536050b4a9d87a1259b395f015e', '58');
INSERT INTO `login_ticket` VALUES ('98', '2019-12-15 16:10:03', '0', '1f1948ca0c084c6ea9f4d723a965ff33', '58');
INSERT INTO `login_ticket` VALUES ('99', '2019-12-15 16:25:58', '0', '46fd8fbd42a249f7a74eec41b1debfae', '58');
INSERT INTO `login_ticket` VALUES ('100', '2019-12-15 18:11:54', '0', 'e00f96e83bb94b789097b4b467952d2e', '58');
INSERT INTO `login_ticket` VALUES ('101', '2019-12-15 18:17:32', '0', 'f9942c32838d4f47b867401d51dbb394', '58');
INSERT INTO `login_ticket` VALUES ('102', '2019-12-15 18:19:39', '0', 'bc8a06d833f7497abbc53c4659416817', '58');
INSERT INTO `login_ticket` VALUES ('103', '2019-12-15 18:31:53', '0', '0c57456e40ca4cc98ee97fa64a1c2695', '58');
INSERT INTO `login_ticket` VALUES ('104', '2019-12-15 18:39:03', '0', 'b0f6628ca9474334b4ae86eef5157c93', '53');
INSERT INTO `login_ticket` VALUES ('105', '2019-12-15 18:39:35', '0', '433078252a0d4ffa8d9ce9e98cdb7e9b', '58');
INSERT INTO `login_ticket` VALUES ('106', '2019-12-15 19:38:21', '0', '485d912a2cc04297af19cfa23f175598', '58');
INSERT INTO `login_ticket` VALUES ('107', '2019-12-16 09:02:32', '0', 'fcea19b3b3fc45dca2fb089ad7c355fa', '58');
INSERT INTO `login_ticket` VALUES ('108', '2019-12-16 09:06:49', '0', '6d1878d3b1d542eab1ac3ff10d098a68', '58');
INSERT INTO `login_ticket` VALUES ('109', '2019-12-16 11:16:15', '0', '15c2b7f052da4a38a65dce24649f82fe', '58');
INSERT INTO `login_ticket` VALUES ('110', '2019-12-18 17:20:50', '0', '6e213733790c4b038971b9797b3872a8', '59');
INSERT INTO `login_ticket` VALUES ('111', '2019-12-18 17:31:24', '0', '040f48437feb4eb29b16a145b76a5d5f', '59');
INSERT INTO `login_ticket` VALUES ('112', '2019-12-18 19:38:56', '0', 'aca3f0b2a91e4189aec7517d4eecccaa', '58');
INSERT INTO `login_ticket` VALUES ('113', '2019-12-18 19:54:32', '0', '07c5ac7662584acc887bba5dbd2177d6', '58');

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tag
-- ----------------------------
INSERT INTO `tag` VALUES ('14', '1', '开发文档');
INSERT INTO `tag` VALUES ('18', '1', '随心');
INSERT INTO `tag` VALUES ('17', '1', '');
INSERT INTO `tag` VALUES ('16', '1', '错了');
INSERT INTO `tag` VALUES ('15', '1', '没意思');
INSERT INTO `tag` VALUES ('11', '3', '随笔');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `head_url` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `salt` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('50', '', '123', '123', 'admin', null, null);
INSERT INTO `user` VALUES ('2', null, 'FUZEKIN', '34AC78B6BDEF3DF31C4B3CBC917C43EE', null, null, 'fuzekun');
INSERT INTO `user` VALUES ('4', 'https://images.nowcoder.com/head/41m.png', 'fuze', '05D9B39BEC491563DAC6A27C46051C92', 'user', '8d8af', null);
INSERT INTO `user` VALUES ('51', 'https://images.nowcoder.com/head/165m.png', 'zk', '442A5D0B7BDF792DC5EA8368633F2BDB', 'admin', '90497', null);
INSERT INTO `user` VALUES ('52', 'https://images.nowcoder.com/head/545m.png', 'fuzekun123', 'F060062D78E3001A8EEEA569FFABBD8B', 'user', '2c292', null);
INSERT INTO `user` VALUES ('53', 'https://images.nowcoder.com/head/915m.png', 'zekun', 'F4CDB117DDAA7184F0435FE081BE7E67', 'user', '00db7', null);
INSERT INTO `user` VALUES ('54', 'https://images.nowcoder.com/head/386m.png', '付泽坤', 'E4D2030699AAF2C957BDFF9E7FA2D4F4', 'user', '18cd0', null);
INSERT INTO `user` VALUES ('55', 'https://images.nowcoder.com/head/166m.png', 'admin', '87589C420584A26F7F663D9767FC1268', 'user', '71b69', null);
INSERT INTO `user` VALUES ('56', 'https://images.nowcoder.com/head/106m.png', 'fff', 'A53B358574AB2FD4F3ED9CC60EDD2383', 'user', '4dd2f', null);
INSERT INTO `user` VALUES ('57', 'https://images.nowcoder.com/head/100m.png', 'nihao ', '57658A7641366BB45141C4DD6E6EDD3D', 'user', '9df00', null);
INSERT INTO `user` VALUES ('58', 'https://images.nowcoder.com/head/745m.png', 'fuzekun', 'B71C4CA425DFD930A18F09A7A105764D', 'user', '1bf1f', null);
INSERT INTO `user` VALUES ('59', 'https://images.nowcoder.com/head/684m.png', '17861401415', '52D8C6FA44ADC2D05F639C5B196016A9', 'user', '660f2', null);

-- ----------------------------
-- View structure for artcl
-- ----------------------------
DROP VIEW IF EXISTS `artcl`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `artcl` AS select `article`.`category` AS `category`,`article`.`content` AS `content`,`article`.`created_date` AS `created_date`,`article`.`describes` AS `describes`,`article`.`title` AS `title` from `article` ;

-- ----------------------------
-- View structure for articletag
-- ----------------------------
DROP VIEW IF EXISTS `articletag`;
CREATE ALGORITHM=UNDEFINED DEFINER=`root`@`localhost` SQL SECURITY DEFINER VIEW `articletag` AS select `article`.`category` AS `category`,`article`.`content` AS `content`,`article`.`created_date` AS `created_date`,`article`.`describes` AS `describes`,`article`.`title` AS `title`,`tag`.`name` AS `name` from ((`article` join `article_tag`) join `tag`) where ((`article`.`id` = `article_tag`.`article_id`) and (`tag`.`id` = `article_tag`.`tag_id`)) ;

-- ----------------------------
-- Procedure structure for copyUser
-- ----------------------------
DROP PROCEDURE IF EXISTS `copyUser`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `copyUser`()
begin
	 set @sql=concat("select * from user into outfile 'D:/blogtestdata/user",curdate(),".txt'");
	 prepare execsql from @sql;
	 execute execsql;
	end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for login_test
-- ----------------------------
DROP PROCEDURE IF EXISTS `login_test`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `login_test`(in username varchar(255),in psw varchar(255), in tkt VARCHAR(255), out st int)
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
end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for mdftag
-- ----------------------------
DROP PROCEDURE IF EXISTS `mdftag`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `mdftag`()
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

end
;;
DELIMITER ;

-- ----------------------------
-- Procedure structure for saveHot
-- ----------------------------
DROP PROCEDURE IF EXISTS `saveHot`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `saveHot`(in mon varchar(255), in yer varchar(255))
BEGIN
		set @sql=concat("select id, title into @article_id, @article_title from article where created_date like '%", yer, "-", mon,
		"%' order by id limit 1");
		PREPARE execsql from @sql;
		EXECUTE execsql;
		insert into archive(`article_id`, `article_title`, `month`, `year`) values (@article_id, @article_title, mon, yer);
	end
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `trig3`;
DELIMITER ;;
CREATE TRIGGER `trig3` BEFORE INSERT ON `article` FOR EACH ROW set NEW.created_date = NOW()
;;
DELIMITER ;
DROP TRIGGER IF EXISTS `delArticle`;
DELIMITER ;;
CREATE TRIGGER `delArticle` AFTER DELETE ON `article` FOR EACH ROW BEGIN
 DELETE FROM `comment` WHERE article_id = OLD.id;#更新评论表
 SELECT tag_id FROM article_tag WHERE article_id = OLD.id INTO @tag_id;#先选出文章标签表中标签的id
 DELETE FROM article_tag WHERE article_id = OLD.id; #更新文章-标签表
 UPDATE tag SET tag.count = tag.count - 1 WHERE id = @tag_id; #更新标签表;
 DELETE FROM tag WHERE count = 0;											 			#如果标签数量为0就该删除了
END
;;
DELIMITER ;
