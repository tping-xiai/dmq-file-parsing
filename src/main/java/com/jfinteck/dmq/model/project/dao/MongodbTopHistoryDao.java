package com.jfinteck.dmq.model.project.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.jfinteck.dmq.model.project.dto.MongodbTopHistory;
import com.jfinteck.dmq.model.project.dto.MongodbTopHistoryExample;

/**
 * 操作MongoDB执行详情数据
 * 
 * @author weijun.zhu
 * @date 2019年2月14日 下午12:46:06
 * @version 1.0
 */
public interface MongodbTopHistoryDao {

	int countByExample(MongodbTopHistoryExample example);

    int deleteByExample(MongodbTopHistoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(MongodbTopHistory record);
    
    /**
     * 批量插入多条数据
     * @param mongodbTops
     */
    void batchInsert(@Param("mongoTops") List<MongodbTopHistory> mongodbTops);

    int insertSelective(MongodbTopHistory record);

    List<MongodbTopHistory> selectByExample(MongodbTopHistoryExample example);

    MongodbTopHistory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") MongodbTopHistory record, @Param("example") MongodbTopHistoryExample example);

    int updateByExample(@Param("record") MongodbTopHistory record, @Param("example") MongodbTopHistoryExample example);

    int updateByPrimaryKeySelective(MongodbTopHistory record);

    int updateByPrimaryKey(MongodbTopHistory record);
    
}
