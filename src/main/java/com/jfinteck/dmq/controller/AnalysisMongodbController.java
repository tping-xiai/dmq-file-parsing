package com.jfinteck.dmq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.jfinteck.dmq.core.comm.DefaultComm;
import com.jfinteck.dmq.core.comm.ResultBean;
import com.jfinteck.dmq.core.enums.ErrorEnum;
import com.jfinteck.dmq.core.utils.ValidatorUtil;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.dto.MongodbFieldName;
import com.jfinteck.dmq.dto.MongodbTableName;
import com.jfinteck.dmq.service.IAnalysisMongodbService;
import com.jfinteck.dmq.service.IMongodbFieldNameService;
import com.jfinteck.dmq.service.IMongodbServerService;
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
	private IMongodbServerService mongodbServerService;
	@Autowired
	private IAnalysisMongodbService analysisMongodbService;
	@Autowired
	private IMongodbTableNameService mongodbTableNameService;
	@Autowired
	private IMongodbFieldNameService mongodbFieldNameService; 
	
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
		BeanUtils.copyProperties(analysisVo, tableName);
		
		tableName.setIsAnalysis(DefaultComm.DEFAULT_TRUE);
		mongodbTableNameService.updateTableIsAnalysis(tableName);
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS);
	}
	
	@ApiOperation(value="获取集合字段名称")
	@PostMapping("/load-fields")
	public ResultBean<List<String>> getFieldNames(@RequestBody AnalysisMongodbVo analysisVo){
		
		log.info("request data param: {}", JSON.toJSONString(analysisVo));
		
		// 参数校验
		if( ValidatorUtil.hasError(analysisVo) || StringUtils.isEmpty(analysisVo.getCollName()) ){
			return new ResultBean.Builder<List<String>>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		MongodbTableName tableName = new MongodbTableName();
		BeanUtils.copyProperties(analysisVo, tableName);
		
		// 获取指定集合字段名称
		List<String> fields = analysisMongodbService.getConllectonNames(tableName);
		
		return new ResultBean.Builder<List<String>>().build(ErrorEnum.SUCCESS, fields);
	}
	
	@ApiOperation(value="记录解析集合字段名称")
	@PostMapping("/insert-coll-name")
	public ResultBean<String> saveCollName(@RequestBody AnalysisMongodbVo analysisVo){
		
		log.info("request data param: {}", JSON.toJSONString(analysisVo));
		
		if( ValidatorUtil.hasError(analysisVo) || StringUtils.isEmpty(analysisVo.getCollName()) ) {
			return new ResultBean.Builder<String>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		if( StringUtils.isEmpty(analysisVo.getField()) ) {
			return new ResultBean.Builder<String>().buildSucceed("参数缺失，请选择要解析集合字段名称.");
		}
		
		Long id = analysisMongodbService.saveAnalysisColletionName(analysisVo);
		
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS, String.valueOf(id));
	}
	
	@ApiOperation(value="获取创建表字段名称")
	@GetMapping("/load-table-fields/{id}")
	public ResultBean<List<MongodbFieldName>> loadFields(@PathVariable("id") Long tableId){
		
		log.info("request data param: {id = {}}", tableId);
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("table_id", tableId);
		List<MongodbFieldName> fieldNames = mongodbFieldNameService.selectByMap(columnMap);
		return new ResultBean.Builder<List<MongodbFieldName>>().build(ErrorEnum.SUCCESS, fieldNames);
	}
	
	@ApiOperation(value="确定创建表字段", notes="对已经选择要解析字段，确定是否有嵌套字段与是否要解密操作")
	@PostMapping("/confirm-field-property")
	public ResultBean<String> confirmFields(@RequestBody AnalysisMongodbVo analysisVo){
		
		log.info("request data param: {}", JSON.toJSONString(analysisVo));
		
		// 确认字段属性值
		if( analysisVo.getFieldProperty() != null || !analysisVo.getFieldProperty().isEmpty() ) {
			analysisMongodbService.updateFieldProperty(analysisVo.getFieldProperty());
		}
		
		// 创建数据库表
		String executeId = analysisMongodbService.createAnalysisResultTable(analysisVo.getId(), analysisVo.getServerId(), analysisVo.getCollName());
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS, executeId);
	}
	
	/**
	 * 获取解析数据详情内容
	 * 
	 * @param analysisVo
	 * @return
	 */
	@PostMapping("/load-infos")
	public ResultBean<Map<String, Object>> loadInfos(@RequestBody AnalysisMongodbVo analysisVo){
		log.info("request data param: {}", JSON.toJSONString(analysisVo));
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("server", mongodbServerService.selectById(analysisVo.getServerId()));
		resultMap.put("table", mongodbTableNameService.selectById(analysisVo.getId()));
		
		Map<String, Object> columnMap = new HashMap<String, Object>();
		columnMap.put("table_id", analysisVo.getId());
		List<MongodbFieldName> fields = mongodbFieldNameService.selectByMap(columnMap);
		
		resultMap.put("fields", fields);
		return new ResultBean.Builder<Map<String, Object>>().build(ErrorEnum.SUCCESS, resultMap);
	}
	
	@ApiOperation(value="接口方式调用解析数据")
	@PostMapping("/start-analysis")
	public ResultBean<String> analysis(@PathParam("execute_id")String executeId){
		
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS);
	}
	
}
