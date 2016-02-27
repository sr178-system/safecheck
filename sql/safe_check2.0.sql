/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : safe_check_v2

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2016-02-27 23:24:27
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
  `depart_ment` varchar(32) DEFAULT NULL COMMENT '部门',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('admin', 'FoxpBwf+x4bDmWQtNCx7vojz8Oc=', '超级管理员', '1', '2015-09-11', '111', '超级管理员', '', '0', null);

-- ----------------------------
-- Table structure for check_items
-- ----------------------------
DROP TABLE IF EXISTS `check_items`;
CREATE TABLE `check_items` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `item_title` varchar(128) NOT NULL,
  `add_time` datetime NOT NULL,
  `parent_id` int(11) NOT NULL DEFAULT '0',
  `depart_ment` varchar(32) DEFAULT NULL,
  `layer` int(11) NOT NULL DEFAULT '0' COMMENT '层',
  `last_edit_name` varchar(32) DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  `success_or_fail` tinyint(4) DEFAULT NULL,
  `status` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `check_item_id` int(64) NOT NULL,
  `check_result` text,
  `check_username` varchar(32) NOT NULL,
  `checker_name` varchar(32) NOT NULL,
  `resource_id` int(11) NOT NULL,
  `position` varchar(64) NOT NULL,
  `check_time` datetime NOT NULL,
  `check_server_time` datetime NOT NULL,
  `res_person_name` varchar(32) DEFAULT NULL,
  `res_person_call` varchar(32) DEFAULT NULL,
  `pass_status` tinyint(4) NOT NULL DEFAULT '0',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `status` int(11) NOT NULL DEFAULT '0' COMMENT '0 停用   1启用   2顶置',
  `add_time` datetime NOT NULL,
  `attach_ment` text,
  `depart_ment` varchar(32) DEFAULT NULL,
  `edit_time` datetime DEFAULT NULL,
  `last_edit_name` varchar(32) DEFAULT NULL,
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
  `resource_4_names` varchar(512) DEFAULT NULL,
  `updated_time` datetime DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

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
  `depart_ment` varchar(32) DEFAULT NULL,
  `last_edit_name` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('xx_down', 'LpkBu6U7/DzcaD1DryBisxxpVBg=', '执法测试', '1', null, '15919820372', '备注', null, '0', '安监局', '超级管理员');

-- ----------------------------
-- Table structure for user_notice_readlog
-- ----------------------------
DROP TABLE IF EXISTS `user_notice_readlog`;
CREATE TABLE `user_notice_readlog` (
  `user_name` varchar(32) DEFAULT NULL,
  `notice_id` int(11) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  UNIQUE KEY `user_name_notice_id` (`user_name`,`notice_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user_notice_readlog
-- ----------------------------

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
