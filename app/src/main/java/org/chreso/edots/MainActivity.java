package org.chreso.edots;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private Button btnPostData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPostData = findViewById(R.id.btnPostData);
        btnPostData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doBtnPostDataTasks();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.search_item:
                openSeachForm();
                return true;
            case R.id.sync_item:
                startDataSync();
                return true;
            case R.id.settings_item:
                openSettingsForm();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openSeachForm() {
    }

    private void startDataSync() {
    }

    private void openSettingsForm() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void doBtnPostDataTasks() {
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
                Log.e(TAG,"onResponse: "+response.code());
                Log.e(TAG,"onResponse: first_name : "+response.body().getFirst_name());
                Log.e(TAG,"onResponse: last_name : "+response.body().getLast_name());
                //Log.e(TAG,"onResponse: date_of_birth : "+response.body().getDate_of_birth());
                Log.e(TAG,"onResponse: mobile_phone_number : "+response.body().getMobile_phone_number());
            }

            @Override
            public void onFailure(Call<Client> call, Throwable t) {
                Log.e(TAG,"onFailure: "+t.getMessage());

            }
        });

    }
}