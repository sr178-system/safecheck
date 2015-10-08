/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : safe_check

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-10-08 12:03:09
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
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enforce_record
-- ----------------------------

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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------

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
