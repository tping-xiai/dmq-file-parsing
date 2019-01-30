package com.jfinteck.dmq.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinteck.dmq.model.project.dto.ManagerProjectDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/project")
@Api(tags="project", description="項目管理接口")
public class ManagerProjectController {
	
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
