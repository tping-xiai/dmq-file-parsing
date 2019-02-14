package com.jfinteck.dmq.model.project.dto;

import java.math.BigInteger;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserSummaryDTO {

	// 用户id/手机号
	private BigInteger userId;

	// 和码体重(KG)
	private String weight;

	// 重量级
	private String level;

	// 击败率
	private String beatRate;

	// 同吨位人数
	private Integer levelNum;

	private Date createTime;

	private Date updateTime;

}
