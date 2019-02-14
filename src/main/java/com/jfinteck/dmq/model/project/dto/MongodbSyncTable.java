package com.jfinteck.dmq.model.project.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MongodbSyncTable implements Serializable{

	private static final long serialVersionUID = 1L;

	// 编号
	private Long id;
	// 服务编号
	private Long serverId;
	// 监控表名称
	private String tableName;
	// 监控表前缀名称
	private String prefixName;
	// 标识1-可用，0-不可用
	private String flag;
	
}
