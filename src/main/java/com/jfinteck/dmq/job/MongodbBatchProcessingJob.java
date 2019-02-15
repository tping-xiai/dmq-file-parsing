package com.jfinteck.dmq.job;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jfinteck.dmq.job.config.JobMongodbListener;
import com.jfinteck.dmq.job.step.MongoUserSummaryStepHandler;

/**
 * 调度解析MongoDB数据库
 * 
 * @author weijun.zhu
 * @date 2019年2月15日 上午10:06:37
 * @version 1.0
 */
@Configuration
public class MongodbBatchProcessingJob {

	@Autowired
	private JobBuilderFactory jobBuilderFactory;
	@Autowired
	private JobMongodbListener jobMongodbListener;
	@Autowired
	private MongoUserSummaryStepHandler userSummaryStepHandler;
	/**
	 * 一个简单基础的Job通常由一个或者多个Step组成
	 * @return
	 */
	@Bean
	public Job parsingMongoDBJob() {
		return jobBuilderFactory.get("parsingMongoDBJob")
				.incrementer(new RunIdIncrementer())
				.start(userSummaryStepHandler.handleMongoDBStep())
//				.next(step)
				.listener(jobMongodbListener)
				.build();
	}

}
