#drop database if exists bookStore2;
#create database bookStore2;
#use bookStore2;

#用户表
CREATE TABLE `user` (
  `id` INT(11) AUTO_INCREMENT,
  `username` VARCHAR(20) ,
  `PASSWORD` VARCHAR(20) ,
  `gender` VARCHAR(10) ,
  `email` VARCHAR(50) ,
  `telephone` VARCHAR(20) ,
  `introduce` VARCHAR(100),
  `activeCode` VARCHAR(50) ,
  `state` INT(11) ,
  `role` VARCHAR(10),
  `registTime` TIMESTAMP ,
  PRIMARY KEY (`id`)
);
#商品表
CREATE TABLE `products` (
  `id` VARCHAR(100) ,
  `name` VARCHAR(40) ,
  `price` DOUBLE ,
  `category` VARCHAR(40) ,
  `pnum` INT(11) ,
  `imgurl` VARCHAR(100) ,
  `description` VARCHAR(255) ,
  PRIMARY KEY (`id`)
);
#订单表
CREATE TABLE `orders` (
  `id` VARCHAR(100) ,
  `money` DOUBLE ,
  `receiverAddress` VARCHAR(255) ,
  `receiverName` VARCHAR(20) ,
  `receiverPhone` VARCHAR(20) ,
  `paystate` INT(11) ,
  `ordertime` TIMESTAMP ,
  `user_id` INT(11) ,
  PRIMARY KEY (`id`),
  FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
);

#订单详情表
CREATE TABLE `orderitem` (
  `order_id` VARCHAR(100) ,
  `product_id` VARCHAR(100),
  `buynum` INT(11) ,
  PRIMARY KEY (`order_id`,`product_id`),
  FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
);