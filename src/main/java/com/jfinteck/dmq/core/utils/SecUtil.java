package com.jfinteck.dmq.core.utils;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

import com.jfpal.security.JFSecurity;

/**
 * @Description: 敏感数据加解密、脱敏处理
 */
public class SecUtil {

	/**
     * @Description: 信息加密
     * @param text
     * @return
     */
    public static String encryption(String text) {
        if (StringUtils.isBlank(text)) return text;
        return JFSecurity.jfEncryption(text);
    }

    /**
     * @Description: 信息解密
     * @param text
     * @return
     */
    public static String descryption(String text) {
        if (StringUtils.isBlank(text)) return text;
        return new String(JFSecurity.jfDescryption(text));
    }

    /**
     * @Description: 信息摘要
     * @param text
     * @return
     */
    public static String encMsg(String text) {
        if (StringUtils.isBlank(text)) return text;
        return JFSecurity.jfEncodeMessage(text);
    }

    /**
     * @Description: 信息遮罩
     * @param text
     * @param front
     * @param end
     * @return
     */
    public static String maskString(String text, Integer front, Integer end) {
        if (StringUtils.isBlank(text)) return text;
        if (Objects.isNull(front) || Objects.isNull(end)) {
            return JFSecurity.maskString(text);
        }
        return JFSecurity.maskString(text, front.intValue(), end.intValue());
    }
}
