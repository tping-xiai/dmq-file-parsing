package com.jfinteck.dmq.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = false)
@TableName("tb_mongodb_server")
public class MongodbServer extends Model<MongodbServer> {

	private static final long serialVersionUID = 1L;
	// 编号
	@TableId(type = IdType.AUTO)
	private Long id;
	// 连接host
	@NotEmpty
	private String host;
	// 连接端口号
	@NotEmpty
	private String port;
	// 用户名称
	private String username;
	// 登录密码
	private String password;
	// 描述
	private String tags;
	// 标识 1-可用，0-不可用
	private String flag;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}
}
