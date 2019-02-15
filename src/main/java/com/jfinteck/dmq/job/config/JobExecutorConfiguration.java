package com.jfinteck.dmq.job.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * 为JOB添加监听器
 * 
 * 
 * @author weijun.zhu
 * @date 2019年2月15日 上午10:24:05
 * @version 1.0
 */
@Configuration
public class JobExecutorConfiguration {
	
	@Bean
	public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		// 设置核心行程数  核心线程会一直存活，即使没有任务需要执行
		executor.setCorePoolSize(10);
		// 设置最大线程数
		executor.setMaxPoolSize(20);
		// 任务队列容量（阻塞队列） 当核心线程数达到最大时，新任务会放在队列中排队等待执行
		executor.setQueueCapacity(100);
		// 设置线程前缀名称
		executor.setThreadNamePrefix("DMQ-JOB");
		return executor;
	}

}
