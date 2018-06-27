package com.amazon.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 获取系统当前时间
     * @return yyyy-MM-dd HH:mm:ss SS
     */
    public static String getSystemTime (){
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
//        String nowDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date());
//        Date dateNow = format.parse(nowDate);
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS").format(new Date());
    }

    public static String getSystemTimeInMM (){
        return new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
    }
    public static SimpleDateFormat getFormat(){
        return new SimpleDateFormat("yyyy-MM-dd");
    }

}
