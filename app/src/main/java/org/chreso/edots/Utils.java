package org.chreso.edots;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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


}
