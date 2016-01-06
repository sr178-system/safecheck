/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : safe_check

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-01-06 17:12:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `user_name` varchar(32) NOT NULL,
  `pass_word` varchar(32) NOT NULL,
  `name` varchar(16) DEFAULT '',
  `sex` int(11) DEFAULT '1',
  `birthday` date DEFAULT NULL,
  `call` varchar(32) DEFAULT '',
  `remark` varchar(512) DEFAULT NULL,
  `up_user` varchar(32) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('admin', 'FoxpBwf+x4bDmWQtNCx7vojz8Oc=', '超级管理员', '1', '2015-09-11', '111', '超级管理员', '', '0');

-- ----------------------------
-- Table structure for check_items
-- ----------------------------
DROP TABLE IF EXISTS `check_items`;
CREATE TABLE `check_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_title` varchar(128) NOT NULL,
  `item_content` text NOT NULL,
  `add_time` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of check_items
-- ----------------------------
INSERT INTO `check_items` VALUES ('1', 'asdfasfasfdsaf', 'asdfasf', '2015-10-09 09:51:10');
INSERT INTO `check_items` VALUES ('2', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗城市的月光还有很多人在那里呢你们知道吗', '2015-10-09 09:51:50');
INSERT INTO `check_items` VALUES ('3', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '2015-10-09 09:52:58');
INSERT INTO `check_items` VALUES ('4', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '2015-10-09 09:53:33');
INSERT INTO `check_items` VALUES ('5', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '2015-10-09 09:53:37');
INSERT INTO `check_items` VALUES ('6', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '2015-10-09 10:23:13');
INSERT INTO `check_items` VALUES ('7', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很', '2015-10-09 10:23:16');
INSERT INTO `check_items` VALUES ('8', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很123', '城市的月光还有很多人在那里呢你们知道吗城市的月光还有很123', '2015-10-09 10:23:23');

-- ----------------------------
-- Table structure for check_record
-- ----------------------------
DROP TABLE IF EXISTS `check_record`;
CREATE TABLE `check_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cp_name` varchar(32) NOT NULL,
  `check_items` varchar(64) NOT NULL,
  `check_username` varchar(32) NOT NULL,
  `checker_name` varchar(32) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `position` varchar(64) NOT NULL,
  `check_time` datetime NOT NULL,
  `check_server_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cp_Name` (`cp_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of check_record
-- ----------------------------

-- ----------------------------
-- Table structure for enforce_record
-- ----------------------------
DROP TABLE IF EXISTS `enforce_record`;
CREATE TABLE `enforce_record` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cp_name` varchar(32) NOT NULL,
  `enforce_username` varchar(32) NOT NULL,
  `enforce_name` varchar(32) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `enforce_time` datetime NOT NULL,
  `enforce_server_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `cp_name` (`cp_name`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enforce_record
-- ----------------------------
INSERT INTO `enforce_record` VALUES ('1', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('2', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('3', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('4', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('5', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('6', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('7', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('8', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('9', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('10', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('11', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('12', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('13', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('14', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('15', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('16', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('17', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('18', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('19', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('20', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('21', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');
INSERT INTO `enforce_record` VALUES ('22', '1', 'xx_down', '测试账号', '1', '2015-10-09 10:28:30', '2015-10-09 10:28:32');

-- ----------------------------
-- Table structure for notice
-- ----------------------------
DROP TABLE IF EXISTS `notice`;
CREATE TABLE `notice` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `notice_title` varchar(128) NOT NULL,
  `notice_content` text NOT NULL,
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '是否顶置 1 顶置  0不顶置',
  `add_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `status,add_time` (`status`,`add_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------

-- ----------------------------
-- Table structure for resource
-- ----------------------------
DROP TABLE IF EXISTS `resource`;
CREATE TABLE `resource` (
  `resource_id` int(11) NOT NULL AUTO_INCREMENT,
  `resource_1_names` varchar(512) DEFAULT NULL,
  `resource_2_names` varchar(512) DEFAULT NULL,
  `resource_3_names` varchar(512) DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '1d763b5d6062c691.jpg', '1d763b5d6062c691.jpg', '1d763b5d6062c691.jpg', '2015-10-09 10:29:38', '2015-10-09 10:29:41');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_name` varchar(32) NOT NULL,
  `pass_word` varchar(32) NOT NULL,
  `name` varchar(16) DEFAULT NULL,
  `sex` int(11) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `call` varchar(32) DEFAULT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `up_user` varchar(32) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('xx_down', 'LpkBu6U7/DzcaD1DryBisxxpVBg=', '测试账号', '1', '2015-10-07', '', '测试账号', 'admin', '0');

-- ----------------------------
-- Table structure for version
-- ----------------------------
DROP TABLE IF EXISTS `version`;
CREATE TABLE `version` (
  `version_num` int(11) NOT NULL DEFAULT '0',
  `url` varchar(1024) NOT NULL,
  PRIMARY KEY (`version_num`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of version
-- ----------------------------
INSERT INTO `version` VALUES ('2', 'http://xxxx');
