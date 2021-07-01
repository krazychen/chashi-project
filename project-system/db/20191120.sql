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
  `name` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '名称',
  `foo` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Foo',
  `bar` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT 'Bar',
  `remark` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
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

/*Table structure for table `ip` */

DROP TABLE IF EXISTS `ip`;

CREATE TABLE `ip` (
  `ip_start` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `ip_end` varchar(15) COLLATE utf8mb4_general_ci NOT NULL,
  `area` varchar(100) COLLATE utf8mb4_general_ci NOT NULL,
  `operator` varchar(200) COLLATE utf8mb4_general_ci NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip_start_num` bigint(20) NOT NULL,
  `ip_end_num` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=526718 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='IP地址';

/*Data for the table `ip` */

/*Table structure for table `sys_area` */

DROP TABLE IF EXISTS `sys_area`;

CREATE TABLE `sys_area` (
  `area_code` varchar(100) NOT NULL COMMENT '区域编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `area_name` varchar(100) NOT NULL COMMENT '区域名称',
  `area_type` char(1) DEFAULT NULL COMMENT '区域类型',
  `deleted` int(11) NOT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL COMMENT '版本',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='行政区划';

/*Data for the table `sys_area` */

/*Table structure for table `sys_department` */

DROP TABLE IF EXISTS `sys_department`;

CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `level` int(11) DEFAULT NULL COMMENT '部门层级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
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
  `dict_code` varchar(64) NOT NULL COMMENT '字典编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `dict_label` varchar(100) NOT NULL COMMENT '字典标签',
  `dict_value` varchar(100) NOT NULL COMMENT '字典键值',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `is_sys` char(1) NOT NULL COMMENT '系统内置（1是 0否）',
  `description` varchar(500) DEFAULT NULL COMMENT '字典描述',
  `css_style` varchar(500) DEFAULT NULL COMMENT 'css样式（如：color:red)',
  `css_class` varchar(500) DEFAULT NULL COMMENT 'css类名（如：red）',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典数据表';

/*Data for the table `sys_dict_data` */

/*Table structure for table `sys_dict_type` */

DROP TABLE IF EXISTS `sys_dict_type`;

CREATE TABLE `sys_dict_type` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `is_sys` char(1) NOT NULL COMMENT '是否系统字典',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='字典类型表';

/*Data for the table `sys_dict_type` */

/*Table structure for table `sys_log` */

DROP TABLE IF EXISTS `sys_log`;

CREATE TABLE `sys_log` (
  `log_id` bigint(18) NOT NULL COMMENT '主键',
  `type` tinyint(1) DEFAULT NULL COMMENT '类型',
  `content` varchar(255) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
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
  `menu_code` varchar(64) NOT NULL COMMENT '菜单编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `menu_name` varchar(100) NOT NULL COMMENT '菜单名称',
  `menu_type` char(1) NOT NULL COMMENT '菜单类型（1菜单 2权限 3开发）',
  `menu_href` varchar(1000) DEFAULT NULL COMMENT '链接',
  `menu_target` varchar(20) DEFAULT NULL COMMENT '目标',
  `menu_icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `menu_color` varchar(50) DEFAULT NULL COMMENT '颜色',
  `menu_title` varchar(100) DEFAULT NULL COMMENT '菜单标题',
  `permission` varchar(1000) DEFAULT NULL COMMENT '权限标识',
  `weight` decimal(4,0) DEFAULT NULL COMMENT '菜单权重',
  `is_show` char(1) NOT NULL COMMENT '是否显示（1显示 0隐藏）',
  `sys_code` varchar(64) NOT NULL COMMENT '归属系统（default:主导航菜单、mobileApp:APP菜单）',
  `module_codes` varchar(500) NOT NULL COMMENT '归属模块（多个用逗号隔开）',
  `deleted` int(11) NOT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='菜单表';

/*Data for the table `sys_menu` */

/*Table structure for table `sys_office` */

DROP TABLE IF EXISTS `sys_office`;

CREATE TABLE `sys_office` (
  `office_code` varchar(64) NOT NULL COMMENT '机构编码',
  `parent_code` varchar(64) NOT NULL COMMENT '父级编号',
  `parent_codes` varchar(1000) NOT NULL COMMENT '所有父级编号',
  `tree_sort` decimal(10,0) NOT NULL COMMENT '本级排序号（升序）',
  `tree_sorts` varchar(1000) NOT NULL COMMENT '所有级别排序号',
  `tree_leaf` char(1) NOT NULL COMMENT '是否最末级',
  `tree_level` decimal(4,0) NOT NULL COMMENT '层次级别',
  `tree_names` varchar(1000) NOT NULL COMMENT '全节点名',
  `view_code` varchar(100) NOT NULL COMMENT '机构代码',
  `office_name` varchar(100) NOT NULL COMMENT '机构名称',
  `full_name` varchar(200) NOT NULL COMMENT '机构全称',
  `office_type` char(1) NOT NULL COMMENT '机构类型',
  `leader` varchar(100) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(100) DEFAULT NULL COMMENT '办公电话',
  `address` varchar(255) DEFAULT NULL COMMENT '联系地址',
  `zip_code` varchar(100) DEFAULT NULL COMMENT '邮政编码',
  `email` varchar(300) DEFAULT NULL COMMENT '电子邮箱',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态（0正常 1删除 2停用）',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`office_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='组织机构表';

/*Data for the table `sys_office` */

/*Table structure for table `sys_permission` */

DROP TABLE IF EXISTS `sys_permission`;

CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `url` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '路径',
  `code` varchar(100) COLLATE utf8mb4_general_ci NOT NULL COMMENT '唯一编码',
  `icon` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `type` int(11) NOT NULL COMMENT '类型，1：菜单，2：按钮',
  `level` int(11) NOT NULL COMMENT '层级，1：第一级，2：第二级，N：第N级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
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
(4004,'部门分页列表',400,NULL,'sys:department:page',NULL,2,3,1,0,NULL,0,'2019-10-26 11:18:40',NULL);

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色唯一编码',
  `type` int(11) DEFAULT NULL COMMENT '角色类型',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '角色状态，0：禁用，1：启用',
  `remark` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_role_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统角色';

/*Data for the table `sys_role` */

insert  into `sys_role`(`id`,`name`,`code`,`type`,`state`,`remark`,`version`,`create_by`,`create_time`,`update_by`,`update_time`) values 
(1,'管理员','admin',NULL,1,NULL,0,NULL,'2019-10-25 09:47:21',NULL,NULL),
(2,'test','test',NULL,1,NULL,0,NULL,'2019-10-25 09:48:02',NULL,NULL);

/*Table structure for table `sys_role_permission` */

DROP TABLE IF EXISTS `sys_role_permission`;

CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `remark` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
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
(31,1,4004,1,NULL,0,'2019-10-26 22:16:19',NULL);

/*Table structure for table `sys_user` */

DROP TABLE IF EXISTS `sys_user`;

CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `nickname` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '昵称',
  `password` varchar(64) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(32) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '盐值',
  `phone` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '手机号码',
  `gender` int(11) NOT NULL DEFAULT '1' COMMENT '性别，0：女，1：男，默认1',
  `head` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '头像',
  `remark` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'remark',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `department_id` bigint(20) NOT NULL COMMENT '部门id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统用户';

/*Data for the table `sys_user` */

insert  into `sys_user`(`id`,`username`,`nickname`,`password`,`salt`,`phone`,`gender`,`head`,`remark`,`state`,`department_id`,`role_id`,`deleted`,`version`,`create_by`,`create_time`,`update_by`,`update_time`) values 
(1,'admin','管理员','11a254dab80d52bc4a347e030e54d861a9d2cdb2af2185a9ca4a7318e830d04d','666','',1,'http://localhost:8888//resource/201911102329033.png','Administrator Account',1,1,1,0,1,NULL,'2019-08-26 00:52:01',NULL,'2019-10-27 23:32:29'),
(2,'test','测试人员','34783fb724b259beb71a1279f7cd93bdcfd92a273d566f926419a37825c500df','087c2e9857f35f1e243367f3b89b81c1','',1,NULL,'Tester Account',1,1,2,0,0,NULL,'2019-10-05 14:04:27',NULL,NULL),
(1194221927617601537,'test2','test2','ec6dbdf05090add82f37215d3126d240186a8a1933471c04fbc6c39bdffbac00','6d713625fa6efee4e39978b36c1d5352','13950019129',2,NULL,NULL,1,1,1,0,0,NULL,'2019-11-12 19:54:29',NULL,NULL),
(1194273956599758849,'test3','test4','abe422983d5890071d168c4175eb3731ef311d0be609af8de2aee285e8613864','646b54382ba7422870b6aa0fffdc4bd5','13950019129',1,NULL,NULL,1,1,1,1,1,NULL,'2019-11-12 23:21:14',NULL,'2019-11-12 23:47:14'),
(1194284857021235201,'test5','test6','6c203ef68e23f2eca05560bdbc5a94ee20024df8fded8997657bd88eada5b051','bdb235fe0b24b3eeafc51e5aecfb458c','13950019129',1,NULL,NULL,1,1,1,0,1,NULL,'2019-11-13 00:04:33',NULL,'2019-11-13 00:04:55'),
(1196441289736925186,'123456','123456','c8ad8f3303646e1eb3b68a698353495997469a3259719f040c4242308bfb634b','91c1aeb38676529e47fb5aa45ea5c93a','13950019129',1,NULL,NULL,0,1,1,0,3,NULL,'2019-11-18 22:53:27','1','2019-11-18 23:06:18'),
(1196444611160129537,'1234567','12345678','b92375ae1069e27b850d1a673e8470cf3e5e24004bef02c7f3355ed8d00f5d69','df78406a43aaee61720afebf41b9668c','1235',1,NULL,NULL,1,1,1,0,1,'1','2019-11-18 23:06:39','1','2019-11-18 23:08:13');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
