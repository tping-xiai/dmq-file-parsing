package com.jfinteck.dmq.mapper;

import java.util.List;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.jfinteck.dmq.dto.MongodbServer;

/**
  * 操作MongoDB节点接口
 * 
 * @author weijun.zhu
 * @date 2019年3月5日 上午9:38:32
 * @version 1.0
 */
public interface MongodbServerMapper extends BaseMapper<MongodbServer>{

	List<MongodbServer> selectMongodbServerList(Pagination page);
	
}
