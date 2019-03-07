package com.jfinteck.dmq.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbTableName;

public interface MongodbTableNameMapper extends BaseMapper<MongodbTableName>{

	List<BaseDTO> selectTableByServerIdAndDatabaseName(@Param("serverId")Long serverId, @Param("databaseName")String databaseName);
	
	/**
	 * 表是否解析状态值
	 * 
	 * @param serverId
	 * @param databaseName
	 * @param isAnalysis
	 */
	void updateTableIsAnalysis(MongodbTableName tableName);
	
}
