package com.jfinteck.dmq.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootConfiguration
@MapperScan(basePackages= {"com.jfinteck.dmq.mapper*"})
@EnableTransactionManagement
public class MyBatisConfig {

}
