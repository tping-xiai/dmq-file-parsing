package com.jfinteck.dmq.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinteck.dmq.component.MongoOperatingComponent;
import com.jfinteck.dmq.core.utils.CharUtils;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbFieldName;
import com.jfinteck.dmq.dto.MongodbServer;
import com.jfinteck.dmq.dto.MongodbTableName;
import com.jfinteck.dmq.service.IAnalysisMongodbService;
import com.jfinteck.dmq.service.IMongodbFieldNameService;
import com.jfinteck.dmq.service.IMongodbServerService;
import com.jfinteck.dmq.service.IMongodbTableNameService;
import com.jfinteck.dmq.vo.AnalysisMongodbVo;
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
	@Autowired
	private IMongodbFieldNameService mongodbFieldNameService;
	
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
        		_table.setCollName(temp);
        		tableNames.add(_table);
        	}
        }
        
        // 保存数据库
        if( !tableNames.isEmpty() ) {
        	mongodbTableNameService.insertBatch(tableNames);
        	log.info("获取MongoDB[{}]数据库中存在 {} 张表数据.", database, tableNames.size());
        }
		return bases;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<String> getConllectonNames(MongodbTableName tableName) {
		
		// 根据服务编号查询服务基本信息
		MongodbServer server = mongodbServerService.selectById(tableName.getServerId());
		
		// 连接MongoDBClirnt 操作MongoDB数据库
		MongoDatabase database = mongoOperating.getMongoDatabase(server, tableName.getDatabaseName());
		
		// 通过集合名称，获取Document文档集合
		MongoCollection<Document> collection = database.getCollection(tableName.getCollName());
		
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("server_id", tableName.getServerId());
		columnMap.put("database_name", tableName.getDatabaseName());
		columnMap.put("coll_name", tableName.getCollName());
		List<MongodbTableName> tables = mongodbTableNameService.selectByMap(columnMap);
		
		MongodbTableName table = null;
		if( !tables.isEmpty() ) {
			table = tables.get(0);
			Document document = null;
			Map<String, Object> paramMap = new HashMap<String, Object>();
			if( !StringUtils.isEmpty(table.getFilterCondit()) ) {
				paramMap = JSONObject.toJavaObject(JSONObject.parseObject(table.getFilterCondit()), Map.class);
			}
			
			//选择性的显示字段  0：不显示 ，1：显示
			BasicDBObject exclude = new BasicDBObject();
			exclude.append("_id", 0).append("_class", 0);
			document = collection.find(new Document(paramMap))
					.sort(new BasicDBObject("_id", -1))  // _id降序，第一页是最大的，下一页的id比上一页的最后的id还小
					.projection(exclude) // 不显示的字段名称
					.first();  // 获取第一条数据  
			
			if( document != null ) {
				log.info("集合第一条数据：{}", document.toJson());
				
				JSONObject fieldJSONObject = JSONObject.parseObject(document.toJson());
				Map<String, Object> fieldsMap = JSONObject.toJavaObject(fieldJSONObject, Map.class);
				
				List<String> fields = new ArrayList<String>();
				if( fieldsMap != null && !fieldsMap.isEmpty()) {
					for(Map.Entry<String, Object> entry : fieldsMap.entrySet()) {
						fields.add(entry.getKey());
					}
				}
				return fields;
			}
		}
		
		return null;
	}

	@Override
	public Long saveAnalysisColletionName(AnalysisMongodbVo analysisVo) {
		Long id = null;
		String[] fields = analysisVo.getField().split(",");
		if( fields.length > 0 ) {
			
			// 根据服务编号、数据库名称与集合名称获取数据
			Map<String, Object> columnMap = new HashMap<String, Object>();
			columnMap.put("server_id", analysisVo.getServerId());
			columnMap.put("database_name", analysisVo.getDatabaseName());
			columnMap.put("coll_name", analysisVo.getCollName());
			columnMap.put("flag", "1");
			log.info("获取要解析集合详情信息的搜索条件：{}", JSON.toJSONString(columnMap));
			
			List<MongodbTableName> tableNames = mongodbTableNameService.selectByMap(columnMap);
			if( tableNames != null && !tableNames.isEmpty() ) {
				MongodbTableName table = tableNames.get(0);
				log.info("获取要解析集合详情信息：{}", JSON.toJSONString(table));
				
				List<MongodbFieldName> fieldNameList = new ArrayList<MongodbFieldName>();
				Arrays.asList(fields).forEach(field -> {
					/**
					 * 对field做一些处理操作
					 * eg: userId -> user_id
					 */
					fieldNameList.add(new MongodbFieldName(table.getId(), CharUtils.toLowerCase(field)));
				});
				
				if( !fieldNameList.isEmpty() ) {
					// 先根据table_id删除，再保存数据
					Map<String, Object> columnDel = new HashMap<String, Object>();
					columnDel.put("table_id", table.getId());
					mongodbFieldNameService.deleteByMap(columnDel);
					mongodbFieldNameService.insertBatch(fieldNameList);
					
					log.info("解析集合字段名称个数为：{}", fieldNameList.size());
				}
				id = table.getId();
			}
		}
		
		return id;
	}

}
