package com.jfinteck.dmq.biz;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jfinteck.dmq.component.MongoOperatingComponent;
import com.jfinteck.dmq.core.comm.DefaultComm;
import com.jfinteck.dmq.core.page.IMongoPage;
import com.jfinteck.dmq.core.utils.SecUtil;
import com.jfinteck.dmq.dto.ExecutePlanDTO;
import com.jfinteck.dmq.dto.MongodbFieldName;
import com.jfinteck.dmq.dto.MongodbTableName;
import com.jfinteck.dmq.service.IExecutePlanService;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MongoDataAnalysisBiz {

	@Autowired
	private MongoOperatingComponent mongoComponent;
	@Autowired
	private IExecutePlanService executePlanService;
	@Autowired
	private MySQLDataAnalysisBiz mySQLDataAnalysisBiz;
	
	public int start(String executeId) {
		if( StringUtils.isEmpty(executeId) ) return 0;
		return analysis(executeId);
	}

	@SuppressWarnings("unchecked")
	private int analysis(String executeId) {
		
		log.info("【开始解析】MongoDB数据库中数据...");
		Long startTime = System.currentTimeMillis();
		
		ExecutePlanDTO plan = executePlanService.findExecuteById(executeId);
		
		// 通过集合名称，获取Document文档集合
		MongoCollection<Document> collection = initMongoCollection(plan);
		if( collection == null ) {
			log.info("【结束解析】executeId = {} 任务计划不存在.", executeId);
			return 0;
		}
		
		MongodbTableName table = plan.getMongoTable();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String filterCondit = table.getFilterCondit();
		if( !StringUtils.isEmpty(filterCondit) ) {
			paramMap = JSONObject.toJavaObject(JSONObject.parseObject(table.getFilterCondit()), Map.class);
		}
		
		long total = collection.count();
		log.info("MongoDB数据库[{}]集合一共有 {} 条数据.", table.getCollName(), total);
		
		long count = collection.count(new Document(paramMap));
		if( count <= 0 ) {
			log.info("【结束解析】解析MongoDB数据，未找到符合条件的记录!");
			return 0;
		}
		log.info("集合[{}]需要被解析的一共有 {} 条数据.", table.getCollName(), count);
		
		int pageTotal = getPageTotal(count);
		log.info("总页数为：" + pageTotal);
		
		Object id = null;
		for(int page = 0; page < pageTotal; page++) {
			FindIterable<Document> documnets = null;
			if( page == IMongoPage.DEFAULT_PAGE_NUM ) {
				// 第一页
				documnets = collection.find(new Document(paramMap))
						.sort(new BasicDBObject("_id", 1))
						.limit(IMongoPage.DEFAULT_PAGE_SIZE);
			}else {
				// 第一页之后
				if( id != null ) {
					// 获取大于 id 的值
					paramMap.put("_id", new Document("$gt", id));
				}
				System.out.println(paramMap);
				documnets = collection.find(new Document(paramMap))
						.sort(new BasicDBObject("_id", 1))
						.limit(IMongoPage.DEFAULT_PAGE_SIZE);
			}
			id = handleMongoDataAnalysis((page + 1), documnets, plan.getTableName(), plan.getMongoField(), count, collection, paramMap);
		}
		
		return 0;
	}

	private Object handleMongoDataAnalysis(int page, FindIterable<Document> documnets, String tableName, 
			List<MongodbFieldName> fieldNames, long count, MongoCollection<Document> collection, Map<String, Object> paramMap) {
		if( documnets == null ) {
			log.info("本页 {} 数据为空!", page);
			return null;
		}
		Long beginTime = System.currentTimeMillis();
		List<Map<String, Object>> columnMap = new ArrayList<Map<String, Object>>();
		documnets.forEach(getBlock(fieldNames, columnMap));
		log.info("批量处理第 {} 页数据，查询MongoDB数据条数：{}，耗时：{}毫秒", page, columnMap.size(), (System.currentTimeMillis() - beginTime));
		
		/**
		 * 校验当前页获取的数据条数是否有误
		 * eg: page * pageSize == (page -1) * pageSize + list.size()
		 */
		// 当前页，一共获取到的条数
		long completeTotal = IMongoPage.DEFAULT_PAGE_SIZE * (page -1) + columnMap.size();
		// 当前页，实际要获取到的条数
		long shouldTotal = IMongoPage.DEFAULT_PAGE_SIZE * page;
		if( shouldTotal > completeTotal && completeTotal < count ) {
			/**
			 * 则为还有数据没有获取完，所以既要继续获取当前页，剩下的所有数据
			 */
			if( completeTotal < count ) {
				// 没有获取完，则获取当前剩下的所有条数
				int limit = (int)(shouldTotal - completeTotal);
				columnMap.addAll(handleSupplementData(collection, paramMap, fieldNames, (int)completeTotal, limit));
			}
		}
		
		/**
		 * 数据落库
		 */
		mySQLDataAnalysisBiz.executeBatch(columnMap);
		
		return columnMap.get(columnMap.size() -1).get("_id");
	}

	/**
	 * 补充当前页，未获取到的数据
	 * 
	 * 原因：由于_id的类型不一致导致，在排序后，按照上一页的最后一条_id小于当前页的所有_id，
	 *      若由_id是String，后面又变成ObjectId类型时，就会获取不到后面的数据.
	 * 
	 * @param collection
	 * @param paramMap
	 * @param fieldNames
	 * @return
	 */
	private List<Map<String, Object>> handleSupplementData(MongoCollection<Document> collection,
			Map<String, Object> paramMap, List<MongodbFieldName> fieldNames, int completeTotal, int limit) {
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Long beginTime = System.currentTimeMillis();
		paramMap.remove("_id");
		FindIterable<Document> documents = 
				collection.find(new Document(paramMap))
				.sort(new BasicDBObject("_id", 1))
				.skip(completeTotal)
				.limit(limit);
		documents.forEach(getBlock(fieldNames, list));
		int page = (int)Math.floor( completeTotal / IMongoPage.DEFAULT_PAGE_SIZE ) + 1;
		log.info("批量处理第 {} 页数据，补充查询MongoDB数据条数：{}，耗时：{}毫秒", page, list.size(), (System.currentTimeMillis() - beginTime));
		return list;
	}
	
	private Block<Document> getBlock(List<MongodbFieldName> fieldNames, List<Map<String, Object>> columnMap){
		return new Block<Document>() {
			@Override
			public void apply(Document doc) {
				System.out.println("documnet：" + doc.toJson());
				Map<String, Object> map = new HashMap<String, Object>();
				JSONObject jsonDoc = JSONObject.parseObject(doc.toJson());
				
				fieldNames.forEach(field -> {
					String name = field.getFieldName();
					// 有嵌套字段
					if( DefaultComm.DEFAULT_TRUE.equals(field.getNest()) ) {
						
					}else {
						// 字段要解密
						if( DefaultComm.DEFAULT_TRUE.equals(field.getDecryption())) {
							map.put(name, SecUtil.descryption(jsonDoc.getString(name)));
						}else {
							map.put(name, jsonDoc.getString(name));
						}
					}
				});
				
				String _id = jsonDoc.getString("_id");
				if( _id.indexOf("$oid") != -1 ) {
					ObjectId objId = new ObjectId(JSONObject.parseObject(_id).getString("$oid"));
					map.put("_id", objId);
				}else {
					map.put("_id", _id);
				}
				columnMap.add(map);
			}
		};
	}

	private MongoCollection<Document> initMongoCollection(ExecutePlanDTO plan) {
		MongoCollection<Document> connection = null;
		if (plan == null) {
			log.info("未获取到要执行的解析MongoDB数据任务计划.");
			return connection;
		}

		MongodbTableName table = plan.getMongoTable();
		log.info("要解析MongoDB数据库的集合详情信息，collection：{}", JSON.toJSONString(table));
		
		// 创建MongoTemplate对象
		MongoDatabase database = mongoComponent.getMongoDatabase(plan.getMongoServer(), table.getDatabaseName());
		return database.getCollection(table.getCollName());
	}
	
	private int getPageTotal(long count) {
		return (count % IMongoPage.DEFAULT_PAGE_SIZE == 0) ? (int)(count / IMongoPage.DEFAULT_PAGE_SIZE) : ((int)Math.floor(count / IMongoPage.DEFAULT_PAGE_SIZE) + 1);
	}
	
}
