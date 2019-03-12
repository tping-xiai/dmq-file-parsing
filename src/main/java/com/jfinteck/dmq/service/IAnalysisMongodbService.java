package com.jfinteck.dmq.service;

import java.util.List;

import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbFieldName;
import com.jfinteck.dmq.dto.MongodbTableName;
import com.jfinteck.dmq.vo.AnalysisMongodbVo;

public interface IAnalysisMongodbService {

	/**
	 * 获取指定数据库中所有表名称
	 * 
	 * @param id
	 * @param database
	 * @return
	 */
	List<BaseDTO> getDatabaseTables(Long id, String database);

	/**
	  * 获取集合字段名称
	 * 
	 * @param tableName
	 * @return
	 */
	List<String> getConllectonNames(MongodbTableName tableName);

	/**
	  * 记录要解析集合字段名称
	 * 
	 * @param analysisVo
	 * @return 集合编号ID
	 */
	Long saveAnalysisColletionName(AnalysisMongodbVo analysisVo);

	/**
	  * 修改表字段属性值
	 * 
	 * @param fieldProperty
	 */
	void updateFieldProperty(List<MongodbFieldName> fieldProperty);

	/**
	 * 拼接SQL语句创建表
	 * @param id
	 * @param collName 
	 */
	String createAnalysisResultTable(Long id, Long serverId, String collName);

}
