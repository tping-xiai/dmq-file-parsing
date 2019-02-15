package com.jfinteck.dmq.job.step;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.bson.types.ObjectId;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.jfinteck.dmq.job.reader.MongodbDataItemReader;
import com.jfinteck.dmq.model.project.dto.UserSummaryDTO;
import com.jfinteck.dmq.mongo.project.entity.UserSummary;

import lombok.extern.slf4j.Slf4j;

/**
 * 调度步骤Step1
 *  读取MongoDB数据
 * 
 * 
 * @author weijun.zhu
 * @date 2019年2月15日 下午5:05:05
 * @version 1.0
 */
@Slf4j
@Component
public class MongoUserSummaryStepHandler {
	
	@Autowired
	private StepBuilderFactory stepBuilderFactory;
	@Autowired
	private MongodbDataItemReader<UserSummary> mongodbDataItemReader;
	
	private String clazzName = "com.jfinteck.dmq.mongo.project.entity.UserSummary";
	
	/**
	 * 一个简单基础的Step主要分为三个部分
	 * ItemReader : 用于读取数据
	 * ItemProcessor : 用于处理数据
	 * ItemWriter : 用于写数据
	 * @return
	 */
	@Bean
	public Step handleMongoDBStep() {
		
		// 查询条件
		Query query = new Query(Criteria.where("_id").gt(new ObjectId("5c6379ff25eab50e28176138")).lt(new ObjectId("5c6379ff25eab50e2817614b")));
		mongodbDataItemReader.setQuery(query);
		
		// 设置排序规制
		Map<String, Direction> sorts = new HashMap<String, Direction>();
		sorts.put("_id", Sort.Direction.ASC);
		mongodbDataItemReader.setSorts(sorts);
		
		return stepBuilderFactory.get("step1")
				// <输入,输出> 。chunk通俗的讲类似于SQL的commit; 这里表示处理(processor)100条后写入(writer)一次。
				.<UserSummary, UserSummaryDTO>chunk(5000) 
				//捕捉到异常就重试,重试10次还是异常,JOB就停止并标志失败
				.faultTolerant().retryLimit(3).retry(Exception.class).skipLimit(10).skip(Exception.class)
				// 指定ItemReader
				.reader(mongodbDataItemReader.read(clazzName))
				// 指定ItemProcessor
				.processor(processor())
				// 指定ItemWriter
				.writer(writer())
				.build();
	}
	
	@Bean
	public ItemReader<String> reader() {
		/**
		 * 读取数据，这里可以用JPA,JDBC,JMS 等方式 读入数据
		 */
		return new ItemReader<String>() {

			@Override
			public String read()
					throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
				return new String("hi! tping xiai , ni hao ...");
			}
		};
	}
	
	@Bean
	public ItemProcessor<UserSummary, UserSummaryDTO> processor() {
		// TODO 对读取到的数据进行处理
		return new ItemProcessor<UserSummary, UserSummaryDTO>() {
			@Override
			public UserSummaryDTO process(UserSummary item) throws Exception {
				UserSummaryDTO userSummaryDTO = new UserSummaryDTO();
				BeanUtils.copyProperties(userSummaryDTO, item);
				return userSummaryDTO;
			}
		};
	}
	
	@Bean
	public ItemWriter<UserSummaryDTO> writer() {
		// TODO 对处理后的数据进行写的操作
		return item -> {
			log.info("writer data: " + item.toString());
		};
	}
	
}
