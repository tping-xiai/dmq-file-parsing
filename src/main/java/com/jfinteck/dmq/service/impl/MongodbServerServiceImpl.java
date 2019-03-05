package com.jfinteck.dmq.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jfinteck.dmq.component.MongoOperatingComponent;
import com.jfinteck.dmq.core.base.Constant;
import com.jfinteck.dmq.core.utils.MiscUtil;
import com.jfinteck.dmq.dto.MongodbListDatabasesDTO;
import com.jfinteck.dmq.dto.MongodbOverviewDTO;
import com.jfinteck.dmq.dto.MongodbServer;
import com.jfinteck.dmq.dto.MongodbServerDTO;
import com.jfinteck.dmq.mapper.MongodbServerMapper;
import com.jfinteck.dmq.service.IMongodbServerService;
import com.mongodb.BasicDBObject;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import lombok.extern.slf4j.Slf4j;

/**
 * MongoDB操作业务类
 * 
 * 
 * @author weijun.zhu
 * @date 2019年3月5日 上午9:43:35
 * @version 1.0
 */
@Slf4j
@Service
public class MongodbServerServiceImpl extends ServiceImpl<MongodbServerMapper, MongodbServer> implements IMongodbServerService {

	@Autowired
	private MongodbServerMapper mongodbServerMapper;
	
	@Autowired
	private MongoOperatingComponent mongoOperatingComponent;
	
	@Override
	public Page<MongodbServerDTO> findMongodbServerList(Page<MongodbServerDTO> page) {
		
		// 获取MongoDB节点信息
		List<MongodbServer> serverList = mongodbServerMapper.selectMongodbServerList(page);
		List<MongodbServerDTO> serverDtoList = new ArrayList<MongodbServerDTO>();
		serverList.forEach(mongodbServer -> {
			MongodbServerDTO serverDto = new MongodbServerDTO();
			// 对象属性拷贝
			try {
				BeanUtils.copyProperties(serverDto, mongodbServer);
			} catch (IllegalAccessException | InvocationTargetException e) {
				log.error("MongodbServer can not cast MongodbServerDTO, error: ", e);
			}
			
			// 获取当前连接MongoDB节点信息状态
			CommandResult result = getMongodbStatus(mongodbServer.getHost(), 
					Integer.valueOf(mongodbServer.getPort()), 
					mongodbServer.getUsername(), 
					mongodbServer.getPassword());
			
			// 为空则为异常状态,或则为正常状态
			if( result == null ) {
				serverDto.setStatus(Constant.ABNORMAL);
			}
			else
			{
				serverDto.setStatus(Constant.OK);
				Long uptime = result.getLong("uptime");
				serverDto.setUptime(MiscUtil.getHumanTimeBySeconds(uptime));
			}
			serverDtoList.add(serverDto);
		});
		page.setRecords(serverDtoList);
		return page;
	}
	
	@Override
	public MongodbOverviewDTO getMongodbOverview(Long id) {
		
		MongodbServer mongodbServer = mongodbServerMapper.selectById(id);
		
		// 获取serversStatus命令结果
        Map<String, Integer> map = new HashMap<>();
        map.put("serverStatus", 1);
        map.put("version", 1);
        map.put("pid", 1);
        DBObject dbObject = new BasicDBObject(map);
		
        JSONObject jsonObjectServersStatus = 
        		mongoOperatingComponent.getMongodbCommandResult(mongodbServer, dbObject);
        
        JSONObject jsonObjectIsMaster = 
        		mongoOperatingComponent.getMongodbCommandResult(mongodbServer, new BasicDBObject("isMaster", 1));
        
        MongodbOverviewDTO overviewDto = new MongodbOverviewDTO();
        overviewDto.setHost(mongodbServer.getHost());
        overviewDto.setPort(mongodbServer.getPort());
        if( jsonObjectServersStatus != null ) {
        	overviewDto.setVersion(jsonObjectServersStatus.getString("version"));
        	overviewDto.setPid(jsonObjectServersStatus.getJSONObject("pid").getString("$numberLong"));
        	
        	Long uptime = jsonObjectServersStatus.getLong("uptime");
        	overviewDto.setUptime(MiscUtil.getHumanTimeBySeconds(uptime));
        }
        
        if( jsonObjectIsMaster != null ) {
        	overviewDto.setIsMaster(jsonObjectIsMaster.getString("ismaster"));
        }
        
		return overviewDto;
	}
	
	@Override
	public List<MongodbListDatabasesDTO> getMongodbListDatabases(Long id) {
		
		MongodbServer mongoServer = mongodbServerMapper.selectById(id);
		if( mongoServer == null ) return new ArrayList<MongodbListDatabasesDTO>();
		
		// 获取数据列表
		JSONObject commResult = mongoOperatingComponent.getMongodbCommandResult(mongoServer, new BasicDBObject("listDatabases", 1));
		
		JSONArray jsonArray = commResult.getJSONArray("databases");
		List<MongodbListDatabasesDTO> databasesList = new ArrayList<>();
		jsonArray.forEach(object -> {
			MongodbListDatabasesDTO dataDto = new MongodbListDatabasesDTO();
			JSONObject jsonObject = (JSONObject)object;
			dataDto.setName(jsonObject.getString("name"));
			Long sizeOnDiskBytes = jsonObject.getLong("sizeOnDisk");
			dataDto.setSizeOnDisk(MiscUtil.getHumanSizeByBytes(sizeOnDiskBytes));
			databasesList.add(dataDto);
		});
		return databasesList;
	}
	
	@SuppressWarnings({ "deprecation", "resource" })
	private CommandResult getMongodbStatus(String host, Integer port, String username, String password) {
		String stringURL = "";
		if( (username == null || StringUtils.isEmpty(username)) && (password == null || StringUtils.isEmpty(password)) ) {
			stringURL = String.format("mongodb://%s:%s", host, port);
		}
		else 
		{
			stringURL = String.format("mongodb://%s:%s@%s:%s", username, password, host, port);
		}
		
		CommandResult commandResult = null;
		try {
			MongoClientURI uri = new MongoClientURI(stringURL);
			MongoClient mongoClient = new MongoClient(uri);
			List<String> databaseNames = mongoClient.getDatabaseNames();
			if( !databaseNames.isEmpty() ) {
				DB db = mongoClient.getDB(databaseNames.get(0));
				commandResult = db.command("serverStatus");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return commandResult;
	}

}
