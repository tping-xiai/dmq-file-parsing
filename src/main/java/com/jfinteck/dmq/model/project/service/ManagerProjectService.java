package com.jfinteck.dmq.model.project.service;

import java.util.List;

import com.jfinteck.dmq.model.project.dto.ManagerProjectDTO;
import com.jfinteck.dmq.mongo.project.entity.ProjectEntity;

/**
 * 业务Service接口类
 *
 * @author admin
 *
 */
public interface ManagerProjectService {

	/**
	 * 保存数据
	 * 
	 * @param managerProject
	 */
    void saveInfo(ManagerProjectDTO managerProject);
    
    /**
          *  数据对象之间转
          *  对象字段名称相同  
     * 
     * @param projectEntities
     * @return
     */
    List<ManagerProjectDTO> handerJavaBean(List<ProjectEntity> projectEntities);

}
