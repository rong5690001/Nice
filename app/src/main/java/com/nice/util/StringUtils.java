package com.nice.util;

import java.text.SimpleDateFormat;

/**
 * Created by chen on 2016/1/31.
 */
public class StringUtils {

    public static String formatDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("YYMMDD");
        return format.format(date);
    }
}
