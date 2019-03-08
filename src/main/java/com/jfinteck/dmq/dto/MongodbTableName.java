package com.jfinteck.dmq.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@TableName("tb_mongodb_sync_table")
public class MongodbTableName extends Model<MongodbTableName>{

	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO)
	private Long id;
	
	/**
	 * 服务编号
	 */
	@NotNull
	@TableField(value="server_id")
	private Long serverId;
	/**
	 * 数据库名称
	 */
	@NotEmpty
	@TableField(value="database_name")
	private String databaseName;
	
	/**
	 * 表名称
	 */
	@TableField(value="coll_name")
	private String collName;
	
	/**
	 * 标识：1-可用，0-不可用
	 */
	private String flag;
	
	/**
	 * 是否解析：1-解析，0-不解析
	 */
	@TableField(value="is_analysis")
	private String isAnalysis;
	
	/**
	 * 过滤条件
	 */
	@TableField(value="filter_condit")
	private String filterCondit;
	
	public MongodbTableName clone() {
		return new MongodbTableName(this.id, this.serverId, this.databaseName, this.collName, this.flag, this.isAnalysis, this.filterCondit);
	}
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
