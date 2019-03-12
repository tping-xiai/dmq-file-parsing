package com.jfinteck.dmq.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jfinteck.dmq.dto.ExecutePlan;
import com.jfinteck.dmq.dto.ExecutePlanDTO;
import com.jfinteck.dmq.mapper.ExecutePlanMapper;
import com.jfinteck.dmq.service.IExecutePlanService;

/**
 * 计划执行[Service层]
 */
@Service
public class ExecutePlanServiceImpl extends ServiceImpl<ExecutePlanMapper, ExecutePlan> implements IExecutePlanService {

	@Autowired
	private ExecutePlanMapper executePlanMapper;
	
	@Override
	public ExecutePlanDTO findExecuteById(String executeId) {
		return executePlanMapper.selectExecuteById(executeId);
	}
	
}
