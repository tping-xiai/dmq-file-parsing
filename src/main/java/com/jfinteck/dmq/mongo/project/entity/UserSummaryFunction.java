package com.jfinteck.dmq.mongo.project.entity;

import java.util.function.Function;

import com.jfinteck.dmq.model.project.dto.UserSummaryDTO;

/**
 * 一元函数的思想，将转换逻辑提取出来
 * 
 * @author admin
 *
 */
public class UserSummaryFunction implements Function<UserSummary, UserSummaryDTO> {

	@Override
	public UserSummaryDTO apply(UserSummary user) {
		return new UserSummaryDTO(user.getUserId(), user.getWeight(), user.getLevel(), 
				user.getBeatRate(), user.getLevelNum(), user.getCreateTime(), user.getUpdateTime());
	}

}
