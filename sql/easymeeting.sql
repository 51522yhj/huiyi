/*
 Navicat Premium Data Transfer

 Source Server         : test
 Source Server Type    : MySQL
 Source Server Version : 80042 (8.0.42)
 Source Host           : localhost:3306
 Source Schema         : easymeeting

 Target Server Type    : MySQL
 Target Server Version : 80042 (8.0.42)
 File Encoding         : 65001

 Date: 25/07/2025 00:43:51
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for meeting_info
-- ----------------------------
DROP TABLE IF EXISTS `meeting_info`;
CREATE TABLE `meeting_info`  (
  `meeting_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会议ID',
  `meeting_no` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会议号',
  `meeting_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会议名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '创建者ID',
  `join_type` tinyint(1) NULL DEFAULT NULL COMMENT '入会类型',
  `join_password` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '入会密码',
  `start_time` datetime NULL DEFAULT NULL COMMENT '会议开始时间',
  `end_time` datetime NULL DEFAULT NULL COMMENT '会议结束时间',
  `status` int NULL DEFAULT NULL COMMENT '会议状态',
  PRIMARY KEY (`meeting_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting_info
-- ----------------------------
INSERT INTO `meeting_info` VALUES ('0366961876', '4871122598', 'test', '2025-07-19 23:59:11', '992475911179', 0, NULL, '2025-07-19 23:59:11', NULL, 0);
INSERT INTO `meeting_info` VALUES ('1384210128', '4871122598', 'test', '2025-07-18 22:22:10', '992475911179', 0, NULL, '2025-07-18 22:22:10', NULL, 0);
INSERT INTO `meeting_info` VALUES ('1878314818', '0802759147', '啊啊', '2025-07-18 00:40:07', '282957604653', 0, NULL, '2025-07-18 00:40:07', NULL, 0);
INSERT INTO `meeting_info` VALUES ('2494394235', '4871122598', '99', '2025-07-18 00:54:45', '992475911179', 0, NULL, '2025-07-18 00:54:45', NULL, 0);
INSERT INTO `meeting_info` VALUES ('2972150189', '0802759147', 'tets', '2025-07-18 00:13:41', '282957604653', 0, NULL, '2025-07-18 00:13:41', NULL, 1);
INSERT INTO `meeting_info` VALUES ('3229877876', '4871122598', '222', '2025-07-18 00:57:02', '992475911179', 0, NULL, '2025-07-18 00:57:02', NULL, 0);
INSERT INTO `meeting_info` VALUES ('3997934508', '0802759147', 'test', '2025-07-17 23:38:38', '282957604653', 0, NULL, '2025-07-17 23:38:38', NULL, 1);
INSERT INTO `meeting_info` VALUES ('4751490836', '0802759147', 'test', '2025-07-17 23:44:48', '282957604653', 0, NULL, '2025-07-17 23:44:48', NULL, 1);
INSERT INTO `meeting_info` VALUES ('5459339015', '0802759147', 'test', '2025-07-18 00:06:32', '282957604653', 0, NULL, '2025-07-18 00:06:32', NULL, 1);
INSERT INTO `meeting_info` VALUES ('5582672238', '0802759147', 'test', '2025-07-18 00:24:08', '282957604653', 0, NULL, '2025-07-18 00:24:08', NULL, 0);
INSERT INTO `meeting_info` VALUES ('6066654817', '0802759147', '111', '2025-07-18 00:42:34', '282957604653', 0, NULL, '2025-07-18 00:42:34', NULL, 0);
INSERT INTO `meeting_info` VALUES ('7602312432', '0802759147', 'test', '2025-07-17 23:26:16', '282957604653', 0, NULL, '2025-07-17 23:26:16', NULL, 1);
INSERT INTO `meeting_info` VALUES ('7816952336', '0802759147', 't4est', '2025-07-17 23:48:12', '282957604653', 0, NULL, '2025-07-17 23:48:12', NULL, 1);
INSERT INTO `meeting_info` VALUES ('8583439721', '0802759147', 'test', '2025-07-17 23:51:43', '282957604653', 0, NULL, '2025-07-17 23:51:43', NULL, 1);

-- ----------------------------
-- Table structure for meeting_member
-- ----------------------------
DROP TABLE IF EXISTS `meeting_member`;
CREATE TABLE `meeting_member`  (
  `meeting_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '会议ID',
  `user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '入会用户ID',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `last_join_time` datetime NULL DEFAULT NULL COMMENT '最后加入时间',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `member_type` tinyint(1) NULL DEFAULT NULL COMMENT '成员类型',
  `meeting_status` tinyint NULL DEFAULT NULL COMMENT '会议状态',
  PRIMARY KEY (`meeting_id`, `user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting_member
-- ----------------------------
INSERT INTO `meeting_member` VALUES ('0366961876', '282957604653', 'Nick', '2025-07-19 23:59:23', 1, 0, 0);
INSERT INTO `meeting_member` VALUES ('0366961876', '992475911179', 'mary', '2025-07-19 23:59:11', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('1384210128', '282957604653', 'Nick', '2025-07-18 22:25:16', 1, 0, 0);
INSERT INTO `meeting_member` VALUES ('1384210128', '992475911179', 'mary', '2025-07-18 22:22:11', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('1878314818', '282957604653', 'Nick', '2025-07-18 00:40:08', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('2494394235', '992475911179', 'mary', '2025-07-18 00:54:55', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('2972150189', '282957604653', 'Nick', '2025-07-18 00:18:30', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('2972150189', '992475911179', 'mary', '2025-07-18 00:18:30', 1, 0, 0);
INSERT INTO `meeting_member` VALUES ('3229877876', '282957604653', 'Nick', '2025-07-18 00:58:33', 1, 0, 0);
INSERT INTO `meeting_member` VALUES ('3229877876', '992475911179', 'mary', '2025-07-18 00:57:08', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('3997934508', '282957604653', 'Nick', '2025-07-17 23:38:39', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('4751490836', '282957604653', 'Nick', '2025-07-17 23:44:49', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('5459339015', '282957604653', 'Nick', '2025-07-18 00:06:32', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('5459339015', '992475911179', 'mary', '2025-07-18 00:09:19', 1, 0, 0);
INSERT INTO `meeting_member` VALUES ('5582672238', '282957604653', 'Nick', '2025-07-18 00:24:09', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('5582672238', '992475911179', 'mary', '2025-07-18 00:24:35', 1, 0, 0);
INSERT INTO `meeting_member` VALUES ('6066654817', '282957604653', 'Nick', '2025-07-18 00:42:43', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('7602312432', '282957604653', 'Nick', '2025-07-17 23:26:17', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('7816952336', '282957604653', 'Nick', '2025-07-17 23:48:12', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('8583439721', '282957604653', 'Nick', '2025-07-17 23:51:44', 1, 1, 0);
INSERT INTO `meeting_member` VALUES ('8583439721', '992475911179', 'mary', '2025-07-17 23:58:57', 1, 0, 0);

-- ----------------------------
-- Table structure for meeting_reserve
-- ----------------------------
DROP TABLE IF EXISTS `meeting_reserve`;
CREATE TABLE `meeting_reserve`  (
  `meeting_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `meeting_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `join_type` tinyint(1) NULL DEFAULT NULL,
  `join_password` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `duration` int NULL DEFAULT NULL,
  `start_time` datetime NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `create_user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`meeting_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting_reserve
-- ----------------------------
INSERT INTO `meeting_reserve` VALUES ('7482837147', 'test', 0, NULL, 30, '2025-07-23 01:24:00', '2025-07-23 01:25:03', '282957604653', 0);

-- ----------------------------
-- Table structure for meeting_reserve_member
-- ----------------------------
DROP TABLE IF EXISTS `meeting_reserve_member`;
CREATE TABLE `meeting_reserve_member`  (
  `meeting_id` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `invite_user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`meeting_id`, `invite_user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of meeting_reserve_member
-- ----------------------------
INSERT INTO `meeting_reserve_member` VALUES ('7482837147', '282957604653');

-- ----------------------------
-- Table structure for user_contact
-- ----------------------------
DROP TABLE IF EXISTS `user_contact`;
CREATE TABLE `user_contact`  (
  `user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `contact_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  `last_update_time` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`user_id`, `contact_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_contact
-- ----------------------------

-- ----------------------------
-- Table structure for user_contact_apply
-- ----------------------------
DROP TABLE IF EXISTS `user_contact_apply`;
CREATE TABLE `user_contact_apply`  (
  `apply_id` int NOT NULL,
  `apply_user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `receive_user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `last_apply_time` bigint NULL DEFAULT NULL,
  `status` tinyint(1) NULL DEFAULT NULL,
  PRIMARY KEY (`apply_id`) USING BTREE,
  UNIQUE INDEX `idx_key`(`apply_user_id` ASC, `receive_user_id` ASC) USING BTREE,
  INDEX `idx_last_apply_time`(`last_apply_time` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_contact_apply
-- ----------------------------

-- ----------------------------
-- Table structure for user_info
-- ----------------------------
DROP TABLE IF EXISTS `user_info`;
CREATE TABLE `user_info`  (
  `user_id` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户ID',
  `email` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `nick_name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `sex` tinyint(1) NULL DEFAULT NULL COMMENT '性别 0：女1：男',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `status` tinyint(1) NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `last_login_time` bigint NULL DEFAULT NULL COMMENT '最后登录时间',
  `last_off_time` bigint NULL DEFAULT NULL COMMENT '最后离开时间',
  `meeting_no` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个人会议号',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of user_info
-- ----------------------------
INSERT INTO `user_info` VALUES ('117435802883', '8794069882@qq.com', '777', NULL, '96a5d7cf59d55924a56d1d5d0841d01a', 1, '2025-07-12 01:48:00', 1752256080024, 1752256080024, '5505017900');
INSERT INTO `user_info` VALUES ('215025982049', '879406927as@qq.com', 'require', NULL, '96a5d7cf59d55924a56d1d5d0841d01a', 1, '2025-07-12 15:12:37', 1752304357349, 1752304358000, '4133462106');
INSERT INTO `user_info` VALUES ('282957604653', '13840526193@163.com', 'Nick', NULL, '94e8cde4612b3fd390677d42e7b22002', 1, '2025-07-14 21:41:39', 1753206269212, 1753204609078, '0802759147');
INSERT INTO `user_info` VALUES ('562566903909', '87940692@qq.com', NULL, NULL, '96a5d7cf59d55924a56d1d5d0841d01a', 1, '2025-07-12 01:41:41', 1752255700644, 1752255700644, '2349378064');
INSERT INTO `user_info` VALUES ('581729426685', '879406927@qq.com', 'require', NULL, '96a5d7cf59d55924a56d1d5d0841d01a', 1, '2025-07-12 01:39:44', 1752255583840, 1752255583840, '4320642466');
INSERT INTO `user_info` VALUES ('992475911179', '879406927@163.com', 'mary', NULL, 'f2b703ebb125fff045e297ac1d92bbf6', 1, '2025-07-17 23:29:13', 1752945335592, 1752862812245, '4871122598');

SET FOREIGN_KEY_CHECKS = 1;
