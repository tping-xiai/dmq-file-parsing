package com.jfinteck.dmq.dto;

import java.io.Serializable;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown=true)
@TableName("tb_mongodb_execute_plan")
public class ExecutePlan extends Model<ExecutePlan>{

	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 执行计划编号
	 */
	@TableField(value="execute_id")
	private String executeId;
	/**
	 * 服务编号
	 */
	@TableField(value="server_id")
	private Long serverId;
	/**
	 * 集合编号
	 */
	@TableField(value="coll_id")
	private Long collId;
    /**
     * 存放数据表名称
     */
	@TableField(value="table_name")
	private String tableName;
	/**
	 * 标识：1-可用，0-不可用
	 */
	private String flag;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
