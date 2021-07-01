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

/*Table structure for table `QRTZ_BLOB_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;

CREATE TABLE `QRTZ_BLOB_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `BLOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `SCHED_NAME` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_BLOB_TRIGGERS` */

/*Table structure for table `QRTZ_CALENDARS` */

DROP TABLE IF EXISTS `QRTZ_CALENDARS`;

CREATE TABLE `QRTZ_CALENDARS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `CALENDAR_NAME` varchar(200) NOT NULL,
  `CALENDAR` blob NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_CALENDARS` */

/*Table structure for table `QRTZ_CRON_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;

CREATE TABLE `QRTZ_CRON_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `CRON_EXPRESSION` varchar(120) NOT NULL,
  `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_CRON_TRIGGERS` */

/*Table structure for table `QRTZ_FIRED_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;

CREATE TABLE `QRTZ_FIRED_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `ENTRY_ID` varchar(95) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `FIRED_TIME` bigint(13) NOT NULL,
  `SCHED_TIME` bigint(13) NOT NULL,
  `PRIORITY` int(11) NOT NULL,
  `STATE` varchar(16) NOT NULL,
  `JOB_NAME` varchar(200) DEFAULT NULL,
  `JOB_GROUP` varchar(200) DEFAULT NULL,
  `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
  `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`),
  KEY `IDX_QRTZ_FT_TRIG_INST_NAME` (`SCHED_NAME`,`INSTANCE_NAME`),
  KEY `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY` (`SCHED_NAME`,`INSTANCE_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_FT_J_G` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_FT_T_G` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_FT_TG` (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_FIRED_TRIGGERS` */

/*Table structure for table `QRTZ_JOB_DETAILS` */

DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;

CREATE TABLE `QRTZ_JOB_DETAILS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `JOB_CLASS_NAME` varchar(250) NOT NULL,
  `IS_DURABLE` varchar(1) NOT NULL,
  `IS_NONCONCURRENT` varchar(1) NOT NULL,
  `IS_UPDATE_DATA` varchar(1) NOT NULL,
  `REQUESTS_RECOVERY` varchar(1) NOT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_J_REQ_RECOVERY` (`SCHED_NAME`,`REQUESTS_RECOVERY`),
  KEY `IDX_QRTZ_J_GRP` (`SCHED_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_JOB_DETAILS` */

/*Table structure for table `QRTZ_LOCKS` */

DROP TABLE IF EXISTS `QRTZ_LOCKS`;

CREATE TABLE `QRTZ_LOCKS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `LOCK_NAME` varchar(40) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_LOCKS` */

insert  into `QRTZ_LOCKS`(`SCHED_NAME`,`LOCK_NAME`) values 
('quartzScheduler','STATE_ACCESS'),
('quartzScheduler','TRIGGER_ACCESS');

/*Table structure for table `QRTZ_PAUSED_TRIGGER_GRPS` */

DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;

CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_PAUSED_TRIGGER_GRPS` */

/*Table structure for table `QRTZ_SCHEDULER_STATE` */

DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;

CREATE TABLE `QRTZ_SCHEDULER_STATE` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `INSTANCE_NAME` varchar(200) NOT NULL,
  `LAST_CHECKIN_TIME` bigint(13) NOT NULL,
  `CHECKIN_INTERVAL` bigint(13) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_SCHEDULER_STATE` */

insert  into `QRTZ_SCHEDULER_STATE`(`SCHED_NAME`,`INSTANCE_NAME`,`LAST_CHECKIN_TIME`,`CHECKIN_INTERVAL`) values 
('quartzScheduler','krislaptop1576774850311',1576774887789,5000);

/*Table structure for table `QRTZ_SIMPLE_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;

CREATE TABLE `QRTZ_SIMPLE_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `REPEAT_COUNT` bigint(7) NOT NULL,
  `REPEAT_INTERVAL` bigint(12) NOT NULL,
  `TIMES_TRIGGERED` bigint(10) NOT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_SIMPLE_TRIGGERS` */

/*Table structure for table `QRTZ_SIMPROP_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;

CREATE TABLE `QRTZ_SIMPROP_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `STR_PROP_1` varchar(512) DEFAULT NULL,
  `STR_PROP_2` varchar(512) DEFAULT NULL,
  `STR_PROP_3` varchar(512) DEFAULT NULL,
  `INT_PROP_1` int(11) DEFAULT NULL,
  `INT_PROP_2` int(11) DEFAULT NULL,
  `LONG_PROP_1` bigint(20) DEFAULT NULL,
  `LONG_PROP_2` bigint(20) DEFAULT NULL,
  `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
  `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
  `BOOL_PROP_1` varchar(1) DEFAULT NULL,
  `BOOL_PROP_2` varchar(1) DEFAULT NULL,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_SIMPROP_TRIGGERS` */

/*Table structure for table `QRTZ_TRIGGERS` */

DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;

CREATE TABLE `QRTZ_TRIGGERS` (
  `SCHED_NAME` varchar(120) NOT NULL,
  `TRIGGER_NAME` varchar(200) NOT NULL,
  `TRIGGER_GROUP` varchar(200) NOT NULL,
  `JOB_NAME` varchar(200) NOT NULL,
  `JOB_GROUP` varchar(200) NOT NULL,
  `DESCRIPTION` varchar(250) DEFAULT NULL,
  `NEXT_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PREV_FIRE_TIME` bigint(13) DEFAULT NULL,
  `PRIORITY` int(11) DEFAULT NULL,
  `TRIGGER_STATE` varchar(16) NOT NULL,
  `TRIGGER_TYPE` varchar(8) NOT NULL,
  `START_TIME` bigint(13) NOT NULL,
  `END_TIME` bigint(13) DEFAULT NULL,
  `CALENDAR_NAME` varchar(200) DEFAULT NULL,
  `MISFIRE_INSTR` smallint(2) DEFAULT NULL,
  `JOB_DATA` blob,
  PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_J` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_JG` (`SCHED_NAME`,`JOB_GROUP`),
  KEY `IDX_QRTZ_T_C` (`SCHED_NAME`,`CALENDAR_NAME`),
  KEY `IDX_QRTZ_T_G` (`SCHED_NAME`,`TRIGGER_GROUP`),
  KEY `IDX_QRTZ_T_STATE` (`SCHED_NAME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_STATE` (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_N_G_STATE` (`SCHED_NAME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NEXT_FIRE_TIME` (`SCHED_NAME`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST` (`SCHED_NAME`,`TRIGGER_STATE`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_STATE`),
  KEY `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP` (`SCHED_NAME`,`MISFIRE_INSTR`,`NEXT_FIRE_TIME`,`TRIGGER_GROUP`,`TRIGGER_STATE`),
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

/*Data for the table `QRTZ_TRIGGERS` */

/*Table structure for table `hw_class` */

DROP TABLE IF EXISTS `hw_class`;

CREATE TABLE `hw_class` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `class_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '班级名称',
  `type_id` bigint(20) NOT NULL COMMENT '标签id',
  `type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：未发布，1：发布，2：冻结',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='班级表';

/*Data for the table `hw_class` */

/*Table structure for table `hw_class_type` */

DROP TABLE IF EXISTS `hw_class_type`;

CREATE TABLE `hw_class_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标签名称',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：未发布，1：发布，2：冻结',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='标签表';

/*Data for the table `hw_class_type` */

/*Table structure for table `hw_course_category` */

DROP TABLE IF EXISTS `hw_course_category`;

CREATE TABLE `hw_course_category` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类名称',
  `type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类类型',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='课程分类表';

/*Data for the table `hw_course_category` */

/*Table structure for table `hw_homework` */

DROP TABLE IF EXISTS `hw_homework`;

CREATE TABLE `hw_homework` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `subject_name` varchar(100) DEFAULT NULL COMMENT '题目名称',
  `complete_explain` text COMMENT '作业完成说明',
  `start_time` datetime DEFAULT NULL COMMENT '作业开始时间',
  `end_time` datetime DEFAULT NULL COMMENT '作业截止时间',
  `audit_end_time` datetime DEFAULT NULL COMMENT '作业批改截止时间',
  `subject_type_id` bigint(20) DEFAULT NULL COMMENT '题型id',
  `subject_type_name` varchar(100) DEFAULT NULL COMMENT '题型名称',
  `class_id` bigint(20) DEFAULT NULL COMMENT '班级id',
  `class_name` varchar(100) DEFAULT NULL COMMENT '班级名称',
  `class_type_id` bigint(20) DEFAULT NULL COMMENT '标签id',
  `class_type_name` varchar(100) DEFAULT NULL COMMENT '班级标签',
  `is_qa` int(11) DEFAULT NULL COMMENT '是否启用追问',
  `qa_number` decimal(10,0) DEFAULT NULL COMMENT '追问次数',
  `is_remind` int(11) DEFAULT NULL COMMENT '启用系统提醒',
  `sys_cycle` int(11) DEFAULT NULL COMMENT '系统提醒周期',
  `is_wxremind` char(1) DEFAULT NULL COMMENT '启用微信提醒',
  `wxremind_time1` int(11) DEFAULT NULL COMMENT '微信提醒时间1',
  `wxremind_time2` int(11) DEFAULT NULL COMMENT '微信提醒时间2',
  `is_msg` char(1) DEFAULT NULL COMMENT '启用短信提醒',
  `msg_time1` int(11) DEFAULT NULL COMMENT '短信提醒时间1',
  `msg_time2` int(11) DEFAULT NULL COMMENT '短信提醒时间2',
  `subject_state` char(1) DEFAULT NULL COMMENT '题目状态【未发布、已发布】',
  `subject_by` bigint(20) DEFAULT NULL COMMENT '题目发布人id',
  `subject_time` datetime DEFAULT NULL COMMENT '题目发布时间',
  `teacher_id` bigint(20) DEFAULT NULL COMMENT '委托老师id',
  `entrust_start_time` datetime DEFAULT NULL COMMENT '委托开始时间',
  `entrust_end_time` datetime DEFAULT NULL COMMENT '委托结束时间',
  `entrust_by` bigint(20) DEFAULT NULL COMMENT '委托修改人id',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本号',
  `corp_code` varchar(64) DEFAULT NULL COMMENT '租户代码',
  `corp_name` varchar(64) DEFAULT NULL COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作业表';

/*Data for the table `hw_homework` */

/*Table structure for table `hw_homework_audit` */

DROP TABLE IF EXISTS `hw_homework_audit`;

CREATE TABLE `hw_homework_audit` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `stu_homework_id` bigint(20) DEFAULT NULL COMMENT '学员作业id',
  `audit_type` char(1) DEFAULT NULL COMMENT '批改类型【段落、总评】',
  `tag_paragraph` text COMMENT '标注段落',
  `tag_comment` text COMMENT '标注语评',
  `tag_score` decimal(4,0) DEFAULT NULL COMMENT '标注分数',
  `audit_teacher_id` bigint(20) DEFAULT NULL COMMENT '批改老师id',
  `audit_time` datetime DEFAULT NULL COMMENT '批改时间',
  `repeat_comment` text COMMENT '复评评语',
  `repeat_score` decimal(4,0) DEFAULT NULL COMMENT '复评分数',
  `repeat_teacher_id` bigint(20) DEFAULT NULL COMMENT '复评老师id',
  `repeat_time` datetime DEFAULT NULL COMMENT '复评时间',
  `entrust_teacher_id` bigint(20) DEFAULT NULL COMMENT '委托批改老师id',
  `entrust_start_time` datetime DEFAULT NULL COMMENT '委托开始时间',
  `entrust_endt_time` datetime DEFAULT NULL COMMENT '委托结束时间',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本号',
  `corp_code` varchar(64) DEFAULT NULL COMMENT '租户代码',
  `corp_name` varchar(64) DEFAULT NULL COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作业批改表';

/*Data for the table `hw_homework_audit` */

/*Table structure for table `hw_homework_material` */

DROP TABLE IF EXISTS `hw_homework_material`;

CREATE TABLE `hw_homework_material` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `homework_id` bigint(20) DEFAULT NULL COMMENT '作业id',
  `material_name` varchar(100) DEFAULT NULL COMMENT '材料名称',
  `material_address` varchar(1000) DEFAULT NULL COMMENT '材料地址',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本号',
  `corp_code` varchar(64) DEFAULT NULL COMMENT '租户代码',
  `corp_name` varchar(64) DEFAULT NULL COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作业材料表';

/*Data for the table `hw_homework_material` */

/*Table structure for table `hw_homework_qa` */

DROP TABLE IF EXISTS `hw_homework_qa`;

CREATE TABLE `hw_homework_qa` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `stu_homework_id` bigint(20) DEFAULT NULL COMMENT '学员作业id',
  `qa_content` text COMMENT '追问内容',
  `qa_time` datetime DEFAULT NULL COMMENT '追问时间',
  `qa_reply` text COMMENT '追问回答',
  `qa_reply_time` char(10) DEFAULT NULL COMMENT '回答时间',
  `qa_reply_teacher_id` char(10) DEFAULT NULL COMMENT '回答老师id',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本号',
  `corp_code` varchar(64) DEFAULT NULL COMMENT '租户代码',
  `corp_name` varchar(64) DEFAULT NULL COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='作业追问表';

/*Data for the table `hw_homework_qa` */

/*Table structure for table `hw_stu_homework` */

DROP TABLE IF EXISTS `hw_stu_homework`;

CREATE TABLE `hw_stu_homework` (
  `id` bigint(20) NOT NULL COMMENT '主键id',
  `homework_id` bigint(20) DEFAULT NULL COMMENT '作业id',
  `student_id` bigint(20) DEFAULT NULL COMMENT '学员id',
  `start_time` datetime DEFAULT NULL COMMENT '开始作答时间',
  `content` text COMMENT '作业内容',
  `last_time` datetime DEFAULT NULL COMMENT '作答最后一次保存时间',
  `sub_time` datetime DEFAULT NULL COMMENT '作业提交时间',
  `sub_state` char(1) DEFAULT NULL COMMENT '作业状态【未开始、未提交、已提交】',
  `audit_state` char(1) DEFAULT NULL COMMENT '批改状态【未批改、已批改】',
  `audi_complete_time` datetime DEFAULT NULL COMMENT '批改完成时间',
  `repeat_state` char(1) DEFAULT NULL COMMENT '复评状态【未复评、已复评】',
  `repeat_time` datetime DEFAULT NULL COMMENT '复评完成时间',
  `qa_number` decimal(10,0) DEFAULT NULL COMMENT '追问次数',
  `update_by` bigint(20) DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `create_by` bigint(20) DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `deleted` int(11) DEFAULT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `VERSION` int(11) DEFAULT NULL COMMENT '版本号',
  `corp_code` varchar(64) DEFAULT NULL COMMENT '租户代码',
  `corp_name` varchar(64) DEFAULT NULL COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='学员作业表';

/*Data for the table `hw_stu_homework` */

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

insert  into `hw_student`(`id`,`province_code`,`province_name`,`city_code`,`city_name`,`district_code`,`district_name`,`address`,`school_id`,`school_code`,`school_name`,`college_id`,`college_code`,`college_name`,`remarks`,`status`,`deleted`,`version`,`create_by`,`create_time`,`update_by`,`update_time`,`corp_code`,`corp_name`) values 
(1206963191614062594,'','','','','','','',NULL,'','',NULL,'','',NULL,1,0,0,'1','2019-12-17 23:43:44','1','2019-12-17 23:43:44','0','whyy');

/*Table structure for table `hw_student_class` */

DROP TABLE IF EXISTS `hw_student_class`;

CREATE TABLE `hw_student_class` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `class_id` bigint(20) NOT NULL COMMENT '班级id',
  `student_id` bigint(20) NOT NULL COMMENT '学员id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='学员班级表';

/*Data for the table `hw_student_class` */

/*Table structure for table `hw_subject_type` */

DROP TABLE IF EXISTS `hw_subject_type`;

CREATE TABLE `hw_subject_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '题型名称',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：停用',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='题型表';

/*Data for the table `hw_subject_type` */

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
  `address` varchar(1000) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
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

/*Table structure for table `hw_teacher_class` */

DROP TABLE IF EXISTS `hw_teacher_class`;

CREATE TABLE `hw_teacher_class` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `class_id` bigint(20) NOT NULL COMMENT '班级id',
  `teacher_id` bigint(20) NOT NULL COMMENT '老师id',
  `teacher_role_id` bigint(20) NOT NULL COMMENT '老师角色id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='老师班级表';

/*Data for the table `hw_teacher_class` */

/*Table structure for table `hw_teacher_subtype` */

DROP TABLE IF EXISTS `hw_teacher_subtype`;

CREATE TABLE `hw_teacher_subtype` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `subject_type_id` bigint(20) NOT NULL COMMENT '题型id',
  `teacher_id` bigint(20) NOT NULL COMMENT '老师id',
  `teacher_role_id` bigint(20) NOT NULL COMMENT '老师角色id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='老师题型表';

/*Data for the table `hw_teacher_subtype` */

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
  `area_type` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '区域类型',
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
('0000-0001-0001-0001','00000000','00000000',100,'100','0',1,'首级','是','1','sys_yes_no','1','是否',NULL,NULL,0,0,'1','1','2019-11-28 10:27:12','1','2019-11-28 10:27:17',NULL,'0','whyy'),
('0000-0001-0001-0002','00000000','00000000',110,'100','0',1,'首级','否','0','sys_yes_no','1','是否',NULL,NULL,0,0,'1','1','2019-11-28 10:29:00','1','2019-11-28 10:29:01',NULL,'0','whyy'),
('0000-0001-0002-0001','00000000','00000000',100,'100','0',1,'首级','公司','1','sys_office_type','1','机构类型',NULL,NULL,0,0,'1','1','2019-11-28 10:32:11','1','2019-11-28 10:32:16',NULL,'0','whyy'),
('0000-0001-0002-0002','00000000','00000000',110,'100','0',1,'首级','部门','2','sys_office_type','1','机构类型',NULL,NULL,0,0,'1','1','2019-11-28 10:33:14','1','2019-11-28 10:33:18',NULL,'0','whyy'),
('0000-0001-0003-0001','00000000','00000000',100,'100','0',1,'首级','后台管理系统','1','sys_system_type','1','归属系统',NULL,NULL,0,0,'1','1','2019-11-28 10:38:16','1','2019-11-28 10:38:20',NULL,'0','whyy'),
('0000-0001-0003-0002','00000000','00000000',110,'100','0',1,'首级','作业批改系统','2','sys_system_type','1','归属系统',NULL,NULL,0,0,'1','1','2019-11-28 10:45:16','1','2019-11-28 10:45:24',NULL,'0','whyy'),
('0000-0001-0003-0003','00000000','00000000',120,'100','0',1,'首级','在线网课系统','3','sys_system_type','1','归属系统',NULL,NULL,0,0,'1','1','2019-11-28 10:46:58','1','2019-11-28 10:47:02',NULL,'0','whyy'),
('0000-0001-0004-0001','00000000','00000000',100,'100','0',1,'首级','系统管理','1','sys_module_type','1','归属模块',NULL,NULL,0,0,'1','1','2019-11-28 10:49:26','1','2019-11-28 10:49:31',NULL,'0','whyy'),
('0000-0001-0004-0002','00000000','00000000',110,'100','0',1,'首级','后台作业管理','2','sys_module_type','1','归属模块',NULL,NULL,0,0,'1','1','2019-11-28 10:50:50','1','2019-11-28 10:50:56',NULL,'0','whyy'),
('0000-0001-0004-0003','00000000','00000000',120,'100','0',1,'首级','学员作业管理','3','sys_module_type','1','归属模块',NULL,NULL,0,0,'1','1','2019-11-28 10:52:12','1','2019-11-28 10:52:17',NULL,'0','whyy'),
('0000-0001-0004-0004','00000000','00000000',130,'100','0',1,'首级','老师作业管理','4','sys_module_type','1','归属模块',NULL,NULL,0,0,'1','1','2019-11-28 10:53:58','1','2019-11-28 10:54:02',NULL,'0','whyy'),
('0000-0001-0005-0001','00000000','00000000',100,'100','0',1,'首级','菜单','1','sys_menu_type','1','归属模块',NULL,NULL,0,0,'1','1','2019-11-28 10:56:03','1','2019-11-28 10:56:05',NULL,'0','whyy'),
('0000-0001-0005-0002','00000000','00000000',110,'100','0',1,'首级','权限','2','sys_menu_type','1','菜单类型',NULL,NULL,0,0,'1','1','2019-11-28 10:57:34','1','2019-11-28 10:57:38',NULL,'0','whyy'),
('0000-0001-0006-0001','00000000','00000000',100,'100','0',1,'首级','显示','1','sys_show_hide','1','可见',NULL,NULL,0,0,'1','1','2019-11-28 10:59:52','1','2019-11-28 10:59:53',NULL,'0','whyy'),
('0000-0001-0006-0002','00000000','00000000',110,'100','0',1,'首级','隐藏','0','sys_show_hide','1','可见',NULL,NULL,0,0,'1','1','2019-11-28 11:01:26','1','2019-11-28 11:01:29',NULL,'0','whyy'),
('0000-0001-0007-0001','00000000','00000000',100,'100','0',1,'首级','系统用户','1','sys_user_type','1','用户类型',NULL,NULL,0,0,'1','1','2019-11-28 11:02:51','1','2019-11-28 11:02:57',NULL,'0','whyy'),
('0000-0001-0007-0002','00000000','00000000',110,'100','0',1,'首级','学生','2','sys_user_type','1','用户类型',NULL,NULL,0,0,'1','1','2019-11-28 11:04:13','1','2019-11-28 11:04:17',NULL,'0','whyy'),
('0000-0001-0007-0003','00000000','00000000',120,'100','0',1,'首级','老师','3','sys_user_type','1','用户类型',NULL,NULL,0,0,'1','1','2019-11-28 11:05:25','1','2019-11-28 11:05:27',NULL,'0','whyy'),
('0000-0001-0008-0001','00000000','00000000',100,'100','0',1,'首级','系统管理员','sysadmin','sys_role_type','1','角色类型',NULL,NULL,0,0,'1','1','2019-11-28 11:07:23','1','2019-11-28 11:07:28',NULL,'0','whyy'),
('0000-0001-0008-0002','00000000','00000000',110,'100','0',1,'首级','学员','student','sys_role_type','1','角色类型',NULL,NULL,0,0,'1','1','2019-11-28 11:09:19','1','2019-11-28 11:09:28',NULL,'0','whyy'),
('0000-0001-0008-0003','00000000','00000000',120,'100','0',1,'首级','一级老师','leve1teacher','sys_role_type','1','角色类型',NULL,NULL,0,0,'1','1','2019-11-28 11:10:48','1','2019-11-28 11:10:50',NULL,'0','whyy'),
('0000-0001-0008-0004','00000000','00000000',130,'100','0',1,'首级','二级老师','leve2teacher','sys_role_type','1','角色类型',NULL,NULL,0,0,'1','1','2019-11-28 11:12:10','1','2019-11-28 11:12:14',NULL,'0','whyy'),
('0000-0001-0008-0005','00000000','00000000',140,'100','0',1,'首级','三级老师','leve2teacher','sys_role_type','1','角色类型',NULL,NULL,0,0,'1','1','2019-11-28 11:13:28','1','2019-11-28 11:13:33',NULL,'0','whyy'),
('0000-0001-0009-0001','00000000','00000000',100,'100','0',1,'首级','省份直辖市','province','sys_area_type','1','区域类型',NULL,NULL,0,0,'1','1','2019-11-28 11:16:00','1','2019-11-28 11:16:02',NULL,'0','whyy'),
('0000-0001-0009-0002','00000000','00000000',110,'100','0',1,'首级','地市','city','sys_area_type','1','区域类型',NULL,NULL,0,0,'1','1','2019-11-28 11:17:17','1','2019-11-28 11:17:20',NULL,'0','whyy'),
('0000-0001-0009-0003','00000000','00000000',120,'100','0',1,'首级','区县','region','sys_area_type','1','区域类型',NULL,NULL,0,0,'1','1','2019-11-28 11:19:12','1','2019-11-28 11:19:14',NULL,'0','whyy'),
('53e22074-a170-4a28-9815-ea5c3c2a038c','00000000','00000000',100,'1','1',2,'首级节点','未发布','0','hw_class_type_status','1','',NULL,NULL,0,0,'1','1','2019-12-12 17:31:42','1','2019-12-12 17:31:42',NULL,'0','whyy'),
('7965bf1f-f391-45bf-b77e-077fbd6d4269','00000000','00000000',110,'1','1',2,'首级节点','发布','1','hw_class_type_status','1','',NULL,NULL,0,0,'1','1','2019-12-12 17:31:56','1','2019-12-12 17:31:56',NULL,'0','whyy'),
('ad61fa83-3015-4ebb-a66e-71160c78678c','00000000','00000000',120,'1','1',2,'首级节点','冻结','2','hw_class_type_status','1','',NULL,NULL,0,0,'1','1','2019-12-12 17:32:15','1','2019-12-12 17:32:15',NULL,'0','whyy');

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
('0000-0001-0001','是否','sys_yes_no','1',0,0,'1','1','2019-11-28 09:57:06','1','2019-11-28 09:57:21',NULL),
('0000-0001-0002','机构类型','sys_office_type','1',0,0,'1','1','2019-11-28 10:00:49','1','2019-11-28 10:00:55',NULL),
('0000-0001-0003','归属系统','sys_system_type','1',0,0,'1','1','2019-11-28 10:01:46','1','2019-11-28 10:01:57',NULL),
('0000-0001-0004','归属模块','sys_module_type','1',0,0,'1','1','2019-11-28 10:09:44','1','2019-11-28 10:09:50',NULL),
('0000-0001-0005','菜单类型','sys_menu_type','1',0,0,'1','1','2019-11-28 10:10:41','1','2019-11-28 10:10:48',NULL),
('0000-0001-0006','可见','sys_show_hide','1',0,0,'1','1','2019-11-28 10:11:24','1','2019-11-28 10:11:28',NULL),
('0000-0001-0007','用户类型','sys_user_type','1',0,0,'1','1','2019-11-28 10:12:28','1','2019-11-28 10:12:41',NULL),
('0000-0001-0008','角色类型','sys_role_type','1',0,0,'1','1','2019-11-28 10:13:59','1','2019-11-28 10:14:03',NULL),
('0000-0001-0009','区域类型','sys_area_type','1',0,0,'1','1','2019-11-28 10:14:50','1','2019-11-28 10:14:56',NULL),
('0000-0001-0010','发布冻结','hw_class_type_status','1',0,0,'1','1','2019-12-12 17:08:53','1','2019-12-12 17:08:53','0未发布1发布2冻结');

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
('10000','00000000','00000000',100,'1','2',1,'首级节点','系统管理','2','','','fa fa-list-ul','#409EFF','系统管理','sys:user:page',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('11000','10000','00000000,10000',100,'1','1',2,'首级节点,系统管理','机构管理','1','/system/officeMgn','','fa fa-list-ul','#409EFF','机构管理','sys:office:page',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('11001','11000','00000000,10000,11000',100,'1','1',3,'首级节点,系统管理,机构管理','新增机构','2','','','fa fa-list-ul','#409EFF','新增机构','sys:office:add',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('11002','11000','00000000,10000,11000',110,'1','1',3,'首级节点,系统管理,机构管理','删除机构','2','','','fa fa-list-ul','#409EFF','删除机构','sys:office:delete',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('11003','11000','00000000,10000,11000',120,'1','1',3,'首级节点,系统管理,机构管理','更新机构','2','','','fa fa-list-ul','#409EFF','更新机构','sys:office:update',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('11004','11000','00000000,10000,11000',130,'1','1',3,'首级节点,系统管理,机构管理','变更机构状态','2','','','fa fa-list-ul','#409EFF','变更机构状态','sys:office:updateBystatus',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('12000','10000','00000000,10000',100,'1','2',2,'首级节点,系统管理','用户管理','1','/system/usersMgn','','fa fa-list-ul','#409EFF','用户管理','sys:user:page',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('12001','12000','00000000,10000,12000',100,'1','1',3,'首级节点,系统管理,用户管理','新增用户','2','','','fa fa-list-ul','#409EFF','新增用户','sys:user:add',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('12002','12000','00000000,10000,12000',110,'1','1',3,'首级节点,系统管理,用户管理','删除用户','2','','','fa fa-list-ul','#409EFF','删除用户','sys:user:delete',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('12003','12000','00000000,10000,12000',120,'1','1',3,'首级节点,系统管理,用户管理','更新用户','2','','','fa fa-list-ul','#409EFF','更新用户','sys:user:update',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('12004','12000','00000000,10000,12000',130,'1','1',3,'首级节点,系统管理,用户管理','获取用户信息','2','','','fa fa-list-ul','#409EFF','获取用户信息','sys:user:info',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('12005','12000','00000000,10000,12000',140,'1','1',3,'首级节点,系统管理,用户管理','修改密码','2','','','fa fa-list-ul','#409EFF','修改密码','sys:user:updatePassword',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('12006','12000','00000000,10000,12000',150,'1','1',3,'首级节点,系统管理,用户管理','修改头像','2','','','fa fa-list-ul','#409EFF','修改头像','sys:user:uploadHead',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-16 12:04:34',''),
('12007','12000','00000000,10000,12000',160,'1','1',3,'首级节点,系统管理,用户管理','批量更新用户状态','2','','','fa fa-list-ul','#409EFF','更新用户状态','sys:user:updateByStatus',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-14 10:08:29',''),
('12008','12000','00000000,10000,12000',170,'1,100','1',4,'首级节点,系统管理,用户管理','修改用户基本信息','2','','','fa fa-list-ul','#409EFF','','sys:user:updateUserInformation',NULL,'1','1','1',0,0,'1','1','2019-12-13 17:58:09','1','2019-12-14 12:13:43',''),
('12009','12000','00000000,10000,12000',190,'1,100','1',4,'首级节点,系统管理,用户管理','判断用户名是否存在','2','','','','#409EFF','','sys:user:checkUserName',NULL,'1','1','1',0,0,'1','1','2019-12-14 10:04:29','1','2019-12-14 10:04:29',''),
('12010','12000','00000000,10000,12000',200,'1,100','1',4,'首级节点,系统管理,用户管理','修改密码','2','','','','#409EFF','','sys:user:updatePassword',NULL,'1','1','1',0,0,'1','1','2019-12-14 10:06:49','1','2019-12-14 10:06:49',''),
('12011','12000','00000000,10000,12000',210,'1,100','1',4,'首级节点,系统管理,用户管理','重置密码','2','','','','#409EFF','','sys:user:uPassWord',NULL,'1','1','1',0,0,'1','1','2019-12-14 10:08:08','1','2019-12-14 10:08:08',''),
('12012','12000','00000000,10000,12000',220,'1,100','1',4,'首级节点,系统管理,用户管理','修改用户状态','2','','','','#409EFF','','sys:user:updateByState',NULL,'1','1','1',0,0,'1','1','2019-12-14 10:09:52','1','2019-12-14 10:09:52',''),
('13000','10000','00000000,13000',100,'1','1',2,'首级节点,系统管理','菜单管理','1','/system/sysMenu','','fa fa-list-ul','#409EFF','菜单管理','sys:menu:page',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('13001','13000','00000000,10000,13000',100,'1','1',3,'首级节点,系统管理,菜单管理','新增菜单','2','','','fa fa-list-ul','#409EFF','新增菜单','sys:menu:add',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('13002','13000','00000000,10000,13000',110,'1','1',3,'首级节点,系统管理,菜单管理','删除菜单','2','','','fa fa-list-ul','#409EFF','删除菜单','sys:menu:delete',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('13003','13000','00000000,10000,13000',120,'1','1',3,'首级节点,系统管理,菜单管理','更新菜单','2','','','fa fa-list-ul','#409EFF','更新菜单','sys:menu:update',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('13004','13000','00000000,10000,13000',130,'1,100','1',3,'首级节点,系统管理,角色管理','菜单角色列表','2','','','','#409EFF','菜单角色列表','sys:menu:role:list',NULL,'1','1','1',0,0,'1','1','2019-12-11 22:09:30','1','2019-12-11 22:09:30',''),
('14000','10000','00000000,14000',100,'1','1',2,'首级节点,系统管理','角色管理','1','/system/roleMgn','','fa fa-list-ul','#409EFF','角色管理','sys:role:page',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('14001','14000','00000000,10000,14000',100,'1','1',3,'首级节点,系统管理,角色管理','新增角色','2','','','fa fa-list-ul','#409EFF','新增角色','sys:menu:add',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('14002','14000','00000000,10000,14000',110,'1','1',3,'首级节点,系统管理,角色管理','删除角色','2','','','fa fa-list-ul','#409EFF','删除角色','sys:menu:delete',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('14003','14000','00000000,10000,14000',120,'1','1',3,'首级节点,系统管理,角色管理','更新角色','2','','','fa fa-list-ul','#409EFF','更新角色','sys:menu:update',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('14004','14000','00000000,10000,14000',130,'1','1',2,'首级节点,系统管理,角色管理','角色关联菜单','2','','','fa fa-list-ul','#409EFF','角色关联菜单','sys:role:menu:add',NULL,'1','1','1',0,0,'1','1','2019-12-11 21:10:59','1','2019-12-11 21:10:59',''),
('15000','10000','00000000,10000',100,'1,100','2',3,'首级节点,系统管理','行政区划','1','/system/area','','fa fa-sitemap','#409EFF','行政区划','sys:area:page',NULL,'1','1','1',0,0,'1','1','2019-12-12 11:33:48','1','2019-12-12 14:41:33',''),
('15001','15000','00000000,10000,15000',100,'1,100,100','1',4,'首级节点,系统管理,行政区划','行政区划新增','2','','','','#409EFF','行政区划新增','sys:area:add',NULL,'1','1','1',0,0,'1','1','2019-12-12 11:35:30','1','2019-12-12 11:35:30',''),
('15002','15000','00000000,10000,15000',110,'1,100,100','1',4,'首级节点,系统管理,行政区划','行政区划更新','2','','','','#409EFF','行政区划更新','sys:area:update',NULL,'1','1','1',0,0,'1','1','2019-12-12 11:36:40','1','2019-12-12 11:36:40',''),
('15003','15000','00000000,10000,15000',120,'1,100,100','1',4,'首级节点,系统管理,行政区划','行政区划删除','2','','','','#409EFF','行政区划删除','sys:area:delete',NULL,'1','1','1',0,0,'1','1','2019-12-12 11:37:34','1','2019-12-12 12:17:20',''),
('15004','15000','00000000,10000,15000',130,'1,100,100','1',4,'首级节点,系统管理,行政区划','区划状态更新','2','','','','#409EFF','区划状态更新','sys:area:status',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:17:34','1','2019-12-12 14:21:21',''),
('15005','15000','00000000,10000,15000',140,'1,100,100','1',4,'首级节点,系统管理,行政区划','行政区划导入','2','','','','#409EFF','行政区划导入','sys:area:import',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:19:29','1','2019-12-12 14:21:28',''),
('1506','15000','00000000,10000,15000',150,'1,100,100','1',4,'首级节点,系统管理,行政区划','行政区域树形结构','2','','','','#409EFF','','sys:area:simplify:list',NULL,'1','1','1',0,0,'1','1','2019-12-14 14:06:51','1','2019-12-14 14:08:00',''),
('16000','10000','00000000,10000',100,'1,100','2',3,'首级节点,系统管理','学校管理','1','/system/sysSchool','','','#409EFF','学校管理','sys:school:page',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:23:42','1','2019-12-12 14:32:45',''),
('16001','16000','00000000,10000,16000',100,'1,100,100','1',4,'首级节点,系统管理,学校管理','学校新增','2','','','','#409EFF','学校新增','sys:school:add',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:24:28','1','2019-12-12 14:29:50',''),
('16002','16000','00000000,10000,16000',110,'1,100,100','1',4,'首级节点,系统管理,学校管理','学校更新','2','','','','#409EFF','学校更新','sys:school:update',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:25:18','1','2019-12-12 14:29:45',''),
('16003','16000','00000000,10000,16000',120,'1,100,100','1',4,'首级节点,系统管理,学校管理','学校删除','2','','','','#409EFF','学校删除','sys:school:delete',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:26:26','1','2019-12-12 14:29:39',''),
('16004','16000','00000000,10000,16000',130,'1,100,100','1',4,'首级节点,系统管理,学校管理','学校导入','2','','','','#409EFF','学校导入','sys:school:import',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:29:10','1','2019-12-12 14:29:29',''),
('16005','16000','00000000,10000,16000',140,'1,100,100','1',4,'首级节点,系统管理,学校管理','查询所有学校集合','2','','','','#409EFF','','sys:school:getSchoolList',NULL,'1','1','1',0,0,'1','1','2019-12-16 11:31:07','1','2019-12-16 11:31:07',''),
('17000','10000','00000000,10000',100,'1,100','2',3,'首级节点,系统管理','专业管理','1','/system/sysSchoolCollege','','','#409EFF','专业管理','sys:school:college:page',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:31:38','1','2019-12-12 14:31:38',''),
('17001','17000','00000000,10000,17000',100,'1,100,100','1',4,'首级节点,系统管理,专业管理','专业新增','2','','','','#409EFF','专业新增','sys:school:college:add',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:33:46','1','2019-12-12 14:33:46',''),
('17002','17000','00000000,10000,17000',110,'1,100,100','1',4,'首级节点,系统管理,专业管理','专业更新','2','','','','#409EFF','专业更新','sys:school:college:update',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:34:57','1','2019-12-12 14:34:57',''),
('17003','17000','00000000,10000,17000',120,'1,100,100','1',4,'首级节点,系统管理,专业管理','专业删除','2','','','','#409EFF','专业更新','sys:school:college:delete',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:36:16','1','2019-12-12 14:36:35',''),
('17004','17000','00000000,10000,17000',130,'1,100,100','1',4,'首级节点,系统管理,专业管理','专业导入','2','','','','#409EFF','专业删除','sys:school:college:import',NULL,'1','1','1',0,0,'1','1','2019-12-12 14:37:37','1','2019-12-12 14:37:37',''),
('17005','17000','00000000,10000,17000',140,'1,100,100','1',4,'首级节点,系统管理,专业管理','根据学校查询专业集合','2','','','','#409EFF','','sys:school:college:getSchooCollegelList',NULL,'1','1','1',0,0,'1','1','2019-12-16 11:07:10','1','2019-12-16 11:07:10',''),
('18000','10000','00000000,10000',100,'1,100','1',3,'首级节点,系统管理','用户角色管理','1','/sysRole/getPageList','','','#409EFF','用户角色管理','sys:user:role:page',NULL,'1','1','1',0,0,'1','1','2019-12-12 15:50:51','1','2019-12-12 15:51:00',''),
('18001','18000','00000000,10000,18000',110,'1,100,100','1',4,'首级节点,系统管理,用户角色管理','添加用户角色','2','','','','#409EFF','添加用户角色','sys:user:role:add',NULL,'1','1','1',0,0,'1','1','2019-12-12 15:51:56','1','2019-12-12 15:52:12',''),
('18002','18000','00000000,10000,18000',120,'1,100,100','1',4,'首级节点,系统管理,用户角色管理','修改用户角色','2','','','','#409EFF','修改用户角色','sys:user:role:update',NULL,'1','1','1',0,0,'1','1','2019-12-12 15:52:52','1','2019-12-12 15:52:52',''),
('18003','18000','00000000,10000,18000',130,'1,100,100','1',4,'首级节点,系统管理,用户角色管理','删除用户角色','2','','','','#409EFF','删除用户角色','sys:user:role:delete',NULL,'1','1','1',0,0,'1','1','2019-12-12 15:53:50','1','2019-12-12 15:53:50',''),
('18004','18000','00000000,10000,18000',140,'1,100,100','1',4,'首级节点,系统管理,用户角色管理','用户Id查角色','2','','','','#409EFF','用户Id查角色','sys:user:role:findUserById',NULL,'1','1','1',0,0,'1','1','2019-12-12 16:00:03','1','2019-12-12 16:00:03',''),
('18005','18000','00000000,10000,18000',150,'1,100,100','1',4,'首级节点,系统管理,用户角色管理','批量加用户角色','1','','','','#409EFF','','sys:user:addRoles',NULL,'1','1','1',0,0,'1','1','2019-12-12 16:04:58','1','2019-12-12 16:04:58',''),
('18006','18000','00000000,10000,18000',160,'1,100,100','1',4,'首级节点,系统管理,用户角色管理','单个加用户角色','1','','','','#409EFF','','sys:user:addRole',NULL,'1','1','',0,0,'1','1','2019-12-12 16:06:05','1','2019-12-12 16:06:05',''),
('19000','10000','00000000,10000',100,'1,100','2',3,'首级节点,系统管理','数据字典','1','/system/sysDictType','','','#409EFF','','sys:dict:type:page',NULL,'1','1','1',0,0,'1','1','2019-12-12 16:21:02','1','2019-12-12 16:26:54',''),
('19001','19000','00000000,10000,19000',100,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典新增','2','','','','#409EFF','','sys:dict:type:add',NULL,'1','1','1',0,0,'1','1','2019-12-12 16:23:26','1','2019-12-12 16:23:26',''),
('19002','19000','00000000,10000,19000',110,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典修改','2','','','','#409EFF','','sys:dict:type:update',NULL,'1','1','1',0,0,'1','1','2019-12-12 16:24:08','1','2019-12-12 16:24:19',''),
('19003','19000','00000000,10000,19000',120,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典删除','2','','','','#409EFF','','sys:dict:type:delete',NULL,'1','1','1',0,0,'1','1','2019-12-12 16:24:52','1','2019-12-12 16:24:52',''),
('19004','19000','00000000,10000,19000',130,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典状态修改','2','','','','#409EFF','','sys:dict:type:status',NULL,'1','1','1',0,0,'1','1','2019-12-12 16:25:52','1','2019-12-12 17:24:39',''),
('19005','19000','00000000,10000,19000',140,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典数据分页','2','','','','#409EFF','','sys:dict:data:page',NULL,'1','1','1',0,0,'1','1','2019-12-12 17:11:52','1','2019-12-12 17:11:52',''),
('19006','19000','00000000,10000,19000',150,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典数据新增','2','','','','#409EFF','','sys:dict:data:add',NULL,'1','1','1',0,0,'1','1','2019-12-12 17:12:39','1','2019-12-12 17:12:39',''),
('19007','19000','00000000,10000,19000',160,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典数据更新','2','','','','#409EFF','','sys:dict:data:update',NULL,'1','1','1',0,0,'1','1','2019-12-12 17:13:31','1','2019-12-12 17:13:31',''),
('19008','19000','00000000,10000,19000',170,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典数据删除','2','','','','#409EFF','','sys:dict:data:delete',NULL,'1','1','1',0,0,'1','1','2019-12-12 17:14:30','1','2019-12-12 17:14:30',''),
('19009','19000','00000000,10000,19000',180,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典数据状态更新','2','','','','#409EFF','','sys:dict:data:status',NULL,'1','1','1',0,0,'1','1','2019-12-12 17:15:26','1','2019-12-12 17:24:26',''),
('19010','19000','00000000,10000,19000',190,'1,100,100','1',4,'首级节点,系统管理,数据字典','字典数据精简树结构','2','','','','#409EFF','','sys:dict:data:simplify',NULL,'1','1','1',0,0,'1','1','2019-12-12 17:28:40','1','2019-12-12 17:28:40',''),
('20000','00000000','00000000',100,'1','2',2,'首级节点','批改管理','2','','','','#409EFF','批改管理','sys:user:page',NULL,'1','2','2',0,0,'1','1','2019-12-12 11:32:06','1','2019-12-14 09:42:10',''),
('21000','20000','00000000,20000',100,'1,100','2',3,'首级节点,批改管理','学员管理','1','/homework/studentMgn','','fa fa-list-ul','#409EFF','学生管理','hw:student:page',NULL,'1','2','2',0,0,'1','1','2019-12-12 11:36:15','1','2019-12-14 09:42:38',''),
('21001','21000','00000000,20000,21000',110,'1,100,100','1',4,'首级节点,批改管理,学员管理','新增学员','2','','','fa fa-list-ul','#409EFF','新增学员','hw:student:add',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:11:38','1','2019-12-14 09:43:09',''),
('21002','21000','00000000,20000,21000',120,'1,100,100','1',4,'首级节点,批改管理,学员管理','修改学员','2','','','fa fa-list-ul','#409EFF','修改学员','hw:student:update',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:12:24','1','2019-12-14 09:43:16',''),
('21003','21000','00000000,20000,21000',130,'1,100,100','1',4,'首级节点,批改管理,学员管理','删除学员','2','','','fa fa-list-ul','#409EFF','删除学员','hw:student:delete',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:12:58','1','2019-12-14 09:43:23',''),
('21004','21000','00000000,20000,21000',140,'1,100,100','1',4,'首级节点,批改管理,学员管理','修改学员状态','2','','','','#409EFF','','hw:student:updateByState',NULL,'1','2','2',0,0,'1','1','2019-12-14 09:46:23','1','2019-12-14 09:47:38',''),
('21005','21000','00000000,20000,21000',150,'1,100,100','1',4,'首级节点,批改管理,学员管理','批量修改学生状态','2','','','','#409EFF','','hw:student:updateByStatus',NULL,'1','2','2',0,0,'1','1','2019-12-14 09:49:05','1','2019-12-14 09:49:05',''),
('21006','21000','00000000,20000,21000',160,'1,100,100','1',4,'首级节点,批改管理,学员管理','学员上传','2','','','','#409EFF','','hw:student:uploadExcel',NULL,'1','2','2',0,0,'1','1','2019-12-14 09:50:16','1','2019-12-14 09:50:16',''),
('21007','21000','00000000,20000,21000',170,'1,100,100','1',4,'首级节点,批改管理,学员管理','前台学员注册','2','','','','#409EFF','','hw:student:registerHwStudent',NULL,'1','2','2',0,0,'1','1','2019-12-16 15:37:45','1','2019-12-16 15:37:45',''),
('22000','20000','00000000,10000',100,'1,100','2',3,'首级节点,系统管理','老师管理','1','/homework/teacherMgn','','fa fa-list-ul','#409EFF','老师管理','hw:teacher:page',NULL,'1','1','2',0,0,'1','1','2019-12-12 11:43:18','1','2019-12-14 09:39:01',''),
('22001','22000','00000000,10000,22000',110,'1,100,100','1',4,'首级节点,系统管理,老师管理','新增老师','2','','','fa fa-list-ul','#409EFF','新增老师','hw:teacher:add',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:04:15','1','2019-12-14 09:43:30',''),
('22002','22000','00000000,10000,22000',120,'1,100,100','1',4,'首级节点,系统管理,老师管理','修改老师','2','','','fa fa-list-ul','#409EFF','修改老师','hw:teacher:update',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:04:58','1','2019-12-14 09:43:35',''),
('22003','22000','00000000,10000,22000',130,'1,100,100','1',4,'首级节点,系统管理,老师管理','删除老师','2','','','fa fa-list-ul','#409EFF','删除老师','hw:teacher:delete',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:05:43','1','2019-12-14 09:43:41',''),
('22004','22000','00000000,10000,22000',140,'1,100,100','1',4,'首级节点,系统管理,老师管理','修改老师状态','2','','','','#409EFF','','hw:teacher:updateByState',NULL,'1','2','2',0,0,'1','1','2019-12-14 09:53:54','1','2019-12-14 09:53:54',''),
('22005','22000','00000000,10000,22000',150,'1,100,100','1',4,'首级节点,系统管理,老师管理','批量修改老师状态','2','','','','#409EFF','','hw:teacher:updateByStatus',NULL,'1','2','2',0,0,'1','1','2019-12-14 09:54:51','1','2019-12-14 09:54:51',''),
('22006','22000','00000000,10000,22000',160,'1,100,100','1',4,'首级节点,系统管理,老师管理','老师上传','2','','','','#409EFF','','hw:teacher:uploadExcel',NULL,'1','2','2',0,0,'1','1','2019-12-14 09:56:13','1','2019-12-14 09:56:13',''),
('23000','20000','00000000,20000',100,'1,100','2',3,'首级节点,批改管理','课程分类','1','/homework/courseCategory','','fa fa-list-ul','#409EFF','课程分类','hw:course:category:page',NULL,'1','1','2',0,0,'1','1','2019-12-12 11:46:54','1','2019-12-14 09:39:09',''),
('23001','23000','00000000,20000,23000',110,'1,100,100','1',4,'首级节点,批改管理,课程分类','新增课程','2','','','fa fa-list-ul','#409EFF','新增课程','hw:course:category:add',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:14:34','1','2019-12-14 09:43:50',''),
('23002','23000','00000000,20000,23000',120,'1,100,100','1',4,'首级节点,批改管理,课程分类','修改课程','2','','','fa fa-list-ul','#409EFF','修改课程','hw:course:category:update',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:15:24','1','2019-12-14 09:43:55',''),
('23003','23000','00000000,20000,23000',130,'1,100,100','1',4,'首级节点,批改管理,课程分类','删除课程','2','','','fa fa-list-ul','#409EFF','删除课程','hw:course:category:delete',NULL,'1','2','2',0,0,'1','1','2019-12-12 12:16:06','1','2019-12-14 09:44:00',''),
('23004','23000','00000000,20000,23000',140,'1,100,100','1',4,'首级节点,批改管理,课程分类','修改课程状态','2','','','','#409EFF','','hw:course:category:updateByState',NULL,'1','1','2',0,0,'1','1','2019-12-14 09:59:19','1','2019-12-14 09:59:19',''),
('24000','20000','00000000,20000',100,'1,100','2',3,'首级节点,批改管理','题型管理','1','/homework/subjectType','','fa fa-list-ul','#409EFF','题型管理','hw:subject:type:page',NULL,'1','1','2',0,0,'1','1','2019-12-12 11:49:53','1','2019-12-14 09:39:19',''),
('24001','24000','00000000,20000,24000',110,'1,100,100','1',4,'首级节点,批改管理,题型管理','添加题型','2','','','fa fa-list-ul','#409EFF','添加题型','hw:subject:type:add',NULL,'1','2','2',0,0,'1','1','2019-12-12 11:55:37','1','2019-12-14 09:44:08',''),
('24002','24000','00000000,20000,24000',120,'1,100,100','1',4,'首级节点,批改管理,题型管理','修改题型','2','','','fa fa-list-ul','#409EFF','修改题型','hw:subject:type:update',NULL,'1','2','2',0,0,'1','1','2019-12-12 11:58:03','1','2019-12-14 09:44:13',''),
('24003','24000','00000000,20000,24000',130,'1,100,100','1',4,'首级节点,批改管理,题型管理','删除题型','2','','','fa fa-list-ul','#409EFF','删除题型','hw:subject:type:delete',NULL,'1','2','2',0,0,'1','1','2019-12-12 11:59:24','1','2019-12-14 09:44:18',''),
('25000','20000','00000000,20000',100,'1,100','2',2,'首级节点,标签管理','标签管理','1','/homework/hwClassType','','fa fa-list-ul','#409EFF','标签管理','hw:class:type:page',NULL,'1','2','2',0,0,'1','1','2019-12-12 15:23:23','1','2019-12-13 16:02:08',''),
('25001','25000','00000000,20000,25000',100,'1,100,100','1',4,'首级节点,标签管理,标签管理','标签新增','2','','','','#409EFF','','hw:class:type:add',NULL,'1','2','2',0,0,'1','1','2019-12-12 15:30:28','1','2019-12-12 15:30:28',''),
('25002','25000','00000000,20000,25000',110,'1,100,100','1',4,'首级节点,标签管理,标签管理','标签更新','2','','','','#409EFF','','hw:class:type:update',NULL,'1','2','2',0,0,'1','1','2019-12-12 15:31:33','1','2019-12-12 15:31:33',''),
('25003','25000','00000000,20000,25000',120,'1,100,100','1',4,'首级节点,标签管理,标签管理','标签删除','2','','','','#409EFF','','hw:class:type:delete',NULL,'1','2','2',0,0,'1','1','2019-12-12 15:32:24','1','2019-12-12 15:38:14',''),
('25004','25000','00000000,20000,25000',130,'1,100,100','1',4,'首级节点,标签管理,标签管理','标签状态更新','2','','','','#409EFF','','hw:class:type:status',NULL,'1','2','2',0,0,'1','1','2019-12-12 15:37:47','1','2019-12-12 15:37:47',''),
('25005','25000','00000000,20000,25000',140,'1,100,100','1',4,'首级节点,标签管理,标签管理','获取所有标签','2','','','','#409EFF','','hw:class:type:all',NULL,'1','2','2',0,0,'1','1','2019-12-13 12:06:11','1','2019-12-13 12:06:11',''),
('26000','20000','00000000,20000',100,'1,100','2',3,'首级节点,批改管理','班级管理','1','/homework/hwClass','','','#409EFF','班级管理','hw:class:page',NULL,'1','2','2',0,0,'1','1','2019-12-13 11:20:51','1','2019-12-13 16:02:15',''),
('26001','26000','00000000,20000,26000',100,'1,100,100','1',4,'首级节点,批改管理,班级管理','班级新增','2','','','','#409EFF','','hw:class:add',NULL,'1','2','2',0,0,'1','1','2019-12-13 11:23:16','1','2019-12-13 11:23:16',''),
('26002','26000','00000000,20000,26000',110,'1,100,100','1',4,'首级节点,批改管理,班级管理','班级更新','2','','','','#409EFF','','hw:class:update',NULL,'1','2','2',0,0,'1','1','2019-12-13 11:23:49','1','2019-12-13 11:23:49',''),
('26003','26000','00000000,20000,26000',120,'1,100,100','2',4,'首级节点,批改管理,班级管理','班级删除','2','','','','#409EFF','','hw:class:delete',NULL,'1','2','2',0,0,'1','1','2019-12-13 11:24:27','1','2019-12-13 11:24:27',''),
('26004','26000','00000000,20000,26000',130,'1,100,100','1',4,'首级节点,批改管理,班级管理','班级状态更新','2','','','','#409EFF','','hw:class:status',NULL,'1','2','2',0,0,'1','1','2019-12-13 11:31:55','1','2019-12-13 11:31:55','');

/*Table structure for table `sys_msg` */

DROP TABLE IF EXISTS `sys_msg`;

CREATE TABLE `sys_msg` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `msg_title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `msg_content` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息内容',
  `send_user_id` bigint(20) NOT NULL COMMENT '发布人id',
  `send_user_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '发布人用户名',
  `msg_level_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '优先级',
  `msg_type_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型',
  `msg_notify_type_code` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知方式，多个通知方式以逗号隔开',
  `msg_notify_obj_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知对象类型',
  `user_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '指定用户',
  `role_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '指定角色',
  `class_type_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '指定标签',
  `class_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '指定班级',
  `class_subject_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '指定班级题型',
  `class_role_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci COMMENT '指定班级角色',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '发布状态',
  `publish_time` datetime NOT NULL COMMENT '发布时间',
  `cancel_time` datetime NOT NULL COMMENT '撤销时间',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统消息表';

/*Data for the table `sys_msg` */

/*Table structure for table `sys_msg_record` */

DROP TABLE IF EXISTS `sys_msg_record`;

CREATE TABLE `sys_msg_record` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `msg_id` bigint(20) NOT NULL COMMENT '系统消息id',
  `msg_notify_type_code` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '通知方式',
  `receive_user_id` bigint(20) NOT NULL COMMENT '接受者用户id',
  `receive_user_name` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '接受者用户用户名',
  `msg_level_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '优先级',
  `msg_type_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '消息类型',
  `status` char(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '读取状态',
  `read_time` datetime NOT NULL COMMENT '阅读时间',
  `is_star` char(1) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否标星',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='系统消息记录表';

/*Data for the table `sys_msg_record` */

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
('10000','00000000','00000000',100,'1','2',2,'首级节点','10000','作业批改组织','作业批改组织','1','','','','','',0,0,'1','1','2019-11-29 11:58:35','1','2019-12-17 23:44:20','','',''),
('100000','0000000','1',100,'1','1',1,'1','100000','test1','test1','2','','','','','',0,0,'0','1','2019-11-28 12:00:26','1','2019-11-28 12:00:26','','',''),
('110000','00000000','00000000',110,'1','1',2,'首级节点','100000','test1','test1','1','','','','','',0,0,'1','1','2019-12-05 00:35:22','1','2019-12-17 23:41:18','','',''),
('123','123','1',123,'1','1',1,'1','123','123','123','1','','','','','',0,0,'0','1','2019-11-26 12:26:20','1','2019-11-26 12:26:20','','',''),
('20191206-001','110000','00000000',120,'1','1',2,'首级节点','20191206-001','20191206-001','20191206-001','1','','','','','',0,0,'1','1','2019-12-06 16:45:38','1','2019-12-06 16:46:21','','',''),
('210','10000','00000000,10000',210,'1,100','1',3,'首级节点,test1','210','test1-1','test1-1','1','','','','','',0,0,'0','1','2019-12-05 00:37:18','1','2019-12-05 00:37:18','','','');

/*Table structure for table `sys_role` */

DROP TABLE IF EXISTS `sys_role`;

CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色唯一编码',
  `type` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '角色类型',
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
(2,'test','test',NULL,1,NULL,0,NULL,'2019-10-25 09:48:02',NULL,NULL),
(1206962213816942593,'学员','10000','student',1,NULL,0,NULL,'2019-12-17 23:39:50',NULL,NULL);

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

insert  into `sys_role_menu`(`id`,`role_id`,`menu_code`,`deleted`,`version`,`create_by`,`create_time`,`update_by`,`update_time`,`corp_code`,`corp_name`) values 
(1206541587406958594,'1','10000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587474067457,'1','11000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587507621890,'1','11001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587532787714,'1','11002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587566342145,'1','11003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587599896578,'1','11004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587633451010,'1','12000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587667005441,'1','12001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587692171265,'1','12002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587725725698,'1','12003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587759280129,'1','12004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587792834561,'1','12005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587813806082,'1','12006',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587847360513,'1','12007',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587910275073,'1','12008',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541587952218114,'1','12009',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588040298497,'1','12010',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588073852930,'1','12011',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588099018754,'1','12012',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588124184577,'1','13000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588149350401,'1','13001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588191293442,'1','13002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588216459266,'1','13003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588241625090,'1','13004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588300345346,'1','14000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588333899778,'1','14001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588359065602,'1','14002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588384231426,'1','14003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588409397250,'1','14004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588434563074,'1','15000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588459728897,'1','15001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588522643458,'1','15002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588564586498,'1','15003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588589752322,'1','15004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588614918146,'1','15005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588652666881,'1','1506',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588744941569,'1','16000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588770107393,'1','16001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588795273217,'1','16002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588820439041,'1','16003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588841410562,'1','16004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588866576385,'1','16005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588883353602,'1','17000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588946268161,'1','17001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541588992405506,'1','17002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589017571329,'1','17003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589042737154,'1','17004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589067902977,'1','17005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589126623234,'1','18000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589168566273,'1','18001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589193732097,'1','18002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589218897921,'1','18003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589260840961,'1','18004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589319561217,'1','18005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589353115650,'1','18006',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589386670081,'1','19000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589411835906,'1','19001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589445390337,'1','19002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589470556161,'1','19003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589487333378,'1','19004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589558636545,'1','19005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589604773889,'1','19006',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589629939714,'1','19007',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589655105537,'1','19008',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589680271362,'1','19009',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589705437186,'1','19010',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589722214402,'1','20000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589747380225,'1','21000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589785128962,'1','21001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589856432129,'1','21002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589881597953,'1','21003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589906763777,'1','22000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589931929602,'1','22001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589957095425,'1','22002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541589973872642,'1','22003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590003232770,'1','22004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590057758722,'1','22005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590108090369,'1','22006',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590133256193,'1','23000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590158422017,'1','23001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590183587842,'1','23002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590225530882,'1','23003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590284251138,'1','23004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590301028354,'1','24000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590326194178,'1','24001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590351360001,'1','24002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590393303042,'1','24003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590452023298,'1','25000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590477189121,'1','25001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590502354945,'1','25002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590527520769,'1','25003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590569463810,'1','25004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590804344833,'1','25005',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590833704962,'1','26000',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590858870785,'1','26001',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590884036610,'1','26002',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590900813826,'1','26003',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206541590925979649,'1','26004',0,1,'1','2019-12-16 19:48:25','1','2019-12-16 19:48:25','',''),
(1206962341424447490,'1206962213816942593','10000',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962341474779137,'1206962213816942593','11000',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962341923569666,'1206962213816942593','11001',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962341961318402,'1206962213816942593','11002',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342003261441,'1206962213816942593','11003',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342061981698,'1206962213816942593','11004',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342103924738,'1206962213816942593','12000',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342154256385,'1206962213816942593','12001',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342456246273,'1206962213816942593','12002',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342498189313,'1206962213816942593','12003',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342577881090,'1206962213816942593','12004',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342691127298,'1206962213816942593','12005',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342741458946,'1206962213816942593','12006',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342783401985,'1206962213816942593','12007',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342825345025,'1206962213816942593','12008',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342867288065,'1206962213816942593','12009',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342909231105,'1206962213816942593','12010',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962342984728577,'1206962213816942593','12011',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343026671618,'1206962213816942593','12012',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343068614657,'1206962213816942593','14000',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343110557697,'1206962213816942593','14001',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343160889345,'1206962213816942593','14002',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343202832386,'1206962213816942593','14003',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343244775425,'1206962213816942593','14004',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343282524161,'1206962213816942593','21000',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343324467202,'1206962213816942593','21001',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343366410241,'1206962213816942593','21002',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343408353282,'1206962213816942593','21003',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343446102018,'1206962213816942593','21004',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343488045057,'1206962213816942593','21005',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343525793794,'1206962213816942593','21006',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343567736833,'1206962213816942593','21007',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343609679873,'1206962213816942593','22000',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343651622914,'1206962213816942593','22001',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343685177345,'1206962213816942593','22002',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343727120385,'1206962213816942593','22003',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343769063426,'1206962213816942593','22004',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343802617858,'1206962213816942593','22005',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','',''),
(1206962343844560898,'1206962213816942593','22006',0,1,'1','2019-12-17 23:40:21','1','2019-12-17 23:40:21','','');

/*Table structure for table `sys_schedule_job` */

DROP TABLE IF EXISTS `sys_schedule_job`;

CREATE TABLE `sys_schedule_job` (
  `id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务id',
  `job_name` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务分组',
  `job_type` varchar(20) NOT NULL COMMENT 'simple,cron',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) DEFAULT NULL COMMENT 'cron表达式',
  `simple_repeat_count` int(11) DEFAULT NULL COMMENT '重复次数',
  `simple_unit` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '周期参数 - 间隔单位',
  `simple_time` bigint(20) DEFAULT NULL COMMENT '周期参数 - 间隔时间',
  `start_time` datetime DEFAULT NULL COMMENT 'job开始时间',
  `end_time` datetime DEFAULT NULL COMMENT 'job结束时间',
  `status` tinyint(4) NOT NULL COMMENT '任务状态  0：正常  1：暂停',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='定时任务';

/*Data for the table `sys_schedule_job` */

/*Table structure for table `sys_schedule_joblog` */

DROP TABLE IF EXISTS `sys_schedule_joblog`;

CREATE TABLE `sys_schedule_joblog` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '任务日志id',
  `job_id` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '任务id',
  `bean_name` varchar(200) DEFAULT NULL COMMENT 'spring bean名称',
  `params` varchar(2000) DEFAULT NULL COMMENT '参数',
  `status` tinyint(4) NOT NULL COMMENT '任务状态    0：成功    1：失败',
  `error` varchar(2000) DEFAULT NULL COMMENT '失败信息',
  `times` int(11) NOT NULL COMMENT '耗时(单位：毫秒)',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建者',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新者',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',
  `remarks` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`),
  KEY `job_id` (`job_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='定时任务日志';

/*Data for the table `sys_schedule_joblog` */

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

insert  into `sys_user`(`id`,`username`,`nickname`,`password`,`salt`,`email`,`mobile`,`phone`,`gender`,`avatar`,`signtext`,`wx_openid`,`mobile_imei`,`user_type`,`ref_code`,`ref_name`,`mgr_type`,`reg_type`,`pwd_security_level`,`pwd_update_date`,`pwd_update_record`,`pwd_question`,`pwd_question_answer`,`pwd_question2`,`pwd_question_answer2`,`pwd_question3`,`pwd_question_answer3`,`pwd_quest_update_date`,`last_login_ip`,`last_login_date`,`freeze_date`,`freeze_cause`,`user_weight`,`remarks`,`state`,`deleted`,`version`,`create_by`,`create_time`,`update_by`,`update_time`,`corp_code`,`corp_name`) values 
(1,'admin','管理员','11a254dab80d52bc4a347e030e54d861a9d2cdb2af2185a9ca4a7318e830d04d','666',NULL,NULL,'13950019129',1,'http://localhost:8888//resource/201911102329033.png',NULL,NULL,NULL,'1',NULL,NULL,'1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,0,0,'1','2019-11-23 10:42:01','1','2019-12-06 16:53:27','0','whyy'),
(1202874381137620993,'123456','123455','4fc8c424063b79ac1ab844642b9e8e9561968097d16cc6433e78de0e624d43e5','7238ea97fb5b1459f417b642f2b74d0d',NULL,NULL,'',1,NULL,NULL,NULL,NULL,'1',NULL,NULL,'0','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,0,0,'1','2019-12-06 16:56:15','1','2019-12-06 16:56:15','0','whyy'),
(1206963191614062594,'xueyuan001','xueyuan001','d5ef342317a14a9f64c82db0f3e75d4a6c4c4e52d143292aa34edc73107529ba','5630c47d5017f3b4fe709b6410780e5a',NULL,NULL,'',1,NULL,NULL,NULL,NULL,'2',NULL,NULL,'0','1',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0,NULL,1,0,0,'1','2019-12-17 23:43:44','1','2019-12-17 23:43:44','0','whyy');

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

insert  into `sys_user_office`(`id`,`user_id`,`office_code`,`deleted`,`version`,`create_by`,`create_time`,`update_by`,`update_time`,`corp_code`,`corp_name`) values 
(1,1,'110000',0,0,NULL,'2019-12-10 15:43:09',NULL,NULL,'1','whyy'),
(1202874381632548866,1202874381137620993,'110000',0,0,NULL,'2019-12-06 16:56:14',NULL,NULL,'1','whyy'),
(1206963191756668930,1206963191614062594,'10000',0,0,NULL,'2019-12-17 23:43:44',NULL,NULL,'0','whyy');

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

insert  into `sys_user_role`(`id`,`user_id`,`role_id`,`deleted`,`version`,`create_by`,`create_time`,`update_by`,`update_time`,`corp_code`,`corp_name`) values 
(1203702080529805313,1,1,0,1,'1','2019-12-08 23:45:14','1','2019-12-08 23:45:14','',''),
(1203702080546582530,1202874381137620993,1,0,1,'1','2019-12-08 23:45:14','1','2019-12-08 23:45:14','',''),
(1206963192381620226,1206963191614062594,1206962213816942593,0,0,NULL,'2019-12-17 23:43:44',NULL,NULL,'1','1');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
