package com.fengfujie.product.simple_http_server.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * 时间处理类
 * @Auther: fujie.feng
 * @Date: 2019-04-03
 */
public class DateUtils {

    //格式Tue, 02 Apr 2019 09:46:40 GMT
    private final static String DATE_FORMAT_PATTERN = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";

    public static String getDate() {
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_PATTERN, Locale.CHINA);
        dateFormat.setTimeZone(new SimpleTimeZone(0, "GMT"));

        String date = dateFormat.format(new Date());

        return date;
    }
}
