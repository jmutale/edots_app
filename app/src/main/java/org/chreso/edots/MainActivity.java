package org.chreso.edots;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Toast;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private TextView txtSyncMessage;
    private TextView txtFirstNameWelcome;
    private TextView currentDate;
    private Calendar calendar;
    private String date;
    private SimpleDateFormat simpleDateFormat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        dbHandler = new DBHandler(this);

        searchView = findViewById(R.id.search_bar);

        currentDate = findViewById(R.id.txtCurrentDate);
        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("d MMMM, yyyy");
        date = simpleDateFormat.format(calendar.getTime());
        currentDate.setText(date);

        list=findViewById(R.id.list);

        setPrefs(PreferenceManager
                .getDefaultSharedPreferences(this));
        //adapter=new ArrayAdapter(this,R.layout.list_item,R.id.text,nameList);
        ArrayList<Client> arrayOfClients = dbHandler.getLisOfClientDetailsFromDatabase();

        clientAdapter = new ClientAdapter(this,arrayOfClients);
        list.setAdapter(clientAdapter);
        txtFirstNameWelcome = findViewById(R.id.txtFirstNameWelcome);
        txtSyncMessage = findViewById(R.id.txtSyncMessage);

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
        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("first_name")!=null) {
            txtFirstNameWelcome.setText("Welcome, " + bundle.getString("first_name"));
        }else {
                txtFirstNameWelcome.setText("Welcome");
        }

       // getExternalStoragePermission();

}

    @Override
    protected void onStart() {
        super.onStart();
        boolean previouslyStarted = getPrefs().getBoolean(getString(R.string.pref_previously_started), false);
        if(!previouslyStarted) {
            SharedPreferences.Editor edit = getPrefs().edit();
            edit.putBoolean(getString(R.string.pref_previously_started), Boolean.TRUE);
            edit.commit();
            showWelcomeScreen();
        }

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

    private void loadingUserName(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("eDOT Application")
                .setMessage("Loading...")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
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
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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
            txtSyncMessage.setVisibility(View.VISIBLE);
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
                publishProgress(90);
                Thread.sleep(1000);
                syncOperations.syncClientDOTCardPartAData();
                syncOperations.syncClientDOTCardPartBData();
                syncOperations.syncClientTBLabData();
                syncOperations.syncClientHIVCounsellingAndTestingData();
                syncOperations.syncClientHIVCareData();
                syncOperations.syncPatientDispensationStatusData();
                syncOperations.syncChwUserData();
                publishProgress(100);
                Thread.sleep(500);

            }catch(InterruptedException e){
                //Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();

            }catch(Exception e){
                //Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
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
            txtSyncMessage.setVisibility(View.GONE);
            ArrayList<Client> arrayOfClients = dbHandler.getLisOfClientDetailsFromDatabase();
            clientAdapter = new ClientAdapter(MainActivity.this,arrayOfClients);
            list.setAdapter(clientAdapter);
        }
    }

}