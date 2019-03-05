package com.jfinteck.dmq.core.utils;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ValidatorUtil {

	private static Validator validator = null;

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> Boolean hasError(T object) {

		Set<ConstraintViolation<T>> violations = validator.validate(object);

		if (violations.isEmpty()) {
			return Boolean.FALSE;
		}

		/**
		 * String str = "
		 *    参数校验异常：
		 *    1、不能为空
		 *    2、日期格式不对
		 *    3、数字必须大于零
		 * ";
		 */
		StringBuilder sb = new StringBuilder();

		for (Iterator iterator = violations.iterator(); iterator.hasNext();) {
			ConstraintViolation<T> constraintViolation = (ConstraintViolation<T>) iterator.next();
			sb.append(constraintViolation.getPropertyPath().toString()).append("【")
					.append(constraintViolation.getMessage()).append("】；");
		}

		log.error("参数校验异常：{} 校验对象：{}", sb.toString(), object.toString());

		return Boolean.TRUE;
	}
}
