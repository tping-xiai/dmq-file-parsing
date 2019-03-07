package com.jfinteck.dmq.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbTableName;
import com.jfinteck.dmq.mapper.MongodbTableNameMapper;
import com.jfinteck.dmq.service.IMongodbTableNameService;

@Service
public class MongodbTableNameServiceImpl extends ServiceImpl<MongodbTableNameMapper, MongodbTableName> implements IMongodbTableNameService {

	@Autowired
	private MongodbTableNameMapper mongodbTableNameMapper;
	
	@Override
	public List<BaseDTO> findTableByServerIdAndDatabaseName(Long serverId, String databaseName) {
		return mongodbTableNameMapper.selectTableByServerIdAndDatabaseName(serverId, databaseName);
	}

	@Override
	public void updateTableIsAnalysis(MongodbTableName tableName) {
		mongodbTableNameMapper.updateTableIsAnalysis(tableName);
	}

}
