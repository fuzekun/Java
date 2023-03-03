/*
Navicat MySQL Data Transfer

Source Server         : locahost
Source Server Version : 80013
Source Host           : localhost:3306
Source Database       : blog

Target Server Type    : MYSQL
Target Server Version : 80013
File Encoding         : 65001

Date: 2019-12-18 10:21:05
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
  `month` varchar(20) NOT NULL,
  `year` varchar(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  CONSTRAINT `archive_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of archive
-- ----------------------------
INSERT INTO `archive` VALUES ('1', '2', '你好', '12', '2019');

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
  KEY `tm` (`created_date`),
  KEY `compt` (`id`,`category`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of article
-- ----------------------------
INSERT INTO `article` VALUES ('2', '数据库', '0', '超级兵', '2019-12-12 18:38:16', null, '你好', '0');

-- ----------------------------
-- Table structure for article_tag
-- ----------------------------
DROP TABLE IF EXISTS `article_tag`;
CREATE TABLE `article_tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `article_id` int(11) NOT NULL,
  `tag_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `article_id` (`article_id`),
  KEY `tag_id` (`tag_id`),
  CONSTRAINT `article_tag_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `article_tag_ibfk_2` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of article_tag
-- ----------------------------

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
  KEY `user_id` (`user_id`),
  CONSTRAINT `comment_ibfk_1` FOREIGN KEY (`article_id`) REFERENCES `article` (`id`),
  CONSTRAINT `comment_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of comment
-- ----------------------------

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
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `login_ticket_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of login_ticket
-- ----------------------------

-- ----------------------------
-- Table structure for tag
-- ----------------------------
DROP TABLE IF EXISTS `tag`;
CREATE TABLE `tag` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of tag
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('1', null, null, '123', null, null, 'fuzekun');

-- ----------------------------
-- Procedure structure for copyArticle
-- ----------------------------
DROP PROCEDURE IF EXISTS `copyArticle`;
DELIMITER ;;
CREATE DEFINER=`root`@`localhost` PROCEDURE `copyArticle`(in mon varchar(255))
begin
	 set @sql=concat("select * from article where created_date like '%", mon, "%'", " into outfile 'D:/blogtestdata/tmp",curdate(),".txt'");
	 select @sql;
	 prepare execsql from @sql;
	 execute execsql;
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
