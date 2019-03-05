package com.jfinteck.dmq.mongo.project.service;

import java.util.List;

import com.jfinteck.dmq.mongo.project.entity.ProjectEntity;

public interface ProjectRepositoryService {

    List<ProjectEntity> saveAll(List<ProjectEntity> projects);
}
