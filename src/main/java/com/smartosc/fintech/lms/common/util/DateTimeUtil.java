package com.smartosc.fintech.lms.common.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DateTimeUtil {
    private final static String FORMAT_DATE = "yyyy-MM-dd";
    private final static String FORMAT_TIMESTAMP = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

    public static String formatDate(Date date) {
        DateFormat format = new SimpleDateFormat(FORMAT_DATE);
        return format.format(date);
    }
    public static  String getFormatTimestamp(Timestamp date) {
        DateFormat format = new SimpleDateFormat(FORMAT_TIMESTAMP);
        return format.format(date);
    }
}
