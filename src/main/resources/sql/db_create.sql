
-- 创建数据库
CREATE DATABASE db_mongo;

use db_mongo;

SET FOREIGN_KEY_CHECKS=0;

-- 创建表
DROP TABLE IF EXISTS `manager_project`;
CREATE TABLE `manager_project` (
  `id`				bigint(20) unsigned		NOT NULL AUTO_INCREMENT		COMMENT '项目id',
  `project_name`	varchar(50)				NOT NULL	DEFAULT ''		COMMENT '项目名',
  `project_remark`	varchar(2000) 						DEFAULT ''		COMMENT '项目描述',
  `in_explain`		varchar(2000) 						DEFAULT ''		COMMENT '接口说明',
  `operator`		varchar(50) 			NOT NULL	DEFAULT ''		COMMENT '创建人',
  `state`			tinyint(3) unsigned		NOT NULL	DEFAULT '0'		COMMENT '0-启用 1-禁用',
  `create_time`		datetime				NOT NULL	DEFAULT  NOW()				COMMENT '创建时间',
  `update_time`		datetime							DEFAULT NOW()	COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='项目信息表';

-- 连接MongoDB的服务表
DROP TABLE IF EXISTS `tb_mongodb_server`;
CREATE TABLE `tb_mongodb_server` (
  `id`   int NOT NULL AUTO_INCREMENT COMMENT '编号',
  `host` varchar(30) COLLATE utf8_unicode_ci NOT NULL COMMENT '主机地址',
  `port` varchar(10) COLLATE utf8_unicode_ci NOT NULL COMMENT '端口',
  `username` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(30) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '密码',
  `tags` varchar(50) COLLATE utf8_unicode_ci DEFAULT NULL COMMENT '标签',
  `flag` varchar(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '1-可用,0-不可用',
  `create_time`  datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time`  datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='连接MongoDB的服务表';

-- 测试数据
-- INSERT INTO tb_mongodb_server (host, port, username, password, tags) VALUES('localhost', '27017', null, null, '测试监控MongoDB服务');

-- 需要监控数据库表
DROP TABLE IF EXISTS `tb_mongodb_sync_table`;
CREATE TABLE `tb_mongodb_sync_table` (
   `id`  int NOT NULL AUTO_INCREMENT COMMENT '编号',
   `server_id` int NOT NULL COMMENT 'mongoDB Server 编号',
   `table_name`  varchar(50) COLLATE utf8_unicode_ci NOT NULL COMMENT '表名称',
   `prefix_name` varchar(20) COLLATE utf8_unicode_ci NOT NULL COMMENT '前缀名称',
   `flag`  varchar(1) COLLATE utf8_unicode_ci DEFAULT '1' COMMENT '1-可用,0-不可用',
   `create_time`  datetime DEFAULT NOW() COMMENT '创建日期',
   `update_time`  datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期',
   PRIMARY KEY (`id`)
)ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='监控数据库表';

-- mongodb执行详情
DROP TABLE IF EXISTS `tb_mongodb_top_history`;
CREATE TABLE `tb_mongodb_top_history` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `server_id` bigint(20) DEFAULT NULL COMMENT 'mongodb服务器id',
  `ns` varchar(255) DEFAULT NULL COMMENT '名称空间',
  `total_count` bigint(11) DEFAULT NULL COMMENT '总条数',
  `total_time` bigint(11) DEFAULT NULL COMMENT '执行使用总时间',
  `total_count_persecond` varchar(11) DEFAULT NULL COMMENT '每秒执行总条数',
  `write_lock_count` bigint(11) DEFAULT NULL,
  `write_lock_time` bigint(11) DEFAULT NULL,
  `write_lock_count_persecond` varchar(11) DEFAULT NULL,
  `read_lock_count` bigint(11) DEFAULT NULL,
  `read_lock_time` bigint(11) DEFAULT NULL,
  `read_lock_count_persecond` varchar(11) DEFAULT NULL,
  `insert_count` bigint(11) DEFAULT NULL COMMENT '插入条数',
  `insert_time` bigint(11) DEFAULT NULL COMMENT '插入使用时间',
  `insert_count_persecond` varchar(11) DEFAULT NULL COMMENT '每秒插入条数',
  `update_count` bigint(11) DEFAULT NULL COMMENT '更新条数',
  `update_time` bigint(11) DEFAULT NULL COMMENT '更新使用时间',
  `update_count_persecond` varchar(11) DEFAULT NULL COMMENT '每秒更新条数',
  `get_more_count` bigint(11) DEFAULT NULL,
  `get_more_time` bigint(11) DEFAULT NULL,
  `get_more_count_persecond` varchar(11) DEFAULT NULL,
  `queries_count` bigint(11) DEFAULT NULL COMMENT '查询条数',
  `queries_time` bigint(11) DEFAULT NULL COMMENT '查询使用时间',
  `queries_count_persecond` varchar(11) DEFAULT NULL COMMENT '每秒查询条数',
  `remove_count` bigint(11) DEFAULT NULL COMMENT '删除条数',
  `remove_time` bigint(11) DEFAULT NULL COMMENT '删除使用时间',
  `remove_count_persecond` varchar(11) DEFAULT NULL COMMENT '每秒删除条数',
  `commands_count` bigint(11) DEFAULT NULL,
  `commands_time` bigint(11) DEFAULT NULL,
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10295132 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='mongodb执行详情';