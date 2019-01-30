package com.jfinteck.dmq.mongo.project.entity;

import lombok.Data;

@Data
public class ProjectEntity {

    /**
     * 项目编号
     */
    private Long id;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目描述
     */
    private String projectRemark;

    /**
     * 接口说明
     */
    private String inExplain;

    /**
     * 创建人
     */
    private String operator;

    /**
     * 状态：0-启用 1-禁用
     */
    private String state;

}
