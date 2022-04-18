package org.chreso.edots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class DispensationActivity extends AppCompatActivity {

    private Button btnAddDispensation;
    private EditText txtDose, txtItemsPerDose;
    DBHandler dbHandler;
    private DatePicker dteRefillDate;
    private String meddrug_uuid;
    private String genericName;
    private String client_uuid;
    private Spinner spnFrequency, spnDrugsFromDatabase;
    private String video_path;
    private String dispensation_date;
    private Map<String,String> namesOfDrugs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensation);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.pharmacy_title_text);
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        dispensation_date = now.format(dtf);
        dteRefillDate = findViewById(R.id.editRefillDate);
        setRefillDateToCurrentDate();

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");
        spnDrugsFromDatabase = findViewById(R.id.spnDrugsFromDatabase);
        txtDose = findViewById(R.id.txtDose);
        txtItemsPerDose = findViewById(R.id.txtItemsPerDose);
        spnFrequency = findViewById(R.id.spnFrequency);

        spnFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Set the refill date here. Make sure dose, items per dose are filled in before doing this.
                Calendar cal = Calendar.getInstance();

                LocalDateTime now = LocalDateTime.now();

                cal.set(now.getYear(), now.getMonthValue(), now.getDayOfMonth());

                int numberOfDaysToAddToCurrentDate = getNumberOfDaysToAddToCurrentDateFromDoseItemsPerDoseAndFrequency(Integer.parseInt(txtDose.getText().toString()),Integer.parseInt(txtItemsPerDose.getText().toString()));
                cal.add(Calendar.DATE, numberOfDaysToAddToCurrentDate);
                dteRefillDate.updateDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1,cal.get(Calendar.DATE));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnAddDispensation = findViewById(R.id.btnAddDispensation);
        btnAddDispensation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //openDispensationVideoActivity();
                saveDispensationToDatabase();
            }
        });
        dbHandler = new DBHandler(getApplicationContext());
        namesOfDrugs = dbHandler.loadDrugsIntoSpinnerFromDatabase();
        ArrayList<String> listOfValues
                = new ArrayList<>(namesOfDrugs.values());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,listOfValues );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spnDrugsFromDatabase);
        sItems.setAdapter(adapter);

    }

    private void setRefillDateToCurrentDate() {
        Calendar cal = Calendar.getInstance();
        LocalDateTime now = LocalDateTime.now();
        cal.set(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        dteRefillDate.updateDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH)-1,cal.get(Calendar.DATE));
    }


    private int getNumberOfDaysToAddToCurrentDateFromDoseItemsPerDoseAndFrequency(int dose, int itemsPerDose) {
        int toReturn = 0;

        switch(spnFrequency.getSelectedItem().toString()){
            case "od":
                toReturn = (dose/1)/itemsPerDose;
                break;
            case "bd":
                toReturn = (dose/2)/itemsPerDose;
                break;
            case "td":
                toReturn = (dose/3)/itemsPerDose;
                break;
            case "qid":
                toReturn = (dose/4)/itemsPerDose;
                break;
            default:
                toReturn = 30;
                break;
        }

        return toReturn;
    }

    private void saveDispensationToDatabase() {
        genericName = spnDrugsFromDatabase.getSelectedItem().toString();
        meddrug_uuid = getUuidFromGenericName(genericName);

        String refillDate = getRefillDateString();
        dbHandler.saveDispensationToDatabase(meddrug_uuid,client_uuid,dispensation_date,txtDose.getText().toString(),txtItemsPerDose.getText().toString(),spnFrequency.getSelectedItem().toString(), refillDate,video_path);
    }

    @NonNull
    private String getRefillDateString() {
        int day  = dteRefillDate.getDayOfMonth();
        int month = dteRefillDate.getMonth();
        int year = dteRefillDate.getYear();

        String refillDate = day + "/"+ month + "/" +year;
        return refillDate;
    }

    private String getUuidFromGenericName(String genericName) {
        String toReturn = "";
        for (Map.Entry<String, String> pair : namesOfDrugs.entrySet()) {
            if(pair.getValue() == genericName) toReturn = pair.getKey();
        }
        return toReturn;
    }


    private void openDispensationVideoActivity() {
        Intent intent = new Intent(this, DispensationVideoActivity.class);
        startActivity(intent);
    }


}