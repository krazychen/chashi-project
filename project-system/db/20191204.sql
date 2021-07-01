/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.18 : Database - whyy-system
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`whyy-system` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `whyy-system`;

/*Table structure for table `foo_bar` */

DROP TABLE IF EXISTS `foo_bar`;

CREATE TABLE `foo_bar` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `foo` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Foo',
  `bar` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Bar',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='FooBar';

/*Data for the table `foo_bar` */

insert  into `foo_bar`(`id`,`name`,`foo`,`bar`,`remark`,`state`,`version`,`create_time`,`update_time`) values 
(1,'FooBar','foo','bar','remark...',1,0,'2019-11-01 14:05:14',NULL),
(2,'HelloWorld','hello','world',NULL,1,0,'2019-11-01 14:05:14',NULL);

/*Table structure for table `hw_student` */

DROP TABLE IF EXISTS `hw_student`;

CREATE TABLE `hw_student` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `province_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份代码',
  `province_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份名称',
  `city_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市代码',
  `city_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市名称',
  `district_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区县代码',
  `district_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区县名称',
  `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `school_id` bigint(20) DEFAULT NULL COMMENT '学校id',
  `school_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学校代码',
  `school_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学校名称',
  `college_id` bigint(20) DEFAULT NULL COMMENT '专业id',
  `college_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '专业代码',
  `college_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '专业名称',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学员表';

/*Data for the table `hw_student` */

/*Table structure for table `hw_teacher` */

DROP TABLE IF EXISTS `hw_teacher`;

CREATE TABLE `hw_teacher` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `province_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份代码',
  `province_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份名称',
  `city_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市代码',
  `city_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市名称',
  `district_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区县代码',
  `district_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区县名称',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='老师表';

/*Data for the table `hw_teacher` */

/*Table structure for table `ip` */

DROP TABLE IF EXISTS `ip`;

CREATE TABLE `ip` (
  `ip_start` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `ip_end` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `area` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `operator` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip_start_num` bigint(20) NOT NULL,
  `ip_end_num` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=526718 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='IP地址';

/*Data for the table `ip` */

/*Table structure for table `sys_area` */

DROP TABLE IF EXISTS `sys_area`;

CREATE TABLE `sys_area` (
  `area_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域编码',
  `parent_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全节点名',
  `area_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '区域名称',
  `area_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区域类型',
  `deleted` int(11) NOT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL COMMENT '版本',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='行政区划';

/*Data for the table `sys_area` */

/*Table structure for table `sys_department` */

DROP TABLE IF EXISTS `sys_department`;

CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `level` int(11) DEFAULT NULL COMMENT '部门层级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_department_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='部门';

/*Data for the table `sys_department` */

insert  into `sys_department`(`id`,`name`,`parent_id`,`level`,`state`,`sort`,`remark`,`version`,`create_time`,`update_time`) values 
(1,'管理部',NULL,1,1,0,NULL,0,'2019-10-25 09:46:49',NULL),
(2,'技术部',NULL,1,1,0,NULL,0,'2019-11-01 20:45:43',NULL),
(20,'前端开发部',2,2,1,0,NULL,0,'2019-11-01 20:48:38',NULL),
(21,'后台开发部',2,2,1,0,NULL,0,'2019-11-01 20:48:38',NULL),
(22,'测试部',2,2,1,0,NULL,0,'2019-11-01 20:48:38',NULL),
(201,'前端一组',20,3,1,0,NULL,0,'2019-11-01 20:48:38',NULL),
(202,'前端二组',20,3,1,0,NULL,0,'2019-11-01 20:48:38',NULL),
(203,'后台一组',21,3,1,0,NULL,0,'2019-11-01 20:48:38',NULL),
(204,'后台二组',21,3,1,0,NULL,0,'2019-11-01 20:48:38',NULL),
(205,'测试一组',22,3,1,0,NULL,0,'2019-11-01 20:48:38',NULL);

/*Table structure for table `sys_dict_data` */

DROP TABLE IF EXISTS `sys_dict_data`;

CREATE TABLE `sys_dict_data` (
  `dict_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典编码',
  `parent_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全节点名',
  `dict_label` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典标签',
  `dict_value` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典键值',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `is_sys` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '系统内置（1是 0否）',
  `description` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '字典描述',
  `css_style` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'css样式（如：color:red)',
  `css_class` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'css类名（如：red）',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典数据表';

/*Data for the table `sys_dict_data` */

insert  into `sys_dict_data`(`dict_code`,`parent_code`,`parent_codes`,`tree_sort`,`tree_sorts`,`tree_leaf`,`tree_level`,`tree_names`,`dict_label`,`dict_value`,`dict_type`,`is_sys`,`description`,`css_style`,`css_class`,`deleted`,`version`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`,`corp_code`,`corp_name`) values 
('0000-0001-0001-0001','00000000','00000000',100,'100','0',1,'首级','是','1','sys_yes_no','1','是否',NULL,NULL,0,0,'0','1','2019-11-28 10:27:12','1','2019-11-28 10:27:17',NULL,'0','whyy'),
('0000-0001-0001-0002','00000000','00000000',110,'100','0',1,'首级','否','0','sys_yes_no','1','是否',NULL,NULL,0,0,'0','1','2019-11-28 10:29:00','1','2019-11-28 10:29:01',NULL,'0','whyy'),
('0000-0001-0002-0001','00000000','00000000',100,'100','0',1,'首级','公司','1','sys_office_type','1','机构类型',NULL,NULL,0,0,'0','1','2019-11-28 10:32:11','1','2019-11-28 10:32:16',NULL,'0','whyy'),
('0000-0001-0002-0002','00000000','00000000',110,'100','0',1,'首级','部门','2','sys_office_type','1','机构类型',NULL,NULL,0,0,'0','1','2019-11-28 10:33:14','1','2019-11-28 10:33:18',NULL,'0','whyy'),
('0000-0001-0003-0001','00000000','00000000',100,'100','0',1,'首级','后台管理系统','1','sys_system_type','1','归属系统',NULL,NULL,0,0,'0','1','2019-11-28 10:38:16','1','2019-11-28 10:38:20',NULL,'0','whyy'),
('0000-0001-0003-0002','00000000','00000000',110,'100','0',1,'首级','作业批改系统','2','sys_system_type','1','归属系统',NULL,NULL,0,0,'0','1','2019-11-28 10:45:16','1','2019-11-28 10:45:24',NULL,'0','whyy'),
('0000-0001-0003-0003','00000000','00000000',120,'100','0',1,'首级','在线网课系统','3','sys_system_type','1','归属系统',NULL,NULL,0,0,'0','1','2019-11-28 10:46:58','1','2019-11-28 10:47:02',NULL,'0','whyy'),
('0000-0001-0004-0001','00000000','00000000',100,'100','0',1,'首级','系统管理','1','sys_module_type','1','归属模块',NULL,NULL,0,0,'0','1','2019-11-28 10:49:26','1','2019-11-28 10:49:31',NULL,'0','whyy'),
('0000-0001-0004-0002','00000000','00000000',110,'100','0',1,'首级','后台作业管理','2','sys_module_type','1','归属模块',NULL,NULL,0,0,'0','1','2019-11-28 10:50:50','1','2019-11-28 10:50:56',NULL,'0','whyy'),
('0000-0001-0004-0003','00000000','00000000',120,'100','0',1,'首级','学员作业管理','3','sys_module_type','1','归属模块',NULL,NULL,0,0,'0','1','2019-11-28 10:52:12','1','2019-11-28 10:52:17',NULL,'0','whyy'),
('0000-0001-0004-0004','00000000','00000000',130,'100','0',1,'首级','老师作业管理','4','sys_module_type','1','归属模块',NULL,NULL,0,0,'0','1','2019-11-28 10:53:58','1','2019-11-28 10:54:02',NULL,'0','whyy'),
('0000-0001-0005-0001','00000000','00000000',100,'100','0',1,'首级','菜单','1','sys_menu_type','1','归属模块',NULL,NULL,0,0,'0','1','2019-11-28 10:56:03','1','2019-11-28 10:56:05',NULL,'0','whyy'),
('0000-0001-0005-0002','00000000','00000000',110,'100','0',1,'首级','权限','2','sys_menu_type','1','菜单类型',NULL,NULL,0,0,'0','1','2019-11-28 10:57:34','1','2019-11-28 10:57:38',NULL,'0','whyy'),
('0000-0001-0006-0001','00000000','00000000',100,'100','0',1,'首级','显示','1','sys_show_hide','1','可见',NULL,NULL,0,0,'0','1','2019-11-28 10:59:52','1','2019-11-28 10:59:53',NULL,'0','whyy'),
('0000-0001-0006-0002','00000000','00000000',110,'100','0',1,'首级','隐藏','0','sys_show_hide','1','可见',NULL,NULL,0,0,'0','1','2019-11-28 11:01:26','1','2019-11-28 11:01:29',NULL,'0','whyy'),
('0000-0001-0007-0001','00000000','00000000',100,'100','0',1,'首级','系统用户','1','sys_user_type','1','用户类型',NULL,NULL,0,0,'0','1','2019-11-28 11:02:51','1','2019-11-28 11:02:57',NULL,'0','whyy'),
('0000-0001-0007-0002','00000000','00000000',110,'100','0',1,'首级','学生','2','sys_user_type','1','用户类型',NULL,NULL,0,0,'0','1','2019-11-28 11:04:13','1','2019-11-28 11:04:17',NULL,'0','whyy'),
('0000-0001-0007-0003','00000000','00000000',120,'100','0',1,'首级','老师','3','sys_user_type','1','用户类型',NULL,NULL,0,0,'0','1','2019-11-28 11:05:25','1','2019-11-28 11:05:27',NULL,'0','whyy'),
('0000-0001-0008-0001','00000000','00000000',100,'100','0',1,'首级','系统管理员','sysadmin','sys_role_type','1','角色类型',NULL,NULL,0,0,'0','1','2019-11-28 11:07:23','1','2019-11-28 11:07:28',NULL,'0','whyy'),
('0000-0001-0008-0002','00000000','00000000',110,'100','0',1,'首级','学员','student','sys_role_type','1','角色类型',NULL,NULL,0,0,'0','1','2019-11-28 11:09:19','1','2019-11-28 11:09:28',NULL,'0','whyy'),
('0000-0001-0008-0003','00000000','00000000',120,'100','0',1,'首级','一级老师','leve1teacher','sys_role_type','1','角色类型',NULL,NULL,0,0,'0','1','2019-11-28 11:10:48','1','2019-11-28 11:10:50',NULL,'0','whyy'),
('0000-0001-0008-0004','00000000','00000000',130,'100','0',1,'首级','二级老师','leve2teacher','sys_role_type','1','角色类型',NULL,NULL,0,0,'0','1','2019-11-28 11:12:10','1','2019-11-28 11:12:14',NULL,'0','whyy'),
('0000-0001-0008-0005','00000000','00000000',140,'100','0',1,'首级','三级老师','leve2teacher','sys_role_type','1','角色类型',NULL,NULL,0,0,'0','1','2019-11-28 11:13:28','1','2019-11-28 11:13:33',NULL,'0','whyy'),
('0000-0001-0009-0001','00000000','00000000',100,'100','0',1,'首级','省份直辖市','province','sys_area_type','1','区域类型',NULL,NULL,0,0,'0','1','2019-11-28 11:16:00','1','2019-11-28 11:16:02',NULL,'0','whyy'),
('0000-0001-0009-0002','00000000','00000000',110,'100','0',1,'首级','地市','city','sys_area_type','1','区域类型',NULL,NULL,0,0,'0','1','2019-11-28 11:17:17','1','2019-11-28 11:17:20',NULL,'0','whyy'),
('0000-0001-0009-0003','00000000','00000000',120,'100','0',1,'首级','区县','region','sys_area_type','1','区域类型',NULL,NULL,0,0,'0','1','2019-11-28 11:19:12','1','2019-11-28 11:19:14',NULL,'0','whyy');

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '编号',
  `dict_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典名称',
  `dict_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '字典类型',
  `is_sys` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否系统字典',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='字典类型表';

/*Data for the table `sys_dict_type` */

insert  into `sys_dict_type`(`id`,`dict_name`,`dict_type`,`is_sys`,`deleted`,`version`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`) values 
('0000-0001-0001','是否','sys_yes_no','1',0,0,'0','1','2019-11-28 09:57:06','1','2019-11-28 09:57:21',NULL),
('0000-0001-0002','机构类型','sys_office_type','1',0,0,'0','1','2019-11-28 10:00:49','1','2019-11-28 10:00:55',NULL),
('0000-0001-0003','归属系统','sys_system_type','1',0,0,'0','1','2019-11-28 10:01:46','1','2019-11-28 10:01:57',NULL),
('0000-0001-0004','归属模块','sys_module_type','1',0,0,'0','1','2019-11-28 10:09:44','1','2019-11-28 10:09:50',NULL),
('0000-0001-0005','菜单类型','sys_menu_type','1',0,0,'0','1','2019-11-28 10:10:41','1','2019-11-28 10:10:48',NULL),
('0000-0001-0006','可见','sys_show_hide','1',0,0,'0','1','2019-11-28 10:11:24','1','2019-11-28 10:11:28',NULL),
('0000-0001-0007','用户类型','sys_user_type','1',0,0,'0','1','2019-11-28 10:12:28','1','2019-11-28 10:12:41',NULL),
('0000-0001-0008','角色类型','sys_role_type','1',0,0,'0','1','2019-11-28 10:13:59','1','2019-11-28 10:14:03',NULL),
('0000-0001-0009','区域类型','sys_area_type','1',0,0,'0','1','2019-11-28 10:14:50','1','2019-11-28 10:14:56',NULL),
('11','111','1','1',0,0,'0','1','2019-11-29 11:59:03','1','2019-11-29 11:59:03','11');

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `log_id` bigint(18) NOT NULL COMMENT '主键',
  `type` tinyint(1) DEFAULT NULL COMMENT '类型',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `create_id` bigint(18) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统日志';

/*Data for the table `sys_log` */

insert  into `sys_log`(`log_id`,`type`,`content`,`create_id`,`create_time`) values 
(1060438746056376321,0,'A',100000,'2018-11-08 15:41:58'),
(1060438788502732802,0,'B',100000,'2018-11-08 15:42:08'),
(1060438799600861185,0,'C',100000,'2018-11-08 15:42:10'),
(1060438809495224322,0,'D',100000,'2018-11-08 15:42:13');

/*Table structure for table `sys_menu` */

DROP TABLE IF EXISTS `sys_menu`;

CREATE TABLE `sys_menu` (
  `menu_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `parent_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全节点名',
  `menu_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单名称',
  `menu_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单类型（1菜单 2权限 3开发）',
  `menu_href` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接',
  `menu_target` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '目标',
  `menu_icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `menu_color` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '颜色',
  `menu_title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '菜单标题',
  `permission` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限标识',
  `weight` decimal(4,0) DEFAULT NULL COMMENT '菜单权重',
  `is_show` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否显示（1显示 0隐藏）',
  `sys_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '归属系统（default:主导航菜单、mobileApp:APP菜单）',
  `module_codes` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '归属模块（多个用逗号隔开）',
  `deleted` int(11) NOT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='菜单表';

/*Data for the table `sys_menu` */

insert  into `sys_menu`(`menu_code`,`parent_code`,`parent_codes`,`tree_sort`,`tree_sorts`,`tree_leaf`,`tree_level`,`tree_names`,`menu_name`,`menu_type`,`menu_href`,`menu_target`,`menu_icon`,`menu_color`,`menu_title`,`permission`,`weight`,`is_show`,`sys_code`,`module_codes`,`deleted`,`version`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`) values 
('1000','00000000','00000000',100,'1','1',2,'首级节点','test2','1','http://1234','123','','#409EFF','','11:11:11',NULL,'1','666','2',0,0,'0','1','2019-11-26 22:48:12','1','2019-11-26 22:48:12',''),
('123','00000000','00000000',100,'1','2',2,'首级节点','test1','1','http://1123','123','','#409EFF','','123:23:123',NULL,'1','666','1',0,0,'0','1','2019-11-26 22:44:15','1','2019-11-26 22:44:15',''),
('333','123','00000000,123',111,'1,100','1',3,'首级节点,test1','test1-1','1','http://111','1123','','#409EFF','','11:11:11',NULL,'1','666','1',0,0,'0','1','2019-11-26 22:44:52','1','2019-11-26 22:44:52','');

/*Table structure for table `sys_office` */

DROP TABLE IF EXISTS `sys_office`;

CREATE TABLE `sys_office` (
  `office_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构编码',
  `parent_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '全节点名',
  `view_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构代码',
  `office_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构名称',
  `full_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构全称',
  `office_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构类型',
  `leader` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '负责人',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '办公电话',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮政编码',
  `email` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`office_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='组织机构表';

/*Data for the table `sys_office` */

insert  into `sys_office`(`office_code`,`parent_code`,`parent_codes`,`tree_sort`,`tree_sorts`,`tree_leaf`,`tree_level`,`tree_names`,`view_code`,`office_name`,`full_name`,`office_type`,`leader`,`phone`,`address`,`zip_code`,`email`,`deleted`,`version`,`status`,`create_by`,`create_time`,`update_by`,`update_time`,`remarks`,`corp_code`,`corp_name`) values 
('10000','00000000','00000000',100,'1','1',2,'首级节点','10000','test1','test1','1','','','','','',0,0,'0','1','2019-11-29 11:58:35','1','2019-11-29 11:58:35','','',''),
('100000','0000000','1',100,'1','1',1,'1','100000','test1','test1','2','','','','','',0,0,'0','1','2019-11-28 12:00:26','1','2019-11-28 12:00:26','','',''),
('123','123','1',123,'1','1',1,'1','123','123','123','1','','','','','',0,0,'0','1','2019-11-26 12:26:20','1','2019-11-26 12:26:20','','','');

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `url` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路径',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一编码',
  `icon` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `type` int(11) NOT NULL COMMENT '类型，1：菜单，2：按钮',
  `level` int(11) NOT NULL COMMENT '层级，1：第一级，2：第二级，N：第N级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_permission_code_uindex` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统权限';

/*Data for the table `sys_permission` */

insert  into `sys_permission`(`id`,`name`,`parent_id`,`url`,`code`,`icon`,`type`,`level`,`state`,`sort`,`remark`,`version`,`create_time`,`update_time`) values 
(1,'系统管理',NULL,NULL,'system:management',NULL,1,1,1,0,NULL,0,'2019-10-26 11:12:40',NULL),
(100,'用户管理',1,NULL,'sys:user:management',NULL,1,2,1,0,NULL,0,'2019-10-26 11:15:48',NULL),
(200,'角色管理',1,NULL,'sys:role:management',NULL,1,2,1,0,NULL,0,'2019-10-26 11:15:48',NULL),
(300,'权限管理',1,NULL,'sys:permission:management',NULL,1,2,1,0,NULL,0,'2019-10-26 11:15:48',NULL),
(400,'部门管理',1,NULL,'sys:department:management',NULL,1,2,1,0,NULL,0,'2019-10-26 11:15:48',NULL),
(1000,'用户新增',100,NULL,'sys:user:add',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(1001,'用户修改',100,NULL,'sys:user:update',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(1002,'用户删除',100,NULL,'sys:user:delete',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(1003,'用户详情',100,NULL,'sys:user:info',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(1004,'用户分页列表',100,NULL,'sys:user:page',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(1005,'用户修改密码',100,NULL,'sys:user:update:password',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(1006,'用户修改头像',100,NULL,'sys:user:update:head',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(2000,'角色新增',200,NULL,'sys:role:add',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(2001,'角色修改',200,NULL,'sys:role:update',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(2002,'角色删除',200,NULL,'sys:role:delete',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(2003,'角色详情',200,NULL,'sys:role:info',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(2004,'角色分页列表',200,NULL,'sys:role:page',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3000,'权限新增',300,NULL,'sys:permission:add',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3001,'权限修改',300,NULL,'sys:permission:update',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3002,'权限删除',300,NULL,'sys:permission:delete',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3003,'权限详情',300,NULL,'sys:permission:info',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3004,'权限分页列表',300,NULL,'sys:permission:page',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3005,'权限所有列表',300,NULL,'sys:permission:all:menu:list',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3006,'权限所有树形列表',300,NULL,'sys:permission:all:menu:tree',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3007,'权限用户列表',300,NULL,'sys:permission:menu:list',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3008,'权限用户树形列表',300,NULL,'sys:permission:menu:tree',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(3009,'权限用户代码列表',300,NULL,'sys:permission:codes',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(4000,'部门新增',400,NULL,'sys:department:add',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(4001,'部门修改',400,NULL,'sys:department:update',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(4002,'部门删除',400,NULL,'sys:department:delete',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(4003,'部门详情',400,NULL,'sys:department:info',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(4004,'部门分页列表',400,NULL,'sys:department:page',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL),
(5000,'组织新增',500,NULL,'sys:office:add',NULL,2,3,1,0,NULL,0,'2019-11-25 10:38:37',NULL),
(5001,'组织修改',500,NULL,'sys:office:update',NULL,2,3,1,0,NULL,0,'2019-11-25 10:39:11',NULL),
(5002,'组织删除',500,NULL,'sys:office:delete',NULL,2,3,1,0,NULL,0,'2019-11-25 10:39:39',NULL),
(5003,'组织详情',500,NULL,'sys:office:info',NULL,2,3,1,0,NULL,0,'2019-11-25 10:40:09',NULL),
(5004,'组织分页列表',500,NULL,'sys:office:page',NULL,2,3,1,0,NULL,0,'2019-11-25 10:52:32',NULL),
(5005,'组织状态修改',500,NULL,'sys:office:updateBystatus',NULL,2,3,1,0,NULL,0,'2019-11-25 14:38:25',NULL),
(6000,'菜单新增',600,NULL,'sys:menu:add',NULL,2,3,1,0,NULL,0,'2019-11-26 10:07:59',NULL),
(6001,'菜单修改',600,NULL,'sys:menu:update',NULL,2,3,1,0,NULL,0,'2019-11-26 10:11:57',NULL),
(6002,'菜单删除',600,NULL,'sys:menu:delete',NULL,2,3,1,0,NULL,0,'2019-11-26 10:12:42',NULL),
(6003,'菜单详情',600,NULL,'sys:menu:info',NULL,2,3,1,0,NULL,0,'2019-11-26 10:13:44',NULL),
(6004,'菜单分页列表',600,NULL,'sys:menu:page',NULL,2,3,1,0,NULL,0,'2019-11-26 10:14:26',NULL),
(7000,'字典新增',700,NULL,'sys:dict:type:add',NULL,2,3,1,0,NULL,0,'2019-11-28 15:42:54',NULL),
(7001,'字典修改',700,NULL,'sys:dict:type:update',NULL,2,3,1,0,NULL,0,'2019-11-28 15:43:40',NULL),
(7002,'字典删除',700,NULL,'sys:dict:type:delete',NULL,2,3,1,0,NULL,0,'2019-11-28 15:44:12',NULL),
(7003,'字典详情',700,NULL,'sys:dict:type:info',NULL,2,3,1,0,NULL,0,'2019-11-28 15:44:57',NULL),
(7004,'字典分页列表',700,NULL,'sys:dict:type:page',NULL,2,3,1,0,NULL,0,'2019-11-28 15:45:35',NULL),
(8001,'根据用户id查角色集合',800,NULL,'sys:user:role:findUserById',NULL,2,3,1,0,NULL,0,'2019-12-03 14:34:19',NULL),
(8002,'批量新增用户角色',800,NULL,'sys:user:addRoles',NULL,2,3,1,0,NULL,0,'2019-12-03 17:41:33',NULL),
(8003,'单个新增用户角色',800,NULL,'sys:user:addRole',NULL,2,3,1,0,NULL,0,'2019-12-03 17:42:18',NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色唯一编码',
  `type` int(11) DEFAULT NULL COMMENT '角色类型',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_role_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`code`,`type`,`state`,`remark`,`version`,`create_by`,`create_time`,`update_by`,`update_time`) values 
(1,'管理员','admin',NULL,1,NULL,0,NULL,'2019-10-25 09:47:21',NULL,NULL),
(2,'test','test',NULL,1,NULL,0,NULL,'2019-10-25 09:48:02',NULL,NULL);

/*Table structure for table `sys_role_menu` */

DROP TABLE IF EXISTS `sys_role_menu`;

CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色id',
  `menu_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '菜单编码',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色与菜单关联表';

/*Data for the table `sys_role_menu` */

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色权限关系';

/*Data for the table `sys_role_permission` */

insert  into `sys_role_permission`(`id`,`role_id`,`permission_id`,`state`,`remark`,`version`,`create_time`,`update_time`) values 
(1,1,1,1,NULL,0,'2019-10-26 22:16:19',NULL),
(2,1,100,1,NULL,0,'2019-10-26 22:16:19',NULL),
(3,1,200,1,NULL,0,'2019-10-26 22:16:19',NULL),
(4,1,300,1,NULL,0,'2019-10-26 22:16:19',NULL),
(5,1,400,1,NULL,0,'2019-10-26 22:16:19',NULL),
(6,1,1000,1,NULL,0,'2019-10-26 22:16:19',NULL),
(7,1,1001,1,NULL,0,'2019-10-26 22:16:19',NULL),
(8,1,1002,1,NULL,0,'2019-10-26 22:16:19',NULL),
(9,1,1003,1,NULL,0,'2019-10-26 22:16:19',NULL),
(10,1,1004,1,NULL,0,'2019-10-26 22:16:19',NULL),
(11,1,1005,1,NULL,0,'2019-10-26 22:16:19',NULL),
(12,1,1006,1,NULL,0,'2019-10-26 22:16:19',NULL),
(13,1,2000,1,NULL,0,'2019-10-26 22:16:19',NULL),
(14,1,2001,1,NULL,0,'2019-10-26 22:16:19',NULL),
(15,1,2002,1,NULL,0,'2019-10-26 22:16:19',NULL),
(16,1,2003,1,NULL,0,'2019-10-26 22:16:19',NULL),
(17,1,2004,1,NULL,0,'2019-10-26 22:16:19',NULL),
(18,1,3000,1,NULL,0,'2019-10-26 22:16:19',NULL),
(19,1,3001,1,NULL,0,'2019-10-26 22:16:19',NULL),
(20,1,3002,1,NULL,0,'2019-10-26 22:16:19',NULL),
(21,1,3003,1,NULL,0,'2019-10-26 22:16:19',NULL),
(22,1,3004,1,NULL,0,'2019-10-26 22:16:19',NULL),
(23,1,3005,1,NULL,0,'2019-10-26 22:16:19',NULL),
(24,1,3006,1,NULL,0,'2019-10-26 22:16:19',NULL),
(25,1,3007,1,NULL,0,'2019-10-26 22:16:19',NULL),
(26,1,3008,1,NULL,0,'2019-10-26 22:16:19',NULL),
(27,1,3009,1,NULL,0,'2019-10-26 22:16:19',NULL),
(28,1,4001,1,NULL,0,'2019-10-26 22:16:19',NULL),
(29,1,4002,1,NULL,0,'2019-10-26 22:16:19',NULL),
(30,1,4003,1,NULL,0,'2019-10-26 22:16:19',NULL),
(31,1,4004,1,NULL,0,'2019-10-26 22:16:19',NULL),
(32,1,500,1,NULL,0,'2019-11-25 10:43:15',NULL),
(33,1,5000,1,NULL,0,'2019-11-25 10:43:32',NULL),
(34,1,5001,1,NULL,0,'2019-11-25 10:43:43',NULL),
(35,1,5002,1,NULL,0,'2019-11-25 10:43:54',NULL),
(36,1,5003,1,NULL,0,'2019-11-25 10:44:11',NULL),
(37,1,5004,1,NULL,0,'2019-11-25 10:52:55',NULL),
(38,1,5005,1,NULL,0,'2019-11-25 14:39:15',NULL),
(39,1,600,1,NULL,0,'2019-11-26 10:17:26',NULL),
(40,1,6001,1,NULL,0,'2019-11-26 10:17:39',NULL),
(41,1,6002,1,NULL,0,'2019-11-26 10:17:52',NULL),
(42,1,6003,1,NULL,0,'2019-11-26 10:18:02',NULL),
(43,1,6004,1,NULL,0,'2019-11-26 10:18:24',NULL),
(44,1,6000,1,NULL,0,'2019-11-26 10:19:02',NULL),
(45,1,700,1,NULL,0,'2019-11-28 15:49:01',NULL),
(46,1,7000,1,NULL,0,'2019-11-28 15:49:20',NULL),
(47,1,7001,1,NULL,0,'2019-11-28 15:49:34',NULL),
(48,1,7002,1,NULL,0,'2019-11-28 15:49:48',NULL),
(49,1,7003,1,NULL,0,'2019-11-28 15:50:06',NULL),
(50,1,7004,1,NULL,0,'2019-11-28 15:50:21',NULL),
(51,1,1007,1,NULL,0,'2019-11-29 14:45:48',NULL),
(52,1,1008,1,NULL,0,'2019-11-30 15:27:27',NULL),
(53,1,1009,1,NULL,0,'2019-11-30 18:18:12',NULL),
(54,1,1010,1,NULL,0,'2019-12-02 12:15:28',NULL),
(55,1,8001,1,NULL,0,'2019-12-03 14:35:46',NULL),
(56,1,8002,1,NULL,0,'2019-12-03 17:44:37',NULL),
(57,1,8003,1,NULL,0,'2019-12-03 17:44:50',NULL);

/*Table structure for table `sys_school` */

DROP TABLE IF EXISTS `sys_school`;

CREATE TABLE `sys_school` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `school_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '学校代码',
  `school_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学校名称',
  `province_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份代码',
  `province_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '省份名称',
  `city_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市代码',
  `city_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '城市名称',
  `district_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区县代码',
  `district_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区县名称',
  `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `school_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '学校介绍',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_school_school_code_uindex` (`school_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学校表';

/*Data for the table `sys_school` */

/*Table structure for table `sys_school_college` */

DROP TABLE IF EXISTS `sys_school_college`;

CREATE TABLE `sys_school_college` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `school_id` bigint(20) NOT NULL COMMENT '学校id',
  `college_code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '专业代码',
  `college_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '专业名称',
  `college_info` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '专业介绍',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_school_college_college_code_uindex` (`college_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学校专业表';

/*Data for the table `sys_school_college` */

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(30) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '登录密码',
  `salt` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐值',
  `email` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮箱',
  `mobile` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '办公电话',
  `phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号码',
  `gender` int(11) NOT NULL DEFAULT '1' COMMENT '性别，0：女，1：男，默认1',
  `avatar` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像路径',
  `signtext` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '个性签名',
  `wx_openid` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '绑定的微信号',
  `mobile_imei` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '绑定的手机串号',
  `user_type` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户类型',
  `ref_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户类型引用编号',
  `ref_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户类型引用姓名',
  `mgr_type` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '管理员类型（0非管理员 1系统管理员  2二级管理员）',
  `reg_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '1' COMMENT '开通方式（1:手工;2:注册;3:导入;）',
  `pwd_security_level` decimal(1,0) DEFAULT NULL COMMENT '密码安全级别（0初始 1很弱 2弱 3安全 4很安全）',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `pwd_update_record` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码修改记录',
  `pwd_question` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密保问题',
  `pwd_question_answer` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密保问题答案',
  `pwd_question2` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密保问题2',
  `pwd_question_answer2` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密保问题答案2',
  `pwd_question3` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密保问题3',
  `pwd_question_answer3` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密保问题答案3',
  `pwd_quest_update_date` datetime DEFAULT NULL COMMENT '密码问题修改时间',
  `last_login_ip` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `freeze_date` datetime DEFAULT NULL COMMENT '冻结时间',
  `freeze_cause` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '冻结原因',
  `user_weight` decimal(8,0) DEFAULT '0' COMMENT '用户权重（降序）',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`nickname`,`password`,`salt`,`email`,`mobile`,`phone`,`gender`,`avatar`,`signtext`,`wx_openid`,`mobile_imei`,`user_type`,`ref_code`,`ref_name`,`mgr_type`,`reg_type`,`pwd_security_level`,`pwd_update_date`,`pwd_update_record`,`pwd_question`,`pwd_question_answer`,`pwd_question2`,`pwd_question_answer2`,`pwd_question3`,`pwd_question_answer3`,`pwd_quest_update_date`,`last_login_ip`,`last_login_date`,`freeze_date`,`freeze_cause`,`user_weight`,`remarks`,`department_id`,`role_id`,`state`,`deleted`,`version`,`create_by`,`create_time`,`update_by`,`update_time`,`corp_code`,`corp_name`) values 
(1,'admin','管理员','11a254dab80d52bc4a347e030e54d861a9d2cdb2af2185a9ca4a7318e830d04d','666',NULL,NULL,'13950019129',1,'http://localhost:8888//resource/201911102329033.png',NULL,NULL,NULL,'1',NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,1,1,0,0,'1','2019-11-23 10:42:01','1',NULL,'0','whyy');

/*Table structure for table `sys_user_office` */

DROP TABLE IF EXISTS `sys_user_office`;

CREATE TABLE `sys_user_office` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `office_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '机构代码',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户和机构关联表';

/*Data for the table `sys_user_office` */

/*Table structure for table `sys_user_role` */

DROP TABLE IF EXISTS `sys_user_role`;

CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户和角色关联表';

/*Data for the table `sys_user_role` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
