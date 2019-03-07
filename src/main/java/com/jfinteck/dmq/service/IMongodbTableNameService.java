package com.jfinteck.dmq.service;

import java.util.List;

import com.baomidou.mybatisplus.service.IService;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbTableName;

public interface IMongodbTableNameService extends IService<MongodbTableName>{

	List<BaseDTO> findTableByServerIdAndDatabaseName(Long serverId, String databaseName);
	
	/**
	 * 修改表解析状态值
	 * 
	 * @param tableName
	 */
	void updateTableIsAnalysis(MongodbTableName tableName);
}
