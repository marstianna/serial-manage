CREATE DATABASE `serial_manage` /*!40100 DEFAULT CHARACTER SET UTF8 */;
CREATE TABLE `admin` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `login_name` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `login_password` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `order` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL DEFAULT '0',
  `pay_type` tinyint(4) NOT NULL DEFAULT '0',
  `card_id` varchar(45) NOT NULL DEFAULT '0',
  `paper_price` int(11) NOT NULL DEFAULT '0',
  `actual_price` int(11) NOT NULL DEFAULT '0',
  `drink_price` int(11) NOT NULL DEFAULT '0',
  `food_price` int(11) NOT NULL DEFAULT '0',
  `other_price` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `queue_up` tinyint(4) NOT NULL DEFAULT '0',
  `table_code` varchar(45) NOT NULL DEFAULT '',
  `received` int(11) NOT NULL DEFAULT '0',
  `count_of_people` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;


CREATE TABLE `points` (
  `vip_id` int(11) NOT NULL,
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1消费获得积分,2奖励积分',
  `balance_points` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`vip_id`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
;

CREATE TABLE `owner` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `phone` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `mobilephone` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `login_name` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `login_password` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `identity` varchar(19) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `email` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `login_name` (`login_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

CREATE TABLE `points_gift` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cost` int(11) NOT NULL DEFAULT '0',
  `stock` int(11) NOT NULL DEFAULT '0',
  `name` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `description` varchar(255) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `store_id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `points_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `vip_id` int(11) NOT NULL DEFAULT '0',
  `operate` tinyint(4) NOT NULL DEFAULT '0',
  `points_account` int(11) NOT NULL DEFAULT '0',
  `store_id` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `promotion` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `enough` int(11) NOT NULL DEFAULT '0',
  `discount` int(11) NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1满减,2满折',
  `store_id` int(11) NOT NULL DEFAULT '0' COMMENT 'storeId为0时,为全场规则',
  `start_time` int(11) NOT NULL DEFAULT '0',
  `end_time` int(11) NOT NULL DEFAULT '0',
  `pay_type` tinyint(4) NOT NULL DEFAULT '0' COMMENT '0储值卡,1现金,2支付宝,3微信,4团购优惠支付',
  `valid` tinyint(4) NOT NULL DEFAULT '1' COMMENT '是否有效,1有效,0无效',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `setting` (
  `key` varchar(45) CHARACTER SET UTF8 NOT NULL,
  `value` text CHARACTER SET UTF8 NOT NULL,
  PRIMARY KEY (`key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `staff` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `mobilephone` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `identity` varchar(19) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `email` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `staff_level` tinyint(4) NOT NULL DEFAULT '1' COMMENT '1.普通员工,2.大堂经理,3店长',
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `store` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_name` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `address` varchar(255) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `phone` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `owner_id` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `store_table` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `store_id` int(11) NOT NULL DEFAULT '0',
  `default_number` int(11) NOT NULL DEFAULT '0',
  `table_code` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `value_card` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `card_uuid` varchar(63) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `balance` int(11) NOT NULL DEFAULT '0',
  `vip_id` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  `password` varchar(63) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `card_id` (`card_id`,`card_uuid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `value_card_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `card_id` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `operate` tinyint(4) NOT NULL DEFAULT '0',
  `type` tinyint(4) NOT NULL DEFAULT '0',
  `store_id` int(11) NOT NULL DEFAULT '0',
  `account` int(11) NOT NULL DEFAULT '0',
  `price` int(11) NOT NULL DEFAULT '0',
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `vip_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `store_id` int(11) NOT NULL DEFAULT '0',
  `mobilephone` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `wechat` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `email` varchar(45) CHARACTER SET UTF8 NOT NULL DEFAULT '',
  `create_time` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobilephone_UNIQUE` (`mobilephone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

