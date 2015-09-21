/*
Navicat MySQL Data Transfer

Source Server         : 127.0.0.1
Source Server Version : 50624
Source Host           : localhost:3306
Source Database       : safe_check

Target Server Type    : MYSQL
Target Server Version : 50624
File Encoding         : 65001

Date: 2015-09-21 21:51:07
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for admin_user
-- ----------------------------
DROP TABLE IF EXISTS `admin_user`;
CREATE TABLE `admin_user` (
  `user_name` varchar(32) NOT NULL,
  `pass_word` varchar(32) NOT NULL,
  `name` varchar(16) NOT NULL,
  `sex` int(11) NOT NULL,
  `birthday` date NOT NULL,
  `call` varchar(32) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `up_user` varchar(32) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of admin_user
-- ----------------------------
INSERT INTO `admin_user` VALUES ('aa', 'LpkBu6U7/DzcaD1DryBisxxpVBg=', 'aa', '1', '2015-09-16', 'aa', 'aa', 'xx', '0');
INSERT INTO `admin_user` VALUES ('xx', 'LpkBu6U7/DzcaD1DryBisxxpVBg=', 'xx', '1', '2015-09-11', '111', '1', '', '0');
INSERT INTO `admin_user` VALUES ('yy', 'yy', 'yy', '1', '2015-09-16', '11', '1', 'xx', '1');
INSERT INTO `admin_user` VALUES ('zz', 'zz', 'zz', '1', '2015-09-16', 'zz', 'zz', 'xx', '1');

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
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of check_items
-- ----------------------------
INSERT INTO `check_items` VALUES ('1', 'adfasdfas', 'asdfasdf我是这个世界的主人', '2015-09-18 20:33:17');
INSERT INTO `check_items` VALUES ('2', '2', '2', '2015-09-18 20:33:24');

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
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of check_record
-- ----------------------------
INSERT INTO `check_record` VALUES ('1', '俗人工作室', '1,2', 'xxx', '1', '4', '112.914604,28.232497', '2015-09-11 12:53:40', '2015-09-11 12:56:00');
INSERT INTO `check_record` VALUES ('2', 'cp2', 'xxx', 'aa_down', '刘德华', '4', '112.914604,28.232497', '2015-09-11 11:11:45', '2015-09-17 11:11:49');
INSERT INTO `check_record` VALUES ('3', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-08 11:12:19', '2015-09-08 11:12:19');
INSERT INTO `check_record` VALUES ('4', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-07 11:12:19', '2015-09-07 11:12:19');
INSERT INTO `check_record` VALUES ('5', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-06 11:12:19', '2015-09-06 11:12:19');
INSERT INTO `check_record` VALUES ('6', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-05 11:12:19', '2015-09-05 11:12:19');
INSERT INTO `check_record` VALUES ('7', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-04 11:12:19', '2015-09-04 11:12:19');
INSERT INTO `check_record` VALUES ('8', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-03 11:12:19', '2015-09-03 11:12:19');
INSERT INTO `check_record` VALUES ('9', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-02 11:12:19', '2015-09-02 11:12:19');
INSERT INTO `check_record` VALUES ('10', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-09-01 11:12:19', '2015-09-01 11:12:19');
INSERT INTO `check_record` VALUES ('11', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-08-08 11:12:19', '2015-08-08 11:12:19');
INSERT INTO `check_record` VALUES ('12', 'cp2', 'xxx', 'aa_down', '刘', '4', '112.914604,28.232497', '2015-08-07 11:12:19', '2015-08-07 11:12:19');

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
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of enforce_record
-- ----------------------------
INSERT INTO `enforce_record` VALUES ('1', '俗人工作室', 'xx', 'xx_name', '5', '2015-09-11 12:53:41', '2015-09-11 12:57:41');
INSERT INTO `enforce_record` VALUES ('2', '俗人工作室', 'yy', 'yy_name', '5', '2015-09-12 11:50:45', '2015-09-12 11:50:51');
INSERT INTO `enforce_record` VALUES ('3', '几把工作室', 'zz', 'zz_name', '5', '2015-09-01 11:16:30', '2015-09-16 11:16:34');
INSERT INTO `enforce_record` VALUES ('4', '几把二工作室', 'aa_down', 'zz_name', '5', '2015-10-01 11:16:55', '2015-08-01 11:16:57');

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
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of notice
-- ----------------------------
INSERT INTO `notice` VALUES ('9', 'asdf111', 'asdf', '1', '2015-09-18 21:10:34');
INSERT INTO `notice` VALUES ('10', 'ttt', 'ttt', '0', '2015-09-19 16:07:46');
INSERT INTO `notice` VALUES ('11', 'tt2', 'tt2', '2', '2015-09-19 16:07:57');

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
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of resource
-- ----------------------------
INSERT INTO `resource` VALUES ('1', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', null, '2015-09-10 13:26:45');
INSERT INTO `resource` VALUES ('2', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', null, '2015-09-10 13:28:23');
INSERT INTO `resource` VALUES ('3', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '2015-09-10 13:29:10', '2015-09-10 13:29:10');
INSERT INTO `resource` VALUES ('4', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '2015-09-11 14:04:12', '2015-09-11 12:56:00');
INSERT INTO `resource` VALUES ('5', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '20b545561a9fb741.jpg', '2015-09-11 12:57:41', '2015-09-11 12:57:41');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_name` varchar(32) NOT NULL,
  `pass_word` varchar(32) NOT NULL,
  `name` varchar(16) NOT NULL,
  `sex` int(11) NOT NULL,
  `birthday` date NOT NULL,
  `call` varchar(32) NOT NULL,
  `remark` varchar(512) DEFAULT NULL,
  `up_user` varchar(32) DEFAULT NULL,
  `status` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`user_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES ('aa_down', 'aa', 'aa_name', '1', '2015-09-16', 'aa_down', 'aa_down', 'aa', '0');
INSERT INTO `user` VALUES ('xx_down', 'LpkBu6U7/DzcaD1DryBisxxpVBg=', 'xx_name', '1', '2015-09-10', '111', '11', 'xx', '0');
INSERT INTO `user` VALUES ('yy_down', 'yy', 'yy_name', '1', '2015-09-16', 'yy_down', 'yy_down', 'yy', '0');
