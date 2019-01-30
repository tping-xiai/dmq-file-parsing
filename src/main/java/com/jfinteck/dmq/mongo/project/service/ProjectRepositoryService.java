package com.jfinteck.dmq.mongo.project.service;

import com.jfinteck.dmq.mongo.project.entity.ProjectEntity;

import java.util.List;

public interface ProjectRepositoryService {

    List<ProjectEntity> saveAll(List<ProjectEntity> projects);
}
