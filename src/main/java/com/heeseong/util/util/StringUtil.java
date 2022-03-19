package com.heeseong.util.util;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.Map;

public class StringUtil {

    /**
     * 공백, null 검사
     *
     * @param input 입력 문자열
     * @return boolean
     */
    public static boolean isEmpty(String input) {
        return input == null || input.length() == 0;
    }

    /**
     * 입력 문자열 안에 들어있는 문자 삭제
     *
     * @param input      입력 문자열
     * @param removeChar 삭제할 문자
     * @return String
     */
    public static String removeCharacter(String input, char removeChar) {
        StringBuilder result = new StringBuilder("");
        if (isEmpty(input)) {
            result.append(input);
        } else {
            char[] chars = input.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] != removeChar) {
                    result.append(String.valueOf(chars[i]));
                }
            }
        }
        return result.toString();
    }

    /**
     * @param input         입력 문자열
     * @param compare       비교 문자열
     * @param output        sourceString == compareString 리턴 문자열
     * @param defaultString sourceString != compareString 리턴 문자열
     * @return String
     */
    public static String decode(String input, String compare, String output, String defaultString) {
        if (input == null && compare == null) {
            return output;
        }

        if (input == null && compare != null) {
            return defaultString;
        }

        if (input.trim().equals(compare.trim())) {
            return output;
        }

        return defaultString;
    }

    /**
     * @param input   입력 문자열
     * @param compare 비교 문자열
     * @param output  sourceString == compareString 리턴 문자열
     * @return String
     */
    public static String decode(String input, String compare, String output) {
        return decode(input, compare, output, input);
    }

    /**
     * 향후 object 로 파라메터 변환 및 클래스 검사하여 처리 또는 몇가지 선택하여
     *
     * @param map
     */
    public static void searchReset(Map<String, Object> map) {
        Iterator<String> it = map.keySet().iterator();
        while (it.hasNext()) {
            String itv = it.next();

            if (itv.indexOf("search") == -1 && itv.indexOf("cPage") == -1 && itv.indexOf("pageSize") == -1 && itv.indexOf("SeqNo") == -1) {
                it.remove();
            }
        }
    }

    /**
     * 길이 만큼 랜덤 난수 생성
     *
     * @param length 길이
     * @return String
     */
    public static String getRandomNumber(int length) {
        String randomNumber = "";
        for (int i = 0; i < length; i++) {
            randomNumber += String.valueOf(((int) (Math.random() * 9) + 1));
        }
        return randomNumber;
    }

    /**
     * 클라이언트 IP 호출
     *
     * @param request
     * @return String
     */
    public static String getClientIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-FORWARDED-FOR");

        if (ip == null) {
            ip = request.getHeader("PROXY-CLIENT-IP");
        }
        if (ip == null) {
            ip = request.getHeader("WL-PROXY-CLIENT-IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}