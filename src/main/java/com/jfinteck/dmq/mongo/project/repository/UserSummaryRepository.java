package com.jfinteck.dmq.mongo.project.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.jfinteck.dmq.mongo.project.entity.UserSummary;

/**
 * 定义重置MonogoDB表userSummary表数据 
 * 
 * @author admin
 * @date 2019-02-13 09:27:34
 * @version 1.0
 */
public interface UserSummaryRepository extends MongoRepository<UserSummary, ObjectId>{

}
