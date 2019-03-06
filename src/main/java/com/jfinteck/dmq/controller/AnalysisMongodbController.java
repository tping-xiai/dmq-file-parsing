package com.jfinteck.dmq.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jfinteck.dmq.core.comm.ResultBean;
import com.jfinteck.dmq.core.enums.ErrorEnum;
import com.jfinteck.dmq.dto.BaseDTO;
import com.jfinteck.dmq.service.IAnalysisMongodbService;

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
	
	@ApiOperation(value="获取指定数据库中所有表名称")
	@GetMapping("/load/tables")
	public ResultBean<List<BaseDTO>> getDatabaseTables(@PathParam("id") Long id, @PathParam("database") String database){
		
		log.info("request data param: {database = {}}", database);
		if( StringUtils.isEmpty(database) ) {
			return new ResultBean.Builder<List<BaseDTO>>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		analysisMongodbService.getDatabaseTables(id, database);
		
		return null;
	}
	
}
