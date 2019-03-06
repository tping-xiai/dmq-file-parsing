package com.jfinteck.dmq.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.jfinteck.dmq.component.MongoOperatingComponent;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbServer;
import com.jfinteck.dmq.service.IAnalysisMongodbService;
import com.jfinteck.dmq.service.IMongodbServerService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

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
	
	@Override
	public List<BaseDTO> getDatabaseTables(Long id, String database) {
		
		MongodbServer server = mongodbServerService.selectById(id);
		if( server == null ) return new ArrayList<BaseDTO>();
		
		// 获取TOP属性值
		DBObject dbObject = new BasicDBObject("top", 1);
		
		JSONObject commandResult = mongoOperating.getMongodbCommandResult(server, dbObject);
		JSONObject total = commandResult.getJSONObject("totals");
		total.remove("note");
        total.remove("ok");
        for (Map.Entry<String, Object> entry : total.entrySet())
        {
        	if( entry.getKey() != null && entry.getKey().startsWith(database) ) {
        		System.out.println(entry.getKey() + "------------------" + entry.getValue());
        	}
        }
		return null;
	}

}
