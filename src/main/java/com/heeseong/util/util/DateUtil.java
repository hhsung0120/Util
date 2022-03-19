package com.heeseong.util.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * 자바 8부터 java.time 패키지 활용
 * localDate : 날짜 정보만 필요할때
 * localDateTime : 날짜와 시간 모두 필요할때
 * localtime : 시간 정보만 필요할때
 */
public class DateUtil {


    /**
     * LocalDate 객체 반환
     *
     * @return LocalDate
     * @throws Exception
     */
    public static LocalDate localDate() {
        return LocalDate.now();
    }

    /**
     * LocalDate.parse 객체 반환
     *
     * @param target yyyy-MM-dd
     * @return LocalDate.parse(tartget);
     * @throws Exception
     */
    public static LocalDate parseLocalDate(String target) {
        return LocalDate.parse(target);
    }

    /**
     * LocalDateTime 객체 반환
     *
     * @return LocalDateTime
     * @throws Exception
     */
    public static LocalDateTime localDateTime() {
        return LocalDateTime.now();
    }

    /**
     * LocalDateTime.parse 객체 반환
     *
     * @param target yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime.parse(tartget);
     * @throws Exception
     */
    public static LocalDateTime parseLocalDateTime(String target) {
        return LocalDateTime.parse(target);
    }

    /**
     * 현재 년도
     *
     * @return int
     * @throws Exception
     */
    public static int getCurrentYear() {
        return localDate().getYear();
    }

    /**
     * 현재 월
     *
     * @return int
     * @throws Exception
     */
    public static int getCurrentMonth() {
        return localDate().getMonthValue();
    }

    /**
     * 현재 일
     *
     * @return int
     * @throws Exception
     */
    public static int getCurrentDay() {
        return localDate().getDayOfMonth();
    }

    /**
     * 운년 여부
     *
     * @return boolean
     * @throws Exception
     */
    public static boolean isLeapYear() {
        return localDate().isLeapYear();
    }

    /**
     * 오늘 날짜 : 년-월-일
     * BASIC_ISO_DATE : 20200621 리턴
     *
     * @return LocalDate
     */
    public static LocalDate getToday() {
        return localDate();
    }

    /**
     * 오늘 날짜 : 년-월-일
     * BASIC_ISO_DATE : 20200621 리턴
     *
     * @param delimiter 년원일 사이 구분자
     * @return String
     * @throws Exception
     */
    public static String getToday(String delimiter) {
        String result = "";
        String pattern = "yyyy" + delimiter + "MM" + delimiter + "dd";

        try {
            if (StringUtil.isEmpty(delimiter)) {
                result = localDate().format(DateTimeFormatter.BASIC_ISO_DATE);
            } else {
                result = localDate().format(DateTimeFormatter.ofPattern(pattern));
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 오늘 날짜 : 년-월-일 시분초
     *
     * @return LocalDate
     */
    public static LocalDateTime getTodayAndNowTime() {
        return localDateTime();
    }

    /**
     * 오늘 날짜 : 년-월-일 시분초
     *
     * @param delimiter     년원일 사이 구분자
     * @param isMillisecond 밀리 세컨드 여부
     * @return String
     * @throws Exception
     */
    public static String getTodayAndNowTime(String delimiter, boolean isMillisecond) {
        String pattern = "";

        try {
            pattern = "yyyy" + delimiter + "MM" + delimiter + "dd" + " HH:mm:ss" + (isMillisecond ? ".SSS" : "");
        } catch (Exception e) {
            e.getMessage();
        }

        return localDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 오늘 날짜 : 년-월-일 시분초
     *
     * @param userFormat 사용자가 원하는 포멧
     * @return String
     */
    public static String getTodayAndNowTime(String userFormat) {
        String pattern = "";

        try {
            pattern = "yyyy-MM-dd HH:mm:ss";
            if (!StringUtil.isEmpty(userFormat)) {
                pattern = userFormat;
            }
        } catch (Exception e) {
            e.getMessage();
        }

        return localDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 년 월 일 계산
     * 날짜만 계산 년-월-일 형태로 넘겨줘야함(안그럼 예외)
     * 해당월에 존재하지 않는 일을 넘겨도 예외
     * 빼기는 음수를 붙여서 넘기면 된다.
     * ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일
     * @param year       계산할 년
     * @param month      계산할 월
     * @param day        계산할 일
     * @return String
     * @throws Exception
     */
    public static String getCalculatorDate(String targetDate, int year, int month, int day) {
        String result = "";

        try {
            LocalDate localDate = parseLocalDate(targetDate);
            localDate = localDate.plusYears(year);
            localDate = localDate.plusMonths(month);
            localDate = localDate.plusDays(day);
            result = localDate.toString();
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 년 월 계산
     * 년-월-일 형태로 넘겨줘야함(안그럼 예외)
     * 해당월에 존재하지 않는 일을 넘겨도 예외
     * 빼기는 음수를 붙여서 넘기면 된다.
     * ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일
     * @param year       계산할 년
     * @param month      계산할 월
     * @return String
     * @throws Exception
     */
    public static String getCalculatorDate(String targetDate, int year, int month) throws Exception {
        return getCalculatorDate(targetDate, year, month, 0);
    }

    /**
     * 년 계산
     * 년-월-일 형태로 넘겨줘야함(안그럼 예외)
     * 해당월에 존재하지 않는 일을 넘겨도 예외
     * 빼기는 음수를 붙여서 넘기면 된다.
     * ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일
     * @param year       계산할 년
     * @return String
     * @throws Exception
     */
    public static String getCalculatorDate(String targetDate, int year) throws Exception {
        return getCalculatorDate(targetDate, year, 0, 0);
    }

    /**
     * 날짜&시간 계산 년-월-일 HH:mm:ss 형태(안그럼 예외)
     * 2020-06-22T23:20:32 ISO 시간 표기법에 따라 'T'를 꼭 붙여야 함.
     * 빼기는 음수를 붙여서 ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일T시:분:초
     * @param year       계산할 년
     * @param month      계산할 월
     * @param day        계산할 일
     * @param hour       계산할 시간
     * @param minute     계산할 분
     * @param second     계산할 초
     * @return String
     * @throws Exception
     */
    public static String getCalculatorDateAndTime(String targetDate, int year, int month, int day, int hour, int minute, int second) {
        String result = "";

        try {
            LocalDateTime localDateTime = parseLocalDateTime(targetDate);
            localDateTime = localDateTime.plusYears(year);
            localDateTime = localDateTime.plusMonths(month);
            localDateTime = localDateTime.plusDays(day);
            localDateTime = localDateTime.plusHours(hour);
            localDateTime = localDateTime.plusMinutes(minute);
            localDateTime = localDateTime.plusSeconds(second);
            result = localDateTime.toString();
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 년 월 일 시 분 계산
     * 날짜&시간 계산 년-월-일THH:mm:ss 형태(안그럼 예외)
     * 2020-06-22T23:20:32 ISO 시간 표기법에 따라 'T'를 꼭 붙여야 함.
     * 빼기는 음수를 붙여서 ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일T시:분:초
     * @param year       계산할 년
     * @param month      계산할 월
     * @param day        계산할 일
     * @param hour       계산할 시간
     * @param minute     계산할 분
     * @return String
     */
    public static String getCalculatorDateAndTime(String targetDate, int year, int month, int day, int hour, int minute) {
        return getCalculatorDateAndTime(targetDate, year, month, day, hour, minute, 0);
    }

    /**
     * 년 월 일 시 계산
     * 2020-06-22T23:20:32 ISO 시간 표기법에 따라 'T'를 꼭 붙여야 함.
     * 빼기는 음수를 붙여서 ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일T시:분:초
     * @param year       계산할 년
     * @param month      계산할 월
     * @param day        계산할 일
     * @param hour       계산할 시간
     * @return String
     */
    public static String getCalculatorDateAndTime(String targetDate, int year, int month, int day, int hour) {
        return getCalculatorDateAndTime(targetDate, year, month, day, hour, 0, 0);
    }

    /**
     * 년 월 일 계산
     * 2020-06-22T23:20:32 ISO 시간 표기법에 따라 'T'를 꼭 붙여야 함.
     * 빼기는 음수를 붙여서 ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일T시:분:초
     * @param year       계산할 년
     * @param month      계산할 월
     * @param day        계산할 일
     * @return String
     */
    public static String getCalculatorDateAndTime(String targetDate, int year, int month, int day) {
        return getCalculatorDateAndTime(targetDate, year, month, day, 0, 0, 0);
    }

    /**
     * 년 월 계산
     * 2020-06-22T23:20:32 ISO 시간 표기법에 따라 'T'를 꼭 붙여야 함.
     * 빼기는 음수를 붙여서 ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일T시:분:초
     * @param year       계산할 년
     * @param month      계산할 월
     * @return String
     */
    public static String getCalculatorDateAndTime(String targetDate, int year, int month) {
        return getCalculatorDateAndTime(targetDate, year, month, 0, 0, 0, 0);
    }

    /**
     * 년 계산
     * 2020-06-22T23:20:32 ISO 시간 표기법에 따라 'T'를 꼭 붙여야 함.
     * 빼기는 음수를 붙여서 ex : -1 = 빼기 1
     *
     * @param targetDate 계산할 년-월-일T시:분:초
     * @param year       계산할 년
     * @return String
     */
    public static String getCalculatorDateAndTime(String targetDate, int year) {
        return getCalculatorDateAndTime(targetDate, year, 0, 0, 0, 0, 0);
    }

    /**
     * 2020-06-22
     * 이전 날짜 확인
     * sourceDate < compareDate = true
     * sourceDate > compareDate = false
     * sourceDate == compareDate = false
     *
     * @param sourceDate  시작 년-월-일
     * @param compareDate 비교 년-월-일
     * @return boolean
     */
    public static boolean isBeforeLocalDate(String sourceDate, String compareDate) {
        boolean result = false;

        try {
            LocalDate source = parseLocalDate(sourceDate);
            result = source.isBefore(parseLocalDate(compareDate));
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 2020-06-22
     * 지난 날짜 확인
     * sourceDate > compareDate = true
     * sourceDate < compareDate = false
     * sourceDate == compareDate = false
     *
     * @param sourceDate  시작 년-월-일
     * @param compareDate 비교 년-월-일
     * @return boolean
     */
    public static boolean isAfterLocalDate(String sourceDate, String compareDate) {
        boolean result = false;

        try {
            LocalDate source = parseLocalDate(sourceDate);
            result = source.isAfter(parseLocalDate(compareDate));
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 2020-06-22T22:57:33
     * 이전 날짜&시간 확인
     * sourceDate < compareDate = true
     * sourceDate > compareDate = false
     * sourceDate == compareDate = false
     *
     * @param sourceDate  시작 년월일
     * @param compareDate 비교 년월일
     * @return boolean
     */
    public static boolean isBeforeLocalDateTime(String sourceDate, String compareDate) {
        boolean result = false;

        try {
            LocalDateTime source = parseLocalDateTime(sourceDate);
            result = source.isBefore(parseLocalDateTime(compareDate));
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 2020-06-22T22:57:33
     * 지난 날짜&시간 확인
     * sourceDate > compareDate = true
     * sourceDate < compareDate = false
     * sourceDate == compareDate = false
     *
     * @param sourceDate  시작 년월일
     * @param compareDate 비교 년월일
     * @return boolean
     */
    public static boolean isAfterLocalDateTime(String sourceDate, String compareDate) {
        boolean result = false;

        try {
            LocalDateTime source = parseLocalDateTime(sourceDate);
            result = source.isAfter(parseLocalDateTime(compareDate));
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 날짜 차이 계산
     * 2020-06-23 - 2020-06-22 = 1
     * 2020-06-23 - 2020-06-24 = -1
     * 앞에꺼 - 뒤에꺼
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static int getDateDiffPeriodForLocalDate(String startDate, String endDate) {
        int result = 0;

        try {
            LocalDate source = parseLocalDate(endDate);
            result = (int) ChronoUnit.DAYS.between(source, parseLocalDate(startDate));
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }

    /**
     * 날짜 차이 계산(시분초까지 계산해줌)
     * 2020-06-23T23:12:45 - 2020-06-22T23:12:45 = 1
     * 2020-07-23T23:59:59 - 2020-07-24T23:59:58 = 0
     * 2020-07-23T23:59:58 - 2020-07-24T23:59:59 = -1
     * 앞에꺼 - 뒤에꺼
     *
     * @param startDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static int getDateDiffPeriodForLocalDateTime(String startDate, String endDate) {
        int result = 0;

        try {
            LocalDateTime source = parseLocalDateTime(endDate);
            result = (int) ChronoUnit.DAYS.between(source, parseLocalDateTime(startDate));
        } catch (Exception e) {
            e.getMessage();
        }

        return result;
    }
}    
