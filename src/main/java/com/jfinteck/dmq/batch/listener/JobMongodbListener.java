package com.jfinteck.dmq.batch.listener;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * 为解析MongoDB数据时，添加简单监听器
 * 
 * 
 * @author weijun.zhu
 * @date 2019年2月15日 上午10:43:21
 * @version 1.0
 */
@Slf4j
public class JobMongodbListener implements JobExecutionListener{

	private ThreadPoolTaskExecutor threadPoolTaskExecutor;
	
	private long startTime = 0;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		startTime = System.currentTimeMillis();
		log.info("job before ... " + jobExecution.getJobParameters());
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info("JOB STATUS: " + jobExecution.getStatus());
		
		if( jobExecution.getStatus() == BatchStatus.COMPLETED ) {
			log.info("JOB FINISHED ... JOB 任务执行完毕.");
			threadPoolTaskExecutor.destroy();
		}else if( jobExecution.getStatus() == BatchStatus.FAILED ){
			log.info("JOB FAILED ... JOB 任务执行失败，请查询执行日志.");
		}
		
		log.info("Job Cost Time : " + ( System.currentTimeMillis() - startTime ));
	}

}
