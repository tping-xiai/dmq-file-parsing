package com.jfinteck.dmq.model.project.service.impl;

import com.jfinteck.dmq.model.project.dao.ManagerProjectDao;
import com.jfinteck.dmq.model.project.dto.ManagerProjectDTO;
import com.jfinteck.dmq.model.project.service.ManagerProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManagerProjectServiceImpl implements ManagerProjectService {

    @Autowired
    private ManagerProjectDao managerProjectDao;

	public void saveInfo(ManagerProjectDTO managerProject) {
		managerProjectDao.insertInfo(managerProject);
	}

}
