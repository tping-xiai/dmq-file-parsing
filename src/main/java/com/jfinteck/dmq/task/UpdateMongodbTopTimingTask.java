/*
 * package com.jfinteck.dmq.task;
 * 
 * import java.math.BigDecimal; import java.util.ArrayList; import
 * java.util.Date; import java.util.List; import java.util.Map;
 * 
 * import org.apache.commons.lang3.StringUtils; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.scheduling.annotation.Scheduled; import
 * org.springframework.stereotype.Component;
 * 
 * import com.alibaba.fastjson.JSONObject; import
 * com.jfinteck.dmq.component.MongoOperatingComponent; import
 * com.jfinteck.dmq.core.base.Constant; import
 * com.jfinteck.dmq.core.base.TimingTask; import
 * com.jfinteck.dmq.dto.MongodbServerDTO; import
 * com.jfinteck.dmq.model.dao.MongodbServerDao; import
 * com.jfinteck.dmq.model.project.dao.MongodbSyncTableDao; import
 * com.jfinteck.dmq.model.project.dao.MongodbTopHistoryDao; import
 * com.jfinteck.dmq.model.project.dto.MongodbSyncTable; import
 * com.jfinteck.dmq.model.project.dto.MongodbTopHistory; import
 * com.jfinteck.dmq.model.project.dto.MongodbTopHistoryExample; import
 * com.jfinteck.dmq.model.project.dto.MongodbTopHistoryExample.Criteria; import
 * com.mongodb.BasicDBObject; import com.mongodb.DBObject;
 * 
 * import lombok.extern.slf4j.Slf4j;
 * 
 *//**
	 * 定时任务监控MongoDB数据库的操作
	 * 
	 * @author weijun.zhu
	 * @date 2019年2月14日 上午10:18:00
	 * @version 1.0
	 */
/*
 * @Slf4j
 * 
 * @Component public class UpdateMongodbTopTimingTask implements TimingTask{
 * 
 *//**
	 * 默认设置定时任务时间间隙
	 */
/*
 * private final static long DEAFULT_LONG_TIME = 3000;
 * 
 * @Autowired private MongoOperatingComponent mongoOperatingComponent;
 * 
 * @Scheduled(fixedDelay = DEAFULT_LONG_TIME) public void execute() {
 * 
 * // 获取需要监控MongoDB的服务 List<MongodbServerDTO> mongodbs =
 * mongodbServerDao.findAll(); if( mongodbs == null || mongodbs.isEmpty()) {
 * log.info("[TimingTask] mongodbServers is null. 暂无 MongoDB Server 服务可监控.");
 * }else { for (MongodbServerDTO mongodbServer : mongodbs) { Long id =
 * mongodbServer.getId();
 * 
 * // 获取该MongoDB服务下要监控的表 List<MongodbSyncTable> syncTables =
 * mongodbSyncTableDao.findByServerId(id);
 * 
 * DBObject dbObject = new BasicDBObject("top", 1);
 * 
 * JSONObject jsonObject =
 * mongoOperatingComponent.getMongodbCommandResult(mongodbServer, dbObject);
 * 
 * if( jsonObject != null ) { // 获取需要的数据 JSONObject total =
 * jsonObject.getJSONObject("totals");
 * 
 * // 去掉不需要的数据 total.remove("ok"); total.remove("note");
 * 
 * // 遍历数组 List<MongodbTopHistory> mongodbTopHistories = new
 * ArrayList<MongodbTopHistory>(); for (Map.Entry<String, Object> entry :
 * total.entrySet()) { JSONObject value =
 * JSONObject.parseObject(entry.getValue().toString());
 * 
 * String namespace = entry.getKey(); // 判断当前 namespace 是否要监控的表空间 if(
 * checkMongodbNameSpace(namespace, syncTables) ) { MongodbTopHistory
 * mongodbTopHistory = initMongodbTopHistory(id, namespace, value);
 * mongodbTopHistories.add(mongodbTopHistory); } } // 集合不为空时，插入数据库 if(
 * !mongodbTopHistories.isEmpty() )
 * mongodbTopHistoryDao.batchInsert(mongodbTopHistories); } } } }
 * 
 *//**
	 * 根据用户添加的需要监控的表空间，减少不必要的操作
	 * 
	 * @param namespace
	 * @param syncTables
	 * @return
	 */
/*
 * private boolean checkMongodbNameSpace(String namespace,
 * List<MongodbSyncTable> syncTables) {
 * 
 * if( StringUtils.isEmpty(namespace) ) return false;
 * 
 * // 若用户未设置需要监控的表空间，则进行全部的监控操作 if( syncTables == null || syncTables.isEmpty() )
 * return true;
 * 
 *//**
	 * e.g. namespace = my_mongodb.userSummary
	 */
/*
 * boolean flag = false; for (MongodbSyncTable mongodbSyncTable : syncTables) {
 * // 如果表名称不为空，则先比较表名 if( !StringUtils.isEmpty(mongodbSyncTable.getTableName())
 * && namespace.endsWith(mongodbSyncTable.getTableName())) { flag = true; break;
 * }
 * 
 * // 再次比较匹配前缀名称 if( !StringUtils.isEmpty(mongodbSyncTable.getPrefixName()) &&
 * namespace.startsWith(mongodbSyncTable.getPrefixName())) { flag = true; break;
 * } } return flag; }
 * 
 *//**
	 * 初始化数据
	 * 
	 * @param id    MongoDB 服务编号
	 * @param ns    命名空间名称
	 * @param value
	 * @return
	 */
/*
 * private MongodbTopHistory initMongodbTopHistory(Long id, String ns,
 * JSONObject value) { MongodbTopHistory mongodbTopHistory = new
 * MongodbTopHistory(); mongodbTopHistory.setNs(ns); Long totalCount =
 * value.getJSONObject("total").getLong("count");
 * mongodbTopHistory.setTotalCount(totalCount);
 * mongodbTopHistory.setTotalTime(value.getJSONObject("total").getLong("time"));
 * Long commandsCount = value.getJSONObject("commands").getLong("count");
 * mongodbTopHistory.setCommandsCount(commandsCount);
 * mongodbTopHistory.setCommandsTime(value.getJSONObject("commands").getLong(
 * "time")); Date createTime = new Date();
 * mongodbTopHistory.setCreateTime(createTime); Long getMoreCount =
 * value.getJSONObject("getmore").getLong("count");
 * mongodbTopHistory.setGetMoreCount(getMoreCount);
 * mongodbTopHistory.setGetMoreTime(value.getJSONObject("getmore").getLong(
 * "time")); // 插入 Long insertCount =
 * value.getJSONObject("insert").getLong("count");
 * mongodbTopHistory.setInsertCount(insertCount);
 * mongodbTopHistory.setInsertTime(value.getJSONObject("insert").getLong("time")
 * ); // 查询 Long queriesCount = value.getJSONObject("queries").getLong("count");
 * mongodbTopHistory.setQueriesCount(queriesCount);
 * mongodbTopHistory.setQueriesTime(value.getJSONObject("queries").getLong(
 * "time"));
 * 
 * Long readLockCount = value.getJSONObject("readLock").getLong("count");
 * mongodbTopHistory.setReadLockCount(readLockCount);
 * mongodbTopHistory.setReadLockTime(value.getJSONObject("readLock").getLong(
 * "time")); // 删除 Long removeCount =
 * value.getJSONObject("remove").getLong("count");
 * mongodbTopHistory.setRemoveCount(removeCount);
 * mongodbTopHistory.setRemoveTime(value.getJSONObject("remove").getLong("time")
 * ); mongodbTopHistory.setServerId(id); // 更新 Long updateCount =
 * value.getJSONObject("update").getLong("count");
 * mongodbTopHistory.setUpdateCount(updateCount);
 * mongodbTopHistory.setUpdateTime(value.getJSONObject("update").getLong("time")
 * );
 * 
 * Long writeLockCount = value.getJSONObject("writeLock").getLong("count");
 * mongodbTopHistory.setWriteLockCount(writeLockCount);
 * mongodbTopHistory.setWriteLockTime(value.getJSONObject("writeLock").getLong(
 * "time"));
 * 
 * MongodbTopHistory lastMongodbTopHistory = findLastMongodbTopHistryByNs(id,
 * ns); if (lastMongodbTopHistory != null) { Date lastTime =
 * lastMongodbTopHistory.getCreateTime();
 * 
 * Long lastGetMoreCount = lastMongodbTopHistory.getGetMoreCount(); String
 * getMoreCountPersecond = getCountPersecond(lastGetMoreCount, getMoreCount,
 * lastTime, createTime);
 * mongodbTopHistory.setGetMoreCountPersecond(getMoreCountPersecond);
 * 
 * Long lastInsertCount = lastMongodbTopHistory.getInsertCount(); String
 * insertCountPersecond = getCountPersecond(lastInsertCount, insertCount,
 * lastTime, createTime);
 * mongodbTopHistory.setInsertCountPersecond(insertCountPersecond);
 * 
 * Long lastQueriesCount = lastMongodbTopHistory.getQueriesCount(); String
 * queriesCountPersecond = getCountPersecond(lastQueriesCount, queriesCount,
 * lastTime, createTime);
 * mongodbTopHistory.setQueriesCountPersecond(queriesCountPersecond);
 * 
 * Long lastReadLockCount = lastMongodbTopHistory.getReadLockCount(); String
 * readLockCountPersecond = getCountPersecond(lastReadLockCount, readLockCount,
 * lastTime, createTime);
 * mongodbTopHistory.setReadLockCountPersecond(readLockCountPersecond);
 * 
 * Long lastRemoveCount = lastMongodbTopHistory.getRemoveCount(); String
 * removeCountPersecond = getCountPersecond(lastRemoveCount, removeCount,
 * lastTime, createTime);
 * mongodbTopHistory.setRemoveCountPersecond(removeCountPersecond);
 * 
 * Long lastTotalCount = lastMongodbTopHistory.getTotalCount(); String
 * totalCountPersecond = getCountPersecond(lastTotalCount, totalCount, lastTime,
 * createTime); mongodbTopHistory.setTotalCountPersecond(totalCountPersecond);
 * 
 * Long lastUpdateCount = lastMongodbTopHistory.getUpdateCount(); String
 * updateCountPersecond = getCountPersecond(lastUpdateCount, updateCount,
 * lastTime, createTime);
 * mongodbTopHistory.setUpdateCountPersecond(updateCountPersecond);
 * 
 * Long lastWriteLockCount = lastMongodbTopHistory.getWriteLockCount(); String
 * writeLockCountPersecond = getCountPersecond(lastWriteLockCount,
 * writeLockCount, lastTime, createTime);
 * mongodbTopHistory.setWriteLockCountPersecond(writeLockCountPersecond); }
 * 
 * return mongodbTopHistory; }
 * 
 *//**
	 * 根据名称空间获取数据库中最新的一条TOP数据
	 * 
	 * @param serverId
	 * @param ns
	 * @return
	 *//*
		 * private MongodbTopHistory findLastMongodbTopHistryByNs(Long serverId, String
		 * ns) { MongodbTopHistoryExample example = new MongodbTopHistoryExample();
		 * Criteria createCriteria = example.createCriteria();
		 * createCriteria.andServerIdEqualTo(serverId); createCriteria.andNsEqualTo(ns);
		 * example.setOrderByClause("id desc"); example.setLimitStart(1);
		 * example.setPageSize(1);
		 * 
		 * List<MongodbTopHistory> list = mongodbTopHistoryDao.selectByExample(example);
		 * MongodbTopHistory mongodbTopHistory = null; if( list != null &&
		 * !list.isEmpty() ) { mongodbTopHistory = list.get(0); } return
		 * mongodbTopHistory; }
		 * 
		 * private String getCountPersecond(Long lastCount, Long currentCount, Date
		 * lastTime, Date currentTime) { if ((currentCount == null) || (lastCount ==
		 * null)) { return Constant.ZERO.toString(); }
		 * 
		 * if (new BigDecimal(currentCount).compareTo(new BigDecimal(lastCount)) == 1) {
		 * Long second = diffSecond(lastTime, currentTime); Long count = currentCount -
		 * lastCount; BigDecimal resultBigDecimal = new BigDecimal(count).divide(new
		 * BigDecimal(second), 2, BigDecimal.ROUND_HALF_EVEN);
		 * 
		 * String result = resultBigDecimal.toString(); // 如果为负数则设为零 if
		 * (result.indexOf("-") != -1) { result = Constant.ZERO.toString(); } return
		 * result; } else { return Constant.ZERO.toString(); } }
		 * 
		 * private Long diffSecond(Date lastTime, Date currentTime) { long second =
		 * (currentTime.getTime() - lastTime.getTime()) / 1000; //如果秒数相差为0，则置为1 if
		 * (second == 0) { second = 1L; } return second; } }
		 */