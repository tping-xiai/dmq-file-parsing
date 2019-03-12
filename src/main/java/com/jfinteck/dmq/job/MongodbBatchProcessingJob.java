package com.jfinteck.dmq.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfinteck.dmq.batch.listener.JobMongodbListener;
import com.jfinteck.dmq.batch.step.MongoUserSummaryStepHandler;

/**
 * 调度解析MongoDB数据库
 * 
 * @author weijun.zhu
 * @date 2019年2月15日 上午10:06:37
 * @version 1.0
 */
public class MongodbBatchProcessingJob {

	private JobBuilderFactory jobBuilderFactory;
	private JobMongodbListener jobMongodbListener;
	private MongoUserSummaryStepHandler userSummaryStepHandler;
	
	/**
	 * 一个简单基础的Job通常由一个或者多个Step组成
	 * @return
	 */
	public Job parsingMongoDBJob() {
		return jobBuilderFactory.get("parsingMongoDBJob")
				.incrementer(new RunIdIncrementer())
				.start(userSummaryStepHandler.handleMongoDBStep())
				.listener(jobMongodbListener)
				.build();
	}

}
