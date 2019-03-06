package com.jfinteck.dmq.service;

import java.util.List;

import com.jfinteck.dmq.dto.BaseDTO;

public interface IAnalysisMongodbService {

	/**
	 * 获取指定数据库中所有表名称
	 * 
	 * @param id
	 * @param database
	 * @return
	 */
	List<BaseDTO> getDatabaseTables(Long id, String database);
}
