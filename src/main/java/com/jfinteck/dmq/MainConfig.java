package com.jfinteck.dmq;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;


/**
 * @author admin
 */
@Configuration
@ComponentScan({"com.jfinteck.dmq.*"})
@MapperScan(basePackages = "com.jfinteck.dmq.model.*.dao")
@EnableTransactionManagement
@EnableApolloConfig
@EnableScheduling
@EnableBatchProcessing
public class MainConfig {
}
