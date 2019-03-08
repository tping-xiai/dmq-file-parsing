package com.jfinteck.dmq;

public class TestMain {

	public static void main(String[] args) {
		String perfix = "admin.system.version";
		//System.out.println(perfix.startsWith("admin."));
		
		//System.out.println(perfix.substring("admin.".length(), perfix.length()));
		
		String userid = "userIdAndName3";
		
		char[] chzr = userid.toCharArray();
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < chzr.length; i++) {
			if( chzr[i] >= 'A' && chzr[i] <= 'Z') {
				builder.append("_");
				builder.append((chzr[i] + "").toLowerCase());
			}else {
				builder.append(chzr[i]);
			}
		}
		System.out.println(builder.toString());
	}
}
