package com.jfinteck.dmq.model.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinteck.dmq.model.project.dao.UserSummaryDao;
import com.jfinteck.dmq.model.project.dto.UserSummaryDTO;
import com.jfinteck.dmq.model.project.service.UserSummaryService;

/**
 * [Service] 业务处理类
 * 
 * 
 * @author weijun.zhu
 * @date 2019年2月13日 下午1:40:38
 * @version 1.0
 */
@Service
public class UserSummaryServiceImpl implements UserSummaryService{

	@Autowired
	private UserSummaryDao userSummaryDao;
	
	@Override
	public void save(UserSummaryDTO userSummary) {
		userSummaryDao.insert(userSummary);
	}

	@Override
	public void batchSave(List<UserSummaryDTO> userSummarys) {
		userSummaryDao.batchInsert(userSummarys);
	}

}
