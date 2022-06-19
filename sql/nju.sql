-- MySQL dump 10.13  Distrib 5.7.38, for Linux (x86_64)
--
-- Host: localhost    Database: ruoyi-vue-pro
-- ------------------------------------------------------
-- Server version	5.7.38-0ubuntu0.18.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `system_company`
--

DROP TABLE IF EXISTS `system_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(200) NOT NULL COMMENT '公司名称',
  `address` varchar(200) NOT NULL COMMENT '公司地址',
  `phone` varchar(200) NOT NULL COMMENT '公司联系方式',
  `code` varchar(8) NOT NULL COMMENT '公司编码，认证公司时使用',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建者',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 COMMENT='公司';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_contract`
--

DROP TABLE IF EXISTS `system_contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_contract` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '合同编号',
  `table4_id` varchar(255) DEFAULT NULL COMMENT '软件委托测试合同ID',
  `table5_id` varchar(255) DEFAULT NULL COMMENT '软件项目委托测试保密协议ID',
  `client_remark` varchar(1024) DEFAULT NULL COMMENT '客户审核合同意见',
  `staff_remark` varchar(1024) DEFAULT NULL COMMENT '市场部人员审核合同意见',
  `url` varchar(1024) DEFAULT NULL COMMENT '实体合同材料url',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新者',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=141 DEFAULT CHARSET=utf8 COMMENT='合同';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_report`
--

DROP TABLE IF EXISTS `system_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_report` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试报告编号',
  `table7_id` varchar(255) DEFAULT NULL COMMENT '软件测试报告ID',
  `table8_id` varchar(255) DEFAULT NULL COMMENT '测试用例（电子记录）ID',
  `table9_id` varchar(255) DEFAULT NULL COMMENT '软件测试记录（电子记录）ID',
  `table11_id` varchar(255) DEFAULT NULL COMMENT '软件测试问题清单（电子记录）ID',
  `testing_dept_manager_id` bigint(20) DEFAULT NULL COMMENT '测试部主管id',
  `manager_remark` varchar(1024) DEFAULT NULL COMMENT '测试部主管审核意见',
  `table10_id` varchar(255) DEFAULT NULL COMMENT '测试报告检查表ID',
  `signatory_remark` varchar(1024) DEFAULT NULL COMMENT '签字人审核意见',
  `signatory_id` bigint(20) DEFAULT NULL COMMENT '签字人id',
  `client_remark` varchar(1024) DEFAULT NULL COMMENT '客户意见',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8 COMMENT='测试报告';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_flow_log`
--

DROP TABLE IF EXISTS `system_flow_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_flow_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `delegation_id` bigint(20) NOT NULL COMMENT '委托的编号',
  `to_state` int(11) NOT NULL COMMENT '现状态',
  `operator_id` bigint(20) NOT NULL COMMENT '操作人编号',
  `operate_time` datetime NOT NULL COMMENT '日志时间',
  `remark` varchar(1023) NOT NULL COMMENT '日志信息',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `map_value` varchar(10240) DEFAULT NULL COMMENT '日志变量',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `from_state` int(11) DEFAULT NULL COMMENT '原状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3946 DEFAULT CHARSET=utf8 COMMENT='流程日志';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_delegation`
--

DROP TABLE IF EXISTS `system_delegation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_delegation` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `launch_time` datetime NOT NULL COMMENT '发起时间',
  `creator_id` bigint(20) DEFAULT NULL COMMENT '发起者编号',
  `name` varchar(255) NOT NULL COMMENT '委托名称',
  `table2_id` varchar(255) DEFAULT NULL COMMENT '软件项目委托测试申请表ID',
  `table3_id` varchar(255) DEFAULT NULL COMMENT '委托测试软件功能列表ID',
  `url` varchar(255) DEFAULT NULL COMMENT '文档材料url',
  `market_dept_staff_id` bigint(20) DEFAULT NULL COMMENT '分配的市场部人员id',
  `testing_dept_staff_id` bigint(20) DEFAULT NULL COMMENT '分配的测试部人员id',
  `market_remark` varchar(1024) DEFAULT NULL COMMENT '市场部人员处理意见',
  `testing_remark` varchar(1024) DEFAULT NULL COMMENT '测试部人员处理意见',
  `table14_id` varchar(1024) DEFAULT NULL COMMENT ' 软件文档评审表ID',
  `offer_id` varchar(255) DEFAULT NULL COMMENT '报价单ID',
  `offer_remark` varchar(1024) DEFAULT NULL COMMENT '用户报价单意见',
  `contract_id` bigint(20) DEFAULT NULL COMMENT '合同id',
  `sample_id` bigint(20) DEFAULT NULL COMMENT '样品id',
  `solution_id` bigint(20) DEFAULT NULL COMMENT '测试方案id',
  `report_id` bigint(20) DEFAULT NULL COMMENT '测试报告id',
  `state` int(11) NOT NULL COMMENT '状态',
  `cancel_remark` varchar(1024) DEFAULT NULL COMMENT '委托取消原因',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `table12_id` varchar(255) DEFAULT NULL COMMENT '软件项目委托测试工作检查表ID',
  `project_id` varchar(255) DEFAULT NULL COMMENT '项目编号',
  PRIMARY KEY (`id`),
  UNIQUE KEY `system_delegation_project_id_uindex` (`project_id`)
) ENGINE=InnoDB AUTO_INCREMENT=332 DEFAULT CHARSET=utf8 COMMENT='委托';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_sample`
--

DROP TABLE IF EXISTS `system_sample`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_sample` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '样品编号',
  `type` varchar(255) DEFAULT NULL COMMENT '样品上传方式，如果在线上传则填写为线上，其余需说明方式的具体信息',
  `process_type` varchar(255) DEFAULT NULL COMMENT '处理方式',
  `url` varchar(1024) DEFAULT NULL COMMENT '如果样品为线上上传，需要填写样品的url',
  `information` varchar(1024) DEFAULT NULL COMMENT '样品信息',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人id，只能为选定的市场部或者测试部两个人中的一个',
  `remark` varchar(1024) DEFAULT NULL COMMENT '审核意见',
  `modify_remark` varchar(1024) DEFAULT NULL COMMENT '修改意见',
  `state` int(11) DEFAULT NULL COMMENT '样品状态 0.未发送 1.已发送 2.已审核 3.待修改 4.已修改 5.已处理 ',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8 COMMENT='样品';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_solution`
--

DROP TABLE IF EXISTS `system_solution`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_solution` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '测试方案编号',
  `table6_id` varchar(255) DEFAULT NULL COMMENT '软件测试方案ID',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '质量部审核人id',
  `table13_id` varchar(255) DEFAULT NULL COMMENT '测试方案评审表ID',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8 COMMENT='测试方案';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_user_company`
--

DROP TABLE IF EXISTS `system_user_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user_company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户编号',
  `company_id` bigint(20) NOT NULL COMMENT '公司编号',
  `creator` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COMMENT='用户和公司关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `system_role_front_menu`
--

DROP TABLE IF EXISTS `system_role_front_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_role_front_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `role_id` bigint(20) DEFAULT NULL COMMENT '角色编号',
  `front_menu_id` bigint(20) DEFAULT NULL COMMENT '菜单编号',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=168 DEFAULT CHARSET=utf8 COMMENT='角色前台菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_role_front_menu`
--

LOCK TABLES `system_role_front_menu` WRITE;
/*!40000 ALTER TABLE `system_role_front_menu` DISABLE KEYS */;
INSERT INTO `system_role_front_menu` VALUES (5,115,3,'1','2022-05-18 22:40:18','1','2022-05-18 22:52:30',_binary ''),(6,122,14,'1','2022-05-26 13:40:33','1','2022-05-26 13:40:33',_binary '\0'),(7,1,48,'1','2022-06-05 08:37:31','1','2022-06-15 08:33:08',_binary ''),(8,1,49,'1','2022-06-05 08:37:39','1','2022-06-05 08:44:49',_binary ''),(9,1,51,'115','2022-06-05 10:49:03','115','2022-06-14 14:22:50',_binary ''),(10,1,49,'115','2022-06-05 11:03:06','115','2022-06-05 11:04:06',_binary ''),(11,1,53,'115','2022-06-14 09:31:43','115','2022-06-16 07:20:53',_binary ''),(12,112,48,'115','2022-06-14 09:32:22','115','2022-06-15 08:33:08',_binary ''),(13,112,49,'115','2022-06-14 09:32:22','115','2022-06-16 07:28:23',_binary ''),(14,112,50,'115','2022-06-14 09:32:22','115','2022-06-14 14:23:18',_binary ''),(15,112,51,'115','2022-06-14 09:32:22','115','2022-06-14 14:22:50',_binary ''),(16,112,52,'115','2022-06-14 09:32:22','115','2022-06-14 09:43:31',_binary ''),(17,112,53,'115','2022-06-14 09:32:22','115','2022-06-14 14:20:58',_binary ''),(18,112,54,'115','2022-06-14 09:32:22','115','2022-06-14 14:20:58',_binary ''),(19,1,49,'115','2022-06-14 14:23:45','115','2022-06-16 07:28:23',_binary ''),(20,1,54,'115','2022-06-14 14:23:45','115','2022-06-16 07:20:56',_binary ''),(21,1,55,'115','2022-06-15 08:32:52','115','2022-06-16 07:28:21',_binary ''),(22,1,59,'115','2022-06-15 08:32:52','115','2022-06-16 07:28:19',_binary ''),(23,1,61,'115','2022-06-15 08:32:52','115','2022-06-16 07:28:15',_binary ''),(24,1,62,'115','2022-06-15 08:32:52','115','2022-06-16 07:28:12',_binary ''),(25,1,64,'115','2022-06-16 02:52:38','115','2022-06-16 07:20:48',_binary ''),(26,1,65,'115','2022-06-16 02:52:38','115','2022-06-16 07:20:46',_binary ''),(27,1,66,'115','2022-06-16 02:52:38','115','2022-06-16 07:28:09',_binary ''),(28,1,77,'115','2022-06-16 07:22:04','115','2022-06-16 07:22:04',_binary '\0'),(29,1,78,'115','2022-06-16 07:23:27','115','2022-06-16 07:23:27',_binary '\0'),(30,1,79,'115','2022-06-16 15:11:09','115','2022-06-16 15:11:09',_binary '\0'),(31,112,80,'115','2022-06-16 15:44:27','115','2022-06-16 15:48:23',_binary ''),(32,112,84,'115','2022-06-16 15:44:27','115','2022-06-16 15:45:48',_binary ''),(33,112,85,'115','2022-06-16 15:44:28','115','2022-06-16 15:45:48',_binary ''),(34,112,86,'115','2022-06-16 15:44:28','115','2022-06-16 15:45:48',_binary ''),(35,112,91,'115','2022-06-16 15:44:28','115','2022-06-16 15:45:48',_binary ''),(36,112,92,'115','2022-06-16 15:44:28','115','2022-06-16 15:45:48',_binary ''),(37,112,93,'115','2022-06-16 15:44:28','115','2022-06-16 15:45:48',_binary ''),(38,112,95,'115','2022-06-16 15:44:28','115','2022-06-16 15:45:48',_binary ''),(39,112,96,'115','2022-06-16 15:48:24','115','2022-06-16 15:48:24',_binary '\0'),(40,112,80,'115','2022-06-16 15:49:13','115','2022-06-16 15:49:13',_binary '\0'),(41,1,80,'115','2022-06-17 04:58:41','115','2022-06-17 04:58:41',_binary '\0'),(42,1,96,'115','2022-06-17 04:58:41','115','2022-06-17 04:58:41',_binary '\0'),(43,1,97,'115','2022-06-17 05:08:24','115','2022-06-17 05:08:24',_binary '\0'),(44,1,98,'115','2022-06-17 05:08:24','115','2022-06-17 05:08:24',_binary '\0'),(45,1,99,'115','2022-06-17 05:08:24','115','2022-06-17 05:08:24',_binary '\0'),(46,1,100,'115','2022-06-17 05:08:24','115','2022-06-17 05:08:24',_binary '\0'),(47,1,101,'115','2022-06-17 05:08:24','115','2022-06-17 05:08:24',_binary '\0'),(48,1,102,'115','2022-06-17 05:08:24','115','2022-06-17 05:08:24',_binary '\0'),(49,1,103,'115','2022-06-17 05:08:24','115','2022-06-17 05:08:24',_binary '\0'),(50,1,104,'115','2022-06-17 05:09:58','115','2022-06-17 05:09:58',_binary '\0'),(51,1,105,'115','2022-06-17 05:09:58','115','2022-06-17 05:09:58',_binary '\0'),(52,1,106,'115','2022-06-17 05:09:58','115','2022-06-17 05:09:58',_binary '\0'),(53,1,107,'115','2022-06-17 05:09:58','115','2022-06-17 05:18:09',_binary ''),(54,1,108,'115','2022-06-17 05:09:58','115','2022-06-17 05:36:17',_binary ''),(55,112,100,'115','2022-06-17 05:16:20','115','2022-06-17 05:16:20',_binary '\0'),(56,112,101,'115','2022-06-17 05:16:20','115','2022-06-17 05:16:20',_binary '\0'),(57,112,102,'115','2022-06-17 05:16:20','115','2022-06-17 05:16:20',_binary '\0'),(58,112,105,'115','2022-06-17 05:16:20','115','2022-06-17 05:16:20',_binary '\0'),(59,112,106,'115','2022-06-17 05:16:20','115','2022-06-17 05:16:20',_binary '\0'),(60,112,108,'115','2022-06-17 05:16:20','115','2022-06-17 05:17:38',_binary ''),(61,112,109,'115','2022-06-17 05:16:20','115','2022-06-17 05:17:38',_binary ''),(62,112,110,'115','2022-06-17 05:16:20','115','2022-06-17 05:17:38',_binary ''),(63,112,111,'115','2022-06-17 05:16:20','115','2022-06-17 05:35:56',_binary ''),(64,112,112,'115','2022-06-17 05:16:20','115','2022-06-17 05:16:20',_binary '\0'),(65,112,114,'115','2022-06-17 05:16:20','115','2022-06-17 05:34:00',_binary ''),(66,112,117,'115','2022-06-17 05:16:20','115','2022-06-17 05:34:00',_binary ''),(67,112,119,'115','2022-06-17 05:16:20','115','2022-06-17 05:34:00',_binary ''),(68,112,122,'115','2022-06-17 05:16:20','115','2022-06-17 05:34:00',_binary ''),(69,112,108,'115','2022-06-17 05:28:58','115','2022-06-17 05:35:56',_binary ''),(70,112,109,'115','2022-06-17 05:28:58','115','2022-06-17 05:34:00',_binary ''),(71,112,110,'115','2022-06-17 05:28:58','115','2022-06-17 05:34:00',_binary ''),(72,112,108,'130','2022-06-17 05:41:27','130','2022-06-17 05:41:27',_binary '\0'),(73,112,111,'130','2022-06-17 05:41:27','130','2022-06-17 05:41:27',_binary '\0'),(74,1,108,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(75,1,109,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(76,1,110,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(77,1,111,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(78,1,112,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(79,1,113,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(80,1,114,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(81,1,115,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(82,1,116,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(83,1,117,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(84,1,118,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(85,1,119,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(86,1,120,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(87,1,121,'115','2022-06-17 06:34:14','115','2022-06-17 08:33:10',_binary ''),(88,1,122,'115','2022-06-17 06:34:14','115','2022-06-17 06:34:14',_binary '\0'),(89,112,109,'130','2022-06-17 06:40:57','130','2022-06-17 06:40:57',_binary '\0'),(90,112,110,'130','2022-06-17 06:40:57','130','2022-06-17 06:40:57',_binary '\0'),(91,112,114,'130','2022-06-17 06:40:57','130','2022-06-17 06:40:57',_binary '\0'),(92,112,117,'130','2022-06-17 06:40:57','130','2022-06-17 06:40:57',_binary '\0'),(93,112,119,'130','2022-06-17 06:40:57','130','2022-06-17 06:40:57',_binary '\0'),(94,112,122,'130','2022-06-17 06:40:57','130','2022-06-17 06:40:57',_binary '\0'),(95,113,80,'115','2022-06-17 06:42:19','115','2022-06-17 06:42:19',_binary '\0'),(96,113,96,'115','2022-06-17 06:42:19','115','2022-06-17 06:42:19',_binary '\0'),(97,113,97,'115','2022-06-17 06:42:19','115','2022-06-17 06:42:19',_binary '\0'),(98,113,119,'115','2022-06-17 06:42:19','115','2022-06-17 06:42:19',_binary '\0'),(99,113,104,'115','2022-06-17 06:42:19','115','2022-06-17 06:42:19',_binary '\0'),(100,113,110,'115','2022-06-17 06:42:19','115','2022-06-17 06:42:19',_binary '\0'),(101,113,114,'115','2022-06-17 06:42:42','115','2022-06-17 06:42:42',_binary '\0'),(102,113,117,'115','2022-06-17 06:42:42','115','2022-06-17 06:42:42',_binary '\0'),(103,113,109,'115','2022-06-17 06:42:42','115','2022-06-17 06:42:42',_binary '\0'),(104,114,96,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(105,114,98,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(106,114,108,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(107,114,109,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(108,114,110,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(109,114,111,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(110,114,80,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(111,114,113,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(112,114,114,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(113,114,115,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(114,114,117,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(115,114,118,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(116,114,120,'115','2022-06-17 06:44:13','115','2022-06-17 06:44:13',_binary '\0'),(117,115,80,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(118,115,96,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(119,115,97,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(120,115,114,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(121,115,117,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(122,115,108,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(123,115,109,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(124,115,110,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(125,115,111,'115','2022-06-17 06:44:59','115','2022-06-17 06:44:59',_binary '\0'),(126,116,96,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(127,116,98,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(128,116,99,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(129,116,101,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(130,116,102,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(131,116,103,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(132,116,105,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(133,116,106,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(134,116,108,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(135,116,109,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(136,116,110,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(137,116,80,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(138,116,113,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(139,116,114,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(140,116,117,'115','2022-06-17 06:46:00','115','2022-06-17 06:46:00',_binary '\0'),(141,116,119,'115','2022-06-17 06:46:00','115','2022-06-17 08:28:07',_binary ''),(142,118,80,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(143,118,96,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(144,118,114,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(145,118,116,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(146,118,117,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(147,118,105,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(148,118,106,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(149,118,108,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(150,118,109,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(151,118,110,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(152,118,111,'115','2022-06-17 06:46:36','115','2022-06-17 06:46:36',_binary '\0'),(153,120,80,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(154,120,114,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(155,120,117,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(156,120,119,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(157,120,105,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(158,120,106,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(159,120,108,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(160,120,109,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(161,120,110,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(162,120,111,'115','2022-06-17 06:47:04','115','2022-06-17 06:47:04',_binary '\0'),(163,1,123,'115','2022-06-17 06:52:33','115','2022-06-17 06:52:33',_binary '\0'),(164,114,105,'115','2022-06-17 07:36:02','115','2022-06-17 07:36:02',_binary '\0'),(165,114,106,'115','2022-06-17 07:39:54','115','2022-06-17 07:39:54',_binary '\0'),(166,116,121,'115','2022-06-17 08:28:08','115','2022-06-17 08:33:10',_binary ''),(167,116,124,'115','2022-06-17 08:33:51','115','2022-06-17 08:33:51',_binary '\0');
/*!40000 ALTER TABLE `system_role_front_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_front_menu`
--

DROP TABLE IF EXISTS `system_front_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_front_menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '编号',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名称',
  `path` varchar(1024) DEFAULT NULL COMMENT '路由路径',
  `hide_in_menu` tinyint(4) DEFAULT NULL COMMENT '是否隐藏（0为否，1为是）',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `creator` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `parent_keys` varchar(1024) DEFAULT NULL COMMENT '关联数组',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8 COMMENT='前台菜单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_front_menu`
--

LOCK TABLES `system_front_menu` WRITE;
/*!40000 ALTER TABLE `system_front_menu` DISABLE KEYS */;
INSERT INTO `system_front_menu` VALUES (48,'登陆','/user/login',1,0,'115','2022-05-29 10:05:55','115','2022-06-15 08:33:08',_binary '',NULL),(49,'委托管理','/delegation',1,0,'115','2022-05-29 10:47:00','115','2022-06-16 07:28:23',_binary '','[]'),(50,'合同管理','/contract',0,0,'115','2022-06-05 08:38:54','115','2022-06-14 14:23:18',_binary '',NULL),(51,'样品管理','/sample',0,0,'115','2022-06-05 08:49:59','115','2022-06-14 14:22:50',_binary '',NULL),(52,'系统管理','/system',1,0,'115','2022-06-14 01:50:47','115','2022-06-14 09:43:31',_binary '',NULL),(53,'系统管理','/system',0,0,'115','2022-06-14 01:51:06','115','2022-06-16 07:20:53',_binary '','[]'),(54,'菜单管理','/menulist',0,0,'115','2022-06-14 01:51:26','115','2022-06-16 07:20:56',_binary '','[\"/system\"]'),(55,'登陆','/user/login',1,0,'115','2022-06-14 06:00:55','115','2022-06-16 07:28:21',_binary '',NULL),(56,'da','da',1,1,'115','2022-06-14 06:51:25','115','2022-06-14 07:24:23',_binary '',NULL),(57,'da','da',1,1,'115','2022-06-14 06:54:02','115','2022-06-14 07:24:22',_binary '',NULL),(58,'da','da',1,1,'115','2022-06-14 06:54:16','115','2022-06-14 07:24:20',_binary '',NULL),(59,'分配委托','/delegation/distribute',0,0,'115','2022-06-14 14:19:45','115','2022-06-16 07:28:19',_binary '',NULL),(60,'审核委托','/delegation/audit',0,1,'115','2022-06-14 14:20:12','115','2022-06-14 14:33:11',_binary '',NULL),(61,'查看委托','/delegation/list',0,0,'115','2022-06-14 14:20:27','115','2022-06-16 07:28:15',_binary '',NULL),(62,'项目管理','/project',0,0,'115','2022-06-14 14:23:14','115','2022-06-16 07:28:12',_binary '',NULL),(63,'用户管理','/userlist',0,1,'115','2022-06-15 08:40:47','115','2022-06-16 07:20:50',_binary '','[\"/system\"]'),(64,'样品管理','/sample',0,0,'115','2022-06-15 08:42:20','115','2022-06-16 07:20:48',_binary '','[\"/project\"]'),(65,'审核委托','/audit',0,0,'115','2022-06-15 08:42:56','115','2022-06-16 07:20:46',_binary '','[\"/delegation\"]'),(66,'生成报价','/delegation/offer/write',0,0,'115','2022-06-15 08:43:12','115','2022-06-16 07:28:09',_binary '','[\"78\"]'),(75,'设这确决办','ad dolor in voluptate ea',31,95,'1','2022-06-16 12:25:29','1','2022-06-16 05:46:22',_binary '','[\"4321423423423\",\"423423432234234\"]'),(76,'县传几式京','ipsum do dolor eiusmod exercitation',55,43,'1','2022-06-16 05:06:45','1','2022-06-16 05:46:25',_binary '','[\"do qui\",\"deserunt exercitation\",\"exercitation magna minim dolor in\"]'),(77,'角色管理','/system/rolelist',0,0,'115','2022-06-16 06:06:52','115','2022-06-16 07:29:45',_binary '\0','[\"78\"]'),(78,'系统管理','/system',0,0,'115','2022-06-16 07:22:47','115','2022-06-16 07:22:47',_binary '\0','null'),(79,'菜单管理','/system/menulist',0,0,'115','2022-06-16 07:29:10','115','2022-06-16 07:29:10',_binary '\0','[\"78\"]'),(80,'委托管理','/delegation',0,0,'115','2022-06-16 15:12:47','115','2022-06-16 15:12:47',_binary '\0','null'),(81,'分配委托','/delegation/distribute',0,0,'115','2022-06-16 15:15:34','115','2022-06-16 15:46:42',_binary '','[\"80\"]'),(82,'审核委托','/delegation/audit',0,0,'115','2022-06-16 15:15:44','115','2022-06-16 15:46:40',_binary '','[\"80\"]'),(83,'生成报价','/delegation/offer/write',0,0,'115','2022-06-16 15:16:03','115','2022-06-16 15:46:37',_binary '','[\"80\"]'),(84,'处理报价','/delegation/offer/read',0,0,'115','2022-06-16 15:16:13','115','2022-06-16 15:46:35',_binary '','[\"80\"]'),(85,'填写合同','/delegation/contract/write',0,0,'115','2022-06-16 15:16:30','115','2022-06-16 15:46:32',_binary '','[\"80\"]'),(86,'审核合同','/delegation/contract/audit',0,0,'115','2022-06-16 15:17:21','115','2022-06-16 15:47:14',_binary '','[\"80\"]'),(87,'上传合同','/delegation/contract/audit',0,0,'115','2022-06-16 15:17:36','115','2022-06-16 15:47:09',_binary '','[\"80\"]'),(88,'填写项目编号','/delegation/fillProjectId',0,0,'115','2022-06-16 15:17:54','115','2022-06-16 15:47:05',_binary '','[\"80\"]'),(89,'项目管理','/project',0,0,'115','2022-06-16 15:18:14','115','2022-06-16 15:47:01',_binary '','[]'),(90,'查看项目','/project/list',0,0,'115','2022-06-16 15:18:49','115','2022-06-16 15:46:58',_binary '','[\"89\"]'),(91,'样品管理','/project/sample',0,0,'115','2022-06-16 15:19:03','115','2022-06-16 15:46:55',_binary '','[\"91\"]'),(92,'查看样品','/project/sample/list',0,0,'115','2022-06-16 15:19:24','115','2022-06-16 15:46:53',_binary '','[\"89\"]'),(93,'上传样品','/project/sample/submit',0,0,'115','2022-06-16 15:20:06','115','2022-06-16 15:46:50',_binary '','[\"91\"]'),(94,'验收样品','/project/sample/audit',0,0,'115','2022-06-16 15:20:24','115','2022-06-16 15:46:48',_binary '','[\"91\"]'),(95,'查看委托','/delegation/list',0,0,'115','2022-06-16 15:43:45','115','2022-06-16 15:46:46',_binary '','[\"80\"]'),(96,'查看委托','/delegation/list',0,0,'115','2022-06-16 15:47:39','115','2022-06-16 15:47:39',_binary '\0','[\"80\"]'),(97,'分配委托','/delegation/distribute',0,0,'115','2022-06-17 05:06:32','115','2022-06-17 05:06:32',_binary '\0','[\"80\"]'),(98,'审核委托','/delegation/audit',0,0,'115','2022-06-17 05:07:01','115','2022-06-17 05:07:01',_binary '\0','[\"80\"]'),(99,'生成报价','/delegation/offer/write',0,0,'115','2022-06-17 05:07:18','115','2022-06-17 05:07:18',_binary '\0','[\"80\"]'),(100,'处理报价','/delegation/offer/audit',0,0,'115','2022-06-17 05:07:31','115','2022-06-17 05:07:31',_binary '\0','[\"80\"]'),(101,'填写合同','/delegation/contract/write',0,0,'115','2022-06-17 05:07:44','115','2022-06-17 05:07:44',_binary '\0','[\"80\"]'),(102,'审核合同','/delegation/contract/audit',0,0,'115','2022-06-17 05:07:55','115','2022-06-17 05:07:55',_binary '\0','[\"80\"]'),(103,'上传合同','/delegation/contract/upload',0,0,'115','2022-06-17 05:08:06','115','2022-06-17 07:54:31',_binary '\0','[\"80\"]'),(104,'填写项目编号','/delegation/fillProjectId',0,0,'115','2022-06-17 05:08:19','115','2022-06-17 05:08:19',_binary '\0','[\"80\"]'),(105,'项目管理','/project',0,0,'115','2022-06-17 05:08:43','115','2022-06-17 05:08:43',_binary '\0','[]'),(106,'查看项目','/project/list',0,0,'115','2022-06-17 05:09:25','115','2022-06-17 05:09:25',_binary '\0','[\"105\"]'),(107,'合同管理','/delegation/contract',0,0,'115','2022-06-17 05:09:32','115','2022-06-17 05:18:09',_binary '','[\"80\"]'),(108,'样品管理','/project/sample',0,0,'115','2022-06-17 05:09:47','115','2022-06-17 05:09:47',_binary '\0','[\"105\"]'),(109,'测试方案管理','/project/solution',0,0,'115','2022-06-17 05:10:08','115','2022-06-17 05:10:08',_binary '\0','[\"105\"]'),(110,'测试报告管理','/project/report',0,0,'115','2022-06-17 05:10:21','115','2022-06-17 05:10:21',_binary '\0','[\"105\"]'),(111,'查看样品','/project/sample/list',0,0,'115','2022-06-17 05:10:48','115','2022-06-17 05:10:48',_binary '\0','[\"108\"]'),(112,'上传样品','/project/sample/submit',0,0,'115','2022-06-17 05:11:12','115','2022-06-17 05:11:12',_binary '\0','[\"108\"]'),(113,'审核样品','/project/sample/audit',0,0,'115','2022-06-17 05:11:30','115','2022-06-17 05:11:30',_binary '\0','[\"108\"]'),(114,'查看测试方案','/project/solution/list',0,0,'115','2022-06-17 05:11:56','115','2022-06-17 05:11:56',_binary '\0','[\"109\"]'),(115,'编写测试方案','/project/solution/write',0,0,'115','2022-06-17 05:12:10','115','2022-06-17 05:12:10',_binary '\0','[\"109\"]'),(116,'审核测试方案','/project/solution/audit',0,0,'115','2022-06-17 05:12:21','115','2022-06-17 05:12:21',_binary '\0','[\"109\"]'),(117,'查看测试报告','/project/report/read',0,0,'115','2022-06-17 05:12:42','115','2022-06-17 05:13:59',_binary '\0','[\"110\"]'),(118,'编写测试文档','/project/report/write',0,0,'115','2022-06-17 05:13:31','115','2022-06-17 05:13:31',_binary '\0','[\"110\"]'),(119,'审核测试报告','/project/report/audit',0,0,'115','2022-06-17 05:14:15','115','2022-06-17 05:14:15',_binary '\0','[\"110\"]'),(120,'归档测试报告','/project/report/archive',0,0,'115','2022-06-17 05:14:33','115','2022-06-17 05:14:33',_binary '\0','[\"110\"]'),(121,'发送测试报告','/project/report/archive',0,0,'115','2022-06-17 05:14:42','115','2022-06-17 08:33:10',_binary '','[\"110\"]'),(122,'接收测试报告','/project/report/receive',0,0,'115','2022-06-17 05:15:07','115','2022-06-17 05:15:07',_binary '\0','[\"110\"]'),(123,'用户管理','/system/userlist',0,0,'115','2022-06-17 06:52:18','115','2022-06-17 06:52:18',_binary '\0','[\"78\"]'),(124,'发送测试报告','/project/report/send',0,0,'115','2022-06-17 08:33:35','115','2022-06-17 08:33:35',_binary '\0','[\"110\"]');
/*!40000 ALTER TABLE `system_front_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_role`
--

DROP TABLE IF EXISTS `system_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `name` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色名称',
  `code` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '角色权限字符串',
  `sort` int(11) NOT NULL COMMENT '显示顺序',
  `data_scope` tinyint(4) NOT NULL DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `data_scope_dept_ids` varchar(500) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '数据范围(指定部门数组)',
  `status` tinyint(4) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `type` tinyint(4) NOT NULL COMMENT '角色类型',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=128 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_role`
--

LOCK TABLES `system_role` WRITE;
/*!40000 ALTER TABLE `system_role` DISABLE KEYS */;
INSERT INTO `system_role` VALUES (1,'超级管理员','super_admin',1,1,'',0,1,'超级管理员','admin','2021-01-05 17:03:48','','2022-02-22 05:08:21',_binary '\0',1),(2,'普通角色','common',2,2,'',0,1,'普通角色','admin','2021-01-05 17:03:48','','2022-02-22 05:08:20',_binary '\0',1),(101,'测试账号','test',0,1,'[]',0,2,'132','','2021-01-06 13:49:35','1','2022-04-01 21:37:13',_binary '\0',1),(109,'租户管理员','tenant_admin',0,1,'',0,1,'系统自动生成','1','2022-02-22 00:56:14','1','2022-02-22 00:56:14',_binary '\0',121),(110,'测试角色','test',0,1,'[]',0,2,'嘿嘿','110','2022-02-23 00:14:34','110','2022-02-23 13:14:58',_binary '\0',121),(111,'租户管理员','tenant_admin',0,1,'',0,1,'系统自动生成','1','2022-03-07 21:37:58','1','2022-03-07 21:37:58',_binary '\0',122),(112,'客户','customer',1,1,'',0,1,NULL,'115','2022-04-29 22:43:34','115','2022-05-31 12:58:17',_binary '\0',0),(113,'测试部主管','test_department_manager',4,1,'',0,1,NULL,'115','2022-04-29 22:52:44','115','2022-05-18 16:38:05',_binary '\0',0),(114,'测试部员工','test_department_staff',5,1,'',0,1,NULL,'115','2022-04-29 22:53:16','115','2022-05-18 16:38:05',_binary '\0',0),(115,'市场部主管','marketing_department_manger',2,1,'',0,1,NULL,'115','2022-04-29 22:54:33','115','2022-05-18 16:38:05',_binary '\0',0),(116,'市场部员工','marketing_department_staff',3,1,'',0,1,NULL,'115','2022-04-29 22:55:45','115','2022-05-18 16:38:05',_binary '\0',0),(117,'质量部主管','quality_department_manger',6,1,'',0,2,NULL,'115','2022-04-29 22:57:21','115','2022-05-12 20:51:59',_binary '',0),(118,'质量部员工','quality_department_staff',7,1,'',0,1,NULL,'115','2022-04-29 22:57:38','115','2022-05-18 16:38:05',_binary '\0',0),(119,'普通用户','normal_user',119,1,'',0,1,'尚未认证的普通用户','1','2022-04-30 23:44:38','1','2022-05-18 16:38:05',_binary '\0',0),(120,'签字人','signatory',120,1,'',0,1,NULL,'115','2022-05-12 20:53:12','115','2022-05-18 16:38:05',_binary '\0',0),(121,'广照资市八入工','testcc',16,1,'',0,2,'amet in nisi aute','1','2022-05-18 20:32:57','1','2022-05-18 21:10:17',_binary '',0),(122,'内置管理员','admin',0,1,'',0,2,NULL,'115','2022-05-18 21:04:27','115','2022-05-27 15:22:45',_binary '',0),(123,'废物','test1',0,1,'',0,2,'11','115','2022-06-14 08:26:02','115','2022-06-14 08:28:31',_binary '',0),(124,'测试1','test1',100,1,'',0,2,NULL,'115','2022-06-15 08:33:23','115','2022-06-15 08:33:47',_binary '',0),(125,'测试2','test2',100,1,'',0,2,'test2','115','2022-06-15 08:33:33','115','2022-06-15 08:33:49',_binary '',0),(126,'test1','test1',100,1,'',0,2,NULL,'115','2022-06-15 10:33:43','115','2022-06-17 06:40:17',_binary '',0),(127,'test2','test2',100,1,'',0,2,NULL,'115','2022-06-15 10:33:50','115','2022-06-15 11:16:59',_binary '',0);
/*!40000 ALTER TABLE `system_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user`
--

DROP TABLE IF EXISTS `system_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户账号',
  `password` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '密码',
  `nickname` varchar(30) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户昵称',
  `remark` varchar(500) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '备注',
  `dept_id` bigint(20) DEFAULT NULL COMMENT '部门ID',
  `post_ids` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '岗位编号数组',
  `email` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '用户邮箱',
  `mobile` varchar(11) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '手机号码',
  `sex` tinyint(4) DEFAULT '0' COMMENT '用户性别',
  `avatar` varchar(100) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '头像地址',
  `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `login_ip` varchar(50) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE KEY `idx_username` (`username`,`update_time`,`tenant_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=135 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user`
--

LOCK TABLES `system_user` WRITE;
/*!40000 ALTER TABLE `system_user` DISABLE KEYS */;
INSERT INTO `system_user` VALUES (1,'admin','$2a$10$0acJOIk2D25/oC87nyclE..0lzeu9DtQ/n3geP4fkun/zIVRhHJIO','芋道源码','管理员',103,'[1]','c.kfrcrnsxbj@qq.com','15612345678',1,'http://test.yudao.iocoder.cn/36929804-1396-4ddd-be30-13eb8ab3c38b',0,'10.0.255.254','2022-06-18 06:16:05','admin','2021-01-05 17:03:47',NULL,'2022-06-18 06:16:05',_binary '\0',1),(100,'yudao','$2a$10$11U48RhyJ5pSBYWSn12AD./ld671.ycSzJHbyrtpeoMeYiw31eo8a','芋道','不要吓我',104,'[1]','yudao@iocoder.cn','15601691300',1,'',1,'',NULL,'','2021-01-07 09:07:17','104','2021-12-16 09:26:10',_binary '\0',1),(103,'yuanma','$2a$10$wWoPT7sqriM2O1YXRL.je.GiL538OR6ZTN8aQZr9JAGdnpCH2tpYe','源码',NULL,106,NULL,'yuanma@iocoder.cn','15601701300',0,'',0,'127.0.0.1','2022-01-18 00:33:40','','2021-01-13 23:50:35',NULL,'2022-01-18 00:33:40',_binary '\0',1),(104,'test','$2a$10$e5RpuDCC0GYSt0Hvd2.CjujIXwgGct4SnXi6dVGxdgFsnqgEryk5a','测试号',NULL,107,'[]','111@qq.com','15601691200',1,'',0,'127.0.0.1','2022-03-19 21:46:19','','2021-01-21 02:13:53',NULL,'2022-03-19 21:46:19',_binary '\0',1),(107,'admin107','$2a$10$dYOOBKMO93v/.ReCqzyFg.o67Tqk.bbc2bhrpyBGkIw9aypCtr2pm','芋艿',NULL,NULL,NULL,'','15601691300',0,'',0,'',NULL,'1','2022-02-20 22:59:33','1','2022-02-27 08:26:51',_binary '\0',118),(108,'admin108','$2a$10$y6mfvKoNYL1GXWak8nYwVOH.kCWqjactkzdoIDgiKl93WN3Ejg.Lu','芋艿',NULL,NULL,NULL,'','15601691300',0,'',0,'',NULL,'1','2022-02-20 23:00:50','1','2022-02-27 08:26:53',_binary '\0',119),(109,'admin109','$2a$10$JAqvH0tEc0I7dfDVBI7zyuB4E3j.uH6daIjV53.vUS6PknFkDJkuK','芋艿',NULL,NULL,NULL,'','15601691300',0,'',0,'',NULL,'1','2022-02-20 23:11:50','1','2022-02-27 08:26:56',_binary '\0',120),(110,'admin110','$2a$10$qYxoXs0ogPHgYllyEneYde9xcCW5hZgukrxeXZ9lmLhKse8TK6IwW','小王',NULL,NULL,NULL,'','15601691300',0,'',0,'127.0.0.1','2022-02-23 19:36:28','1','2022-02-22 00:56:14',NULL,'2022-02-27 08:26:59',_binary '\0',121),(111,'test','$2a$10$mExveopHUx9Q4QiLtAzhDeH3n4/QlNLzEsM4AqgxKrU.ciUZDXZCy','测试用户',NULL,NULL,'[]','','',0,'',0,'',NULL,'110','2022-02-23 13:14:33','110','2022-02-23 13:14:33',_binary '\0',121),(112,'newobject','$2a$10$jh5MsR.ud/gKe3mVeUp5t.nEXGDSmHyv5OYjWQwHO8wlGmMSI9Twy','新对象',NULL,NULL,'[]','','',0,'',0,'',NULL,'1','2022-02-23 19:08:03','1','2022-02-23 19:08:03',_binary '\0',1),(113,'aoteman','$2a$10$0acJOIk2D25/oC87nyclE..0lzeu9DtQ/n3geP4fkun/zIVRhHJIO','芋道',NULL,NULL,NULL,'','15601691300',0,'',0,'127.0.0.1','2022-03-19 18:38:51','1','2022-03-07 21:37:58',NULL,'2022-03-19 18:38:51',_binary '\0',122),(114,'hrmgr','$2a$10$TR4eybBioGRhBmDBWkqWLO6NIh3mzYa8KBKDDB5woiGYFVlRAi.fu','hr 小姐姐',NULL,NULL,'[3]','','',0,'',0,'127.0.0.1','2022-05-09 19:23:14','1','2022-03-19 21:50:58',NULL,'2022-05-09 19:23:14',_binary '\0',1),(115,'mytest1','$2a$10$98UTGTQ6e65nuZr4QqIH6eOkolaNaRHJqt.JN2hYGY800F7a8ZLUe','mytest11',NULL,111,'[]','1348288404@qq.com','18534914155',1,'http://test.yudao.iocoder.cn/497cf52f-9a26-4cea-8118-4cbbadf63e15',0,'10.0.255.254','2022-06-17 11:34:38','1','2022-04-29 20:04:10','115','2022-06-17 14:32:41',_binary '\0',0),(116,'azdka','$2a$10$k5iv8.O98ErnwKbOq7Rss.TT0pBRo35EaJdcba8MztXM/ssC2Ug.K','邹艳',NULL,NULL,NULL,'','18534914156',0,'',0,'127.0.0.1','2022-05-03 23:07:21',NULL,'2022-04-30 18:38:18',NULL,'2022-05-03 23:07:21',_binary '\0',0),(117,'sdsaderew','$2a$10$GI322cTf3wGprPxAsqOmF.jHD1r2ZtlEwEP7G8RhcjfcG5aS5DvcC','潘勇',NULL,NULL,NULL,'','12345678912',0,'',0,'127.0.0.1','2022-05-03 22:23:03',NULL,'2022-05-03 22:23:03',NULL,'2022-05-03 22:23:03',_binary '\0',0),(119,'solitarynew1','$2a$10$hJuJ4P0xMZeRj3yh8nToT.tjpiGl1GfbA7wtH54QOnX8LAsVJdOgq','龚丽',NULL,NULL,NULL,'','12345678913',0,'',0,'127.0.0.1','2022-05-04 20:07:16',NULL,'2022-05-04 20:00:21',NULL,'2022-05-04 20:07:16',_binary '\0',0),(120,'asdasdas1','$2a$10$uoR0QC.ozkC8p1ixAJSTdOrN5l9JDfZIfHoyi.tz7BlCJwglQePlK','市场部主管1',NULL,NULL,'[]','','',0,'',0,'127.0.0.1','2022-06-12 21:10:08','115','2022-05-10 14:58:59',NULL,'2022-06-12 21:10:08',_binary '\0',0),(121,'atest1','$2a$10$FDbj.Ef97JDDmeN5Z0eAb.D4dqtBnq1iq1243DyjNoKoaezq4Ikly','市场部员工',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-18 06:16:02','1','2022-05-12 11:58:30',NULL,'2022-06-18 06:16:02',_binary '\0',0),(122,'q1w3','$2a$10$n4cT/2n4cswPZWj/1pJfquFxJ200hTRIC4NBo3Fa.lET3IVSSNPl6','测试部主管',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-15 00:06:02','1','2022-05-12 13:57:26',NULL,'2022-06-15 00:06:02',_binary '\0',0),(123,'ly25','$2a$10$AWQhLjwnUrQBN4mnd2yDGutVuM.YG8/noGzwHD1eGejFCfGzEucte','测试部员工',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-05-30 11:14:44','1','2022-05-12 20:49:05',NULL,'2022-05-30 11:14:44',_binary '\0',0),(124,'222e','$2a$10$xL97aaKRwWTH41HZeAn3Z.thoS5PgTBdl7kE40fEY5Zr1qDyf/Gea','质量部员工',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-02 08:18:45','1','2022-05-12 20:55:50',NULL,'2022-06-02 08:18:45',_binary '\0',0),(125,'33ee','$2a$10$uwZvINM.bGrAr/PWOFBMF.fA7Q2ggH3fLh0lAeAQ1tCcJQE3eyL/e','签字人',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-17 10:57:16','1','2022-05-12 20:56:17',NULL,'2022-06-17 10:57:16',_binary '\0',0),(126,'22re','$2a$10$qX8pRjzgK4wRIjmwuPrL0.BER85YkIjSXUhEPcuMbO0ZUsPXmI3Xe','我是用户and客户',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-05-29 06:47:27','1','2022-05-12 20:56:47',NULL,'2022-05-29 06:47:27',_binary '\0',0),(127,'19953102403','$2a$10$vpJLMXPly6bM/0b4YzFs0u6NGzRe8F4oRCbP35jx6U7iV3tzo/w.W','liam',NULL,NULL,NULL,'','19953102403',0,'',0,'10.0.255.254','2022-06-13 11:28:09',NULL,'2022-05-13 17:00:44',NULL,'2022-06-13 11:28:09',_binary '\0',0),(128,'8qFVIajxhVxKrW','$2a$10$rdvTVa/A8w6ZZmgjtm9f..1VLq3FMG.69q9A3MF3CZnF8Wvag4One','唐伟','dolore',NULL,NULL,'r.fuesolve@qq.com','18131689827',0,'http://dummyimage.com/100x100',1,'',NULL,'1','2022-05-18 20:18:00','1','2022-05-18 20:25:40',_binary '',0),(129,'kehu2','$2a$10$4mDiwYHW50rs.x49lJn/nOjGaBjjAoC1tkUqJGgr7a0Y9Y2em7OIK','客户2',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-11 05:46:16','1','2022-05-21 14:35:39',NULL,'2022-06-11 05:46:16',_binary '\0',0),(130,'kehu3','$2a$10$kPDWTtCapjXF7Yauuh4B1uD/SYnBs4HhQWQ9NVjruK3xLGfHyin.C','客户3',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-18 04:08:13','1','2022-05-21 14:35:51',NULL,'2022-06-18 04:08:13',_binary '\0',0),(131,'shichangbu2','$2a$10$UB2sQIOPREVEhkfGizFQKuPZ/JqF95MgV5vXiCaswsP/NZcup3Fsm','市场部员工2',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-17 10:29:46','1','2022-05-21 14:36:22',NULL,'2022-06-17 10:29:46',_binary '\0',0),(132,'ceshibu2','$2a$10$Esw4C1H2Q1n1FqeKnXb13umAaiZ5UnrGU.6AV/RrXq1EhgGJQmNnK','测试部员工2',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-17 10:31:12','1','2022-05-21 14:36:45',NULL,'2022-06-17 10:31:12',_binary '\0',0),(133,'zhiliangbu2','$2a$10$yyeakVgsYqKvJK/2EUTc2OGzLcF00i8c7ul/YBsXPWoPU.wnUZloq','质量部员工2',NULL,NULL,'[]','','',0,'',0,'10.0.255.254','2022-06-17 10:45:21','1','2022-05-21 14:37:10',NULL,'2022-06-17 10:45:21',_binary '\0',0),(134,'sadfsdfsd','$2a$10$dsUWifP8AYKEVaJCQtaT7elN96VW0oF9L52405HdxYDFmwl.LjYfu','3fdsfsd','34534543543534',110,'[2,3]','1348288445@qq.com','18534914158',1,'',0,'',NULL,'115','2022-06-16 11:38:50','115','2022-06-16 03:39:31',_binary '',0);
/*!40000 ALTER TABLE `system_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `system_user_role`
--

DROP TABLE IF EXISTS `system_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `system_user_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增编号',
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `creator` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `updater` varchar(64) COLLATE utf8mb4_unicode_ci DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `deleted` bit(1) DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint(20) NOT NULL DEFAULT '0' COMMENT '租户编号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `system_user_role`
--

LOCK TABLES `system_user_role` WRITE;
/*!40000 ALTER TABLE `system_user_role` DISABLE KEYS */;
INSERT INTO `system_user_role` VALUES (1,1,1,'',NULL,'',NULL,_binary '',1),(2,2,2,'',NULL,'',NULL,_binary '\0',1),(3,100,1,'',NULL,'',NULL,_binary '',1),(4,100,101,'',NULL,'',NULL,_binary '\0',1),(5,100,1,'',NULL,'',NULL,_binary '\0',1),(6,100,2,'',NULL,'',NULL,_binary '\0',1),(7,104,101,'',NULL,'',NULL,_binary '',1),(9,105,1,'1','2021-10-30 13:40:48','1','2021-10-30 13:40:48',_binary '',1),(10,103,1,'1','2022-01-11 13:19:45','1','2022-01-11 13:19:45',_binary '\0',1),(11,107,106,'1','2022-02-20 22:59:33','1','2022-02-20 22:59:33',_binary '',118),(12,108,107,'1','2022-02-20 23:00:50','1','2022-02-20 23:00:50',_binary '',119),(13,109,108,'1','2022-02-20 23:11:50','1','2022-02-20 23:11:50',_binary '',120),(14,110,109,'1','2022-02-22 00:56:14','1','2022-02-22 00:56:14',_binary '',121),(15,111,110,'110','2022-02-23 13:14:38','110','2022-02-23 13:14:38',_binary '\0',121),(16,113,111,'1','2022-03-07 21:37:58','1','2022-03-07 21:37:58',_binary '\0',122),(17,114,101,'1','2022-03-19 21:51:13','1','2022-03-19 21:51:13',_binary '\0',1),(18,112,1,'1','2022-04-29 20:04:29','1','2022-04-29 20:04:29',_binary '\0',0),(19,115,1,'1','2022-04-29 20:04:53','1','2022-04-29 20:04:53',_binary '\0',0),(20,116,112,'115','2022-04-30 19:12:32','115','2022-04-30 19:12:32',_binary '\0',0),(21,117,119,'115','2022-05-04 19:47:45','115','2022-05-04 19:47:45',_binary '\0',0),(22,119,119,NULL,'2022-05-04 20:00:21',NULL,'2022-05-04 20:00:21',_binary '\0',0),(23,1,112,'1','2022-05-04 20:03:17','1','2022-05-04 20:03:17',_binary '',0),(24,1,119,'1','2022-05-04 20:03:17','1','2022-05-04 20:03:17',_binary '',0),(25,119,112,'119','2022-05-04 20:07:17','119','2022-05-04 20:07:17',_binary '\0',0),(26,1,1,'115','2022-05-08 15:15:05','115','2022-05-08 15:15:05',_binary '\0',0),(27,120,115,'115','2022-05-10 14:59:07','115','2022-05-10 14:59:07',_binary '\0',0),(28,1,113,'115','2022-05-10 15:01:01','115','2022-05-10 15:01:01',_binary '',0),(29,1,114,'115','2022-05-10 15:01:01','115','2022-05-10 15:01:01',_binary '',0),(30,1,115,'115','2022-05-10 15:01:01','115','2022-05-10 15:01:01',_binary '',0),(31,1,116,'115','2022-05-10 15:01:01','115','2022-05-10 15:01:01',_binary '',0),(32,1,117,'115','2022-05-10 15:01:01','115','2022-05-10 15:01:01',_binary '',0),(33,1,118,'115','2022-05-10 15:01:01','115','2022-05-10 15:01:01',_binary '',0),(34,115,112,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '\0',0),(35,115,113,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '\0',0),(36,115,114,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '\0',0),(37,115,115,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '\0',0),(38,115,116,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '\0',0),(39,115,117,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '',0),(40,115,118,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '\0',0),(41,115,119,'1','2022-05-10 16:11:27','1','2022-05-10 16:11:27',_binary '\0',0),(42,121,116,'1','2022-05-12 11:58:52','1','2022-05-12 11:58:52',_binary '\0',0),(43,121,115,'1','2022-05-12 11:58:52','1','2022-05-12 11:58:52',_binary '',0),(44,120,116,'1','2022-05-12 11:58:57','1','2022-05-12 11:58:57',_binary '\0',0),(45,122,117,'1','2022-05-12 13:57:35','1','2022-05-12 13:57:35',_binary '',0),(46,122,118,'1','2022-05-12 13:57:35','1','2022-05-12 13:57:35',_binary '',0),(47,122,118,'1','2022-05-12 20:49:37','1','2022-05-12 20:49:37',_binary '',0),(48,123,113,'1','2022-05-12 20:51:13','1','2022-05-12 20:51:13',_binary '\0',0),(49,122,113,'1','2022-05-12 20:54:02','1','2022-05-12 20:54:02',_binary '\0',0),(50,122,114,'1','2022-05-12 20:54:02','1','2022-05-12 20:54:02',_binary '\0',0),(51,124,116,'1','2022-05-12 20:56:02','1','2022-05-12 20:56:02',_binary '',0),(52,125,120,'1','2022-05-12 20:56:25','1','2022-05-12 20:56:25',_binary '\0',0),(53,126,119,'1','2022-05-12 20:56:56','1','2022-05-12 20:56:56',_binary '\0',0),(54,126,112,'115','2022-05-13 12:55:12','115','2022-05-13 12:55:12',_binary '\0',0),(55,127,119,NULL,'2022-05-13 17:00:44',NULL,'2022-05-13 17:00:44',_binary '\0',0),(56,129,112,'1','2022-05-21 14:37:27','1','2022-05-21 14:37:27',_binary '\0',0),(57,129,119,'1','2022-05-21 14:37:27','1','2022-05-21 14:37:27',_binary '\0',0),(58,130,112,'1','2022-05-21 14:37:34','1','2022-05-21 14:37:34',_binary '\0',0),(59,130,119,'1','2022-05-21 14:37:34','1','2022-05-21 14:37:34',_binary '\0',0),(60,131,116,'1','2022-05-21 14:37:41','1','2022-05-21 14:37:41',_binary '\0',0),(61,132,114,'1','2022-05-21 14:37:50','1','2022-05-21 14:37:50',_binary '\0',0),(62,133,118,'1','2022-05-21 14:37:57','1','2022-05-21 14:37:57',_binary '\0',0),(63,124,118,'115','2022-05-27 23:06:47','115','2022-05-27 23:06:47',_binary '\0',0),(64,115,120,'1','2022-05-31 04:40:38','1','2022-05-31 04:40:38',_binary '\0',0),(65,103,112,'115','2022-06-16 04:33:49','115','2022-06-16 04:33:49',_binary '\0',0),(66,104,115,'115','2022-06-16 04:34:03','115','2022-06-16 04:34:03',_binary '\0',0),(67,107,1,'115','2022-06-16 04:34:40','115','2022-06-16 04:34:40',_binary '\0',0),(68,1,112,'115','2022-06-17 14:14:00','115','2022-06-17 14:14:00',_binary '',0),(69,110,113,'115','2022-06-17 14:14:16','115','2022-06-17 14:14:16',_binary '\0',0),(70,110,115,'115','2022-06-17 14:14:16','115','2022-06-17 14:14:16',_binary '\0',0);
/*!40000 ALTER TABLE `system_user_role` ENABLE KEYS */;
UNLOCK TABLES;

/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-06-18  6:11:56
