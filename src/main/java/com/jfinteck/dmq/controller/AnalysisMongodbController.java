package com.jfinteck.dmq.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jfinteck.dmq.comm.ManagerInterfaceComm;
import com.jfinteck.dmq.core.comm.ResultBean;
import com.jfinteck.dmq.core.enums.ErrorEnum;
import com.jfinteck.dmq.core.utils.ValidatorUtil;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbTableName;
import com.jfinteck.dmq.service.IAnalysisMongodbService;
import com.jfinteck.dmq.service.IMongodbTableNameService;
import com.jfinteck.dmq.vo.AnalysisMongodbVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 解析指定MongoDB数据信息 
 * 
 * @author weijun.zhu
 * @date 2019年3月6日 下午4:04:05
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/analysis")
@Api(tags="api-analysis-controller", description="解析指定MongoDB数据库数据")
public class AnalysisMongodbController {

	@Autowired
	private IAnalysisMongodbService analysisMongodbService;
	
	@Autowired
	private IMongodbTableNameService mongodbTableNameService;
	
	@ApiOperation(value="获取指定数据库中所有表名称")
	@GetMapping("/load/tables")
	public ResultBean<List<BaseDTO>> getDatabaseTables(@PathParam("id") Long id, @PathParam("database") String database){
		
		log.info("request data param: {database = {}}", database);
		if( StringUtils.isEmpty(database) ) {
			return new ResultBean.Builder<List<BaseDTO>>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		/**
		 * 先从数据库中获取表名称，若没有则连接MongoDB直接获取返回，并且将数据保存到数据库中
		 */
		List<BaseDTO> bases = analysisMongodbService.getDatabaseTables(id, database);
		
		return new ResultBean.Builder<List<BaseDTO>>().build(ErrorEnum.SUCCESS, bases);
	}
	
	@ApiOperation(value="修改集合的解析状态值")
	@PostMapping("/update/analysis")
	public ResultBean<String> updIsAnalysis(@RequestBody AnalysisMongodbVo analysisVo){
		
		log.info("request data param: {}", JSON.toJSONString(analysisVo));
		
		if( ValidatorUtil.hasError(analysisVo) || StringUtils.isEmpty(analysisVo.getCollName()) ) {
			return new ResultBean.Builder<String>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		// 对象属性值赋值
		MongodbTableName tableName = new MongodbTableName();
		try {
			BeanUtils.copyProperties(tableName, analysisVo);
			tableName.setTableName(analysisVo.getCollName());
		} catch (IllegalAccessException | InvocationTargetException e) {
			log.error("[AnalysisMongodbVo]对象属性赋值给[MongodbTableName]属性使用BeanUtils.copyProperties方法抛出异常.", e);
		}
		tableName.setIsAnalysis(ManagerInterfaceComm.DEFAULT_TRUE);
		mongodbTableNameService.updateTableIsAnalysis(tableName);
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS);
	}
	
	@ApiOperation(value="记录解析集合字段名称")
	@PostMapping("/insert/collname")
	public ResultBean<String> saveCollName(@RequestBody AnalysisMongodbVo analysisVo){
		
		log.info("request data param: {}", JSON.toJSONString(analysisVo));
		
		if( ValidatorUtil.hasError(analysisVo) || StringUtils.isEmpty(analysisVo.getCollName()) ) {
			return new ResultBean.Builder<String>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		//List<String> collNames = analysisMongodbService.getConllectonNames(analysisVo);
		
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS);
	}
	
}
