package com.jfinteck.dmq.vo;

import com.jfinteck.dmq.core.base.BaseVo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MongodbServerVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	// 数据库名称
	private String database;
	
	// 集合名称
	private String ns;

}
