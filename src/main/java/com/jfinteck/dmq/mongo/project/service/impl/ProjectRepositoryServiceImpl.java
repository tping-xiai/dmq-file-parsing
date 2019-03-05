package com.jfinteck.dmq.mongo.project.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jfinteck.dmq.mongo.project.entity.ProjectEntity;
import com.jfinteck.dmq.mongo.project.repository.ProjectRepository;
import com.jfinteck.dmq.mongo.project.service.ProjectRepositoryService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ProjectRepositoryServiceImpl implements ProjectRepositoryService {

    @Autowired
    private ProjectRepository projectRepository;

    public List<ProjectEntity> saveAll(List<ProjectEntity> projects) {
        return projectRepository.saveAll(projects);
    }
}
