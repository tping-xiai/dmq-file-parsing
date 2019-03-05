package com.jfinteck.dmq.batch.reader;

import java.util.Map;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.data.MongoItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 读取MongoDB数据
 * 
 * @author weijun.zhu
 * @date 2019年2月15日 下午3:32:03
 * @version 1.0
 */
@Slf4j
public class MongodbDataItemReader<T> {

	private MongoTemplate mongoTemplate;
	
	private static final int DEAFULT_PAGE_SIZE = 10000;
	// 定义查询条件
	private Query query;
	// 定义排序规制
	private Map<String, Direction> sorts;
	
	/**
	 * 读取MongoDB数据库集合
	 * 
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ItemReader<T> read(String clazzName) {
		
		MongoItemReader<T> mongoItemReader = new MongoItemReader<T>();
		mongoItemReader.setTemplate(mongoTemplate);
		try {
			Class clazz = Class.forName(clazzName);
			mongoItemReader.setTargetType(clazz);
		} catch (ClassNotFoundException e) {
			log.error("Class Name is not find. throws error：", e );
			return null;
		}
		
		if( query == null ) {
			log.error("query is null.", new RuntimeException("A query is required."));
			return null;
		}
		mongoItemReader.setQuery(query);
		mongoItemReader.setSort(sorts);
		mongoItemReader.setPageSize(DEAFULT_PAGE_SIZE);
		
		return mongoItemReader;
	}

	public void setQuery(Query query) {
		this.query = query;
	}

	public void setSorts(Map<String, Direction> sorts) {
		this.sorts = sorts;
	}
	
}
