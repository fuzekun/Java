/*
Navicat MySQL Data Transfer

Source Server         : locahost
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : blogtest

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-12-17 22:52:19
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
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

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
	 set @sql=concat("select * from user into outfile 'D:/blogtestdata/tmp",curdate(),".txt'");
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
