package com.jfinteck.dmq.service;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.jfinteck.dmq.dto.MongodbListDatabasesDTO;
import com.jfinteck.dmq.dto.MongodbOverviewDTO;
import com.jfinteck.dmq.dto.MongodbServer;
import com.jfinteck.dmq.dto.MongodbServerDTO;

public interface IMongodbServerService extends IService<MongodbServer>{

	Page<MongodbServerDTO> findMongodbServerList(Page<MongodbServerDTO> page);

	/**
	 * 获取MongoDB概况信息
	 * 
	 * @param id
	 * @return
	 */
	MongodbOverviewDTO getMongodbOverview(Long id);

	/**
	 * 获取MongoDB节点数据库列表数据
	 * 
	 * @param id
	 * @return
	 */
	List<MongodbListDatabasesDTO> getMongodbListDatabases(Long id);

}
