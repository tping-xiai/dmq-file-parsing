package com.jfinteck.dmq.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class MongodbListDatabasesDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String name;
	
	private String sizeOnDisk;
	
}
