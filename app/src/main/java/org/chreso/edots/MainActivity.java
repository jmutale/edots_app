package org.chreso.edots;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Main";
    private ExecutorService myExecutor;
    private SearchView searchView;
    private ListView list;
    ClientAdapter clientAdapter;
    private DBHandler dbHandler;
    private final int EXTERNAL_STORAGE_PERMISSION_CODE = 23;
    private ProgressBar progressBar;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);


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

    @Override
    protected void onStart() {
        super.onStart();
        //showWelcomeScreen();
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

    private void showWelcomeScreen() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("eDOT Application")
                .setMessage("Welcome to the eDOT App. Search for or click on a client record to start.")
                .setCancelable(true)

                ;
        builder.create();
        builder.show();
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

            case R.id.sync_item:

                String server = Utils.getServerUrl(this);
                String token = PreferenceManager
                        .getDefaultSharedPreferences(this).getString("token",null);
                if(server!=null&&token!=null) {
                    if(Utils.isInternetAvailable(getApplicationContext())) {
                        //new SyncOperations(getApplicationContext()).startDataSync();
                        new SyncAsyncTask().execute();
                    }else{
                        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                                .setTitle("Network Error")
                                .setMessage("Please connect to the internet before you can sync data.")
                                .setCancelable(true)

                                ;
                        builder.create();
                        builder.show();
                    }


                }else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Server Error")
                            .setMessage("Please assign a server URL value and log in before you can sync data.")
                            .setCancelable(true)

                            ;
                    builder.create();
                    builder.show();
                }
                return true;
            case R.id.settings_item:
                openSettingsForm();
                return true;
            case R.id.register_client_item:
                openClientRegistrationForm();
                return true;

            case R.id.logout_item:
                logout();
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openClientRegistrationForm() {
        Intent intent = new Intent(this, RegisterClientActivity.class);
        startActivity(intent);
    }

    private void logout() {
        getPrefs().edit().remove("token").commit();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openSettingsForm() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public void setPrefs(SharedPreferences prefs) {
        this.prefs = prefs;
    }


    private class SyncAsyncTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            SyncOperations syncOperations = new SyncOperations(getApplicationContext());
            try {
                syncOperations.syncMedDrugs();
                publishProgress(13);
                Thread.sleep(1000);
                syncOperations.syncFacilityData();
                publishProgress(26);
                Thread.sleep(1000);
                syncOperations.syncClientData();
                publishProgress(39);
                Thread.sleep(1000);
                syncOperations.syncDrugDispensations();
                publishProgress(42);
                Thread.sleep(1000);
                syncOperations.syncClientStatusData();
                publishProgress(73);
                Thread.sleep(1000);
                syncOperations.syncClientFeedbackData();
                publishProgress(86);
                Thread.sleep(1000);
                syncOperations.syncClientEDOTSurvey();
                publishProgress(95);
                Thread.sleep(1000);
                syncOperations.syncClientTBLabData();
                publishProgress(100);
            }catch(InterruptedException e){

            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);

        }

        @Override
        protected void onPostExecute(Void unused) {
            super.onPostExecute(unused);
            progressBar.setProgress(0);
            progressBar.setVisibility(View.GONE);
            ArrayList<Client> arrayOfClients = dbHandler.getLisOfClientDetailsFromDatabase();
            clientAdapter = new ClientAdapter(MainActivity.this,arrayOfClients);
            list.setAdapter(clientAdapter);
        }
    }

}