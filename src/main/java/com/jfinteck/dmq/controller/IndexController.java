package com.jfinteck.dmq.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	/**
	 * Index 页面
	 * @return
	 */
	@GetMapping("/index")
	public String index() {
		return "index";
	}
	
	/**
	 * 数据库列表
	 * @return
	 */
	@GetMapping("/databases")
	public String databases() {
		return "databases";
	}
	
	/**
	 * 解析页面
	 * @return
	 */
	@GetMapping("/analysis")
	public String analysis() {
		return "analysis";
	}
}
