/*
Navicat MySQL Data Transfer

Source Server         : test01
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : dormitory_repair

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2021-06-22 13:47:34
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `apartment`
-- ----------------------------
DROP TABLE IF EXISTS `apartment`;
CREATE TABLE `apartment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `apname` varchar(20) NOT NULL,
  `apno` varchar(20) NOT NULL,
  `build_year` varchar(10) DEFAULT '2006-09-01',
  `repair_amount` int(11) DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `id` (`id`) USING BTREE,
  KEY `apname` (`apname`) USING BTREE,
  KEY `apno` (`apno`)
) ENGINE=InnoDB AUTO_INCREMENT=200029 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of apartment
-- ----------------------------
INSERT INTO `apartment` VALUES ('100000', '区分苑', 'A', '2020-01-01', '0');
INSERT INTO `apartment` VALUES ('100021', '博雅苑', 'A', '2006-09-01', '3');
INSERT INTO `apartment` VALUES ('100022', '博雅苑', 'B', '2006-09-01', '0');
INSERT INTO `apartment` VALUES ('100023', '新民苑', 'A', '2000-01-01', '0');
INSERT INTO `apartment` VALUES ('100024', '新民苑', 'B', '2006-09-01', '1');
INSERT INTO `apartment` VALUES ('100025', '信勇苑', 'A', '2006-09-01', '0');
INSERT INTO `apartment` VALUES ('100026', '信勇苑', 'B', '2006-09-01', '0');
INSERT INTO `apartment` VALUES ('200021', '明德苑', 'A', '2006-09-01', '4');
INSERT INTO `apartment` VALUES ('200022', '明德苑', 'B', '2006-09-01', '0');
INSERT INTO `apartment` VALUES ('200023', '至善苑', 'A', '2009-09-08', '0');
INSERT INTO `apartment` VALUES ('200024', '至善苑', 'B', '2009-09-01', '2');
INSERT INTO `apartment` VALUES ('200025', '至道苑', 'A', '2006-09-01', '0');
INSERT INTO `apartment` VALUES ('200026', '至道苑', 'B', '2006-09-01', '0');
INSERT INTO `apartment` VALUES ('200027', '尚雅苑', 'A', '2006-09-01', '0');
INSERT INTO `apartment` VALUES ('200028', '尚雅苑', 'B', '2006-09-01', '0');

-- ----------------------------
-- Table structure for `feedback`
-- ----------------------------
DROP TABLE IF EXISTS `feedback`;
CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `s_name` varchar(32) NOT NULL,
  `content` varchar(10000) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `s_tel` varchar(32) DEFAULT NULL,
  `f_time` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `s_name` (`s_name`),
  CONSTRAINT `feedback_ibfk_1` FOREIGN KEY (`s_name`) REFERENCES `maintenance_record` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of feedback
-- ----------------------------
INSERT INTO `feedback` VALUES ('39', '张涵', '效率真快，早上报修的，下午就来了。\n希望修好后质量也可以是杠杠的。', 'haha@qq.com', '15613593799', '2021-05-28');
INSERT INTO `feedback` VALUES ('40', 'Lisia', '修复质量不错！', '', '12345678900', '2021-06-12');
INSERT INTO `feedback` VALUES ('42', '张涵', '1111111', 'haha@qq.com', '15613593799', '2021-06-16');
INSERT INTO `feedback` VALUES ('43', '李乐乐', '真不错呢', 'haha@qq.com', '15613593799', '2021-06-19');

-- ----------------------------
-- Table structure for `maintenance_record`
-- ----------------------------
DROP TABLE IF EXISTS `maintenance_record`;
CREATE TABLE `maintenance_record` (
  `sno` varchar(32) NOT NULL,
  `name` varchar(20) DEFAULT NULL,
  `ap_name` varchar(20) NOT NULL,
  `ap_no` varchar(20) NOT NULL,
  `ap_room` int(11) NOT NULL,
  `repair_content` varchar(1000) NOT NULL,
  `repair_data` varchar(32) DEFAULT NULL,
  `pno` varchar(11) DEFAULT '15613593799',
  KEY `Foreign_key_ap_name` (`ap_name`),
  KEY `Foreign_key_sno` (`sno`),
  KEY `Foreign_key_ap_no` (`ap_no`),
  KEY `Foreign_key_sname` (`name`),
  KEY `Foreign_key_aproom` (`ap_room`),
  KEY `Foreign_key_pno` (`pno`),
  CONSTRAINT `Foreign_key_ap_name` FOREIGN KEY (`ap_name`) REFERENCES `apartment` (`apname`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Foreign_key_ap_no` FOREIGN KEY (`ap_no`) REFERENCES `apartment` (`apno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Foreign_key_ap_room` FOREIGN KEY (`ap_room`) REFERENCES `学生` (`aproom`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Foreign_key_pno` FOREIGN KEY (`pno`) REFERENCES `学生` (`pno`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Foreign_key_sname` FOREIGN KEY (`name`) REFERENCES `学生` (`name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of maintenance_record
-- ----------------------------
INSERT INTO `maintenance_record` VALUES ('20210904', '邓丽', '至道苑', 'B', '310', '墙体漏水', '2021-04-10', '15613593799');
INSERT INTO `maintenance_record` VALUES ('20210901', '张涵', '博雅苑', 'A', '802', '墙体漏水了。', '2021-05-28', '15613593799');
INSERT INTO `maintenance_record` VALUES ('20210901', '张涵', '博雅苑', 'A', '802', '灯泡坏了', '2021-06-09', '15613593799');
INSERT INTO `maintenance_record` VALUES ('20210900', 'Lisia', '明德苑', 'A', '602', '风扇坏了', '2021-06-09', '12345678900');
INSERT INTO `maintenance_record` VALUES ('20210902', '何文', '新民苑', 'B', '302', '风扇坏了', '2021-06-09', '15613593799');
INSERT INTO `maintenance_record` VALUES ('20210900', 'Lisia', '明德苑', 'A', '602', '地板开裂了', '2021-06-12', '12345678900');
INSERT INTO `maintenance_record` VALUES ('20210903', '李乐乐', '明德苑', 'B', '602', '灯泡坏了', '2021-06-12', '15613593799');
INSERT INTO `maintenance_record` VALUES ('20210904', '邓丽', '至道苑', 'B', '310', '柜子坏了', '2021-05-10', '15613593799');
INSERT INTO `maintenance_record` VALUES ('20210901', '张涵', '博雅苑', 'A', '802', '11111', '2021-06-16', '15613593799');
INSERT INTO `maintenance_record` VALUES ('20210903', '李乐乐', '明德苑', 'A', '602', '厕所堵了。', '2021-06-19', '15613593799');

-- ----------------------------
-- Table structure for `学生`
-- ----------------------------
DROP TABLE IF EXISTS `学生`;
CREATE TABLE `学生` (
  `id` int(4) NOT NULL AUTO_INCREMENT,
  `name` varchar(20) NOT NULL,
  `sno` varchar(32) NOT NULL,
  `sex` varchar(2) DEFAULT NULL,
  `apname` varchar(20) NOT NULL,
  `apno` varchar(20) NOT NULL,
  `aproom` int(11) NOT NULL,
  `collage` varchar(50) DEFAULT '信息工程学院',
  `password` varchar(32) NOT NULL DEFAULT '123456',
  `age` varchar(4) DEFAULT '20',
  `pno` varchar(11) DEFAULT '15613593799',
  `email` varchar(32) DEFAULT 'haha@qq.com',
  PRIMARY KEY (`sno`),
  KEY `id` (`id`),
  KEY `sname` (`name`),
  KEY `sno` (`sno`),
  KEY `aproom` (`aproom`),
  KEY `pno` (`pno`),
  KEY `name` (`name`,`sno`),
  KEY `sno_2` (`sno`,`name`),
  KEY `Foreign_key_apname` (`apname`),
  CONSTRAINT `Foreign_key_apname` FOREIGN KEY (`apname`) REFERENCES `apartment` (`apname`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 学生
-- ----------------------------
INSERT INTO `学生` VALUES ('12', 'Lisia', '20210900', '女', '明德苑', 'A', '602', '音乐舞蹈学院', '123456', '20', '12345678900', '156@qq.com');
INSERT INTO `学生` VALUES ('1', '张涵', '20210901', '男', '博雅苑', 'A', '802', '信息工程学院', '123456', '20', '15613593799', 'haha@qq.com');
INSERT INTO `学生` VALUES ('2', '何文', '20210902', '男', '新民苑', 'B', '302', '机电工程学院', '123456', '20', '15613593799', 'haha@qq.com');
INSERT INTO `学生` VALUES ('3', '李乐乐', '20210903', '女', '明德苑', 'A', '602', '音乐舞蹈学院', '123456', '20', '15613593799', 'haha@qq.com');
INSERT INTO `学生` VALUES ('4', '邓丽', '20210904', '女', '至道苑', 'B', '310', '文学与传媒学院', '123456', '20', '15613593799', 'haha@qq.com');
INSERT INTO `学生` VALUES ('5', '贺家辉', '20210905', '男', '信勇苑', 'A', '311', '数学与统计学院', '123456', '20', '15613593799', 'haha@qq.com');
INSERT INTO `学生` VALUES ('6', '刘静', '20210907', '女', '明德苑', 'A', '302', '信息工程学院', '123456', '20', '15613593799', 'haha@qq.com');
INSERT INTO `学生` VALUES ('7', '张萌', '20210911', '女', '尚雅苑', 'A', '103', '生物与科学学院', '123456', '20', '15613593799', 'haha@qq.com');
INSERT INTO `学生` VALUES ('13', '张三', '20210920', '男', '博雅苑', 'A ', '802', '商学院', '123456', '19', '12345678901', '123@qq.com');
INSERT INTO `学生` VALUES ('8', '刘能', '20220919', '男', '博雅苑', 'A', '802', '数学与统计学院', '123456', '21', '13241414211', '329@qq.com');

-- ----------------------------
-- Table structure for `管理员`
-- ----------------------------
DROP TABLE IF EXISTS `管理员`;
CREATE TABLE `管理员` (
  `sno` varchar(32) NOT NULL DEFAULT '',
  `name` varchar(32) NOT NULL,
  `password` varchar(32) NOT NULL DEFAULT ''
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of 管理员
-- ----------------------------
INSERT INTO `管理员` VALUES ('admin', '', 'admin');
DROP TRIGGER IF EXISTS `insert_n`;
DELIMITER ;;
CREATE TRIGGER `insert_n` AFTER INSERT ON `maintenance_record` FOR EACH ROW BEGIN
UPDATE apartment AS a SET repair_amount=repair_amount+1 WHERE a.apname = new.ap_name and a.apno=new.ap_no;
END
;;
DELIMITER ;
