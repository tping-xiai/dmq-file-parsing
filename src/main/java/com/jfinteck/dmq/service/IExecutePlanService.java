package com.jfinteck.dmq.service;

import com.baomidou.mybatisplus.service.IService;
import com.jfinteck.dmq.dto.ExecutePlan;
import com.jfinteck.dmq.dto.ExecutePlanDTO;

public interface IExecutePlanService extends IService<ExecutePlan>{

     ExecutePlanDTO findExecuteById(String executeId);
}
