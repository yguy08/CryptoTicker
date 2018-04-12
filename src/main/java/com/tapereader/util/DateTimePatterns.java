package com.tapereader.util;

import java.time.format.DateTimeFormatter;

public class DateTimePatterns {
    
    /**
     * The date time format: MMM dd, yyyy
     */
    public static DateTimeFormatter MMM_dd_COMMA_yyyy = DateTimeFormatter.ofPattern("MMM dd, yyyy");
    
    /**
     * The date time format: YYYYMMDD
     */
    public static DateTimeFormatter YYYYMMDD = DateTimeFormatter.BASIC_ISO_DATE;

}
