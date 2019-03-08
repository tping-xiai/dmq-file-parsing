package com.jfinteck.dmq.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.jfinteck.dmq.dto.MongodbFieldName;
import com.jfinteck.dmq.mapper.MongodbFieldNameMapper;
import com.jfinteck.dmq.service.IMongodbFieldNameService;

@Service
public class MongodbFieldNameServiceImpl extends ServiceImpl<MongodbFieldNameMapper, MongodbFieldName> implements IMongodbFieldNameService {

}
