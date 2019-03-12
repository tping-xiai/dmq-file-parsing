package com.jfinteck.dmq.task;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MysqlParallelThread implements Runnable {

	public MysqlParallelThread() {}
	
	@Override
	public void run() {
		log.info("MysqlParallelThread is running");
	}

}
