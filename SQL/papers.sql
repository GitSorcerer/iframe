/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50544
 Source Host           : localhost:3306
 Source Schema         : assets_management

 Target Server Type    : MySQL
 Target Server Version : 50544
 File Encoding         : 65001

 Date: 15/07/2019 17:18:16
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for papers
-- ----------------------------
DROP TABLE IF EXISTS `papers`;
CREATE TABLE `papers`  (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `objId` varchar(60) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件对象的Id 合同对象的Id或者UUid',
  `objType` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件对象的类型  经营  外包',
  `filename` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件名称',
  `filetype` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件类型(01 图片 02 PDF)',
  `filesize` int(10) NULL DEFAULT NULL COMMENT '文件大小  KB',
  `fileurl` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '文件路径  相对路径',
  `filepath` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '所在文件夹',
  `suffixname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '后缀名 pdf jpg',
  `createDate` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `reserve1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段1',
  `reserve2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段2',
  `reserve3` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '扩展字段3',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 61 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Compact;

-- ----------------------------
-- Records of papers
-- ----------------------------
INSERT INTO `papers` VALUES (33, 'B96ACA83-05F6-46D6-AFC3-9BC46C18DE031562913100332', '经营合同', '4.jpg', 'JPG', 15, '\\operate\\2019-07-12\\4.jpg', 'operate\\2019-07-12', 'jpg', '2019-07-12 14:31:40', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (34, 'B96ACA83-05F6-46D6-AFC3-9BC46C18DE031562913100332', '经营合同', 'Copy_Screenshots.pdf', 'PDF', 1274, '\\operate\\2019-07-12\\Copy_Screenshots.pdf', 'operate\\2019-07-12', 'pdf', '2019-07-12 14:31:40', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (35, 'B96ACA83-05F6-46D6-AFC3-9BC46C18DE031562913100332', '经营合同', '数据字典-智慧社区20190402-WZ.doc', 'DOC', 491, '\\operate\\2019-07-12\\数据字典-智慧社区20190402-WZ.doc', 'operate\\2019-07-12', 'doc', '2019-07-12 14:31:40', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (36, 'B96ACA83-05F6-46D6-AFC3-9BC46C18DE031562913100332', '经营合同', '完成功能.docx', 'DOCX', 481, '\\operate\\2019-07-12\\完成功能.docx', 'operate\\2019-07-12', 'docx', '2019-07-12 14:31:40', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (38, '1A6B0E59-E75A-4209-997A-0B3A484E1DE71562915162420', '经营合同', '副本.pdf', 'PDF', 1274, '\\operate\\2019-07-12\\副本.pdf', 'operate\\2019-07-12', 'pdf', '2019-07-12 15:06:02', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (39, '1A6B0E59-E75A-4209-997A-0B3A484E1DE71562915162420', '经营合同', '资产管理-合同 客户相关表  数据字典.doc', 'DOC', 76, '\\operate\\2019-07-12\\资产管理-合同 客户相关表  数据字典.doc', 'operate\\2019-07-12', 'doc', '2019-07-12 15:06:02', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (40, 'B36D3E0B-AA9D-4186-A0FE-291148CBEDB91562918752159', '经营合同', '4.jpg', 'JPG', 15, '\\operate\\2019-07-12\\4.jpg', 'operate\\2019-07-12', 'jpg', '2019-07-12 16:05:52', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (44, '041ADA9F-379B-45C0-9B96-CDB938BF53761562924064304', '外包合同', 'assets_management.sql', 'SQL', 164, '\\operate\\2019-07-12\\assets_management.sql', 'operate\\2019-07-12', 'sql', '2019-07-12 17:34:28', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (45, '041ADA9F-379B-45C0-9B96-CDB938BF53761562924064304', '外包合同', '完成功能模块.docx', 'DOCX', 5086, '\\operate\\2019-07-12\\完成功能模块.docx', 'operate\\2019-07-12', 'docx', '2019-07-12 17:34:28', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (46, 'D0C04D01-2DFD-42B5-A6CA-6404A317DF771562924629188', '外包合同', '4.jpg', 'JPG', 15, '\\operate\\2019-07-12\\4.jpg', 'operate\\2019-07-12', 'jpg', '2019-07-12 17:43:49', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (47, 'D0C04D01-2DFD-42B5-A6CA-6404A317DF771562924629188', '外包合同', 'assets_management.sql', 'SQL', 164, '\\operate\\2019-07-12\\assets_management.sql', 'operate\\2019-07-12', 'sql', '2019-07-12 17:43:49', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (48, 'C7F1C26E-3906-4DC4-A0AF-74FD63C26D941562924817288', '外包合同', '数据字典-智慧社区20190402-WZ.doc', 'DOC', 491, '\\operate\\2019-07-12\\数据字典-智慧社区20190402-WZ.doc', 'operate\\2019-07-12', 'doc', '2019-07-12 17:46:57', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (49, 'C7F1C26E-3906-4DC4-A0AF-74FD63C26D941562924817288', '外包合同', '资产管理-合同 客户相关表  数据字典.doc', 'DOC', 76, '\\operate\\2019-07-12\\资产管理-合同 客户相关表  数据字典.doc', 'operate\\2019-07-12', 'doc', '2019-07-12 17:46:57', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (50, 'FFABCA72-2068-48BD-BB2A-34218EC0EE611563150701282', '外包合同', '完成功能.txt', 'TXT', 0, '\\operate\\2019-07-15\\完成功能.txt', 'operate\\2019-07-15', 'txt', '2019-07-15 08:31:54', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (51, 'FFABCA72-2068-48BD-BB2A-34218EC0EE611563150701282', '外包合同', '完成功能模块.docx', 'DOCX', 5086, '\\operate\\2019-07-15\\完成功能模块.docx', 'operate\\2019-07-15', 'docx', '2019-07-15 08:31:54', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (52, '84BFA519-5D90-493E-BF26-75C3CEEE001C1563150800043', '经营合同', '完成功能模块.docx', 'DOCX', 5086, '\\operate\\2019-07-15\\完成功能模块.docx', 'operate\\2019-07-15', 'docx', '2019-07-15 08:33:29', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (53, '4AF7049F-5E17-4046-865E-2FE7FD2C94C91563151027882', '外包合同', '完成功能.txt', 'TXT', 0, '\\operate\\2019-07-15\\完成功能.txt', 'operate\\2019-07-15', 'txt', '2019-07-15 08:37:23', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (54, '30E88668-9E53-4FC8-BEE1-1DCCA0DCECFB1563151154615', '外包合同', 'Copy_Screenshots.pdf', 'PDF', 1274, '\\operate\\2019-07-15\\Copy_Screenshots.pdf', 'operate\\2019-07-15', 'pdf', '2019-07-15 08:39:41', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (55, '30E88668-9E53-4FC8-BEE1-1DCCA0DCECFB1563151154615', '外包合同', '资产管理-合同 客户相关表  数据字典.doc', 'DOC', 76, '\\operate\\2019-07-15\\资产管理-合同 客户相关表  数据字典.doc', 'operate\\2019-07-15', 'doc', '2019-07-15 08:39:41', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (56, '698509B7-8817-4D4D-98EC-20599D2EB1391563151241965', '外包合同', '资产管理-合同 客户相关表  数据字典.doc', 'DOC', 76, '\\operate\\2019-07-15\\资产管理-合同 客户相关表  数据字典.doc', 'operate\\2019-07-15', 'doc', '2019-07-15 08:41:00', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (57, '071CED37-142F-4ED1-A650-3CD2A95F6C171563151760256', '经营合同', 'Copy_Screenshots副本.pdf', 'PDF', 1274, '\\operate\\2019-07-15\\Copy_Screenshots副本.pdf', 'operate\\2019-07-15', 'pdf', '2019-07-15 08:49:40', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (58, '5618B98A-0AB6-4861-9F78-AF18D290C2991563151899985', '经营合同', '完成功能模块.docx', 'DOCX', 5086, '\\operate\\2019-07-15\\完成功能模块.docx', 'operate\\2019-07-15', 'docx', '2019-07-15 08:51:47', NULL, NULL, NULL);
INSERT INTO `papers` VALUES (59, '5618B98A-0AB6-4861-9F78-AF18D290C2991563151899985', '经营合同', '资产管理-合同 客户相关表  数据字典.doc', 'DOC', 76, '\\operate\\2019-07-15\\资产管理-合同 客户相关表  数据字典.doc', 'operate\\2019-07-15', 'doc', '2019-07-15 08:51:47', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
