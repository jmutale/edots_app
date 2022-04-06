package org.chreso.edots;

import java.net.MalformedURLException;
import java.net.URL;
import android.util.Log;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;
    private static final String TAG = "RetrofitClient";
    private static URL myURL;
    public static Retrofit getRetrofitInstance(){
        try {
            myURL = new URL("http://192.168.0.111:8000/");

        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(myURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }
        return retrofit;
    }
}
