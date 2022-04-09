package org.chreso.edots;


import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.concurrent.ExecutorService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends EdotActivity {

    private static final String TAG = "Main";
    private Button btnPostData;
    private ExecutorService myExecutor;
    private EditText textBox;
    private TextView text;
    private ListView list;
    private DBHandler dbHandler;
    ArrayAdapter adapter;

    private SharedPreferences prefs;

    String patient[] = {"John","Terry","Henry","Chisanga","Mildred","Thomas","Mutale","Steven","Trevor"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHandler = new DBHandler(getApplicationContext());
        //myExecutor = Executors.newSingleThreadExecutor();
        textBox=(EditText)findViewById(R.id.textBox);
        text=(TextView)findViewById(R.id.text);
        list=(ListView)findViewById(R.id.list);

        prefs = PreferenceManager
                .getDefaultSharedPreferences(this);

        adapter=new ArrayAdapter(this,R.layout.list_item,R.id.text,patient);
        list.setAdapter(adapter);


        textBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                openClientRecord();
            }
        }
    );
}





    private void openClientRecord() {
        Intent intent = new Intent(this, ClientMain.class);
        startActivity(intent);
    }

    private void startDataSync() {

        //Handler myHandler = new Handler(Looper.getMainLooper());

        //myExecutor.execute(new NetworkTask());
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(prefs.getString("server",null))
                .build();

        ApiInterface api = retrofit.create(ApiInterface.class);

        Call<List<MedDrug>> call = api.getMedDrugs();

        call.enqueue(new Callback<List<MedDrug>>() {
            @Override
            public void onResponse(Call<List<MedDrug>> call, Response<List<MedDrug>> response) {
                for (MedDrug drug: response.body()) {

                    dbHandler.addNewMedDrug(drug.getUuid(), drug.getGeneric_name(), drug.getBrand_name(), drug.getFormulation(), drug.getGeneric_ingredients(), drug.getGeneric_strength());
                }
            }

            @Override
            public void onFailure(Call<List<MedDrug>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }




}