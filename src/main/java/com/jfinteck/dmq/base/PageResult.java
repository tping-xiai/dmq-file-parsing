package com.jfinteck.dmq.base;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 分页结果
 * 
 * @author admin
 * @date 2019-01-31 15:46
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(Include.NON_NULL)
public class PageResult<T> {

	@ApiModelProperty("页码，从1页开始")
	private Integer pageNum;
	
	@ApiModelProperty("一页显示条数")
	private Integer pageSize;
	
	@ApiModelProperty("总条数")
	private Long total;
	
	@ApiModelProperty("总页数")
	private Integer pages;
	
	@ApiModelProperty("返回数据")
	private List<T> list;
}
