package com.jfinteck.dmq.model.project.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MongoDB 命令行执行详情
 * 
 * @author weijun.zhu
 * @date 2019年2月14日 下午12:38:39
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MongodbTopHistory implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
	// mongodb服务器id
    private Long serverId;
    // 名称空间
    private String ns;
    // 总条数
    private Long totalCount;
    // 执行使用总时间
    private Long totalTime;
    // 每秒执行总条数
    private String totalCountPersecond;

    private Long writeLockCount;

    private Long writeLockTime;

    private String writeLockCountPersecond;

    private Long readLockCount;

    private Long readLockTime;

    private String readLockCountPersecond;
    // 插入条数
    private Long insertCount;
    // 插入使用时间
    private Long insertTime;
    // 每秒插入条数
    private String insertCountPersecond;
    // 更新条数
    private Long updateCount;
    // 更新使用时间
    private Long updateTime;
    // 每秒更新条数
    private String updateCountPersecond;

    private Long getMoreCount;

    private Long getMoreTime;

    private String getMoreCountPersecond;
    // 查询条数
    private Long queriesCount;

    private Long queriesTime;

    private String queriesCountPersecond;
    // 删除条数
    private Long removeCount;

    private Long removeTime;

    private String removeCountPersecond;

    private Long commandsCount;

    private Long commandsTime;

    private Date createTime;

}
