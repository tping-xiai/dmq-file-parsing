
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