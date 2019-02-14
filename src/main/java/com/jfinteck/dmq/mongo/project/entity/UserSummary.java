package com.jfinteck.dmq.mongo.project.entity;

import java.math.BigInteger;
import java.util.Date;

import com.jfinteck.dmq.base.BeanEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class UserSummary extends BeanEntity{
	
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

	@Override
	public String toString() {
		return "UserSummary [userId=" + userId + ", weight=" + weight + ", level=" + level + ", beatRate=" + beatRate
				+ ", levelNum=" + levelNum + ", createTime=" + createTime + ", updateTime=" + updateTime + ", id=" + id
				+ "]";
	}
	
}
