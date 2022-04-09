package org.chreso.edots;

import android.util.Log;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncOperations {
    public void sendClientDataToServer() {
        DateTimeFormatter formatter_3 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String str_date_3 = "24-09-2019";
        LocalDate local_date_3 = LocalDate.parse(str_date_3, formatter_3);

        ApiInterface apiInterface = RetrofitClient.getRetrofitInstance().create(ApiInterface.class);
        Call<Client> call = apiInterface.getClientData("Johnnie",
                "Daka",
                local_date_3,
                "male",
                "+260978895623");
        call.enqueue(new Callback<Client>() {
            @Override
            public void onResponse(Call<Client> call, Response<Client> response) {
                Log.e("Send Client Data","onResponse: "+response.code());
                Log.e("Send Client Data","onResponse: first_name : "+response.body().getFirst_name());
                Log.e("Send Client Data","onResponse: last_name : "+response.body().getLast_name());
                //Log.e(TAG,"onResponse: date_of_birth : "+response.body().getDate_of_birth());
                Log.e("Send Client Data","onResponse: mobile_phone_number : "+response.body().getMobile_phone_number());
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.e("Send Client Data","onFailure: "+t.getMessage());

            }
        });

    }

    public void getClientDataFromServer(){

    }

    public void startDataSync(){

    }
}
