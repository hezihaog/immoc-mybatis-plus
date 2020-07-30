/*
 Navicat Premium Data Transfer

 Source Server         : mac
 Source Server Type    : MySQL
 Source Server Version : 50716
 Source Host           : localhost:3306
 Source Schema         : mp

 Target Server Type    : MySQL
 Target Server Version : 50716
 File Encoding         : 65001

 Date: 30/07/2020 23:12:46
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mp_user
-- ----------------------------
DROP TABLE IF EXISTS `mp_user`;
CREATE TABLE `mp_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(30) DEFAULT NULL COMMENT '姓名',
  `age` int(11) DEFAULT NULL COMMENT '年龄',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `manager_id` bigint(20) DEFAULT NULL COMMENT '直属上级id',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `manager_fk` (`manager_id`),
  CONSTRAINT `manager_fk` FOREIGN KEY (`manager_id`) REFERENCES `mp_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of mp_user
-- ----------------------------
BEGIN;
INSERT INTO `mp_user` VALUES (1087982257332887553, '大boss', 40, 'boss@baomidou.com', NULL, '2019-01-11 14:20:20');
INSERT INTO `mp_user` VALUES (1088248166370832385, '王天风', 26, 'wtf2@baomidou.com', 1087982257332887553, '2019-02-05 11:12:22');
INSERT INTO `mp_user` VALUES (1088250446457389058, '李艺伟', 32, 'lyw2019@baomidou.com', 1088248166370832385, '2019-02-14 08:31:16');
INSERT INTO `mp_user` VALUES (1094590409767661570, '张雨琪', 31, 'zjq@baomidou.com', 1088248166370832385, '2019-01-14 09:15:15');
INSERT INTO `mp_user` VALUES (1094592041087729666, '刘红雨', 32, 'lhm@baomidou.com', 1088248166370832385, '2019-01-14 09:48:16');
INSERT INTO `mp_user` VALUES (1288460195017072641, '刘明强', 31, NULL, 1088248166370832385, '2020-07-29 21:03:44');
INSERT INTO `mp_user` VALUES (1288842439342780417, '张强', 29, 'lh@baomidou.com', 1088248166370832385, '2020-07-30 22:23:33');
INSERT INTO `mp_user` VALUES (1288848935841488897, '刘花', 29, 'lh@baomidou.com', 1088248166370832385, '2020-07-30 22:48:27');
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
