package com.jfinteck.dmq.model.project.service;

import java.util.List;

import com.jfinteck.dmq.model.project.dto.UserSummaryDTO;

/**
 * UserSummary业务处理类
 * 
 * @author weijun.zhu
 * @date 2019年2月13日 下午1:39:40
 * @version 1.0
 */
public interface UserSummaryService {

	/**
	 * 单条保存数据
	 * 
	 * @param userSummay
	 */
	void save(UserSummaryDTO userSummay);
	
	/**
	 * 批量保存数据
	 * 
	 * @param userSummarys
	 */
	void batchSave(List<UserSummaryDTO> userSummarys);
}
