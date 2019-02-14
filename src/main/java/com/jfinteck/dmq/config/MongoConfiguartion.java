package com.jfinteck.dmq.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.jfinteck.dmq.component.MongoPageHelper;

/**
 * 配置MongoPageHelper
 * 
 * @author admin
 *
 */
@Configuration
public class MongoConfiguartion {

	@Autowired
	private MongoTemplate mongoTemplate;
	
	@Bean
	public MongoPageHelper mongoPageHelper() {
		return new MongoPageHelper(mongoTemplate);
	}
}
