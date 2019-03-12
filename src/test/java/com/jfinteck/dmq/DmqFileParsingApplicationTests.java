package com.jfinteck.dmq;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jfinteck.dmq.biz.MongoDataAnalysisBiz;
import com.jfinteck.dmq.dto.ExecutePlanDTO;
import com.jfinteck.dmq.dto.MongodbServer;
import com.jfinteck.dmq.mapper.ExecutePlanMapper;
import com.jfinteck.dmq.service.IMongodbServerService;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class DmqFileParsingApplicationTests {

	@Autowired
    private IMongodbServerService mongodbServerService;
	
	@Autowired
	private ExecutePlanMapper executePlanMapper;
	@Autowired
	private MongoDataAnalysisBiz mongoDataAnalysisBiz;
	
	@Test
	public void testInsert() {
		MongodbServer mongodb = new MongodbServer();
		mongodb.setHost("localhost");
		mongodb.setPort("27017");
		mongodb.setTags("本地测试MongoDB数据库");
		mongodbServerService.insert(mongodb);
	}
	
	@Test
	public void testSelectExecutePlan() {
		String executeId = "EXECUTE_20190311101011304";
		ExecutePlanDTO planDTO = executePlanMapper.selectExecuteById(executeId);
		System.out.println(planDTO.toString());
	}
	
	@Test
	public void testAnalysisMongoData() {
		String executeId = "EXECUTE_20190311101011304";
		mongoDataAnalysisBiz.start(executeId);
	}
	
}

