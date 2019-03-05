package com.jfinteck.dmq.batch.writer;

import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.batch.MyBatisBatchItemWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 通过Mybatis操作Mysql数据库写操作
 * 
 * @author weijun.zhu
 * @date 2019年2月18日 上午9:22:12
 * @version 1.0
 * @param <T>
 */
@Slf4j
public class MySqlDataItemWriter<T> {

	private SqlSessionTemplate sqlSessionTemplate;
	
	/**
	 * DAO操作表接口名称
	 */
	private String statementId;
	
	public ItemWriter<T> writer() {
		
		MyBatisBatchItemWriter<T> batchItemWriter = new MyBatisBatchItemWriter<T>();
		batchItemWriter.setSqlSessionTemplate(sqlSessionTemplate);
		//batchItemWriter.setAssertUpdates(false);
		
		if( statementId == null || StringUtils.isEmpty(statementId) ) {
			log.info("[MySqlDataItemWriter - writer] the statementId is null.操作MySQL数据库写方法为空.");
		}
		batchItemWriter.setStatementId(statementId);
		
		return batchItemWriter;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}
	
}
