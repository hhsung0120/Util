package com.heeseong.util.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * 자바 8부터 java.time 패키지 활용
 * localDate : 날짜 정보만 필요할때
 * localDateTime : 날짜와 시간 모두 필요할때
 * localtime : 시간 정보만 필요할때
 */
public class DateUtil {

    /**
     * LocalDate 객체 반환
     * @return LocalDate
     */
    private static LocalDate localDate(){
        return LocalDate.now();
    }

    /**
     * LocalDate.parse 객체 반환
     * @param target yyyy-MM-dd
     * @return LocalDate.parse(tartget);
     */
    private static LocalDate parseLocalDate(String target){
        return LocalDate.parse(target);
    }

    /**
     * LocalDateTime 객체 반환
     * @return LocalDateTime
     */
    private static LocalDateTime localDateTime(){
        return LocalDateTime.now();
    }

    /**
     * LocalDateTime.parse 객체 반환
     * @param target yyyy-MM-dd HH:mm:ss
     * @return LocalDateTime.parse(tartget);
     */
    private static LocalDateTime parseLocalDateTime(String target){
        return LocalDateTime.parse(target);
    }

    /**
     * 현재 년도
     * @return int
     */
    public static int getCurrentYear(){
        return localDate().getYear();
    }

    /**
     * 현재 월
     * @return int
     */
    public static int getCurrentMonth(){
        return localDate().getMonthValue();
    }

    /**
     * 현재 일
     * @return int
     */
    public static int getCurrentDay(){
        return localDate().getDayOfMonth();
    }

    /**
     * 운년 여부
     * @return boolean
     */
    public static boolean isLeapYear(){
        return localDate().isLeapYear();
    }
    /**
     * 오늘 날짜 : 년-월-일
     * BASIC_ISO_DATE : 20200621 리턴
     * @param delimiter 년원일 사이 구분자
     * @return String
     */
    public static String getToday(String delimiter){
        String result = "";
        String pattern = "yyyy"+delimiter+"MM"+delimiter+"dd";
        if(StringUtil.isEmpty(delimiter)){
            result = localDate().format(DateTimeFormatter.BASIC_ISO_DATE);
        }else{
            result = localDate().format(DateTimeFormatter.ofPattern(pattern));
        }
        return result;
    }

    /**
     * 오늘 날짜 : 년-월-일 시분초
     * @param delimiter 년원일 사이 구분자
     * @param isMillisecond 밀리 세컨드 여부
     * @return
     */
    public static String getTodayAndNowTime(String delimiter, boolean isMillisecond){
        String pattern = "yyyy"+delimiter+"MM"+delimiter+"dd"+" HH:mm:ss"+(isMillisecond ? ".SSS" : "");
        return localDateTime().format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 날짜만 계산 년-월-일 형태로 넘겨줘야함(안그럼 예외)
     * 해당월에 존재하지 않는 일을 넘겨도 예외
     * 빼기는 음수를 붙여서 넘기면 된다.
     * ex : -1 = 빼기 1
     * @param targetDate 계산할 년-월-일
     * @param year 계산할 년
     * @param month 계산할 월
     * @param day 계산할 일
     * @return String
     * @throws DateTimeParseException
     */
    public static String getCalculatorDate(String targetDate, int year, int month, int day) throws DateTimeParseException {
        LocalDate localDate = parseLocalDate(targetDate);
        localDate = localDate.plusYears(year);
        localDate = localDate.plusMonths(month);
        localDate = localDate.plusDays(day);
        return localDate.toString();
    }

    /**
     * 년도만 계산
     * 빼기는 음수를 붙여서 넘기면 된다.
     * @param targetDate 계산할 년-월-일
     * @param year 계산할 년
     * @return String
     * @throws DateTimeParseException
     */
    public static String getCalculatorDateForYear(String targetDate, int year) throws DateTimeParseException {
        return getCalculatorDate(parseLocalDate(targetDate).toString(), year, 0, 0);
    }

    /**
     * 월만 계산
     * 빼기는 음수를 붙여서 넘기면 된다.
     * @param targetDate 계산할 년-월-일
     * @param month 계산할 월
     * @return String
     * @throws DateTimeParseException
     */
    public static String getCalculatorDateForMonth(String targetDate, int month) throws DateTimeParseException {
        return getCalculatorDate(parseLocalDate(targetDate).toString(), 0, month, 0);
    }

    /**
     * 일만 계산
     * 빼기는 음수를 붙여서 넘기면 된다.
     * @param targetDate 계산할 년-월-일
     * @param day 계산할 일
     * @return String
     * @throws DateTimeParseException
     */
    public static String getCalculatorDateForDay(String targetDate, int day) throws DateTimeParseException {
        return getCalculatorDate(parseLocalDate(targetDate).toString(), 0, 0, day);
    }

    /**
     * 날짜&시간 계산 년-월-일THH:mm:ss 형태(안그럼 예외)
     * 2020-06-22T23:20:32 ISO 시간 표기법에 따라 'T'를 꼭 붙여야 함.
     * 빼기는 음수를 붙여서 ex : -1 = 빼기 1
     * @param targetDate 계산할 년-월-일 시:분:초
     * @param year 계산할 년
     * @param month 계산할 월
     * @param day 계산할 일
     * @param hour 계산할 시간
     * @param minute 계산할 분
     * @param second 계산할 초
     * @return
     * @throws DateTimeParseException
     */
    public static String getCalculatorDateAndTime(String targetDate, int year, int month, int day, int hour, int minute, int second) throws DateTimeParseException{
       LocalDateTime localDateTime = parseLocalDateTime(targetDate);
       localDateTime = localDateTime.plusYears(year);
       localDateTime = localDateTime.plusMonths(month);
       localDateTime = localDateTime.plusDays(day);
       localDateTime = localDateTime.plusHours(hour);
       localDateTime = localDateTime.plusMinutes(minute);
       localDateTime = localDateTime.plusSeconds(second);
       return localDateTime.toString();
    }




/*



    public static int getDaysDiff(String sDate1, String sDate2) {
        String dateStr1 = validChkDate(sDate1);
        String dateStr2 = validChkDate(sDate2);

        if (!checkDate(sDate1) || !checkDate(sDate2)) {
            throw new IllegalArgumentException("Invalid date format: args[0]=" + sDate1 + " args[1]=" + sDate2);
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());

        Date date1 = null;
        Date date2 = null;
        try {
            date1 = sdf.parse(dateStr1);
            date2 = sdf.parse(dateStr2);
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: args[0]=" + dateStr1 + " args[1]=" + dateStr2);
        }
        int days1 = (int)((date1.getTime()/3600000)/24);
        int days2 = (int)((date2.getTime()/3600000)/24);

        return days2 - days1;
    }

    *//**
     * <p>yyyyMMdd 혹은 yyyy-MM-dd 형식의 날짜 문자열을 입력 받아 유효한 날짜인지 검사.</p>
     *
     * <pre>
     * DateUtil.checkDate("1999-02-35") = false
     * DateUtil.checkDate("2000-13-31") = false
     * DateUtil.checkDate("2006-11-31") = false
     * DateUtil.checkDate("2006-2-28")  = false
     * DateUtil.checkDate("2006-2-8")   = false
     * DateUtil.checkDate("20060228")   = true
     * DateUtil.checkDate("2006-02-28") = true
     * </pre>
     *
     * @param  dateStr 날짜 문자열(yyyyMMdd, yyyy-MM-dd의 형식)
     * @return  유효한 날짜인지 여부
     *//*
    public static boolean checkDate(String sDate) {
        String dateStr = validChkDate(sDate);

        String year  = dateStr.substring(0,4);
        String month = dateStr.substring(4,6);
        String day   = dateStr.substring(6);

        return checkDate(year, month, day);
    }

    *//**
     * <p>입력한 년, 월, 일이 유효한지 검사.</p>
     *
     * @param  year 연도
     * @param  month 월
     * @param  day 일
     * @return  유효한 날짜인지 여부
     *//*
    public static boolean checkDate(String year, String month, String day) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy.MM.dd", Locale.getDefault());

            Date result = formatter.parse(year + "." + month + "." + day);
            String resultStr = formatter.format(result);
            if (resultStr.equalsIgnoreCase(year + "." + month + "." + day))
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }

    *//**
     * 날짜형태의 String의 날짜 포맷 및 TimeZone을 변경해 주는 메서드
     *
     * @param  strSource       바꿀 날짜 String
     * @param  fromDateFormat  기존의 날짜 형태
     * @param  toDateFormat    원하는 날짜 형태
     * @param  strTimeZone     변경할 TimeZone(""이면 변경 안함)
     * @return  소스 String의 날짜 포맷을 변경한 String
     *//*
    public static String convertDate(String strSource, String fromDateFormat,
                                     String toDateFormat, String strTimeZone) {
        SimpleDateFormat simpledateformat = null;
        Date date = null;
        String _fromDateFormat = "";
        String _toDateFormat = "";

        if(StringUtil.isNullToString(strSource).trim().equals("")) {
            return "";
        }
        if(StringUtil.isNullToString(fromDateFormat).trim().equals(""))
            _fromDateFormat = "yyyyMMddHHmmss";                    // default값
        if(StringUtil.isNullToString(toDateFormat).trim().equals(""))
            _toDateFormat = "yyyy-MM-dd HH:mm:ss";                 // default값

        try {
            simpledateformat = new SimpleDateFormat(_fromDateFormat, Locale.getDefault());
            date = simpledateformat.parse(strSource);
            if (!StringUtil.isNullToString(strTimeZone).trim().equals("")) {
                simpledateformat.setTimeZone(TimeZone.getTimeZone(strTimeZone));
            }
            simpledateformat = new SimpleDateFormat(_toDateFormat, Locale.getDefault());
        }
        catch(Exception exception) {
            Logger.getLogger(DateUtil.class).debug(exception);//exception.printStackTrace();
        }
        if(simpledateformat != null && simpledateformat.format(date) != null ){
            return simpledateformat.format(date);
        }else {
            return "";
        }

    }


    *//**
     * 연도를 입력 받아 해당 연도 2월의 말일(일수)를 문자열로 반환한다.
     *
     * @param year
     * @return 해당 연도 2월의 말일(일수)
     *//*
    public static String leapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return "29";
        }

        return "28";
    }

    *//**
     * <p>입력받은 연도가 윤년인지 아닌지 검사한다.</p>
     *
     * <pre>
     * DateUtil.isLeapYear(2004) = false
     * DateUtil.isLeapYear(2005) = true
     * DateUtil.isLeapYear(2006) = true
     * </pre>
     *
     * @param  year 연도
     * @return  윤년 여부
     *//*
    public static boolean isLeapYear(int year) {
        if (year % 4 == 0 && year % 100 != 0 || year % 400 == 0) {
            return false;
        }
        return true;
    }


    *//**
     * 입력받은 일자 사이의 임의의 일자를 반환
     * @param sDate1 시작일자
     * @param sDate2 종료일자
     * @return 임의일자
     *//*
    public static String getRandomDate(String sDate1, String sDate2) {
        String dateStr1 = validChkDate(sDate1);
        String dateStr2 = validChkDate(sDate2);

        String randomDate   = null;

        int sYear, sMonth, sDay;
        int eYear, eMonth, eDay;

        sYear  = Integer.parseInt(dateStr1.substring(0, 4));
        sMonth = Integer.parseInt(dateStr1.substring(4, 6));
        sDay   = Integer.parseInt(dateStr1.substring(6, 8));

        eYear  = Integer.parseInt(dateStr2.substring(0, 4));
        eMonth = Integer.parseInt(dateStr2.substring(4, 6));
        eDay   = Integer.parseInt(dateStr2.substring(6, 8));

        GregorianCalendar beginDate = new GregorianCalendar(sYear, sMonth-1, sDay,    0, 0);
        GregorianCalendar endDate   = new GregorianCalendar(eYear, eMonth-1, eDay,   23,59);

        if (endDate.getTimeInMillis() < beginDate.getTimeInMillis()) {
            throw new IllegalArgumentException("Invalid input date : " + sDate1 + "~" + sDate2);
        }

        SecureRandom r = new SecureRandom();

        long rand = ((r.nextLong()>>>1)%( endDate.getTimeInMillis()-beginDate.getTimeInMillis() + 1)) + beginDate.getTimeInMillis();

        GregorianCalendar cal = new GregorianCalendar();
        //SimpleDateFormat calformat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat calformat = new SimpleDateFormat("yyyyMMdd",Locale.ENGLISH);
        cal.setTimeInMillis(rand);
        randomDate = calformat.format(cal.getTime());

        // 랜덤문자열를 리턴
        return  randomDate;
    }



    *//**
     * 입력받은 요일의 영문명을 국문명의 요일로 반환
     * @param sWeek 영문 요일명
     * @return 국문 요일명
     *//*
    public static String convertWeek(String sWeek) {
        String retStr = null;

        if        (sWeek.equals("SUN")   ) { retStr = "일요일";
        } else if (sWeek.equals("MON")   ) { retStr = "월요일";
        } else if (sWeek.equals("TUE")   ) { retStr = "화요일";
        } else if (sWeek.equals("WED")   ) { retStr = "수요일";
        } else if (sWeek.equals("THR")   ) { retStr = "목요일";
        } else if (sWeek.equals("FRI")   ) { retStr = "금요일";
        } else if (sWeek.equals("SAT")   ) { retStr = "토요일";
        }

        return retStr;
    }

    *//**
     * 입력일자의 유효 여부를 확인
     * @param sDate 일자
     * @return 유효 여부
     *//*
    public static boolean validDate(String sDate) {
        String dateStr = validChkDate(sDate);

        Calendar cal ;
        boolean ret  = false;

        cal = Calendar.getInstance() ;

        cal.set(Calendar.YEAR        , Integer.parseInt(dateStr.substring(0,4)));
        cal.set(Calendar.MONTH       , Integer.parseInt(dateStr.substring(4,6))-1 );
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6,8)));

        String year  = String.valueOf(cal.get(Calendar.YEAR        )    );
        String month = String.valueOf(cal.get(Calendar.MONTH       ) + 1);
        String day   = String.valueOf(cal.get(Calendar.DAY_OF_MONTH)    );

        String pad4Str = "0000";
        String pad2Str = "00";

        String retYear  = (pad4Str + year ).substring(year .length());
        String retMonth = (pad2Str + month).substring(month.length());
        String retDay   = (pad2Str + day  ).substring(day  .length());

        String retYMD = retYear+retMonth+retDay;

        if(sDate.equals(retYMD)) {
            ret  = true;
        }

        return ret;
    }

    *//**
     * 입력일자, 요일의 유효 여부를 확인
     * @param     sDate 일자
     * @param     sWeek 요일 (DAY_OF_WEEK)
     * @return    유효 여부
     *//*
    public static boolean validDate(String sDate, int sWeek) {
        String dateStr = validChkDate(sDate);

        Calendar cal ;
        boolean ret  = false;

        cal = Calendar.getInstance() ;

        cal.set(Calendar.YEAR        , Integer.parseInt(dateStr.substring(0,4)));
        cal.set(Calendar.MONTH       , Integer.parseInt(dateStr.substring(4,6))-1 );
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dateStr.substring(6,8)));

        int    Week  =                cal.get(Calendar.DAY_OF_WEEK      );

        if (validDate(sDate)) {
            if (sWeek == Week) {
                ret = true;
            }
        }

        return ret;
    }

    *//**
     * 입력시간의 유효 여부를 확인
     * @param     sTime 입력시간
     * @return    유효 여부
     *//*
    public static boolean validTime(String sTime) {
        String timeStr = validChkTime(sTime);

        Calendar cal ;
        boolean ret = false;

        cal = Calendar.getInstance() ;

        cal.set(Calendar.HOUR_OF_DAY  , Integer.parseInt(timeStr.substring(0,2)));
        cal.set(Calendar.MINUTE       , Integer.parseInt(timeStr.substring(2,4)));

        String HH     = String.valueOf(cal.get(Calendar.HOUR_OF_DAY  ));
        String MM     = String.valueOf(cal.get(Calendar.MINUTE       ));

        String pad2Str = "00";

        String retHH = (pad2Str + HH).substring(HH.length());
        String retMM = (pad2Str + MM).substring(MM.length());

        String retTime = retHH + retMM;

        if(sTime.equals(retTime)) {
            ret  = true;
        }

        return ret;
    }

    *//**
     * 입력된 일자에 연, 월, 일을 가감한 날짜의 요일을 반환
     * @param sDate 날짜
     * @param year 연
     * @param month 월
     * @param day 일
     * @return 계산된 일자의 요일(DAY_OF_WEEK)
     *//*
    public static String addYMDtoWeek(String sDate, int year, int month, int day) {
        String dateStr = validChkDate(sDate);

        dateStr = addYearMonthDay(dateStr, year, month, day);

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd",Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        SimpleDateFormat rsdf = new SimpleDateFormat("E",Locale.ENGLISH);

        return rsdf.format(cal.getTime());
    }

    *//**
     * 입력된 일자에 연, 월, 일, 시간, 분을 가감한 날짜, 시간을 포멧스트링 형식으로 반환
     * @param sDate 날짜
     * @param sTime 시간
     * @param year 연
     * @param month 월
     * @param day 일
     * @param hour 시간
     * @param minute 분
     * @param formatStr 포멧스트링
     * @return
     *//*
    public static String addYMDtoDayTime(String sDate, String sTime, int year, int month, int day, int hour, int minute, String formatStr) {
        String dateStr = validChkDate(sDate);
        String timeStr = validChkTime(sTime);

        dateStr = addYearMonthDay(dateStr, year, month, day);

        dateStr = convertDate(dateStr, timeStr, "yyyyMMddHHmm");

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm",Locale.ENGLISH);

        try {
            cal.setTime(sdf.parse(dateStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }

        if (hour != 0) {
            cal.add(Calendar.HOUR, hour);
        }

        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }

        SimpleDateFormat rsdf = new SimpleDateFormat(formatStr,Locale.ENGLISH);

        return rsdf.format(cal.getTime());
    }

    *//**
     * 입력된 일자를 int 형으로 반환
     * @param sDate 일자
     * @return int(일자)
     *//*
    public static int datetoInt(String sDate) {
        return Integer.parseInt(convertDate(sDate, "0000", "yyyyMMdd"));
    }

    *//**
     * 입력된 시간을 int 형으로 반환
     * @param sTime 시간
     * @return int(시간)
     *//*
    public static int timetoInt(String sTime) {
        return Integer.parseInt(convertDate("00000101", sTime, "HHmm"));
    }

    *//**
     * 입력된 일자 문자열을 확인하고 8자리로 리턴
     * @param sDate
     * @return
     *//*
    public static String validChkDate(String dateStr) {
        String _dateStr = dateStr;

        if (dateStr == null || !(dateStr.trim().length() == 8 || dateStr.trim().length() == 10)) {
            throw new IllegalArgumentException("Invalid date format: " + dateStr);
        }
        if (dateStr.length() == 10) {
            _dateStr = StringUtil.removeMinusChar(dateStr);
        }
        return _dateStr;
    }

    *//**
     * 입력된 일자 문자열을 확인하고 8자리로 리턴
     * @param sDate
     * @return
     *//*
    public static String validChkTime(String timeStr) {
        String _timeStr = timeStr;

        if (_timeStr.length() == 5) {
            _timeStr = StringUtil.remove(_timeStr,':');
        }
        if (_timeStr == null || !(_timeStr.trim().length() == 4)) {
            throw new IllegalArgumentException("Invalid time format: " + _timeStr);
        }

        return _timeStr;
    }

    *//**
     * 해당 월의 마지막 날짜를 반환한다.
     * @param year
     * @param month
     * @return
     *//*
    public static int getLastDate (int year, int month) {

        Calendar calendar = calendar();

        year = year == 0 ? getCurrentYear() : year;
        month = (month == 0 || month > 12) ? getCurrentMonth() : month;
        calendar.set(Calendar.DAY_OF_YEAR, year);
        calendar.set(Calendar.MONTH, month-1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getActualMaximum(Calendar.DATE);
    }

    *//**
     * 입력된 날짜를 yyyy-MM-dd 형태로 변환
     * @param sDate
     * @return
     *//*
    public static String dataFormatConvert(String sDate) {

        return FormatUtil.formatDate(sDate, "-");
    }

    *//**
     * <pre>
     * 1. MethodName : getCurrentDate
     * 2. ClassName  : DateUtil.java
     * 3. Comment    : 현재날짜와 시간을 밀리초 까지 반환 yyyyMMddHHmmssSSS
     * </pre>
     *
     * @return
     *//*
    public static String getCurrentDateMilli()
    {
        Calendar today = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String day = sdf.format(today.getTime());

        return day;
    }*/
}