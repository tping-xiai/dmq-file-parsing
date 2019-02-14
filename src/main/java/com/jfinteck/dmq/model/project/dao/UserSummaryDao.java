package com.jfinteck.dmq.model.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jfinteck.dmq.model.project.dto.UserSummaryDTO;

public interface UserSummaryDao {

	/**
	 * 单条保存数据
	 * 
	 * @param userSummary
	 */
	void insert(@Param("userSummary") UserSummaryDTO userSummary);
	
	/**
	 * 批量保存多条数据
	 * 
	 * @param userSummary
	 */
	void batchInsert(@Param("userSummarys") List<UserSummaryDTO> userSummary);
	
}
