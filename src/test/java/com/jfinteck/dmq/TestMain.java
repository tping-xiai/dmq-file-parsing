package com.jfinteck.dmq;

public class TestMain {

	public static void main(String[] args) {
		String perfix = "admin.system.version";
		System.out.println(perfix.startsWith("admin"));
		System.out.println(perfix.endsWith("version"));
	}
}
