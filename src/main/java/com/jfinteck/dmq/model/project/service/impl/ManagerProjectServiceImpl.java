package com.jfinteck.dmq.model.project.service.impl;

import com.alibaba.fastjson.JSON;
import com.jfinteck.dmq.model.project.dao.ManagerProjectDao;
import com.jfinteck.dmq.model.project.dto.ManagerProjectDTO;
import com.jfinteck.dmq.model.project.service.ManagerProjectService;
import com.jfinteck.dmq.mongo.project.entity.ProjectEntity;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ManagerProjectServiceImpl implements ManagerProjectService {

    @Autowired
    private ManagerProjectDao managerProjectDao;

	public void saveInfo(ManagerProjectDTO managerProject) {
		managerProjectDao.insertInfo(managerProject);
	}

	public List<ManagerProjectDTO> handerJavaBean(List<ProjectEntity> projectEntities) {
		if( projectEntities == null || projectEntities.isEmpty() ) {
			log.info("[ManagerProjectService] 无数据进行对象类型转换.");
			return null;
		}
		// 开始时间
		long startTime = System.currentTimeMillis();
		
		String jsonString = JSON.toJSONString(projectEntities);
		
		List<ManagerProjectDTO> managers = JSON.parseArray(jsonString, ManagerProjectDTO.class);
		
		long endTime = System.currentTimeMillis();
		log.info("[ManagerProjectService - ProjectEntity TO ManagerProjectDTO ] 一共处理{}条数据.", managers.size());
		log.info("[ManagerProjectService - ProjectEntity TO ManagerProjectDTO ] 所需要时间为：{} ",(endTime - startTime));
		return managers;
	}

}
