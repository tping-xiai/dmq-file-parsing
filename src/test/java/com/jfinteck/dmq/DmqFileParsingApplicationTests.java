package com.jfinteck.dmq;

import com.jfinteck.dmq.model.project.dao.ManagerProjectDao;
import com.jfinteck.dmq.model.project.dto.ManagerProjectDTO;
import com.jfinteck.dmq.mongo.project.entity.ProjectEntity;
import com.jfinteck.dmq.mongo.project.repository.ProjectRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DmqFileParsingApplicationTests {

    @Autowired
    private ManagerProjectDao managerProjectDao;

    @Autowired
    private ProjectRepository projectRepository;

    /**
          * 测试数据保存到MySQL数据库
     *
     */
    @Test
    public void insertInfo() {

        ManagerProjectDTO project = new ManagerProjectDTO();
        project.setProjectName("测试项目JD白条");
        project.setProjectRemark("提供外部使用的接口");
        project.setInExplain("开户JD表条接口");
        project.setOperator("xiaoming");
        project.setState("0");
        managerProjectDao.insertInfo(project);
    }

    @Test
    public void insertMongoInfo(){

        ProjectEntity projectEntity = new ProjectEntity();
        projectEntity.setId(10002L);
        projectEntity.setProjectName("蚂蚁金服借呗");
        projectEntity.setProjectRemark("蚂蚁金服接口");
        projectEntity.setInExplain("蚂蚁金服借呗借款接口");
        projectEntity.setOperator("黎明");
        projectEntity.setState("0");

        try {
            projectRepository.insert(projectEntity);
        } catch (Exception e) {
            log.error("将数据插入Mongodb中报错，id is exists.");
            e.printStackTrace();
        }
    }

    @Test
    public void findAll(){
        List<ProjectEntity> projects = projectRepository.findAllByProjectName("蚂蚁金服借呗");
        for (ProjectEntity project : projects) {
            System.out.println(project.toString());
        }
    }

}

