package com.jfinteck.dmq.model.project.dao;

import java.util.List;

import com.jfinteck.dmq.model.project.dto.MongodbSyncTable;

/**
 * 监控数据表操作DAO
 * 
 * @author weijun.zhu
 * @date 2019年2月14日 下午3:34:55
 * @version 1.0
 */
public interface MongodbSyncTableDao {

	void insert(MongodbSyncTable mongodbSyncTable);
	
	void delete(Long id);
	
	void update(MongodbSyncTable mongodbSyncTable);
	
	/**
	 * 根据编号查询数据
	 * 
	 * @param id
	 * @return
	 */
	MongodbSyncTable findById(Long id);
	
	/**
	 * 根据服务编号ID查询要监控的表名称
	 * 
	 * @param serverId
	 * @return
	 */
	List<MongodbSyncTable> findByServerId(Long serverId);
}
