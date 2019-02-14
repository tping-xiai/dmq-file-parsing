package com.jfinteck.dmq.model.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jfinteck.dmq.model.project.dto.MongodbServer;

/**
 * 
 * 操作MongoDB Server 服务表数据
 * 
 * @author weijun.zhu
 * @date 2019年2月14日 上午10:39:54
 * @version 1.0
 */
public interface MongodbServerDao {
	
	MongodbServer findById(Long id);
	
	List<MongodbServer> findAll();

	void insert(@Param("mongo") MongodbServer mongodbServer);
	
	void delete(Long id);
	
}
