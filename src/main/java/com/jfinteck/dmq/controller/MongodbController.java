package com.jfinteck.dmq.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.plugins.Page;
import com.jfinteck.dmq.core.base.BaseVo;
import com.jfinteck.dmq.core.comm.ResultBean;
import com.jfinteck.dmq.core.enums.ErrorEnum;
import com.jfinteck.dmq.core.utils.ValidatorUtil;
import com.jfinteck.dmq.dto.MongodbListDatabasesDTO;
import com.jfinteck.dmq.dto.MongodbOverviewDTO;
import com.jfinteck.dmq.dto.MongodbServer;
import com.jfinteck.dmq.dto.MongodbServerDTO;
import com.jfinteck.dmq.service.IMongodbServerService;
import com.jfinteck.dmq.vo.MongodbServerVo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 操作MongoDB 控制层类
 * 
 * @author weijun.zhu
 * @date 2019年3月5日 上午10:34:49
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/api/mongodb")
@Api(tags="api-mongodb-controller", description="操作MongoDB节点数据")
public class MongodbController {

	@Autowired
	private IMongodbServerService mongodbServerService;
	
	/**
	 * 添加新节点
	 * 
	 * @param mongodbServer
	 * @return
	 */
	@ApiOperation(value="添加MongoDB新节点")
	@PostMapping("/add")
	public ResultBean<String> add(@Valid @RequestBody MongodbServer mongodb){
		
		// 参数校验
		if( ValidatorUtil.hasError(mongodb) ) {
			return new ResultBean.Builder<String>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		// 请求参数
		log.info("request param data:{}", JSONObject.toJSON(mongodb));
		mongodbServerService.insert(mongodb);
		
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS);
	}
	
	/**
	 * 删除节点数据
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="删除MongoDB节点数据")
	@DeleteMapping("/delete/{id}")
	public ResultBean<String> delete(@PathVariable Long id){
		
		log.info("delete mongodb node:{id:{}}", id);
		mongodbServerService.deleteById(id);
		
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS);
	}
	
	/**
	  * 查询节点数据列表
	 * 
	 * @param mongodbServer
	 * @return
	 */
	@ApiOperation(value="查询节点数据列表")
	@PostMapping("/load")
	public ResultBean<Page<MongodbServerDTO>> load(@RequestBody BaseVo mongodbServer){
		
		log.info("request data param:{}", JSON.toJSONString(mongodbServer));
		
		/**
		 * 分页查询数据
		 */
		Page<MongodbServerDTO> page = new Page<MongodbServerDTO>(mongodbServer.getPageNum(), mongodbServer.getPageSize());
		page = mongodbServerService.findMongodbServerList(page);
		
		return new ResultBean.Builder<Page<MongodbServerDTO>>().build(ErrorEnum.SUCCESS, page);
	}
	
	/**
	 * 编辑基本信息
	 * 
	 * @param mongodbServer
	 * @return
	 */
	@ApiOperation(value="编辑基本信息")
	@PutMapping("/update")
	public ResultBean<String> update(@RequestBody MongodbServer mongodbServer){
		
		log.info("request data param: {}", JSON.toJSONString(mongodbServer));
		
		if( ValidatorUtil.hasError(mongodbServer) ) {
			return new ResultBean.Builder<String>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		// 对象之间值转换
		mongodbServerService.updateById(mongodbServer);
		return new ResultBean.Builder<String>().build(ErrorEnum.SUCCESS);
	}
	
	/**
	 * 获取概况信息
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="获取MongoDBj节点概况信息")
	@GetMapping("/get-overview/{id}")
	public ResultBean<MongodbOverviewDTO> getMongodbOverview(@PathVariable Long id){
		
		log.info("request data param: {id = {}}", id);
		
		if( id == null ) {
			return new ResultBean.Builder<MongodbOverviewDTO>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		MongodbOverviewDTO overview = mongodbServerService.getMongodbOverview(id);
		
		return new ResultBean.Builder<MongodbOverviewDTO>().build(ErrorEnum.SUCCESS, overview);
	}
	
	/**
	 * 获取数据库列表
	 * 
	 * @param id
	 * @return
	 */
	@ApiOperation(value="获取数据库列表")
	@GetMapping("/get-databases/{id}")
	public ResultBean<List<MongodbListDatabasesDTO>> getMongodbListDatabases(@PathVariable Long id){
		
		log.info("request data param: {id = {}}", id);
		
		if( id == null ) {
			return new ResultBean.Builder<List<MongodbListDatabasesDTO>>().build(ErrorEnum.VALIDATE_EXCEPTION);
		}
		
		List<MongodbListDatabasesDTO> databases = mongodbServerService.getMongodbListDatabases(id);
		return new ResultBean.Builder<List<MongodbListDatabasesDTO>>().build(ErrorEnum.SUCCESS, databases);
	}
	
	/**
	 * 获取指定数据库表名称
	 * 
	 * @param serverVo
	 * @return
	 */
	@ApiOperation(value="获取指定数据库表名称")
	@PostMapping("/get-top-ns")
	public ResultBean<List<MongodbListDatabasesDTO>> getMongodbTopNs(@RequestBody MongodbServerVo serverVo){
		log.info("request data param: {}", JSON.toJSONString(serverVo));
		
		
		
		return new ResultBean.Builder<List<MongodbListDatabasesDTO>>().build(ErrorEnum.SUCCESS);
	}
	
}
