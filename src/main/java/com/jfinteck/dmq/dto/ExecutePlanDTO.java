package com.jfinteck.dmq.dto;

import java.util.List;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ExecutePlanDTO {

	private Long id;
	private String executeId;
	private Long serverId;
	private Long collId;
	private String tableName;
	private String flag;
	
	private MongodbServer mongoServer;
	
	private MongodbTableName mongoTable;
	
	private List<MongodbFieldName> mongoField;
	
}
