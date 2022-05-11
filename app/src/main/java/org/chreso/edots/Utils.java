package org.chreso.edots;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
    public static String getFormattedDate(Date date){
        if(date == null){
            return null;
        }
        String pattern = Constants.DATE_PATTERN;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date);
    }
    public static String getFormattedDateTime(Date date){
        if(date == null){
            return null;
        }
        String pattern = Constants.DATETIME_PATTERN;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.format(date).replace(" ", "T");
    }
}
