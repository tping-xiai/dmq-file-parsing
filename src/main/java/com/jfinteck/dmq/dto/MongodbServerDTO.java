package com.jfinteck.dmq.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MongodbServerDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;
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
	
	private String status;
	
	private String uptime;
	
}
