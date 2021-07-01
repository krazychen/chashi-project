/*
Navicat MySQL Data Transfer

Source Server         : 192.168.0.103
Source Server Version : 50715
Source Host           : 192.168.0.103:3306
Source Database       : whyy-system

Target Server Type    : MYSQL
Target Server Version : 50715
File Encoding         : 65001

Date: 2019-12-10 10:34:20
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `foo_bar`
-- ----------------------------
DROP TABLE IF EXISTS `foo_bar`;
CREATE TABLE `foo_bar` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(20) NOT NULL COMMENT '名称',
  `foo` varchar(20) DEFAULT NULL COMMENT 'Foo',
  `bar` varchar(20) NOT NULL COMMENT 'Bar',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='FooBar';

-- ----------------------------
-- Records of foo_bar
-- ----------------------------
INSERT INTO `foo_bar` VALUES ('1', 'FooBar', 'foo', 'bar', 'remark...', '1', '0', '2019-11-01 14:05:14', null);
INSERT INTO `foo_bar` VALUES ('2', 'HelloWorld', 'hello', 'world', null, '1', '0', '2019-11-01 14:05:14', null);

-- ----------------------------
-- Table structure for `hw_class`
-- ----------------------------
DROP TABLE IF EXISTS `hw_class`;
CREATE TABLE `hw_class` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `class_name` varchar(100) NOT NULL COMMENT '班级名称',
  `type_id` bigint(20) NOT NULL COMMENT '标签id',
  `type_name` varchar(100) NOT NULL COMMENT '标签名称',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：未发布，1：发布，2：冻结',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='班级表';

-- ----------------------------
-- Records of hw_class
-- ----------------------------

-- ----------------------------
-- Table structure for `hw_class_type`
-- ----------------------------
DROP TABLE IF EXISTS `hw_class_type`;
CREATE TABLE `hw_class_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type_name` varchar(100) NOT NULL COMMENT '标签名称',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：未发布，1：发布，2：冻结',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='标签表';

-- ----------------------------
-- Records of hw_class_type
-- ----------------------------

-- ----------------------------
-- Table structure for `hw_course_category`
-- ----------------------------
DROP TABLE IF EXISTS `hw_course_category`;
CREATE TABLE `hw_course_category` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `name` varchar(100) NOT NULL COMMENT '分类名称',
  `type` varchar(100) NOT NULL COMMENT '分类类型',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='课程分类表';

-- ----------------------------
-- Records of hw_course_category
-- ----------------------------

-- ----------------------------
-- Table structure for `hw_student`
-- ----------------------------
DROP TABLE IF EXISTS `hw_student`;
CREATE TABLE `hw_student` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `province_code` varchar(100) DEFAULT NULL COMMENT '省份代码',
  `province_name` varchar(100) DEFAULT NULL COMMENT '省份名称',
  `city_code` varchar(100) DEFAULT NULL COMMENT '城市代码',
  `city_name` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `district_code` varchar(100) DEFAULT NULL COMMENT '区县代码',
  `district_name` varchar(100) DEFAULT NULL COMMENT '区县名称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `school_id` bigint(20) DEFAULT NULL COMMENT '学校id',
  `school_code` varchar(100) DEFAULT NULL COMMENT '学校代码',
  `school_name` varchar(100) DEFAULT NULL COMMENT '学校名称',
  `college_id` bigint(20) DEFAULT NULL COMMENT '专业id',
  `college_code` varchar(100) DEFAULT NULL COMMENT '专业代码',
  `college_name` varchar(100) DEFAULT NULL COMMENT '专业名称',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学员表';

-- ----------------------------
-- Records of hw_student
-- ----------------------------
INSERT INTO `hw_student` VALUES ('1202167314691682306', null, '湖北省', null, '武汉市', null, '江夏区', '大花岭', null, null, '武汉学院', null, null, '播音主持', null, '0', '0', '0', '1', '2019-12-04 18:06:38', '1', '2019-12-05 19:34:02', '', '');
INSERT INTO `hw_student` VALUES ('1202561131567644674', null, '湖北省', null, '武汉市', null, '洪山区', '街道口', null, null, '武汉大学', null, null, '计算机专业', null, '1', '0', '0', '1', '2019-12-05 20:11:31', '1', '2019-12-05 20:11:31', '', '');
INSERT INTO `hw_student` VALUES ('1202562202159669249', null, '湖北省', null, '武汉市', null, '洪山区', '马房山', null, null, '武汉理工大学', null, null, '新闻', null, '1', '0', '0', '1', '2019-12-05 20:15:46', '1', '2019-12-06 10:58:48', '', '');
INSERT INTO `hw_student` VALUES ('1203999992835575809', '0002', '武汉市', '0002-0001', '江夏区', '002-001-001', '大桥新区', '乡里乡村', null, null, '理工大学', null, null, '计算机', null, '1', '0', '0', '1', '2019-12-09 19:29:02', '1', '2019-12-09 19:32:12', '', '');
INSERT INTO `hw_student` VALUES ('1204003213826088961', '0001', '武汉市', '0001-0001', '江夏区', '001-001-001', '大桥新区', '大花岭', '1203961696399110145', '002', '理工大学', null, '002', '计算机', null, '1', '0', '0', '1', '2019-12-09 19:41:50', '1', '2019-12-09 19:41:50', '', '');
INSERT INTO `hw_student` VALUES ('1204012799299702786', null, '湖北省', null, '武汉市', null, '江夏区', '纸坊街道', null, null, '孝感大学', null, null, '数控', null, '1', '0', '0', '1', '2019-12-09 20:19:55', '1', '2019-12-09 20:19:55', '', '');
INSERT INTO `hw_student` VALUES ('1204012800406999042', null, '湖北省', null, '武汉市', null, '江夏区', '红旗小区', null, null, '恩施大学', null, null, '磨具', null, '1', '0', '0', '1', '2019-12-09 20:19:56', '1', '2019-12-09 20:19:56', '', '');
INSERT INTO `hw_student` VALUES ('1204012801082281985', null, '湖北省', null, '武汉市', null, '江夏区', '江南新天地', null, null, '咸宁大学', null, null, '机电一体化', null, '1', '0', '0', '1', '2019-12-09 20:19:56', '1', '2019-12-09 20:19:56', '', '');
INSERT INTO `hw_student` VALUES ('1204012801644318722', null, '湖北省', null, '武汉市', null, '江夏区', '菩提院', null, null, '襄阳大学', null, null, '传媒', null, '1', '0', '0', '1', '2019-12-09 20:19:56', '1', '2019-12-09 20:19:56', '', '');

-- ----------------------------
-- Table structure for `hw_student_class`
-- ----------------------------
DROP TABLE IF EXISTS `hw_student_class`;
CREATE TABLE `hw_student_class` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `class_id` bigint(20) NOT NULL COMMENT '班级id',
  `student_id` bigint(20) NOT NULL COMMENT '学员id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学员班级表';

-- ----------------------------
-- Records of hw_student_class
-- ----------------------------

-- ----------------------------
-- Table structure for `hw_subject_type`
-- ----------------------------
DROP TABLE IF EXISTS `hw_subject_type`;
CREATE TABLE `hw_subject_type` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `type_name` varchar(100) NOT NULL COMMENT '题型名称',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：停用',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='题型表';

-- ----------------------------
-- Records of hw_subject_type
-- ----------------------------

-- ----------------------------
-- Table structure for `hw_teacher`
-- ----------------------------
DROP TABLE IF EXISTS `hw_teacher`;
CREATE TABLE `hw_teacher` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `province_code` varchar(100) DEFAULT NULL COMMENT '省份代码',
  `province_name` varchar(100) DEFAULT NULL COMMENT '省份名称',
  `city_code` varchar(100) DEFAULT NULL COMMENT '城市代码',
  `city_name` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `district_code` varchar(100) DEFAULT NULL COMMENT '区县代码',
  `district_name` varchar(100) DEFAULT NULL COMMENT '区县名称',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师表';

-- ----------------------------
-- Records of hw_teacher
-- ----------------------------
INSERT INTO `hw_teacher` VALUES ('1202843140902887425', null, '湖南省', null, '常德市', null, '津市', null, '襄阳街', '1', '0', '0', '1', '2019-12-06 14:52:07', '1', '2019-12-06 16:05:05', '', '');
INSERT INTO `hw_teacher` VALUES ('1202868381809221633', null, '广东省', null, '深圳市', null, '龙华区', null, '大浪村委', '1', '0', '0', '1', '2019-12-06 16:32:25', '1', '2019-12-06 16:32:25', '', '');
INSERT INTO `hw_teacher` VALUES ('1203924643783131137', null, '湖北省', null, '武汉市', null, '江夏区', null, '纸坊街道', '1', '0', '0', '1', '2019-12-09 14:29:38', '1', '2019-12-09 14:29:38', '', '');
INSERT INTO `hw_teacher` VALUES ('1203924644550688769', null, '湖北省', null, '武汉市', null, '江夏区', null, '纸坊街道', '1', '0', '0', '1', '2019-12-09 14:29:38', '1', '2019-12-09 14:29:38', '', '');
INSERT INTO `hw_teacher` VALUES ('1203924645091753985', null, '湖北省', null, '武汉市', null, '江夏区', null, '纸坊街道', '1', '0', '0', '1', '2019-12-09 14:29:38', '1', '2019-12-09 14:29:38', '', '');
INSERT INTO `hw_teacher` VALUES ('1204008916515053569', '0001', '武汉市', '0001-0001', '江夏区', '001-001-001', '大桥新区', null, '红旗社区', '1', '0', '0', '1', '2019-12-09 20:04:30', '1', '2019-12-09 20:28:03', '', '');

-- ----------------------------
-- Table structure for `hw_teacher_class`
-- ----------------------------
DROP TABLE IF EXISTS `hw_teacher_class`;
CREATE TABLE `hw_teacher_class` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `class_id` bigint(20) NOT NULL COMMENT '班级id',
  `teacher_id` bigint(20) NOT NULL COMMENT '老师id',
  `teacher_role_id` bigint(20) NOT NULL COMMENT '老师角色id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师班级表';

-- ----------------------------
-- Records of hw_teacher_class
-- ----------------------------

-- ----------------------------
-- Table structure for `hw_teacher_subtype`
-- ----------------------------
DROP TABLE IF EXISTS `hw_teacher_subtype`;
CREATE TABLE `hw_teacher_subtype` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `subject_type_id` bigint(20) NOT NULL COMMENT '题型id',
  `teacher_id` bigint(20) NOT NULL COMMENT '老师id',
  `teacher_role_id` bigint(20) NOT NULL COMMENT '老师角色id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='老师题型表';

-- ----------------------------
-- Records of hw_teacher_subtype
-- ----------------------------

-- ----------------------------
-- Table structure for `ip`
-- ----------------------------
DROP TABLE IF EXISTS `ip`;
CREATE TABLE `ip` (
  `ip_start` varchar(15) NOT NULL,
  `ip_end` varchar(15) NOT NULL,
  `area` varchar(100) NOT NULL,
  `operator` varchar(200) NOT NULL,
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip_start_num` bigint(20) NOT NULL,
  `ip_end_num` bigint(20) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=526718 DEFAULT CHARSET=utf8 COMMENT='IP地址';

-- ----------------------------
-- Records of ip
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_area`
-- ----------------------------
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
  `area_type` varchar(100) DEFAULT NULL COMMENT '区域类型',
  `deleted` int(11) NOT NULL COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL COMMENT '版本',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`area_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='行政区划';

-- ----------------------------
-- Records of sys_area
-- ----------------------------
INSERT INTO `sys_area` VALUES ('0001', '00000000', '00000000', '1', '1', '2', '2', '首级节点', '武汉市', 'province', '0', '0', '0', '1', '2019-12-06 11:53:09', '1', '2019-12-06 15:04:19', '我是大哥大');
INSERT INTO `sys_area` VALUES ('0001-0001', '0001', '00000000,0001', '1', '1,1', '2', '3', '首级节点,武汉市', '江夏区', 'region', '0', '0', '0', '1', '2019-12-06 15:36:42', '1', '2019-12-06 15:36:58', '真特么大');
INSERT INTO `sys_area` VALUES ('0001-0002', '0001', '00000000,0001', '2', '1,1', '1', '3', '首级节点,武汉市', '洪山区', 'region', '0', '0', '0', '1', '2019-12-06 15:38:21', '1', '2019-12-06 15:38:21', '这个区是最近变牛逼的');
INSERT INTO `sys_area` VALUES ('0001-0003', '0001', '00000000,0001', '3', '1,1', '1', '3', '首级节点,武汉市', '汉口区', 'city', '0', '0', '0', '1', '2019-12-06 15:39:33', '1', '2019-12-06 15:39:33', '三大镇之首');
INSERT INTO `sys_area` VALUES ('0001-0004', '0001', '00000000,0001', '5', '1,1', '1', '3', '首级节点,武汉市', '武昌区', 'region', '0', '0', '0', '1', '2019-12-06 15:40:22', '1', '2019-12-06 15:40:22', '三大镇第二');
INSERT INTO `sys_area` VALUES ('0001-0005', '0001', '00000000,0001', '6', '1,1', '1', '3', '首级节点,武汉市', '汉阳区', 'region', '0', '0', '0', '1', '2019-12-06 15:40:53', '1', '2019-12-06 15:40:53', '三大镇垫底的');
INSERT INTO `sys_area` VALUES ('0002', '00000000', '00000000', '2', '1', '2', '2', '首级节点', '孝感市', 'province', '0', '0', '0', '1', '2019-12-06 15:41:58', '1', '2019-12-06 15:41:58', '孝感有很多将军');
INSERT INTO `sys_area` VALUES ('0002-0001', '0002', '00000000,0002', '1', '1,2', '2', '3', '首级节点,孝感市', '大悟县', 'region', '0', '0', '0', '1', '2019-12-06 15:56:29', '1', '2019-12-06 15:56:29', '红色的土地，中原突围第一枪的打响地');
INSERT INTO `sys_area` VALUES ('001-001-001', '0001-0001', '00000000,0001,0001-0001', '1', '1,1,1', '1', '4', '首级节点,武汉市,江夏区', '大桥新区', 'region', '0', '0', '0', '1', '2019-12-08 17:53:39', '1', '2019-12-08 17:53:39', '新区');
INSERT INTO `sys_area` VALUES ('002-001-001', '0002-0001', '00000000,0002,0002-0001', '1', '1,2,1', '1', '4', '首级节点,孝感市,大悟县', '城关镇', 'region', '0', '0', '0', '1', '2019-12-08 17:53:00', '1', '2019-12-08 17:53:00', '县城');

-- ----------------------------
-- Table structure for `sys_department`
-- ----------------------------
DROP TABLE IF EXISTS `sys_department`;
CREATE TABLE `sys_department` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '部门名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `level` int(11) DEFAULT NULL COMMENT '部门层级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_department_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='部门';

-- ----------------------------
-- Records of sys_department
-- ----------------------------
INSERT INTO `sys_department` VALUES ('1', '管理部', null, '1', '1', '0', null, '0', '2019-10-25 09:46:49', null);
INSERT INTO `sys_department` VALUES ('2', '技术部', null, '1', '1', '0', null, '0', '2019-11-01 20:45:43', null);
INSERT INTO `sys_department` VALUES ('20', '前端开发部', '2', '2', '1', '0', null, '0', '2019-11-01 20:48:38', null);
INSERT INTO `sys_department` VALUES ('21', '后台开发部', '2', '2', '1', '0', null, '0', '2019-11-01 20:48:38', null);
INSERT INTO `sys_department` VALUES ('22', '测试部', '2', '2', '1', '0', null, '0', '2019-11-01 20:48:38', null);
INSERT INTO `sys_department` VALUES ('201', '前端一组', '20', '3', '1', '0', null, '0', '2019-11-01 20:48:38', null);
INSERT INTO `sys_department` VALUES ('202', '前端二组', '20', '3', '1', '0', null, '0', '2019-11-01 20:48:38', null);
INSERT INTO `sys_department` VALUES ('203', '后台一组', '21', '3', '1', '0', null, '0', '2019-11-01 20:48:38', null);
INSERT INTO `sys_department` VALUES ('204', '后台二组', '21', '3', '1', '0', null, '0', '2019-11-01 20:48:38', null);
INSERT INTO `sys_department` VALUES ('205', '测试一组', '22', '3', '1', '0', null, '0', '2019-11-01 20:48:38', null);

-- ----------------------------
-- Table structure for `sys_dict_data`
-- ----------------------------
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
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`dict_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典数据表';

-- ----------------------------
-- Records of sys_dict_data
-- ----------------------------
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0001-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '是', '1', 'sys_yes_no', '1', '是否', null, null, '0', '0', '0', '1', '2019-11-28 10:27:12', '1', '2019-11-28 10:27:17', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0001-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '否', '0', 'sys_yes_no', '1', '是否', null, null, '0', '0', '0', '1', '2019-11-28 10:29:00', '1', '2019-11-28 10:29:01', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0002-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '公司', '1', 'sys_office_type', '1', '机构类型', null, null, '0', '0', '0', '1', '2019-11-28 10:32:11', '1', '2019-11-28 10:32:16', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0002-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '部门', '2', 'sys_office_type', '1', '机构类型', null, null, '0', '0', '0', '1', '2019-11-28 10:33:14', '1', '2019-11-28 10:33:18', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0003-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '后台管理系统', '1', 'sys_system_type', '1', '归属系统', null, null, '0', '0', '0', '1', '2019-11-28 10:38:16', '1', '2019-11-28 10:38:20', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0003-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '作业批改系统', '2', 'sys_system_type', '1', '归属系统', null, null, '0', '0', '0', '1', '2019-11-28 10:45:16', '1', '2019-11-28 10:45:24', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0003-0003', '00000000', '00000000', '120', '100', '0', '1', '首级', '在线网课系统', '3', 'sys_system_type', '1', '归属系统', null, null, '0', '0', '0', '1', '2019-11-28 10:46:58', '1', '2019-11-28 10:47:02', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0004-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '系统管理', '1', 'sys_module_type', '1', '归属模块', null, null, '0', '0', '0', '1', '2019-11-28 10:49:26', '1', '2019-11-28 10:49:31', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0004-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '后台作业管理', '2', 'sys_module_type', '1', '归属模块', null, null, '0', '0', '0', '1', '2019-11-28 10:50:50', '1', '2019-11-28 10:50:56', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0004-0003', '00000000', '00000000', '120', '100', '0', '1', '首级', '学员作业管理', '3', 'sys_module_type', '1', '归属模块', null, null, '0', '0', '0', '1', '2019-11-28 10:52:12', '1', '2019-11-28 10:52:17', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0004-0004', '00000000', '00000000', '130', '100', '0', '1', '首级', '老师作业管理', '4', 'sys_module_type', '1', '归属模块', null, null, '0', '0', '0', '1', '2019-11-28 10:53:58', '1', '2019-11-28 10:54:02', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0005-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '菜单', '1', 'sys_menu_type', '1', '归属模块', null, null, '0', '0', '0', '1', '2019-11-28 10:56:03', '1', '2019-11-28 10:56:05', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0005-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '权限', '2', 'sys_menu_type', '1', '菜单类型', null, null, '0', '0', '0', '1', '2019-11-28 10:57:34', '1', '2019-11-28 10:57:38', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0006-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '显示', '1', 'sys_show_hide', '1', '可见', null, null, '0', '0', '0', '1', '2019-11-28 10:59:52', '1', '2019-11-28 10:59:53', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0006-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '隐藏', '0', 'sys_show_hide', '1', '可见', null, null, '0', '0', '0', '1', '2019-11-28 11:01:26', '1', '2019-11-28 11:01:29', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0007-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '系统用户', '1', 'sys_user_type', '1', '用户类型', null, null, '0', '0', '0', '1', '2019-11-28 11:02:51', '1', '2019-11-28 11:02:57', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0007-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '学生', '2', 'sys_user_type', '1', '用户类型', null, null, '0', '0', '0', '1', '2019-11-28 11:04:13', '1', '2019-11-28 11:04:17', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0007-0003', '00000000', '00000000', '120', '100', '0', '1', '首级', '老师', '3', 'sys_user_type', '1', '用户类型', null, null, '0', '0', '0', '1', '2019-11-28 11:05:25', '1', '2019-11-28 11:05:27', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0008-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '系统管理员', 'sysadmin', 'sys_role_type', '1', '角色类型', null, null, '0', '0', '0', '1', '2019-11-28 11:07:23', '1', '2019-11-28 11:07:28', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0008-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '学员', 'student', 'sys_role_type', '1', '角色类型', null, null, '0', '0', '0', '1', '2019-11-28 11:09:19', '1', '2019-11-28 11:09:28', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0008-0003', '00000000', '00000000', '120', '100', '0', '1', '首级', '一级老师', 'leve1teacher', 'sys_role_type', '1', '角色类型', null, null, '0', '0', '0', '1', '2019-11-28 11:10:48', '1', '2019-11-28 11:10:50', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0008-0004', '00000000', '00000000', '130', '100', '0', '1', '首级', '二级老师', 'leve2teacher', 'sys_role_type', '1', '角色类型', null, null, '0', '0', '0', '1', '2019-11-28 11:12:10', '1', '2019-11-28 11:12:14', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0008-0005', '00000000', '00000000', '140', '100', '0', '1', '首级', '三级老师', 'leve2teacher', 'sys_role_type', '1', '角色类型', null, null, '0', '0', '0', '1', '2019-11-28 11:13:28', '1', '2019-11-28 11:13:33', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0009-0001', '00000000', '00000000', '100', '100', '0', '1', '首级', '省份直辖市', 'province', 'sys_area_type', '1', '区域类型', null, null, '0', '0', '0', '1', '2019-11-28 11:16:00', '1', '2019-11-28 11:16:02', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0009-0002', '00000000', '00000000', '110', '100', '0', '1', '首级', '地市', 'city', 'sys_area_type', '1', '区域类型', null, null, '0', '0', '0', '1', '2019-11-28 11:17:17', '1', '2019-11-28 11:17:20', null, '0', 'whyy');
INSERT INTO `sys_dict_data` VALUES ('0000-0001-0009-0003', '00000000', '00000000', '120', '100', '0', '1', '首级', '区县', 'region', 'sys_area_type', '1', '区域类型', null, null, '0', '0', '0', '1', '2019-11-28 11:19:12', '1', '2019-11-28 11:19:14', null, '0', 'whyy');

-- ----------------------------
-- Table structure for `sys_dict_type`
-- ----------------------------
DROP TABLE IF EXISTS `sys_dict_type`;
CREATE TABLE `sys_dict_type` (
  `id` varchar(64) NOT NULL COMMENT '编号',
  `dict_name` varchar(100) NOT NULL COMMENT '字典名称',
  `dict_type` varchar(100) NOT NULL COMMENT '字典类型',
  `is_sys` char(1) NOT NULL COMMENT '是否系统字典',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='字典类型表';

-- ----------------------------
-- Records of sys_dict_type
-- ----------------------------
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0001', '是否', 'sys_yes_no', '1', '0', '0', '0', '1', '2019-11-28 09:57:06', '1', '2019-11-28 09:57:21', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0002', '机构类型', 'sys_office_type', '1', '0', '0', '0', '1', '2019-11-28 10:00:49', '1', '2019-11-28 10:00:55', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0003', '归属系统', 'sys_system_type', '1', '0', '0', '0', '1', '2019-11-28 10:01:46', '1', '2019-11-28 10:01:57', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0004', '归属模块', 'sys_module_type', '1', '0', '0', '0', '1', '2019-11-28 10:09:44', '1', '2019-11-28 10:09:50', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0005', '菜单类型', 'sys_menu_type', '1', '0', '0', '0', '1', '2019-11-28 10:10:41', '1', '2019-11-28 10:10:48', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0006', '可见', 'sys_show_hide', '1', '0', '0', '0', '1', '2019-11-28 10:11:24', '1', '2019-11-28 10:11:28', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0007', '用户类型', 'sys_user_type', '1', '0', '0', '0', '1', '2019-11-28 10:12:28', '1', '2019-11-28 10:12:41', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0008', '角色类型', 'sys_role_type', '1', '0', '0', '0', '1', '2019-11-28 10:13:59', '1', '2019-11-28 10:14:03', null);
INSERT INTO `sys_dict_type` VALUES ('0000-0001-0009', '区域类型', 'sys_area_type', '1', '0', '0', '0', '1', '2019-11-28 10:14:50', '1', '2019-11-28 10:14:56', null);

-- ----------------------------
-- Table structure for `sys_log`
-- ----------------------------
DROP TABLE IF EXISTS `sys_log`;
CREATE TABLE `sys_log` (
  `log_id` bigint(18) NOT NULL COMMENT '主键',
  `type` tinyint(1) DEFAULT NULL COMMENT '类型',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `create_id` bigint(18) DEFAULT NULL COMMENT '创建人ID',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统日志';

-- ----------------------------
-- Records of sys_log
-- ----------------------------
INSERT INTO `sys_log` VALUES ('1060438746056376321', '0', 'A', '100000', '2018-11-08 15:41:58');
INSERT INTO `sys_log` VALUES ('1060438788502732802', '0', 'B', '100000', '2018-11-08 15:42:08');
INSERT INTO `sys_log` VALUES ('1060438799600861185', '0', 'C', '100000', '2018-11-08 15:42:10');
INSERT INTO `sys_log` VALUES ('1060438809495224322', '0', 'D', '100000', '2018-11-08 15:42:13');

-- ----------------------------
-- Table structure for `sys_menu`
-- ----------------------------
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
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`menu_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='菜单表';

-- ----------------------------
-- Records of sys_menu
-- ----------------------------
INSERT INTO `sys_menu` VALUES ('001', '00000000', '00000000', '100', '1', '2', '2', '首级节点', '这是一个菜单', '1', '/aa/bb', '', 'fa fa-slack', '#2D6BAB', '这是菜单标题', 'aa:bb:cc', null, '1', '1', '4,2', '0', '0', '0', '1', '2019-11-28 11:59:01', '1', '2019-11-28 11:59:01', '');
INSERT INTO `sys_menu` VALUES ('0011', '001', '00000000,001', '0', '1,100', '2', '3', '首级节点,这是一个菜单', '这是一个下级菜', '1', '/bb/cc', '', 'fa fa-hand-rock-o', '#409EFF', '这是一个下级标题', 'bb:cc:dd', null, '1', '1', '3', '0', '0', '0', '1', '2019-11-28 12:01:07', '1', '2019-11-28 12:01:38', '');
INSERT INTO `sys_menu` VALUES ('00111', '0011', '00000000,001,0011', '100', '1,100,0', '1', '4', '首级节点,这是一个菜单,这是一个下级菜', '再来一级吧', '2', '/c/d', '', '', '#409EFF', '', 'a:b:c', null, '1', '2', '4,3', '0', '0', '0', '1', '2019-11-28 12:03:08', '1', '2019-11-28 12:03:08', '');
INSERT INTO `sys_menu` VALUES ('002', '00000000', '00000000', '100', '1', '2', '2', '首级节点', '这也是个顶级菜', '1', '/a/b/c', '', 'el-icon-remove-outline', '#409EFF', '叫什么好呢', 'b:c:d', null, '1', '1', '4', '0', '0', '0', '1', '2019-11-28 12:04:33', '1', '2019-11-28 12:04:33', '');
INSERT INTO `sys_menu` VALUES ('0021', '002', '00000000,002', '110', '1,100', '1', '3', '首级节点,这也是个顶级菜', '也加个小弟吧', '2', '', '', '', '#409EFF', '不会显得那么孤独', '', null, '1', '1', '', '0', '0', '0', '1', '2019-11-28 12:05:49', '1', '2019-11-28 12:05:49', '');

-- ----------------------------
-- Table structure for `sys_office`
-- ----------------------------
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
  `status` char(1) NOT NULL DEFAULT '0' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `create_by` varchar(64) NOT NULL COMMENT '创建者',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `update_by` varchar(64) NOT NULL COMMENT '更新者',
  `update_time` datetime NOT NULL COMMENT '更新时间',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`office_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='组织机构表';

-- ----------------------------
-- Records of sys_office
-- ----------------------------
INSERT INTO `sys_office` VALUES ('1', '3', '1', '2', '1', '1', '1', '1', '111', '武汉分公司', '湖北武汉分公司', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-11-25 15:57:25', '1', '2019-11-26 19:11:44', '', '', '');
INSERT INTO `sys_office` VALUES ('100', '00000000', '00000000', '20', '1', '2', '2', '首级节点', '100', '四川公司', '四川公司', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-12-05 10:12:42', '1', '2019-12-05 10:12:42', '', '', '');
INSERT INTO `sys_office` VALUES ('15', '00000000', '1', '1', '1', '1', '1', '1', '1', '湖南公司', '湖南总公司', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-11-27 15:57:36', '1', '2019-12-05 11:55:02', '', '', '');
INSERT INTO `sys_office` VALUES ('18', '19', '1', '3', '1', '2', '1', '1', '2', '乌鲁木齐分公司', '新疆乌鲁木齐分公司', '2', '', '', '', '', '', '0', '0', '1', '1', '2019-11-27 18:55:01', '1', '2019-12-06 18:47:59', '', '', '');
INSERT INTO `sys_office` VALUES ('19', '00000000', '1', '2', '1', '2', '1', '1', '1', '新疆公司', '新疆总公司', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-11-27 18:02:27', '1', '2019-12-05 14:02:28', '', '', '');
INSERT INTO `sys_office` VALUES ('2', '00000000', '1', '2', '1', '1', '1', '1', '3', '广西公司', '广西总公司', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-11-26 18:53:37', '1', '2019-12-05 12:11:32', '', '', '');
INSERT INTO `sys_office` VALUES ('21', '100', '1', '2', '1', '2', '1', '1', '2', '成都公司', '成都公司', '2', '', '', '', '', '', '0', '0', '1', '1', '2019-11-28 12:17:38', '1', '2019-12-05 10:36:41', '', '', '');
INSERT INTO `sys_office` VALUES ('3', '00000000', '1', '1', '1', '1', '1', '1', '111', '湖北公司', '湖北总公司', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-11-25 11:32:59', '1', '2019-12-05 11:53:16', '', '', '');
INSERT INTO `sys_office` VALUES ('300', '21', '1,21', '30', '1,2', '1', '3', '1,成都公司', '300', '成都组织部', '成都组织部', '3', '', '', '', '', '', '0', '0', '1', '1', '2019-12-05 10:42:28', '1', '2019-12-05 10:42:28', '', '', '');
INSERT INTO `sys_office` VALUES ('4', '00000000', '1', '4', '1', '2', '1', '1', '3', '广东公司', '广东总公司', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-11-26 18:57:49', '1', '2019-12-05 12:11:46', '', '', '');
INSERT INTO `sys_office` VALUES ('400', '4', '1,4', '20', '1,4', '1', '3', '1,广东公司', '400', '深圳公司', '深圳公司', '2', '', '', '', '', '', '0', '0', '1', '1', '2019-12-05 12:17:37', '1', '2019-12-05 12:17:37', '', '', '');
INSERT INTO `sys_office` VALUES ('5', '3', '1', '1', '1', '1', '1', '1', '2', '咸宁分公司', '湖北咸宁分公司', '2', '', '', '', '', '', '0', '0', '1', '1', '2019-11-25 18:21:35', '1', '2019-11-26 19:12:17', '', '', '');
INSERT INTO `sys_office` VALUES ('500', '4', '1,4', '20', '1,4', '2', '3', '1,广东公司', '500', '广州公司', '广州公司', '2', '', '', '', '', '', '0', '0', '1', '1', '2019-12-05 12:24:23', '1', '2019-12-05 12:24:23', '', '', '');
INSERT INTO `sys_office` VALUES ('610', '500', '1,4,500', '20', '1,4,20', '1', '4', '1,广东公司,广州公司', '610', '广州考勤部', '广州考勤部', '3', '', '', '', '', '', '0', '0', '1', '1', '2019-12-05 14:46:41', '1', '2019-12-05 14:46:41', '', '', '');
INSERT INTO `sys_office` VALUES ('7', '5', '1', '23', '1', '1', '1', '1', '2', '指挥部', '湖北咸宁指挥部', '1', '', '', '', '', '', '0', '0', '1', '1', '2019-11-26 15:37:55', '1', '2019-11-26 19:12:53', '', '', '');
INSERT INTO `sys_office` VALUES ('9', '5', '1', '5', '1', '1', '1', '1', '4', '后勤部', '湖北咸宁后勤部', '3', '', '', '', '', '', '0', '0', '1', '1', '2019-11-26 19:20:52', '1', '2019-11-26 19:20:52', '', '', '');

-- ----------------------------
-- Table structure for `sys_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_permission`;
CREATE TABLE `sys_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) DEFAULT NULL COMMENT '权限名称',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父id',
  `url` varchar(200) DEFAULT NULL COMMENT '路径',
  `code` varchar(100) NOT NULL COMMENT '唯一编码',
  `icon` varchar(100) DEFAULT NULL COMMENT '图标',
  `type` int(11) NOT NULL COMMENT '类型，1：菜单，2：按钮',
  `level` int(11) NOT NULL COMMENT '层级，1：第一级，2：第二级，N：第N级',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `sort` int(11) NOT NULL DEFAULT '0' COMMENT '排序',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_permission_code_uindex` (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统权限';

-- ----------------------------
-- Records of sys_permission
-- ----------------------------
INSERT INTO `sys_permission` VALUES ('1', '系统管理', null, null, 'system:management', null, '1', '1', '1', '0', null, '0', '2019-10-26 11:12:40', null);
INSERT INTO `sys_permission` VALUES ('100', '用户管理', '1', null, 'sys:user:management', null, '1', '2', '1', '0', null, '0', '2019-10-26 11:15:48', null);
INSERT INTO `sys_permission` VALUES ('200', '角色管理', '1', null, 'sys:role:management', null, '1', '2', '1', '0', null, '0', '2019-10-26 11:15:48', null);
INSERT INTO `sys_permission` VALUES ('300', '权限管理', '1', null, 'sys:permission:management', null, '1', '2', '1', '0', null, '0', '2019-10-26 11:15:48', null);
INSERT INTO `sys_permission` VALUES ('400', '部门管理', '1', null, 'sys:department:management', null, '1', '2', '1', '0', null, '0', '2019-10-26 11:15:48', null);
INSERT INTO `sys_permission` VALUES ('500', '组织机构', '1', null, 'sys:office:management', null, '1', '2', '1', '0', null, '0', '2019-11-25 10:37:20', null);
INSERT INTO `sys_permission` VALUES ('600', '菜单管理', '1', null, 'sys:menu:management', null, '1', '2', '1', '0', null, '0', '2019-11-26 10:10:45', null);
INSERT INTO `sys_permission` VALUES ('700', '数据字典', '1', null, 'sys:dict:type:management', null, '1', '2', '1', '0', null, '0', '2019-11-28 15:39:37', null);
INSERT INTO `sys_permission` VALUES ('800', '用户角色表', '1', null, 'sys:user:role:management', null, '1', '2', '1', '0', null, '0', '2019-12-03 14:33:01', null);
INSERT INTO `sys_permission` VALUES ('900', '学员表', '1', null, 'hw:student:management', null, '1', '2', '1', '0', null, '0', '2019-12-04 15:27:13', null);
INSERT INTO `sys_permission` VALUES ('910', '老师表', '1', null, 'hw:teacher:management', null, '1', '2', '1', '0', null, '0', '2019-12-06 11:44:21', null);
INSERT INTO `sys_permission` VALUES ('1000', '用户新增', '100', null, 'sys:user:add', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('1001', '用户修改', '100', null, 'sys:user:update', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('1002', '用户删除', '100', null, 'sys:user:delete', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('1003', '用户详情', '100', null, 'sys:user:info', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('1004', '用户分页列表', '100', null, 'sys:user:page', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('1005', '用户修改密码', '100', null, 'sys:user:update:password', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('1006', '用户修改头像', '100', null, 'sys:user:update:head', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('1007', '用户修改状态', '100', null, 'sys:user:updateByState', null, '2', '3', '1', '0', null, '0', '2019-11-29 14:44:37', null);
INSERT INTO `sys_permission` VALUES ('1008', '用户重置密码', '100', null, 'sys:user:uPassWord', null, '2', '3', '1', '0', null, '0', '2019-11-30 15:26:35', null);
INSERT INTO `sys_permission` VALUES ('1010', '用户批量修改状态', '100', null, 'sys:user:updateByStatus', null, '2', '3', '1', '0', null, '0', '2019-12-02 12:13:58', null);
INSERT INTO `sys_permission` VALUES ('2000', '角色新增', '200', null, 'sys:role:add', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('2001', '角色修改', '200', null, 'sys:role:update', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('2002', '角色删除', '200', null, 'sys:role:delete', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('2003', '角色详情', '200', null, 'sys:role:info', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('2004', '角色分页列表', '200', null, 'sys:role:page', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('2005', '更新角色状态', '200', null, 'sys:role:state', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:54:48', null);
INSERT INTO `sys_permission` VALUES ('3000', '权限新增', '300', null, 'sys:permission:add', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3001', '权限修改', '300', null, 'sys:permission:update', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3002', '权限删除', '300', null, 'sys:permission:delete', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3003', '权限详情', '300', null, 'sys:permission:info', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3004', '权限分页列表', '300', null, 'sys:permission:page', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3005', '权限所有列表', '300', null, 'sys:permission:all:menu:list', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3006', '权限所有树形列表', '300', null, 'sys:permission:all:menu:tree', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3007', '权限用户列表', '300', null, 'sys:permission:menu:list', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3008', '权限用户树形列表', '300', null, 'sys:permission:menu:tree', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('3009', '权限用户代码列表', '300', null, 'sys:permission:codes', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('4000', '部门新增', '400', null, 'sys:department:add', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('4001', '部门修改', '400', null, 'sys:department:update', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('4002', '部门删除', '400', null, 'sys:department:delete', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('4003', '部门详情', '400', null, 'sys:department:info', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('4004', '部门分页列表', '400', null, 'sys:department:page', null, '2', '3', '1', '0', null, '0', '2019-10-26 11:18:40', null);
INSERT INTO `sys_permission` VALUES ('5000', '组织新增', '500', null, 'sys:office:add', null, '2', '3', '1', '0', null, '0', '2019-11-25 10:38:37', null);
INSERT INTO `sys_permission` VALUES ('5001', '组织修改', '500', null, 'sys:office:update', null, '2', '3', '1', '0', null, '0', '2019-11-25 10:39:11', null);
INSERT INTO `sys_permission` VALUES ('5002', '组织删除', '500', null, 'sys:office:delete', null, '2', '3', '1', '0', null, '0', '2019-11-25 10:39:39', null);
INSERT INTO `sys_permission` VALUES ('5003', '组织详情', '500', null, 'sys:office:info', null, '2', '3', '1', '0', null, '0', '2019-11-25 10:40:09', null);
INSERT INTO `sys_permission` VALUES ('5004', '组织分页列表', '500', null, 'sys:office:page', null, '2', '3', '1', '0', null, '0', '2019-11-25 10:52:32', null);
INSERT INTO `sys_permission` VALUES ('5005', '组织状态修改', '500', null, 'sys:office:updateBystatus', null, '2', '3', '1', '0', null, '0', '2019-11-25 14:38:25', null);
INSERT INTO `sys_permission` VALUES ('6000', '菜单新增', '600', null, 'sys:menu:add', null, '2', '3', '1', '0', null, '0', '2019-11-26 10:07:59', null);
INSERT INTO `sys_permission` VALUES ('6001', '菜单修改', '600', null, 'sys:menu:update', null, '2', '3', '1', '0', null, '0', '2019-11-26 10:11:57', null);
INSERT INTO `sys_permission` VALUES ('6002', '菜单删除', '600', null, 'sys:menu:delete', null, '2', '3', '1', '0', null, '0', '2019-11-26 10:12:42', null);
INSERT INTO `sys_permission` VALUES ('6003', '菜单详情', '600', null, 'sys:menu:info', null, '2', '3', '1', '0', null, '0', '2019-11-26 10:13:44', null);
INSERT INTO `sys_permission` VALUES ('6004', '菜单分页列表', '600', null, 'sys:menu:page', null, '2', '3', '1', '0', null, '0', '2019-11-26 10:14:26', null);
INSERT INTO `sys_permission` VALUES ('6005', '角色关联的菜单', '600', null, 'sys:menu:role:list', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:28:31', null);
INSERT INTO `sys_permission` VALUES ('6006', '获取菜单精简版的树形结构', '600', null, 'sys:menu:simplify:page', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:29:46', null);
INSERT INTO `sys_permission` VALUES ('7000', '字典新增', '700', null, 'sys:dict:type:add', null, '2', '3', '1', '0', null, '0', '2019-11-28 15:42:54', null);
INSERT INTO `sys_permission` VALUES ('7001', '字典修改', '700', null, 'sys:dict:type:update', null, '2', '3', '1', '0', null, '0', '2019-11-28 15:43:40', null);
INSERT INTO `sys_permission` VALUES ('7002', '字典删除', '700', null, 'sys:dict:type:delete', null, '2', '3', '1', '0', null, '0', '2019-11-28 15:44:12', null);
INSERT INTO `sys_permission` VALUES ('7003', '字典详情', '700', null, 'sys:dict:type:info', null, '2', '3', '1', '0', null, '0', '2019-11-28 15:44:57', null);
INSERT INTO `sys_permission` VALUES ('7004', '字典分页列表', '700', null, 'sys:dict:type:page', null, '2', '3', '1', '0', null, '0', '2019-11-28 15:45:35', null);
INSERT INTO `sys_permission` VALUES ('7005', '字典类型状态修改', '700', null, 'sys:dict:type:status', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:47:51', null);
INSERT INTO `sys_permission` VALUES ('7006', '字典数据修改状态', '7001', null, 'sys:dict:data:status', null, '2', '3', '1', '0', null, '0', '2019-12-05 11:54:02', null);
INSERT INTO `sys_permission` VALUES ('8001', '根据用户id查角色集合', '800', null, 'sys:user:role:findUserById', null, '2', '3', '1', '0', null, '0', '2019-12-03 14:34:19', null);
INSERT INTO `sys_permission` VALUES ('8002', '批量新增用户角色', '800', null, 'sys:user:addRoles', null, '2', '3', '1', '0', null, '0', '2019-12-03 17:41:33', null);
INSERT INTO `sys_permission` VALUES ('8003', '单个新增用户角色', '800', null, 'sys:user:addRole', null, '2', '3', '1', '0', null, '0', '2019-12-03 17:42:18', null);
INSERT INTO `sys_permission` VALUES ('8004', '一个角色关联多个用户', '800', null, 'sys:role:users', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:57:53', null);
INSERT INTO `sys_permission` VALUES ('8005', '一个角色所关联的所有用户', '800', null, 'sys:user:role:alllist', null, '2', '3', '1', '0', null, '0', '2019-12-04 16:05:11', null);
INSERT INTO `sys_permission` VALUES ('8006', '一个角色所关联的所有用户的分页', '800', null, 'sys:user:role:list', null, '2', '3', '1', '0', null, '0', '2019-12-04 16:06:01', null);
INSERT INTO `sys_permission` VALUES ('9001', '新增学员', '900', null, 'hw:student:add', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:28:45', null);
INSERT INTO `sys_permission` VALUES ('9002', '修改学员', '900', null, 'hw:student:update', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:29:22', null);
INSERT INTO `sys_permission` VALUES ('9003', '删除学员', '900', null, 'hw:student:delete', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:29:56', null);
INSERT INTO `sys_permission` VALUES ('9004', '学员分页查询', '900', null, 'hw:student:page', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:30:41', null);
INSERT INTO `sys_permission` VALUES ('9005', '学员状态修改', '900', null, 'hw:student:updateByState', null, '2', '3', '1', '0', null, '0', '2019-12-05 19:18:53', null);
INSERT INTO `sys_permission` VALUES ('9006', '学员状态批量修改', '900', null, 'hw:student:updateByStatus', null, '2', '3', '1', '0', null, '0', '2019-12-05 19:19:47', null);
INSERT INTO `sys_permission` VALUES ('9011', '新增老师', '910', null, 'hw:teacher:add', null, '2', '3', '1', '0', null, '0', '2019-12-06 11:46:03', null);
INSERT INTO `sys_permission` VALUES ('9012', '修改老师', '910', null, 'hw:teacher:update', null, '2', '3', '1', '0', null, '0', '2019-12-06 11:46:34', null);
INSERT INTO `sys_permission` VALUES ('9013', '删除老师', '910', null, 'hw:teacher:delete', null, '2', '3', '1', '0', null, '0', '2019-12-06 11:47:28', null);
INSERT INTO `sys_permission` VALUES ('9014', '老师分页查询', '910', null, 'hw:teacher:page', null, '2', '3', '1', '0', null, '0', '2019-12-06 11:48:04', null);
INSERT INTO `sys_permission` VALUES ('9015', '老师状态修改', '910', null, 'hw:teacher:updateByStatus', null, '2', '3', '1', '0', null, '0', '2019-12-06 11:48:56', null);
INSERT INTO `sys_permission` VALUES ('10000', '字典数据', '1', null, 'sys:dict:data:management', null, '1', '2', '1', '0', null, '0', '2019-12-04 15:36:36', null);
INSERT INTO `sys_permission` VALUES ('10001', '角色和菜单表', '1', null, 'sys:role:menu:management', null, '1', '2', '1', '0', null, '0', '2019-12-04 16:09:58', null);
INSERT INTO `sys_permission` VALUES ('10002', '行政区划', '1', null, 'sys:area:management', null, '1', '2', '1', '0', null, '0', '2019-12-04 17:58:48', null);
INSERT INTO `sys_permission` VALUES ('10003', '学校管理', '1', null, 'sys:school', null, '1', '2', '1', '0', null, '0', '2019-12-08 15:15:22', null);
INSERT INTO `sys_permission` VALUES ('10004', '专业管理', '1', null, 'sys:school:college:management', null, '1', '2', '1', '0', null, '0', '2019-12-08 15:16:42', null);
INSERT INTO `sys_permission` VALUES ('100001', '字典数据新增', '10000', null, 'sys:dict:data:add', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:38:26', null);
INSERT INTO `sys_permission` VALUES ('100002', '字典数据修改', '10000', null, 'sys:dict:data:update', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:39:00', null);
INSERT INTO `sys_permission` VALUES ('100003', '字典数据删除', '10000', null, 'sys:dict:data:delete', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:39:44', null);
INSERT INTO `sys_permission` VALUES ('100004', '字典数据详情', '10000', null, 'sys:dict:data:info', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:41:13', null);
INSERT INTO `sys_permission` VALUES ('100005', '字典数据分页列表', '10000', null, 'sys:dict:data:page', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:42:01', null);
INSERT INTO `sys_permission` VALUES ('100006', '字典数据精简树结构', '10000', null, 'sys:dict:data:simplify', null, '2', '3', '1', '0', null, '0', '2019-12-04 15:42:56', null);
INSERT INTO `sys_permission` VALUES ('100011', '一个角色批量关联菜单', '10001', null, 'sys:role:menu:add', null, '2', '3', '1', '0', null, '0', '2019-12-04 16:12:00', null);
INSERT INTO `sys_permission` VALUES ('100021', '行政区划新增', '10002', null, 'sys:area:add', null, '2', '3', '1', '0', null, '0', '2019-12-04 18:00:38', null);
INSERT INTO `sys_permission` VALUES ('100022', '行政区划修改', '10002', null, 'sys:area:update', null, '2', '3', '1', '0', null, '0', '2019-12-04 18:02:02', null);
INSERT INTO `sys_permission` VALUES ('100023', '行政区划删除', '10002', null, 'sys:area:delete', null, '2', '3', '1', '0', null, '0', '2019-12-04 18:02:26', null);
INSERT INTO `sys_permission` VALUES ('100024', '行政区划详情', '10002', null, 'sys:area:info', null, '2', '3', '1', '0', null, '0', '2019-12-04 18:04:29', null);
INSERT INTO `sys_permission` VALUES ('100025', '行政区划分页', '10002', null, 'sys:area:page', null, '2', '3', '1', '0', null, '0', '2019-12-04 18:05:27', null);
INSERT INTO `sys_permission` VALUES ('100026', '行政区域精简树结构', '10002', null, 'sys:area:simplify:list', null, '2', '3', '1', '0', null, '0', '2019-12-04 18:41:24', null);
INSERT INTO `sys_permission` VALUES ('100031', '学校新增', '10003', null, 'sys:school:add', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:17:58', null);
INSERT INTO `sys_permission` VALUES ('100032', '学校修改', '10003', null, 'sys:school:update', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:18:48', null);
INSERT INTO `sys_permission` VALUES ('100033', '学校删除', '10003', null, 'sys:school:delete', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:19:27', null);
INSERT INTO `sys_permission` VALUES ('100034', '学校详情', '10003', null, 'sys:school:info', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:20:08', null);
INSERT INTO `sys_permission` VALUES ('100035', '学校分页', '10003', null, 'sys:school:page', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:21:06', null);
INSERT INTO `sys_permission` VALUES ('100041', '专业新增', '10004', null, 'sys:school:college:add', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:21:44', null);
INSERT INTO `sys_permission` VALUES ('100042', '专业修改', '10004', null, 'sys:school:college:update', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:22:23', null);
INSERT INTO `sys_permission` VALUES ('100043', '专业删除', null, null, 'sys:school:college:delete', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:22:46', null);
INSERT INTO `sys_permission` VALUES ('100044', '专业详情', null, null, 'sys:school:college:info', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:23:10', null);
INSERT INTO `sys_permission` VALUES ('100045', '专业分页', null, null, 'sys:school:college:page', null, '2', '3', '1', '0', null, '0', '2019-12-08 15:23:33', null);
INSERT INTO `sys_permission` VALUES ('100046', '专业导入', null, null, 'sys:school:college:import', null, '2', '3', '1', '0', null, '0', '2019-12-10 10:01:49', null);

-- ----------------------------
-- Table structure for `sys_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '角色名称',
  `code` varchar(100) DEFAULT NULL COMMENT '角色唯一编码',
  `type` varchar(100) DEFAULT NULL COMMENT '角色类型',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_role_name_uindex` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统角色';

-- ----------------------------
-- Records of sys_role
-- ----------------------------
INSERT INTO `sys_role` VALUES ('1', '管理员', 'admin', null, '1', null, '0', null, '2019-10-25 09:47:21', null, null);
INSERT INTO `sys_role` VALUES ('1203924386560618497', '营业员', '3', null, '1', null, '0', null, '2019-11-30 16:51:14', null, null);
INSERT INTO `sys_role` VALUES ('1203924644160613267', 'test', 'test', null, '1', null, '0', null, '2019-10-25 09:48:02', null, null);
INSERT INTO `sys_role` VALUES ('1203924644160614587', '老师', '1', null, '1', null, '0', null, '2019-11-30 16:50:45', null, null);
INSERT INTO `sys_role` VALUES ('1203924644160617195', '学员', '0000-0001-0007-0002', null, '1', null, '0', null, '2019-11-30 16:50:57', null, null);
INSERT INTO `sys_role` VALUES ('1203924644160618497', '四级老师', '0000-0001-0008-0003', null, '1', null, '0', null, '2019-12-09 14:29:38', null, null);
INSERT INTO `sys_role` VALUES ('1203971454160618497', '司机', '4', null, '1', null, '0', null, '2019-11-30 16:51:26', null, null);

-- ----------------------------
-- Table structure for `sys_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` varchar(64) NOT NULL COMMENT '角色id',
  `menu_code` varchar(64) NOT NULL COMMENT '菜单编码',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='角色与菜单关联表';

-- ----------------------------
-- Records of sys_role_menu
-- ----------------------------

-- ----------------------------
-- Table structure for `sys_role_permission`
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_permission`;
CREATE TABLE `sys_role_permission` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `permission_id` bigint(20) NOT NULL COMMENT '权限id',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用',
  `remark` varchar(200) DEFAULT NULL COMMENT '备注',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='角色权限关系';

-- ----------------------------
-- Records of sys_role_permission
-- ----------------------------
INSERT INTO `sys_role_permission` VALUES ('1', '1', '1', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('2', '1', '100', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('3', '1', '200', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('4', '1', '300', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('5', '1', '400', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('6', '1', '1000', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('7', '1', '1001', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('8', '1', '1002', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('9', '1', '1003', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('10', '1', '1004', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('11', '1', '1005', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('12', '1', '1006', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('13', '1', '2000', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('14', '1', '2001', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('15', '1', '2002', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('16', '1', '2003', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('17', '1', '2004', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('18', '1', '3000', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('19', '1', '3001', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('20', '1', '3002', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('21', '1', '3003', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('22', '1', '3004', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('23', '1', '3005', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('24', '1', '3006', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('25', '1', '3007', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('26', '1', '3008', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('27', '1', '3009', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('28', '1', '4001', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('29', '1', '4002', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('30', '1', '4003', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('31', '1', '4004', '1', null, '0', '2019-10-26 22:16:19', null);
INSERT INTO `sys_role_permission` VALUES ('32', '1', '500', '1', null, '0', '2019-11-25 10:43:15', null);
INSERT INTO `sys_role_permission` VALUES ('33', '1', '5000', '1', null, '0', '2019-11-25 10:43:32', null);
INSERT INTO `sys_role_permission` VALUES ('34', '1', '5001', '1', null, '0', '2019-11-25 10:43:43', null);
INSERT INTO `sys_role_permission` VALUES ('35', '1', '5002', '1', null, '0', '2019-11-25 10:43:54', null);
INSERT INTO `sys_role_permission` VALUES ('36', '1', '5003', '1', null, '0', '2019-11-25 10:44:11', null);
INSERT INTO `sys_role_permission` VALUES ('37', '1', '5004', '1', null, '0', '2019-11-25 10:52:55', null);
INSERT INTO `sys_role_permission` VALUES ('38', '1', '5005', '1', null, '0', '2019-11-25 14:39:15', null);
INSERT INTO `sys_role_permission` VALUES ('39', '1', '600', '1', null, '0', '2019-11-26 10:17:26', null);
INSERT INTO `sys_role_permission` VALUES ('40', '1', '6001', '1', null, '0', '2019-11-26 10:17:39', null);
INSERT INTO `sys_role_permission` VALUES ('41', '1', '6002', '1', null, '0', '2019-11-26 10:17:52', null);
INSERT INTO `sys_role_permission` VALUES ('42', '1', '6003', '1', null, '0', '2019-11-26 10:18:02', null);
INSERT INTO `sys_role_permission` VALUES ('43', '1', '6004', '1', null, '0', '2019-11-26 10:18:24', null);
INSERT INTO `sys_role_permission` VALUES ('44', '1', '6000', '1', null, '0', '2019-11-26 10:19:02', null);
INSERT INTO `sys_role_permission` VALUES ('45', '1', '700', '1', null, '0', '2019-11-28 15:49:01', null);
INSERT INTO `sys_role_permission` VALUES ('46', '1', '7000', '1', null, '0', '2019-11-28 15:49:20', null);
INSERT INTO `sys_role_permission` VALUES ('47', '1', '7001', '1', null, '0', '2019-11-28 15:49:34', null);
INSERT INTO `sys_role_permission` VALUES ('48', '1', '7002', '1', null, '0', '2019-11-28 15:49:48', null);
INSERT INTO `sys_role_permission` VALUES ('49', '1', '7003', '1', null, '0', '2019-11-28 15:50:06', null);
INSERT INTO `sys_role_permission` VALUES ('50', '1', '7004', '1', null, '0', '2019-11-28 15:50:21', null);
INSERT INTO `sys_role_permission` VALUES ('51', '1', '1007', '1', null, '0', '2019-11-29 14:45:48', null);
INSERT INTO `sys_role_permission` VALUES ('52', '1', '1008', '1', null, '0', '2019-11-30 15:27:27', null);
INSERT INTO `sys_role_permission` VALUES ('53', '1', '1009', '1', null, '0', '2019-11-30 18:18:12', null);
INSERT INTO `sys_role_permission` VALUES ('54', '1', '1010', '1', null, '0', '2019-12-02 12:15:28', null);
INSERT INTO `sys_role_permission` VALUES ('55', '1', '8001', '1', null, '0', '2019-12-03 14:35:46', null);
INSERT INTO `sys_role_permission` VALUES ('56', '1', '8002', '1', null, '0', '2019-12-03 17:44:37', null);
INSERT INTO `sys_role_permission` VALUES ('57', '1', '8003', '1', null, '0', '2019-12-03 17:44:50', null);
INSERT INTO `sys_role_permission` VALUES ('58', '1', '6005', '1', null, '0', '2019-12-04 15:30:52', null);
INSERT INTO `sys_role_permission` VALUES ('59', '1', '6006', '1', null, '0', '2019-12-04 15:31:05', null);
INSERT INTO `sys_role_permission` VALUES ('60', '1', '900', '1', null, '0', '2019-12-04 15:31:59', null);
INSERT INTO `sys_role_permission` VALUES ('61', '1', '9001', '1', null, '0', '2019-12-04 15:32:20', null);
INSERT INTO `sys_role_permission` VALUES ('62', '1', '9002', '1', null, '0', '2019-12-04 15:32:31', null);
INSERT INTO `sys_role_permission` VALUES ('63', '1', '9003', '1', null, '0', '2019-12-04 15:32:45', null);
INSERT INTO `sys_role_permission` VALUES ('64', '1', '9004', '1', null, '0', '2019-12-04 15:32:58', null);
INSERT INTO `sys_role_permission` VALUES ('65', '1', '7005', '1', null, '0', '2019-12-04 15:49:14', null);
INSERT INTO `sys_role_permission` VALUES ('66', '1', '10000', '1', null, '0', '2019-12-04 15:49:27', null);
INSERT INTO `sys_role_permission` VALUES ('67', '1', '100001', '1', null, '0', '2019-12-04 15:49:45', null);
INSERT INTO `sys_role_permission` VALUES ('68', '1', '100002', '1', null, '0', '2019-12-04 15:49:57', null);
INSERT INTO `sys_role_permission` VALUES ('69', '1', '100003', '1', null, '0', '2019-12-04 15:50:10', null);
INSERT INTO `sys_role_permission` VALUES ('70', '1', '100004', '1', null, '0', '2019-12-04 15:50:25', null);
INSERT INTO `sys_role_permission` VALUES ('71', '1', '100005', '1', null, '0', '2019-12-04 15:50:37', null);
INSERT INTO `sys_role_permission` VALUES ('72', '1', '100006', '1', null, '0', '2019-12-04 15:50:50', null);
INSERT INTO `sys_role_permission` VALUES ('73', '1', '2005', '1', null, '0', '2019-12-04 15:59:38', null);
INSERT INTO `sys_role_permission` VALUES ('74', '1', '8004', '1', null, '0', '2019-12-04 15:59:48', null);
INSERT INTO `sys_role_permission` VALUES ('75', '1', '8005', '1', null, '0', '2019-12-04 16:06:27', null);
INSERT INTO `sys_role_permission` VALUES ('76', '1', '8006', '1', null, '0', '2019-12-04 16:06:38', null);
INSERT INTO `sys_role_permission` VALUES ('77', '1', '10001', '1', null, '0', '2019-12-04 16:15:04', null);
INSERT INTO `sys_role_permission` VALUES ('78', '1', '100011', '1', null, '0', '2019-12-04 16:15:16', null);
INSERT INTO `sys_role_permission` VALUES ('79', '1', '10002', '1', null, '0', '2019-12-04 18:06:44', null);
INSERT INTO `sys_role_permission` VALUES ('80', '1', '100021', '1', null, '0', '2019-12-04 18:07:09', null);
INSERT INTO `sys_role_permission` VALUES ('81', '1', '100022', '1', null, '0', '2019-12-04 18:07:22', null);
INSERT INTO `sys_role_permission` VALUES ('82', '1', '100023', '1', null, '0', '2019-12-04 18:07:44', null);
INSERT INTO `sys_role_permission` VALUES ('83', '1', '100024', '1', null, '0', '2019-12-04 18:08:00', null);
INSERT INTO `sys_role_permission` VALUES ('84', '1', '100025', '1', null, '0', '2019-12-04 18:08:41', null);
INSERT INTO `sys_role_permission` VALUES ('85', '1', '100026', '1', null, '0', '2019-12-04 18:42:15', null);
INSERT INTO `sys_role_permission` VALUES ('86', '1', '7006', '1', null, '0', '2019-12-05 11:54:31', null);
INSERT INTO `sys_role_permission` VALUES ('87', '1', '9005', '1', null, '0', '2019-12-05 19:20:26', null);
INSERT INTO `sys_role_permission` VALUES ('88', '1', '9006', '1', null, '0', '2019-12-05 19:20:37', null);
INSERT INTO `sys_role_permission` VALUES ('89', '1', '9011', '1', null, '0', '2019-12-06 11:49:42', null);
INSERT INTO `sys_role_permission` VALUES ('90', '1', '9012', '1', null, '0', '2019-12-06 11:49:50', null);
INSERT INTO `sys_role_permission` VALUES ('91', '1', '9013', '1', null, '0', '2019-12-06 11:50:09', null);
INSERT INTO `sys_role_permission` VALUES ('92', '1', '9014', '1', null, '0', '2019-12-06 11:50:23', null);
INSERT INTO `sys_role_permission` VALUES ('93', '1', '9015', '1', null, '0', '2019-12-06 11:50:37', null);
INSERT INTO `sys_role_permission` VALUES ('94', '1', '100041', '1', null, '0', '2019-12-08 15:26:09', null);
INSERT INTO `sys_role_permission` VALUES ('95', '1', '100042', '1', null, '0', '2019-12-08 15:26:27', null);
INSERT INTO `sys_role_permission` VALUES ('96', '1', '100043', '1', null, '0', '2019-12-08 15:26:40', null);
INSERT INTO `sys_role_permission` VALUES ('97', '1', '100044', '1', null, '0', '2019-12-08 15:26:54', null);
INSERT INTO `sys_role_permission` VALUES ('98', '1', '100045', '1', null, '0', '2019-12-08 15:27:12', null);
INSERT INTO `sys_role_permission` VALUES ('99', '1', '10003', '1', null, '0', '2019-12-08 15:24:18', null);
INSERT INTO `sys_role_permission` VALUES ('100', '1', '10004', '1', null, '0', '2019-12-08 15:24:31', null);
INSERT INTO `sys_role_permission` VALUES ('101', '1', '100031', '1', null, '0', '2019-12-08 15:24:47', null);
INSERT INTO `sys_role_permission` VALUES ('102', '1', '100032', '1', null, '0', '2019-12-08 15:25:06', null);
INSERT INTO `sys_role_permission` VALUES ('103', '1', '100033', '1', null, '0', '2019-12-08 15:25:30', null);
INSERT INTO `sys_role_permission` VALUES ('104', '1', '100034', '1', null, '0', '2019-12-08 15:25:42', null);
INSERT INTO `sys_role_permission` VALUES ('105', '1', '100035', '1', null, '0', '2019-12-08 15:25:53', null);
INSERT INTO `sys_role_permission` VALUES ('106', '1', '100046', '1', null, '0', '2019-12-10 10:02:21', null);

-- ----------------------------
-- Table structure for `sys_school`
-- ----------------------------
DROP TABLE IF EXISTS `sys_school`;
CREATE TABLE `sys_school` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `school_code` varchar(100) NOT NULL COMMENT '学校代码',
  `school_name` varchar(100) DEFAULT NULL COMMENT '学校名称',
  `province_code` varchar(100) DEFAULT NULL COMMENT '省份代码',
  `province_name` varchar(100) DEFAULT NULL COMMENT '省份名称',
  `city_code` varchar(100) DEFAULT NULL COMMENT '城市代码',
  `city_name` varchar(100) DEFAULT NULL COMMENT '城市名称',
  `district_code` varchar(100) DEFAULT NULL COMMENT '区县代码',
  `district_name` varchar(100) DEFAULT NULL COMMENT '区县名称',
  `address` varchar(1000) DEFAULT NULL COMMENT '地址',
  `school_info` varchar(1000) DEFAULT NULL COMMENT '学校介绍',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_school_school_code_uindex` (`school_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学校表';

-- ----------------------------
-- Records of sys_school
-- ----------------------------
INSERT INTO `sys_school` VALUES ('1203883036086349826', '001', '武汉大学', '0001', '武汉市', '0001-0002', '洪山区', '', '', '珞珈山', '好学校', '牛逼', '1', '0', '0', null, '2019-12-09 11:44:17', null, null, '0', 'whyy');
INSERT INTO `sys_school` VALUES ('1203961696399110145', '002', '理工大学', '0001', '武汉市', '0001-0001', '江夏区', '001-001-001', '大桥新区', '马房山', '牛逼', '牛逼', '1', '0', '0', null, '2019-12-09 16:56:51', null, null, '0', 'whyy');

-- ----------------------------
-- Table structure for `sys_school_college`
-- ----------------------------
DROP TABLE IF EXISTS `sys_school_college`;
CREATE TABLE `sys_school_college` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `school_id` bigint(20) NOT NULL COMMENT '学校id',
  `college_code` varchar(100) DEFAULT NULL COMMENT '专业代码',
  `college_name` varchar(100) DEFAULT NULL COMMENT '专业名称',
  `college_info` varchar(1000) DEFAULT NULL COMMENT '专业介绍',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `status` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_school_college_college_code_uindex` (`college_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='学校专业表';

-- ----------------------------
-- Records of sys_school_college
-- ----------------------------
INSERT INTO `sys_school_college` VALUES ('1203956246987386881', '1203961696399110145', '002', '计算机', '牛逼', '211', '1', '0', '0', null, '2019-12-09 16:35:12', null, null, '0', 'whyy');

-- ----------------------------
-- Table structure for `sys_user`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `username` varchar(30) NOT NULL COMMENT '用户名',
  `nickname` varchar(30) DEFAULT NULL COMMENT '昵称',
  `password` varchar(100) NOT NULL COMMENT '登录密码',
  `salt` varchar(32) DEFAULT NULL COMMENT '盐值',
  `email` varchar(300) DEFAULT NULL COMMENT '电子邮箱',
  `mobile` varchar(100) DEFAULT NULL COMMENT '办公电话',
  `phone` varchar(100) DEFAULT NULL COMMENT '手机号码',
  `gender` int(11) DEFAULT '1' COMMENT '性别，0：女，1：男，默认1',
  `avatar` varchar(1000) DEFAULT NULL COMMENT '头像路径',
  `signtext` varchar(200) DEFAULT NULL COMMENT '个性签名',
  `wx_openid` varchar(100) DEFAULT NULL COMMENT '绑定的微信号',
  `mobile_imei` varchar(100) DEFAULT NULL COMMENT '绑定的手机串号',
  `user_type` varchar(16) NOT NULL COMMENT '用户类型',
  `ref_code` varchar(64) DEFAULT NULL COMMENT '用户类型引用编号',
  `ref_name` varchar(100) DEFAULT NULL COMMENT '用户类型引用姓名',
  `mgr_type` char(1) NOT NULL COMMENT '管理员类型（0非管理员 1系统管理员  2二级管理员）',
  `pwd_security_level` decimal(1,0) DEFAULT NULL COMMENT '密码安全级别（0初始 1很弱 2弱 3安全 4很安全）',
  `pwd_update_date` datetime DEFAULT NULL COMMENT '密码最后更新时间',
  `pwd_update_record` varchar(1000) DEFAULT NULL COMMENT '密码修改记录',
  `pwd_question` varchar(200) DEFAULT NULL COMMENT '密保问题',
  `pwd_question_answer` varchar(200) DEFAULT NULL COMMENT '密保问题答案',
  `pwd_question2` varchar(200) DEFAULT NULL COMMENT '密保问题2',
  `pwd_question_answer2` varchar(200) DEFAULT NULL COMMENT '密保问题答案2',
  `pwd_question3` varchar(200) DEFAULT NULL COMMENT '密保问题3',
  `pwd_question_answer3` varchar(200) DEFAULT NULL COMMENT '密保问题答案3',
  `pwd_quest_update_date` datetime DEFAULT NULL COMMENT '密码问题修改时间',
  `last_login_ip` varchar(100) DEFAULT NULL COMMENT '最后登陆IP',
  `last_login_date` datetime DEFAULT NULL COMMENT '最后登陆时间',
  `freeze_date` datetime DEFAULT NULL COMMENT '冻结时间',
  `freeze_cause` varchar(200) DEFAULT NULL COMMENT '冻结原因',
  `user_weight` decimal(8,0) DEFAULT '0' COMMENT '用户权重（降序）',
  `remarks` varchar(500) DEFAULT NULL COMMENT '备注信息',
  `department_id` bigint(20) DEFAULT NULL COMMENT '部门id',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色id',
  `state` int(11) NOT NULL DEFAULT '1' COMMENT '状态，0：禁用，1：启用，2：锁定',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`),
  UNIQUE KEY `sys_user_username_uindex` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统用户';

-- ----------------------------
-- Records of sys_user
-- ----------------------------
INSERT INTO `sys_user` VALUES ('1', 'admin', '管理员', '11a254dab80d52bc4a347e030e54d861a9d2cdb2af2185a9ca4a7318e830d04d', '666', '163.com', null, '13950019129', '1', 'http://localhost:8888//resource/201911102329033.png', null, null, null, '1', null, null, '1', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '2', '1', '1', '0', '0', '1', '2019-11-23 10:42:01', '1', null, '0', 'whyy');
INSERT INTO `sys_user` VALUES ('1200616675201261570', 'test1', '', 'a0b7f26b27e2f89d38ba85e173f982d2404f5f58ddc448e28f7e226e6f53bc0f', 'f6a8e65b62facfe999c8b9cf4af93ff9', '102101251420@qq.com', null, '', '1', null, null, null, null, '2', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '3', '1', '1', '0', '0', '1', '2019-11-30 11:24:56', '1', '2019-12-02 14:05:23', '0', 'whyy');
INSERT INTO `sys_user` VALUES ('1200668139391029250', 'test2', 'test2', '4b67aca35fce5c8189d0c350ed5c605b7e8b0ffb886ed4a88242fbad0a6f54b7', '3f022a0d3489f82efa55911978442782', null, null, '', '1', null, null, null, null, '2', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '3', '1', '1', '0', '0', '1', '2019-11-30 14:49:26', '1', '2019-12-04 18:46:17', '0', 'whyy');
INSERT INTO `sys_user` VALUES ('1202167314691682306', 'xueyuan1', 'xueyuan1', '123456', '64748df3bb6f4ee7a5a1630ec833413c', null, null, '18617128100', '2', null, null, '', null, '1', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-04 18:07:29', '1', '2019-12-05 19:29:00', '', '');
INSERT INTO `sys_user` VALUES ('1202398866516267010', 'test3', 'test3', '922607e33fbaa253d7636a003d2e8a2e26cacb9aae265392068eacc030b2b62a', '64748df3bb6f4ee7a5a1630ec833413c', null, null, '13998525652', '1', null, null, null, null, '2', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '3', '1', '1', '0', '0', '1', '2019-12-05 09:26:44', '1', '2019-12-05 09:26:44', '0', 'whyy');
INSERT INTO `sys_user` VALUES ('1202561131567644674', 'xueyuan2', 'xueyuan2', '6804a19f5e57e82aa05ebe0670f8f533a85c9d6993fafa11a8278bb4f186bedb', '64748df3bb6f4ee7a5a1630ec833413c', null, null, '18617128103', '1', null, null, '', null, '1', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-05 20:11:31', '1', '2019-12-05 20:11:31', '', '');
INSERT INTO `sys_user` VALUES ('1202562202159669249', 'xueyuan3', 'xueyuan3', '5cbe2cf54ee48e6eddc5ee3a7ce313eb78af982a073843c23971b50cf41b8bf6', '100cd355a1da3ad24de249649b41aca4', null, null, '18617128104', '1', null, null, '', null, '1', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-05 20:15:46', '1', '2019-12-09 15:03:13', '', '');
INSERT INTO `sys_user` VALUES ('1202843140902887425', 'laoshi1', 'laoshi1', 'f0690c281fa4aed1e5d58e906ecb508ab5c79b884a0596ed44105a9391aeab07', 'a3e870b39e3590b574240dcfbb79c368', null, null, '13995885210', '1', null, null, null, null, '3', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-06 14:52:07', '1', '2019-12-06 16:03:01', '', '');
INSERT INTO `sys_user` VALUES ('1202868381809221633', 'laoshi2', 'laoshi2', 'bc81a41d8e214de58aadc60204b30f30699e60b6ca91c990d8cd2763c441acb9', '586628e477e4247c67e465d70f51db1e', null, null, '16987845213', '1', null, null, null, null, '3', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-06 16:32:25', '1', '2019-12-06 16:32:25', '', '');
INSERT INTO `sys_user` VALUES ('1203924643783131137', 'laoshi4', 'laoshi4', '1ae1523d8e0e291849eedd64cc37b4dfae4ce5091afe2aec64aaa1558eb7aa94', 'f62708f64c492d6a2f5d5bb809bf1e32', null, null, '18617128200', '1', null, null, null, null, '3', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-09 14:29:38', '1', '2019-12-09 14:29:38', '', '');
INSERT INTO `sys_user` VALUES ('1203924644550688769', 'laoshi5', 'laoshi5', '5957306822ea731edb5cd351960f451b5039ebaef66eb01d453e8132da6b4951', 'bf876b63a61a349d12953d93f9ad1b11', null, null, '18617128201', '1', null, null, null, null, '3', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-09 14:29:38', '1', '2019-12-09 14:29:38', '', '');
INSERT INTO `sys_user` VALUES ('1203924645091753985', 'laoshi6', 'laoshi6', '81cd82343481cd602878be34991d83511ba1782fa8502d715947ad1283eaf7b3', 'd4550f9c1e02114c700eff14a0ecf361', null, null, '18617128202', '1', null, null, null, null, '3', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, null, null, '1', '0', '0', '1', '2019-12-09 14:29:38', '1', '2019-12-09 14:29:38', '', '');
INSERT INTO `sys_user` VALUES ('1203999992835575809', 'xueyuan4', 'xueyuan4', '83f4a01f5af386f94ac16f4f5dea2c3f9cffb58aabd10f98a832966beab91bd0', '3c581a645cbefb77a410a2c8c4082dc2', null, null, '18511949521', '1', null, null, null, null, '3', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '10000', null, '1', '0', '0', '1', '2019-12-09 19:29:02', '1', '2019-12-09 19:32:13', '', '');
INSERT INTO `sys_user` VALUES ('1204008916515053569', 'laoshi3', 'laoshi3', '1692b69283a74a002993907358eef53b277289f7ecdd65195570f85d4fd6a2ae', '2312922555706f089616145c7ac75be3', null, null, '18511698452', '1', null, null, null, null, '3', null, null, '0', null, null, null, null, null, null, null, null, null, null, null, null, null, null, '0', null, '10000', null, '1', '0', '0', '1', '2019-12-09 20:04:30', '1', '2019-12-09 20:28:03', '', '');

-- ----------------------------
-- Table structure for `sys_user_office`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_office`;
CREATE TABLE `sys_user_office` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `office_code` varchar(64) NOT NULL COMMENT '机构代码',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和机构关联表';

-- ----------------------------
-- Records of sys_user_office
-- ----------------------------
INSERT INTO `sys_user_office` VALUES ('1200608547260858370', '1200608546866593794', '19', '0', '0', null, '2019-11-30 10:52:38', null, null, '1', '1');
INSERT INTO `sys_user_office` VALUES ('1200616675863961602', '1200616675201261570', '3', '0', '0', null, '2019-11-30 11:24:56', null, null, '1', '1');
INSERT INTO `sys_user_office` VALUES ('1200668140615766017', '1200668139391029250', '3', '0', '0', null, '2019-11-30 14:49:26', null, null, '1', '1');
INSERT INTO `sys_user_office` VALUES ('1202398867761975298', '1202398866516267010', '3', '0', '0', null, '2019-12-05 09:26:43', null, null, '1', '1');
INSERT INTO `sys_user_office` VALUES ('1203924644009623553', '1203924643783131137', '10000', '0', '0', null, '2019-12-09 14:29:37', null, null, '', '');
INSERT INTO `sys_user_office` VALUES ('1203924644718460930', '1203924644550688769', '10000', '0', '0', null, '2019-12-09 14:29:37', null, null, '', '');
INSERT INTO `sys_user_office` VALUES ('1203924645200805889', '1203924645091753985', '10000', '0', '0', null, '2019-12-09 14:29:37', null, null, '', '');
INSERT INTO `sys_user_office` VALUES ('1203999994496520194', '1203999992835575809', '10000', '0', '0', null, '2019-12-09 19:29:02', null, null, '', '');
INSERT INTO `sys_user_office` VALUES ('1204008918041780225', '1204008916515053569', '10000', '0', '0', null, '2019-12-09 20:04:29', null, null, '', '');

-- ----------------------------
-- Table structure for `sys_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `user_id` bigint(20) NOT NULL COMMENT '用户id',
  `role_id` bigint(20) NOT NULL COMMENT '角色id',
  `deleted` int(11) NOT NULL DEFAULT '0' COMMENT '逻辑删除，0：未删除，1：已删除',
  `version` int(11) NOT NULL DEFAULT '0' COMMENT '版本',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建者',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '修改者',
  `update_time` timestamp NULL DEFAULT NULL COMMENT '修改时间',
  `corp_code` varchar(64) NOT NULL DEFAULT '0' COMMENT '租户代码',
  `corp_name` varchar(100) NOT NULL DEFAULT 'whyy' COMMENT '租户名称',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户和角色关联表';

-- ----------------------------
-- Records of sys_user_role
-- ----------------------------
INSERT INTO `sys_user_role` VALUES ('1202528431163101185', '1202527134816980993', '5', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431188267010', '1', '5', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431217627137', '1202398866516267010', '5', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431251181569', '1202167314691682306', '5', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431280541698', '1200668139391029250', '5', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431314096130', '1200616675201261570', '5', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431343456258', '1202527134816980993', '4', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431368622081', '1', '4', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431402176513', '1202398866516267010', '4', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431427342337', '1202167314691682306', '4', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431456702466', '1200668139391029250', '4', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1202528431481868290', '1200616675201261570', '4', '0', '0', null, '2019-12-05 18:01:34', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203924644282253314', '1203924643783131137', '1203924644160618497', '0', '0', null, '2019-12-09 14:29:37', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203924644882038786', '1203924644550688769', '1203924644160618497', '0', '0', null, '2019-12-09 14:29:37', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203924645314052098', '1203924645091753985', '1203924644160618497', '0', '0', null, '2019-12-09 14:29:37', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203932892389457921', '1202868381809221633', '1203924644160614587', '0', '0', null, '2019-12-09 15:02:23', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203932892431400961', '1202868381809221633', '1203924644160613267', '0', '0', null, '2019-12-09 15:02:24', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203932928053624834', '1202843140902887425', '1203924644160614587', '0', '0', null, '2019-12-09 15:02:32', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203932928082984961', '1202843140902887425', '1203924644160618497', '0', '0', null, '2019-12-09 15:02:32', null, null, '1', '1');
INSERT INTO `sys_user_role` VALUES ('1203999994848841729', '1203999992835575809', '1203924644160617195', '0', '0', null, '2019-12-09 19:29:02', null, null, '1', '1');
