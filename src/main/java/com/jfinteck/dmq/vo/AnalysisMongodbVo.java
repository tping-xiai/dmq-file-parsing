package com.jfinteck.dmq.vo;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class AnalysisMongodbVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	// 服务编号
	@NotNull
	private Long serverId;
	// 数据库名称
	@NotEmpty
	private String databaseName;
	// 集合名称
	private String collName;
	// 过滤条件
	private String filter;
}
