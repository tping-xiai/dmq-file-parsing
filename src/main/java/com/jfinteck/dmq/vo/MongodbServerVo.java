package com.jfinteck.dmq.vo;

import com.jfinteck.dmq.core.base.BaseVo;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class MongodbServerVo extends BaseVo {

	private static final long serialVersionUID = 1L;

	// 连接host
	private String host;
	// 连接端口号
	private String port;
	// 用户名称
	private String username;
	// 登录密码
	private String password;
	// 描述
	private String tags;
	// 标识 1-可用，0-不可用
	private String flag;

}
