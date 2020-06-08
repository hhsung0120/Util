package com.heeseong.util.util;

public class FormatUtil {


    /**
     * 천 단위 콤마
     * @param source 입력 값
     * @return String
     */
    public static String addComma(int source) {
        if (StringUtil.isEmpty(String.valueOf(source))) {
            return "";
        }
        return String.format("%,d", source);
    }

    /**
     * 문자열 내부의 콤마 모두 제거
     * @param source 입력 문자열
     * @return String
     */
    public static String removeComma(String source) {
        return StringUtil.removeCharacter(source, ',');
    }
}
