package com.jfinteck.dmq.dto;

import lombok.Data;

@Data
public class MongodbOverviewDTO {

	private String host;
    private String port;
    private String version;
    private String pid;
    private String uptime;
    private String isMaster;
    
}
