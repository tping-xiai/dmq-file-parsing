package com.jfinteck.dmq.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinteck.dmq.component.MongoOperatingComponent;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbServer;
import com.jfinteck.dmq.dto.MongodbTableName;
import com.jfinteck.dmq.service.IAnalysisMongodbService;
import com.jfinteck.dmq.service.IMongodbServerService;
import com.jfinteck.dmq.service.IMongodbTableNameService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import lombok.extern.slf4j.Slf4j;

/**
 * 解析MongoDB数据库数据
 * 
 * @author weijun.zhu
 * @date 2019年3月6日 下午5:49:43
 * @version 1.0
 */
@Slf4j
@Service
public class AnalysisMongodbServiceImpl implements IAnalysisMongodbService {
	
	@Autowired
	private IMongodbServerService mongodbServerService;
	@Autowired
	private MongoOperatingComponent mongoOperating;
	@Autowired
	private IMongodbTableNameService mongodbTableNameService;
	
	@Override
	public List<BaseDTO> getDatabaseTables(Long id, String database) {
		
		/**
		 * 先从数据库中获取表名称，若没有则连接MongoDB直接获取返回，并且将数据保存到数据库中
		 */
		List<BaseDTO> bases = mongodbTableNameService.findTableByServerIdAndDatabaseName(id, database);
		
		if( bases != null && !bases.isEmpty() ) {
			return bases;
		}
		
		bases = new ArrayList<BaseDTO>();
		/**
		 * 连接MongoDB直接获取返回，并且将数据保存到数据库中
		 */
		MongodbServer server = mongodbServerService.selectById(id);
		if( server == null ) return bases;
		
		// 获取TOP属性值
		DBObject dbObject = new BasicDBObject("top", 1);
		
		JSONObject commandResult = mongoOperating.getMongodbCommandResult(server, dbObject);
		JSONObject total = commandResult.getJSONObject("totals");
		total.remove("note");
        total.remove("ok");
        
        log.info("获取MongoDB[{}]数据库中所有表名称.", database);
        String prefix = database + ".";
        
        List<MongodbTableName> tableNames = new ArrayList<MongodbTableName>();
        /**
         * 筛选需要的数据
         */
        MongodbTableName table = new MongodbTableName();
        table.setServerId(id);
        table.setDatabaseName(database);
        for (Map.Entry<String, Object> entry : total.entrySet())
        {
        	if( entry.getKey() != null && entry.getKey().startsWith(prefix) ) {
        		String key = entry.getKey();
        		String temp = key.substring(prefix.length(), key.length());
        		bases.add(new BaseDTO(temp, temp));
        		
        		MongodbTableName _table = table.clone();
        		_table.setTableName(temp);
        		tableNames.add(_table);
        	}
        }
        
        // 保存数据库
        if( !tableNames.isEmpty() ) {
        	mongodbTableNameService.insertBatch(tableNames);
        	log.info("获取MongoDB[{}数据库中存在 {} 张表数据.]", database, tableNames.size());
        }
		return bases;
	}

	@Override
	public List<String> getConllectonNames(MongodbTableName tableName) {
		
		// 根据服务编号查询服务基本信息
		MongodbServer server = mongodbServerService.selectById(tableName.getServerId());
		
		// 连接MongoDBClirnt 操作MongoDB数据库
		MongoDatabase database = mongoOperating.getMongoDatabase(server, tableName.getDatabaseName());
		
		// 通过集合名称，获取Document文档集合
		MongoCollection<Document> collection = database.getCollection(tableName.getTableName());
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("productType", new HashMap<String, Object>().put("$exists", false));
		paramMap.put("channelCode", "JD_JT");
		Document document = collection.find(new Document(paramMap)).first();
		log.info(JSON.toJSONString(document));
		
		
		return null;
	}

}
