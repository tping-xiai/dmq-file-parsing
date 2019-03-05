package com.jfinteck.dmq.core.enums;

import lombok.Getter;

/**
  *  公共枚举。
 * 
 * @author weijun.zhu
 * @date 2019年3月5日 上午10:48:46
 * @version 1.0
  * 系统基础业务错误码范围：00000 ～ 99999；错误码固定前缀DATA。
 */
public enum ErrorEnum {

	SUCCESS("00000", "成功"),
	FAILD("99999", "失败"),
	VALIDATE_EXCEPTION("DATA11", "参数校验异常");
	
	@Getter
	private String code;
	@Getter
	private String desc;
	
	ErrorEnum(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
}
