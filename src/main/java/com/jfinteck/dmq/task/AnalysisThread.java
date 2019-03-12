package com.jfinteck.dmq.task;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.util.Assert;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AnalysisThread extends MysqlParallelThread {

	private DataSource dataSource;
	
	private String sql;
	
	public AnalysisThread(DataSource dataSource, String sql) {
		Assert.notNull(dataSource, "A dataSource is required.");
		Assert.notNull(sql, "A sql is required.");
		this.dataSource = dataSource;
		this.sql = sql;
	}
	
	@Override
	public void run() {
		log.info("AnalysisThread 子进程[{}] Start...", Thread.currentThread().getName());
		Connection conn = null;
		PreparedStatement st = null;
		try {
			conn = dataSource.getConnection();
			st = conn.prepareStatement(sql);
			
			
		} catch (SQLException e) {
			log.error("AnalysisThread 子进程[{}] Connection Database is error. e:", Thread.currentThread().getName(), e);
		} finally {
			if( conn != null ) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
