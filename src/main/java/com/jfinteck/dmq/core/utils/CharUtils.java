package com.jfinteck.dmq.core.utils;

/**
 * 字符转工具 
 */
public final class CharUtils {

	/**
	 * 将字符串中大写改为小写字母，并将大写字母前面添加'_'
	 *  
	 *  eg: userId -> user_id
	 * 
	 * @param str
	 * @return
	 */
	public static String toLowerCase(String str) {
		if( str == null || str == "" ) return str;
		char[] chzr = str.toCharArray();
		
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < chzr.length; i++) {
			if( chzr[i] >= 'A' && chzr[i] <= 'Z') {
				builder.append("_");
				builder.append((chzr[i] + "").toLowerCase());
			}else {
				builder.append(chzr[i]);
			}
		}
		return builder.toString();
	}
	
}
