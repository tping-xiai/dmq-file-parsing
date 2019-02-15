package com.jfinteck.dmq.mongo;

import java.math.BigInteger;
import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import com.jfinteck.dmq.base.PageResult;
import com.jfinteck.dmq.component.MongoPageHelper;
import com.jfinteck.dmq.model.project.dao.UserSummaryDao;
import com.jfinteck.dmq.model.project.dto.UserSummaryDTO;
import com.jfinteck.dmq.mongo.project.entity.UserSummary;
import com.jfinteck.dmq.mongo.project.entity.UserSummaryFunction;
import com.jfinteck.dmq.mongo.project.repository.UserSummaryRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoUserSummaryTest {

	@Autowired
	private UserSummaryRepository userSummaryRepository;
	
	@Autowired
	private MongoPageHelper mongoPageHelper;
	
	@Autowired
	private UserSummaryDao userSummaryDao;
	
	@Test
	public void initData() {
		
		log.info("开始初始化数据....");
		
		long startTime = System.currentTimeMillis();
		
		UserSummary userSummary = null;
		for (int i = 10000; i < 1000000; i++) {
			userSummary = new UserSummary();
			userSummary.setId(new ObjectId());
			userSummary.setUserId(new BigInteger((1000 + i) + ""));
			userSummary.setWeight("80KG");
			userSummary.setLevel("80");
			userSummary.setBeatRate((Math.random() * 100 ) + "");
			userSummary.setLevelNum((int)Math.random() * 100);
			userSummary.setCreateTime(new Date());
			userSummary.setUpdateTime(new Date());
			
			log.info("保存数据：" + userSummary.toString());
			userSummaryRepository.save(userSummary);
		}
		
		long endTime = System.currentTimeMillis();
		
		log.info("初始化数据结束...所使时间为：{}", (endTime - startTime));
	}
	
	@Test
	public void findUserSummaryPage() {
		Query query = new Query(Criteria.where("_id").gt(new ObjectId("5c6379ff25eab50e28176138")));

		log.info("开始查询数据....");
		long startTime = System.currentTimeMillis();

		int pageSize = 100;
		int pageNum = 1;
		PageResult<UserSummaryDTO> pageQequest = mongoPageHelper.pageQuery(query, UserSummary.class,
				new UserSummaryFunction(), pageSize, pageNum);

		System.out.println(pageQequest.getList().size());
		long endTime = System.currentTimeMillis();

		log.info("结束查询数据，使用时间：" + (endTime - startTime));

		for (UserSummaryDTO userSummary : pageQequest.getList()) {
			System.out.println(userSummary.toString());
		}

		//userSummaryDao.batchInsert(pageQequest.getList());
	}
	
}
