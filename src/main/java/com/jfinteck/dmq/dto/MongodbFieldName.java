package com.jfinteck.dmq.dto;

import java.io.Serializable;

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
@TableName("tb_mongodb_field_name")
public class MongodbFieldName extends Model<MongodbFieldName>{

	private static final long serialVersionUID = 1L;

	/**
	 * 编号
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	
	/**
	 * 解析表ID编号
	 */
	@TableField(value="table_id")
	private Long tableId;
	
	/**
	 * 字段名称
	 */
	@TableField(value="field_name")
	private String fieldName;
	
	private String type;
	
	/**
	 * 是否主键：1-是,0-不是
	 */
	private String primary;
	
	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
