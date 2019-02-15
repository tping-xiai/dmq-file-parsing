package com.jfinteck.dmq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jfinteck.dmq.mongo.project.entity.ProjectEntity;
import com.jfinteck.dmq.mongo.project.service.ProjectRepositoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinteck.dmq.comm.ManagerInterfaceComm;
import com.jfinteck.dmq.model.project.dto.ManagerProjectDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Slf4j
@RestController
@RequestMapping("/project")
@Api(tags="project", description="項目管理接口")
public class ManagerProjectController {

    @Autowired
    private ProjectRepositoryService projectRepositoryService;

    @ApiOperation(value = "初始化数据", notes = "作为测试数据")
    @GetMapping("/interface/init")
    public Map<String, Object> init(){
        Map<String, Object> resultMap = new HashMap<String, Object>();

        List<ProjectEntity> projects = new ArrayList<ProjectEntity>();
        log.info("开始准备数据.....");
        for (int i = 0; i <= ManagerInterfaceComm.DEFAULT_NUMBER; i++){
            ProjectEntity projectEntity = new ProjectEntity();
            Long id = 10000L + i;
            projectEntity.setId(id);
            projectEntity.setProjectName("即富对接外部银行项目");
            projectEntity.setProjectRemark("对接提供客户的开户项目");
            projectEntity.setInExplain("对接开户接口-" + i);
            projectEntity.setOperator("即富项目组-01");
            projectEntity.setState("0");
            projects.add(projectEntity);
        }
        log.info("数据准备完毕.....");

        log.info("将准备数据保存到Mongodb数据库中.....");
        try {
			projectRepositoryService.saveAll(projects);
		} catch (Exception e) {
			log.error("保存数据失败，error:{}", e);
			resultMap.put("code", "JF100250");
			resultMap.put("msg", "初始化数据失败");
			return resultMap;
		}

        log.info("数据保存完毕....");
        
        resultMap.put("code", "JF100200");
        resultMap.put("msg", "初始化数据成功");
        return resultMap;
    }

	/**
	 * POST方式调用接口
	 * 
	 * @param managerProject
	 * @return
	 */
	@ApiOperation(value="添加接口信息", notes="保存接口的配置基本信息")
	@ApiImplicitParam(name="managerProject", value="接口信息", dataType="ManagerProjectDTO", required=true, paramType="body")
	@PostMapping("/interface/add")
	public Map<String, Object> addInterface(@RequestBody ManagerProjectDTO managerProject){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("name", managerProject.getProjectName());
		resultMap.put("path", managerProject.getProjectRemark());
		return resultMap;
	}
	
	/**
	 * GET方式调用接口
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据编号ID查询接口基本信息", notes="查询接口基本信息")
	@ApiImplicitParam(name="id", value="接口编号", dataType="Long", required= true, paramType="path")
	@GetMapping("/interface/find/{id}")
	public ManagerProjectDTO findById(@PathVariable Long id) {
		ManagerProjectDTO managerProject = new ManagerProjectDTO();
		managerProject.setId(id);
		managerProject.setProjectName("京东白条");
		managerProject.setProjectRemark("京东白条开户接口");
		managerProject.setOperator("理单");
		managerProject.setInExplain("调用京东白条开户接口，测试开户接口");
		return managerProject;
	}
	
	/**
	 * DELETE方式调用接口
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="根据编号ID删除接口基本信息", notes="删除接口基本信息")
	@ApiImplicitParam(name="id", value="接口编号ID", dataType="Long", required=true, paramType="path")
	@DeleteMapping("/interface/delete/{id}")
	public Map<String, Object> deleteById(@PathVariable Long id){
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("code", "200");
		resultMap.put("msg", "delete is OK!");
		return resultMap;
	}

}
