package com.jfinteck.dmq.vo;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.jfinteck.dmq.dto.MongodbFieldName;

import lombok.Data;

@Data
public class AnalysisMongodbVo implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * 唯一编号
	 */
	private Long id;
	// 服务编号
	@NotNull
	private Long serverId;
	// 数据库名称
	@NotEmpty
	private String databaseName;
	// 集合名称
	private String collName;
	// 过滤条件
	private String filterCondit;
	// 字段名称
	private String field;
	
	
	private List<MongodbFieldName> fieldProperty;
}
