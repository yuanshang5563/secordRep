/*
MySQL Backup
Source Server Version: 5.5.56
Source Database: springboot4
Date: 2018/12/5 22:39:50
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
--  Table structure for `core_dept`
-- ----------------------------
DROP TABLE IF EXISTS `core_dept`;
CREATE TABLE `core_dept` (
  `core_dept_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parent_core_dept_id` bigint(20) DEFAULT NULL,
  `dept_name` varchar(100) DEFAULT NULL,
  `dept_code` varchar(100) DEFAULT NULL,
  `dept_desc` varchar(500) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `del_flag` int(11) DEFAULT NULL,
  PRIMARY KEY (`core_dept_id`),
  KEY `fk_parent_core_dept_id` (`parent_core_dept_id`),
  CONSTRAINT `fk_parent_core_dept_id` FOREIGN KEY (`parent_core_dept_id`) REFERENCES `core_dept` (`core_dept_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_dictionaries`
-- ----------------------------
DROP TABLE IF EXISTS `core_dictionaries`;
CREATE TABLE `core_dictionaries` (
  `core_dict_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_code` varchar(255) DEFAULT NULL,
  `dict_value` varchar(255) DEFAULT NULL,
  `core_dict_group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`core_dict_id`),
  KEY `core_dictionaries_` (`core_dict_group_id`),
  CONSTRAINT `core_dictionaries_` FOREIGN KEY (`core_dict_group_id`) REFERENCES `core_dictionaries_group` (`core_dict_group_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_dictionaries_group`
-- ----------------------------
DROP TABLE IF EXISTS `core_dictionaries_group`;
CREATE TABLE `core_dictionaries_group` (
  `core_dict_group_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dict_group_name` varchar(255) DEFAULT NULL,
  `dict_group_code` varchar(255) DEFAULT NULL,
  `dict_group_desc` varchar(255) DEFAULT NULL,
  `parent_core_dict_group_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`core_dict_group_id`),
  UNIQUE KEY `index_core_dictionaries_group_dict_group_code` (`dict_group_code`) USING BTREE,
  KEY `fk_core_dictionaries_group_parent_core_dict_group_id` (`parent_core_dict_group_id`),
  CONSTRAINT `fk_core_dictionaries_group_parent_core_dict_group_id` FOREIGN KEY (`parent_core_dict_group_id`) REFERENCES `core_dictionaries_group` (`core_dict_group_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_menu`
-- ----------------------------
DROP TABLE IF EXISTS `core_menu`;
CREATE TABLE `core_menu` (
  `core_menu_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `menu_name` varchar(255) DEFAULT NULL,
  `menu_type` varchar(255) DEFAULT NULL,
  `menu_url` varchar(255) DEFAULT NULL,
  `permission` varchar(255) DEFAULT NULL,
  `icon` varchar(255) DEFAULT NULL,
  `order_num` int(11) DEFAULT NULL,
  `parent_core_menu_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`core_menu_id`),
  KEY `fk_parent_core_menu_id` (`parent_core_menu_id`),
  CONSTRAINT `fk_parent_core_menu_id` FOREIGN KEY (`parent_core_menu_id`) REFERENCES `core_menu` (`core_menu_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_parameter`
-- ----------------------------
DROP TABLE IF EXISTS `core_parameter`;
CREATE TABLE `core_parameter` (
  `core_param_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `param_name` varchar(255) DEFAULT NULL,
  `param_type` varchar(10) DEFAULT NULL,
  `param_code` varchar(255) DEFAULT NULL,
  `param_value` varchar(255) DEFAULT NULL,
  `param_desc` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`core_param_id`),
  UNIQUE KEY `index_core_parameter_param_code` (`param_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_role`
-- ----------------------------
DROP TABLE IF EXISTS `core_role`;
CREATE TABLE `core_role` (
  `core_role_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_name` varchar(255) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`core_role_id`),
  UNIQUE KEY `index_core_role_role` (`role`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_role_menu`
-- ----------------------------
DROP TABLE IF EXISTS `core_role_menu`;
CREATE TABLE `core_role_menu` (
  `core_menu_id` bigint(20) NOT NULL,
  `core_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`core_menu_id`,`core_role_id`),
  KEY `fk_core_role_id_role` (`core_role_id`),
  CONSTRAINT `fk_core_menu_id_menu` FOREIGN KEY (`core_menu_id`) REFERENCES `core_menu` (`core_menu_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_core_role_id_role` FOREIGN KEY (`core_role_id`) REFERENCES `core_role` (`core_role_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_user`
-- ----------------------------
DROP TABLE IF EXISTS `core_user`;
CREATE TABLE `core_user` (
  `core_user_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `real_name` varchar(255) DEFAULT NULL,
  `sex` varchar(255) DEFAULT NULL,
  `birthday` date DEFAULT NULL,
  `mobile` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `comment` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `core_dept_id` bigint(20) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `modified_time` datetime DEFAULT NULL,
  PRIMARY KEY (`core_user_id`),
  KEY `fk_user_core_dept_id` (`core_dept_id`),
  CONSTRAINT `fk_user_core_dept_id` FOREIGN KEY (`core_dept_id`) REFERENCES `core_dept` (`core_dept_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `core_user_role`
-- ----------------------------
DROP TABLE IF EXISTS `core_user_role`;
CREATE TABLE `core_user_role` (
  `core_user_id` bigint(20) NOT NULL,
  `core_role_id` bigint(20) NOT NULL,
  PRIMARY KEY (`core_user_id`,`core_role_id`),
  KEY `fk_core_role_id_role_2` (`core_role_id`),
  CONSTRAINT `fk_core_role_id_role_2` FOREIGN KEY (`core_role_id`) REFERENCES `core_role` (`core_role_id`) ON DELETE CASCADE ON UPDATE NO ACTION,
  CONSTRAINT `fk_core_user_id_user_2` FOREIGN KEY (`core_user_id`) REFERENCES `core_user` (`core_user_id`) ON DELETE CASCADE ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
--  Table structure for `t_user`
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1001 DEFAULT CHARSET=utf8;

-- ----------------------------
--  Records 
-- ----------------------------
INSERT INTO `core_dept` VALUES ('0',NULL,'全国',NULL,'根节点','0','0'), ('2','0','湖南省','43','根节点','1','0'), ('5','2','长沙市','4301','','0','0'), ('6','5','望城区','430122','','8','0'), ('7','5','长沙县','430121','','0','0');
INSERT INTO `core_dictionaries` VALUES ('1','sex.0','未知','10'), ('2','sex.1','男','10'), ('3','sex.2','女','10'), ('4','usr','用户参数','12'), ('5','sys','系统参数','12');
INSERT INTO `core_dictionaries_group` VALUES ('0','根节点',NULL,'根节点',NULL), ('9','系统字典组','core_dict_group','','0'), ('10','性别字典组','sex','','9'), ('12','系统参数类型','param_type','','9');
INSERT INTO `core_menu` VALUES ('0','mycms管理系统','0','',NULL,'fa fa-desktop','0',NULL), ('2','系统管理','0','','','fa fa-desktop','0','0'), ('3','菜单列表','1','/manager/core/CoreMenuController/coreMenuList','','fa fa-th-list','2','2'), ('19','部门列表','1','/manager/core/CoreDeptController/coreDeptList','','fa fa-book','2','2'), ('20','用户列表','1','/manager/core/CoreUserController/coreUserList','','fa fa-user','3','2'), ('21','角色列表','1','/manager/core/CoreRoleController/coreRoleList','','fa fa-check','4','2'), ('80','参数管理','1','/manager/core/CoreParameterController/coreParameterList','','fa fa-arrows','5','2'), ('81','数据字典组管理','1','/manager/core/CoreDictionariesGroupController/coreDictionariesGroupList','','fa fa-bars','6','2'), ('82','数据字典管理','1','/manager/core/CoreDictionariesController/coreDictionariesList','','fa fa-building','7','2'), ('83','列表页面','2','/manager/core/CoreDeptController/coreDeptList','ROLE_CORE_DEPT_LIST_PAGE',NULL,'0','19'), ('84','新增和修改页面','2','/manager/core/CoreDeptController/coreDeptForm','ROLE_CORE_DEPT_ADD_EDIT_PAGE',NULL,'1','19'), ('85','新增和修改','2','/manager/core/CoreDeptController/saveCoreDeptForm','ROLE_CORE_DEPT_ADD_EDIT',NULL,'1','19'), ('86','删除','2','/manager/core/CoreDeptController/deleteCoreDept','ROLE_CORE_DEPT_DEL',NULL,'2','19'), ('87','列表数据1','2','/manager/core/CoreDeptController/coreDeptListJsonData','ROLE_CORE_DEPT_LIST1',NULL,'0','19'), ('88','列表数据2','2','/manager/core/CoreDeptController/coreDeptListJsonDataNoPage','ROLE_CORE_DEPT_LIST2',NULL,'0','19'), ('89','列表页面','2','/manager/core/CoreDictionariesController/coreDictionariesList','ROLE_CORE_DICTIONARIES_LIST_PAGE',NULL,'0','82'), ('90','新增和修改页面','2','/manager/core/CoreDictionariesController/coreDictionariesForm','ROLE_CORE_DICTIONARIES_ADD_EDIT_PAGE',NULL,'1','82'), ('91','新增和修改','2','/manager/core/CoreDictionariesController/saveCoreDictionariesForm','ROLE_CORE_DICTIONARIES_ADD_EDIT',NULL,'1','82'), ('92','删除','2','/manager/core/CoreDictionariesController/deleteCoreDictionaries','ROLE_CORE_DICTIONARIES_DEL',NULL,'2','82'), ('93','列表数据','2','/manager/core/CoreDictionariesController/coreDictionariesListJsonData','ROLE_CORE_DICTIONARIES_LIST',NULL,'0','82'), ('94','列表页面','2','/manager/core/CoreDictionariesGroupController/coreDictionariesGroupList','ROLE_CORE_DICTIONARIESGROUP_LIST_PAGE',NULL,'0','81'), ('95','新增和修改页面','2','/manager/core/CoreDictionariesGroupController/coreDictionariesGroupForm','ROLE_CORE_DICTIONARIESGROUP_ADD_EDIT_PAGE',NULL,'1','81'), ('96','新增和修改','2','/manager/core/CoreDictionariesGroupController/saveCoreDictionariesGroupForm','ROLE_CORE_DICTIONARIESGROUP_ADD_EDIT',NULL,'1','81'), ('97','删除','2','/manager/core/CoreDictionariesGroupController/deleteCoreDictionariesGroup','ROLE_CORE_DICTIONARIESGROUP_DEL',NULL,'2','81'), ('98','列表数据1','2','/manager/core/CoreDictionariesGroupController/coreDictionariesGroupListJsonData','ROLE_CORE_DICTIONARIESGROUP_LIST1',NULL,'0','81'), ('99','列表数据2','2','/manager/core/CoreDictionariesGroupController/coreDictionariesGroupListJsonDataNoPage','ROLE_CORE_DICTIONARIESGROUP_LIST2',NULL,'0','81'), ('100','新增和修改页面','2','/manager/core/CoreMenuController/coreMenuForm','ROLE_CORE_MENU_ADD_EDIT_PAGE',NULL,'1','3'), ('101','新增和修改','2','/manager/core/CoreMenuController/saveCoreMenuForm','ROLE_CORE_MENU_ADD_EDIT',NULL,'1','3'), ('102','列表页面','2','/manager/core/CoreMenuController/coreMenuList','ROLE_CORE_MENU_LIST_PAGE',NULL,'0','3'), ('103','删除','2','/manager/core/CoreMenuController/deleteCoreMenu','ROLE_CORE_MENU_DEL',NULL,'2','3'), ('104','列表数据1','2','/manager/core/CoreMenuController/coreMenuListJsonData','ROLE_CORE_MENU_LIST1',NULL,'0','3'), ('105','列表数据2','2','/manager/core/CoreMenuController/coreMenuListJsonDataNoPage','ROLE_CORE_MENU_LIST2',NULL,'0','3'), ('106','列表页面','2','/manager/core/CoreParameterController/coreParameterList','ROLE_CORE_PARAMETER_LIST_PAGE',NULL,'0','80'), ('107','新增和修改页面','2','/manager/core/CoreParameterController/coreParameterForm','ROLE_CORE_PARAMETER_ADD_EDIT_PAGE',NULL,'1','80'), ('108','新增和修改','2','/manager/core/CoreParameterController/saveCoreParameterForm','ROLE_CORE_PARAMETER_ADD_EDIT',NULL,'1','80'), ('109','删除','2','/manager/core/CoreParameterController/deleteCoreParameter','ROLE_CORE_PARAMETER_DEL',NULL,'2','80'), ('110','列表数据','2','/manager/core/CoreParameterController/coreParameterListJsonData','ROLE_CORE_PARAMETER_LIST',NULL,'0','80'), ('111','新增和修改','2','/manager/core/CoreRoleController/saveCoreRoleForm','ROLE_CORE_ROLE_ADD_EDIT',NULL,'1','21'), ('112','列表页面','2','/manager/core/CoreRoleController/coreRoleList','ROLE_CORE_ROLE_LIST_PAGE',NULL,'0','21'), ('113','删除','2','/manager/core/CoreRoleController/deleteCoreRole','ROLE_CORE_ROLE_DEL',NULL,'2','21'), ('114','新增和修改页面','2','/manager/core/CoreRoleController/coreRoleForm','ROLE_CORE_ROLE_ADD_EDIT_PAGE',NULL,'1','21'), ('115','列表数据','2','/manager/core/CoreRoleController/coreRoleListJsonData','ROLE_CORE_ROLE_LIST',NULL,'0','21'), ('116','新增和修改页面','2','/manager/core/CoreUserController/coreUserForm','ROLE_CORE_USER_ADD_EDIT_PAGE',NULL,'1','20'), ('117','列表页面','2','/manager/core/CoreUserController/coreUserList','ROLE_CORE_USER_LIST_PAGE',NULL,'0','20'), ('118','新增和修改','2','/manager/core/CoreUserController/saveCoreUserForm','ROLE_CORE_USER_ADD_EDIT',NULL,'1','20'), ('119','删除','2','/manager/core/CoreUserController/deleteCoreUser','ROLE_CORE_USER_DEL',NULL,'2','20'), ('120','密码重设页面','2','/manager/core/CoreUserController/resetPwd','ROLE_CORE_USER_RESET_PAGE',NULL,'3','20'), ('121','密码重设','2','/manager/core/CoreUserController/saveResetPwd','ROLE_CORE_USER_RESET',NULL,'3','20'), ('122','列表数据','2','/manager/core/CoreUserController/coreUserListJsonData','ROLE_CORE_USER_LIST',NULL,'0','20'), ('123','加载权限','2','/manager/core/CoreMenuController/reLoadPermissions','ROLE_CORE_MENU_LOAD_PERMISSIONS',NULL,'3','3'), ('124','数据源监控','1','/druid/login.html','','fa fa-area-chart','9','2');
INSERT INTO `core_parameter` VALUES ('3','druid的IP白名单','sys','druid_allow','127.0.0.1','','2018-12-05 19:55:58','2018-12-05 19:55:58'), ('4','druid的IP黑名单','sys','druid_deny','','','2018-12-05 19:57:26','2018-12-05 19:57:26'), ('5','druid的管理用户','sys','druid_login_username','druid','','2018-12-05 19:59:17','2018-12-05 19:59:17'), ('6','druid的密码','sys','druid_login_password','123456','','2018-12-05 19:59:48','2018-12-05 19:59:48'), ('7','druid的是否能够重置数据','sys','druid_reset_enable','false','','2018-12-05 20:00:34','2018-12-05 20:00:34'), ('8','druid的过滤规则','sys','druid_url_pattern','/*','','2018-12-05 20:02:05','2018-12-05 20:02:05'), ('9','druid的过滤格式','sys','druid_exclusions','*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,','','2018-12-05 20:02:44','2018-12-05 20:02:44');
INSERT INTO `core_role` VALUES ('1','超级管理员','super_admin','2018-11-08 22:19:54','2018-11-08 22:19:54'), ('3','管理员','admin','2018-11-19 22:49:19','2018-11-19 22:49:19');
INSERT INTO `core_role_menu` VALUES ('0','1'), ('2','1'), ('3','1'), ('19','1'), ('20','1'), ('21','1'), ('80','1'), ('81','1'), ('82','1'), ('83','1'), ('84','1'), ('85','1'), ('86','1'), ('87','1'), ('88','1'), ('89','1'), ('90','1'), ('91','1'), ('92','1'), ('93','1'), ('94','1'), ('95','1'), ('96','1'), ('97','1'), ('98','1'), ('99','1'), ('100','1'), ('101','1'), ('102','1'), ('103','1'), ('104','1'), ('105','1'), ('106','1'), ('107','1'), ('108','1'), ('109','1'), ('110','1'), ('111','1'), ('112','1'), ('113','1'), ('114','1'), ('115','1'), ('116','1'), ('117','1'), ('118','1'), ('119','1'), ('120','1'), ('121','1'), ('122','1'), ('123','1'), ('124','1'), ('0','3'), ('2','3'), ('3','3'), ('19','3'), ('20','3'), ('80','3'), ('81','3'), ('82','3'), ('83','3'), ('84','3'), ('85','3'), ('86','3'), ('87','3'), ('88','3'), ('89','3'), ('93','3'), ('94','3'), ('98','3'), ('99','3'), ('100','3'), ('101','3'), ('102','3'), ('103','3'), ('104','3'), ('105','3'), ('106','3'), ('110','3'), ('116','3'), ('117','3'), ('118','3'), ('119','3'), ('120','3'), ('121','3'), ('122','3');
INSERT INTO `core_user` VALUES ('1','superadmin','$2a$10$NsYa02wGd.j3PiL//tUuquvvaLk/yaVKeRGvrHJUy2COWXp8/9pbG','超级管理员','sex.1','2018-10-31','13322221111','11@163.com','最高权限管理员','1','6','2018-11-10 14:27:38','2018-12-05 22:11:24'), ('2','admin','$2a$10$sq/v0RM/OGx3Dwq/kno4ieTdTFiGIpDjGM8lNkSKPIBMbNJrYd.Ji','管理员','sex.1','2018-10-31','13211112222','111@qq.com','','1','7','2018-11-20 19:02:13','2018-12-05 22:11:52'), ('3','test','$2a$10$8xdUscJYiOiub58fKR.7BONi/0blqOdnxCh46WzrgZXmtGfApQlQu','test','sex.2','2018-10-31','13211112222','11@qq.com',NULL,'1','7','2018-11-20 20:21:14','2018-12-05 22:11:55'), ('9','aaa','$2a$10$u.katU33ODCmLCxVhjo8Zuf.j62QKXccABjY.abnvkQoXL6EjVRGy','tt','sex.1','2018-11-01','11231','11@qq.com','tt','1','7','2018-11-20 20:56:04','2018-12-05 22:11:59'), ('10','1','$2a$10$zqIPEEgdrJd5ROuT17xJLuvPvt6lbrdSkx2PWA6KmezIMx3Bxp9P.','1','sex.1','2018-11-28','1','11@qq.com','','0','6','2018-12-05 22:08:47','2018-12-05 22:08:47');
INSERT INTO `core_user_role` VALUES ('1','1'), ('2','1'), ('3','3'), ('9','3');
INSERT INTO `t_user` VALUES ('1','1','1','1'), ('1000','test','123','13322221111');
