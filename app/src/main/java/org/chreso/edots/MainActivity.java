package org.chreso.edots;


import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class MainActivity extends EdotActivity {

    private static final String TAG = "Main";
    private ExecutorService myExecutor;
    private SearchView searchView;
    private ListView list;
    ClientAdapter clientAdapter;

    private DBHandler dbHandler;
    private final int EXTERNAL_STORAGE_PERMISSION_CODE = 23;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHandler = new DBHandler(this);

        searchView = findViewById(R.id.search_bar);

        list=findViewById(R.id.list);

        setPrefs(PreferenceManager
                .getDefaultSharedPreferences(this));
        //adapter=new ArrayAdapter(this,R.layout.list_item,R.id.text,nameList);
        ArrayList<Client> arrayOfClients = dbHandler.getLisOfClientDetailsFromDatabase();

        clientAdapter = new ClientAdapter(this,arrayOfClients);
        list.setAdapter(clientAdapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                MainActivity.this.clientAdapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                MainActivity.this.clientAdapter.getFilter().filter(s);
                return false;
            }
        });


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client client = (Client) adapterView.getItemAtPosition(i);
                TextView fName = (TextView)view.findViewById(R.id.fName);
                TextView lName = (TextView)view.findViewById(R.id.lName);
                TextView dob = (TextView)view.findViewById(R.id.sDob);
                TextView sex = (TextView)view.findViewById(R.id.sex);
                TextView phone = (TextView)view.findViewById(R.id.mPhone);
                openClientRecord(client.getUuid(),fName.getText().toString(), lName.getText().toString(), dob.getText().toString(),sex.getText().toString(), phone.getText().toString());
            }
        }
    );

       // getExternalStoragePermission();

}

    private void getExternalStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CODE);
        }
    }


    @Override
    public void onResume(){
        super.onResume();
        ArrayList<Client> arrayOfClients = dbHandler.getLisOfClientDetailsFromDatabase();
        clientAdapter = new ClientAdapter(this,arrayOfClients);
        list.setAdapter(clientAdapter);
    }



    private void openClientRecord(String uuid,String first_name, String l_name, String dob, String sex, String mobilePhoneNumber) {
        Bundle b = new Bundle();
        b.putString("uuid", uuid);
        b.putString("fName", first_name);
        b.putString("lName", l_name);
        b.putString("dob",dob);
        b.putString("sex",sex);
        b.putString("mobile", mobilePhoneNumber);
        Intent intent = new Intent(this, ClientMain.class);
        intent.putExtras(b);
        startActivity(intent);
    }






}