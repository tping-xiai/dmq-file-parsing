package com.jfinteck.dmq.model.project.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * MongoDB连接参数
 * 
 * @author weijun.zhu
 * @date 2019年2月14日 上午9:37:20
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MongodbServer implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 编号
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
}
