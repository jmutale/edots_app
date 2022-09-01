package org.chreso.edots;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.GestureDetector;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import androidx.preference.PreferenceManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

    public static boolean isInternetAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(connectivityManager!=null){
            NetworkInfo [] networkInfo = connectivityManager.getAllNetworkInfo();
            if(networkInfo!=null){
                for(int i=0;i<networkInfo.length;i++){
                    if(networkInfo[i].getState()==NetworkInfo.State.CONNECTED){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public static String getServerUrl(Context context){
        String server = PreferenceManager
                .getDefaultSharedPreferences(context).getString("server",null);
        return server;
    }


    public static String getAuthToken(Context context) {
        String toReturn = "";
        if(PreferenceManager
                .getDefaultSharedPreferences(context).getString("token",null)!=null){
            toReturn = PreferenceManager
                    .getDefaultSharedPreferences(context).getString("token",null);
        }
        return toReturn;
    }


    public static String getSelectedCheckboxValuesFromCheckboxGroup(LinearLayout layout) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<layout.getChildCount();i++)
        {
            View v = layout.getChildAt(i);
            if(v instanceof CheckBox){
                builder.append(((CheckBox)v).getText()).append(",");
            }
        }
        String toReturn = builder.toString();
        StringBuffer sb = new StringBuffer(toReturn);
        //remove trailing comma
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }
}
