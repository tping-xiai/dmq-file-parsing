package com.jfinteck.dmq.core.utils;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.jfinteck.dmq.dto.MongodbFieldName;

/**
 * @Description 动态拼接SQL语句
 */
public final class JoinSQLUtils {

	private static Logger LOGGER = LoggerFactory.getLogger(JoinSQLUtils.class);
	
	private static final String CREATE_TABLE_SQL = 
			"CREATE TABLE %s ( id int NOT NULL AUTO_INCREMENT, %s "
			+ " create_time datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间', "
			+ " update_time datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日期', "
			+ " primary key(id) ) ENGINE=InnoDB AUTO_INCREMENT=1018 DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci COMMENT='%s'; ";
	
	private static final String DEFAULT_COMMENT_DESC = "创建表_%s";
	
	/**
	 * 拼接创建表的SQL语句
	 * 
	 * @param tableName
	 * @param fields
	 * @param comment
	 * @return
	 */
	public static String create(String tableName, List<MongodbFieldName> fields, String comment) {
		Assert.notNull(tableName, "A tableName must be not null.");
		Assert.notNull(fields, "fields must be not null.");
		
		StringBuffer buf = new StringBuffer();
		fields.forEach(field -> {
			buf.append(field.getFieldName());
			buf.append(" ");
			buf.append(field.getType());
			buf.append(", ");
		});
		comment = StringUtils.isEmpty(comment) ? String.format(DEFAULT_COMMENT_DESC, tableName) : comment;
		return String.format(CREATE_TABLE_SQL, tableName, buf.toString(), comment);
	}
	
	/**
	 * 判断 tableName 是否存在
	 * 
	 * @param conn
	 * @param tableName
	 * @return
	 */
	public static boolean hasTable(Connection conn, String tableName) {
		try {
			DatabaseMetaData database = conn.getMetaData();
			ResultSet rs = database.getTables(null, null, tableName, null);
			if( rs.next()) {
				return true;
			}else {
				LOGGER.info("当前数据库中可以创建【{}】表.", tableName);
			}
		} catch (Exception e) {
			LOGGER.error("have is error: {}", e);
			return false;
		}
		
		return false;
	}
	
}
