package com.jfinteck.dmq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger項目接口配置类
 * 
 * @author admin
 * @date 2019-01-30 15:59
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestAPI() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())  //调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.jfinteck.dmq.controller"))
                .paths(PathSelectors.any())
				.build();
	}
	
	/**
	 * 构建API文档详情信息
	 * 
	 * @return
	 */
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("解析Mongodb数据存入MySQL数据库   RESTful APIS")  // 页面标题
				.description("Api默认访问地址为：http://localhost:8088/swagger-ui.html")  // 页面描述
				.version("1.0.0")
				.build();
	}
	
}
