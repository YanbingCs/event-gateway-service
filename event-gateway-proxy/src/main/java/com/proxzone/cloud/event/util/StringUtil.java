package com.proxzone.cloud.event.util;

import java.util.List;

/**
 * @author mayanbin@proxzone.com
 * @version 1.0
 * @date 19-11-25 下午5:12
 */
public enum StringUtil {
    ;

    //获取List参数值
    public static String getListString(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (String s : list) {
            result.append(s).append(" ");
        }
        return result.toString();
    }
}
