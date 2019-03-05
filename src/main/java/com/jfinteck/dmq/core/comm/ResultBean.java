package com.jfinteck.dmq.core.comm;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jfinteck.dmq.core.enums.ErrorEnum;
import com.jfinteck.dmq.core.utils.DateUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Description: 公共响应数据
 * @author Zack
 *
 * @param <T>
 */
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultBean<T> {

    private String version;

    private String responseNo;

    private String responseTime;

    private String code;

    private String msg;

    private T responseData;
    
    public static class Builder<T> {

        private String version;

        private String responseNo;

        private String responseTime;

        private String code;

        private String msg;

        private T responseData;

        public Builder<T> version(String version) {
            this.version = version;
            return this;
        }
        
        public Builder<T> responseNo(String responseNo) {
            this.responseNo = responseNo;
            return this;
        }
        
        public Builder<T> responseTime(String responseTime) {
            this.responseTime = responseTime;
            return this;
        }
        
        public Builder<T> code(String code) {
            this.code = code;
            return this;
        }
        
        public Builder<T> msg(String msg) {
            this.msg = msg;
            return this;
        }
        
        public Builder<T> responseData(T responseData) {
            this.responseData = responseData;
            return this;
        }
        
        public ResultBean<T> build() {
            this.version = "v1";
            this.responseNo = DateUtil.getyyyyMMddHHmmssCurDate();
            this.responseTime = DateUtil.getyyyyMMddHHmmssCurDate();
            return new ResultBean<T>(this);
        }
        
        public ResultBean<T> build(ErrorEnum ee) {
            return this.code(ee.getCode()).msg(ee.getDesc()).build();
        }
        
        public ResultBean<T> build(ErrorEnum ee, T responseData) {
            return this.code(ee.getCode()).msg(ee.getDesc()).responseData(responseData).build();
        }
        
        public ResultBean<T> buildSucceed() {
            return this.build(ErrorEnum.SUCCESS);
        }
        
        public ResultBean<T> buildSucceed(T responseData) {
            return this.build(ErrorEnum.SUCCESS, responseData);
        }

        public ResultBean<T> buildSucceed(ErrorEnum ee, T responseData) {
            return this.build(ee, responseData);
        }
    }

    private ResultBean(Builder<T> builder) {
        this.version = builder.version;
        this.responseNo = builder.responseNo;
        this.responseTime = builder.responseTime;
        this.responseData = builder.responseData;
        this.code = builder.code;
        this.msg = builder.msg;
    }

}
