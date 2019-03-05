package com.jfinteck.dmq.component;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.jfinteck.dmq.dto.MongodbServer;
import com.mongodb.CommandResult;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoIterable;

import lombok.extern.slf4j.Slf4j;

/**
 * MongoDB操作组件
 * 
 * @author weijun.zhu
 * @date 2019年2月14日 上午9:35:13
 * @version 1.0
 */
@Slf4j
@Component
public class MongoOperatingComponent {

	/**
	 * 默认连接MongonDB的数据库名称
	 */
	private static final String ADMIN_DATABASE_NAME = "admin";
	
	/**
	 * 获取命令结果集
	 * 
	 * @param mongodbServer
	 * @param dbObject
	 * @return
	 */
	public JSONObject getMongodbCommandResult(MongodbServer mongodbServer, DBObject dbObject) {
		
		if( mongodbServer != null ) {
			String host = mongodbServer.getHost();
			String port = mongodbServer.getPort();
			String username = mongodbServer.getUsername();
			String password = mongodbServer.getPassword();
			return getMongodbCommandResult(host, port, username, password, dbObject);
		}
		
		return null;
	}
	
	/**
	 * 获取命令结果集
	 * 
	 * @param host
	 *         连接地址
	 * @param port
	 *         端口号
	 * @param username
	 *         用户名称
	 * @param password
	 *         登录密码
	 * @param dbObject
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public JSONObject getMongodbCommandResult(String host, String port, String username, String password, DBObject dbObject) {
		
		// 拼接连接MongnoDB的url地址
		String DEFAULT_URL = "";
		
		if(( username == null || username.isEmpty() ) && ( password == null || password.isEmpty())) {
			DEFAULT_URL = String.format("mongodb://" + host + ":" + port);
		}else {
			DEFAULT_URL = String.format("mongodb://" + username + ":" + password + "@" + host + ":" + port);
		}
		
		DB db = null;
		MongoClient mongoClient = null;
		JSONObject jsonObject = null;
		
		try {
			MongoClientURI mongoClientURI = new MongoClientURI(DEFAULT_URL);
			mongoClient = new MongoClient(mongoClientURI);
			MongoIterable<String> iterable = mongoClient.listDatabaseNames();
			
			// 有表存在，则进行下面的操作
			if( iterable != null && iterable.first() != null && !iterable.first().isEmpty()) {
				db = mongoClient.getDB(ADMIN_DATABASE_NAME);
				CommandResult result = db.command(dbObject);
				String json = result.toJson();
				jsonObject = JSONObject.parseObject(json);
			}
			
		} catch (Exception e) {
			log.error("Connection MongoDB is error：{}", e);
		}finally {
			// 关闭连接
			if( mongoClient != null ) {
				mongoClient.close();
			}
		}
		
		return jsonObject;
	}
	
}
