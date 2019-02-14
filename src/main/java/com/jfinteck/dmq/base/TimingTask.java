package com.jfinteck.dmq.base;


public interface TimingTask {

	/**
	 * 定时任务执行方法
	 * 
	 * 可通过在方法上添加注解
	 * @Scheduled
	 */
	void execute();
	
}
