package com.nice.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by chen on 2016/1/31.
 */
public class StringUtils {

    public static String formatDate(String date){
        SimpleDateFormat format = new SimpleDateFormat("YYMMDD");
        return format.format(date);
    }

    public static String getCurrentTime(){
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        return format.format(calendar.getTime());
    }
}
