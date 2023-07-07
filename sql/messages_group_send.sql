/*
 Navicat Premium Data Transfer

 Source Server         : wyd
 Source Server Type    : MySQL
 Source Server Version : 50651
 Source Host           : 192.168.30.43:3306
 Source Schema         : tmsp

 Target Server Type    : MySQL
 Target Server Version : 50651
 File Encoding         : 65001

 Date: 07/07/2023 13:54:33
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for messages_group_send
-- ----------------------------
DROP TABLE IF EXISTS `messages_group_send`;
CREATE TABLE `messages_group_send`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '发送内容',
  `phones_count` int(11) NULL DEFAULT NULL COMMENT '号码数',
  `send_count` int(11) NULL DEFAULT NULL COMMENT '成功发送的数量',
  `unknown_count` int(11) NULL DEFAULT NULL COMMENT '未知是否发送成功数量',
  `user_name` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `create_date` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `phone_numbers` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '需要发送短信的手机号',
  `fail_phone_numbers` varchar(1500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '没能成功发出短信的手机号（包含失败和未知的）',
  `finish_time` datetime(0) NULL DEFAULT NULL COMMENT '完成发送时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1676538405903327234 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '群发短信发送情况' ROW_FORMAT = Compact;

SET FOREIGN_KEY_CHECKS = 1;
