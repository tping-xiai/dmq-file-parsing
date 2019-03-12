package com.jfinteck.dmq.biz;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MySQLDataAnalysisBiz {

	@Autowired
	private DataSource dataSource;
	
}
