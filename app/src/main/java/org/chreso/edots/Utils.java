package org.chreso.edots;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.widget.DatePicker;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

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

    public static String getNewUuid(){
        return String.valueOf(UUID.randomUUID());
    }


    public static String getFormattedTime(Time refill_date_time) {
        if(refill_date_time==null){
            return null;
        }
        String pattern = Constants.TIME_PATTERN;
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(refill_date_time);
    }

    public static String getDateFromDatePicker(DatePicker datePicker){
        int day  = datePicker.getDayOfMonth();
        int month = datePicker.getMonth()+1;
        int year = datePicker.getYear();

        String date = year + "-"+ month + "-" +day;
        return date;
    }


}
