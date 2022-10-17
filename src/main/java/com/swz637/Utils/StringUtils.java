package com.swz637.Utils;

/**
 * @ author: lsq637
 * @ since: 2022-09-26 11:38:15
 * @ describe: 自定义的字符串工具类
 */
public class StringUtils {

    /** 功能：根据传入的新字符串的长度，从尾部截取原字符串的一段并返回（常用于截取UUID的后面12位）
     * @param srcStr 原字符串
     * @param newStrLength 新字符串的长度
     * @return 返回被截取的字符串
     */
    public static String subStringFromTail(String srcStr, int newStrLength) {
        int length = srcStr.length();
        return srcStr.substring(length - newStrLength);
    }
}
