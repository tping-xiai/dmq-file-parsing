package com.jfinteck.dmq.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.jfinteck.dmq.dto.ExecutePlan;
import com.jfinteck.dmq.dto.ExecutePlanDTO;

public interface ExecutePlanMapper extends BaseMapper<ExecutePlan>{

	/**
	 * 获取执行计划详情内容
	 * 
	 * @param executeId
	 * @return
	 */
	ExecutePlanDTO selectExecuteById(String executeId);
	
}
