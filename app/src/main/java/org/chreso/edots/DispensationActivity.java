package org.chreso.edots;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class DispensationActivity extends AppCompatActivity {

    private Button btnAddDispensation;
    private EditText txtDose, txtItemsPerDose, txtRefillDate;
    DBHandler dbHandler;
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

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");
        spnDrugsFromDatabase = findViewById(R.id.spnDrugsFromDatabase);
        txtDose = findViewById(R.id.txtDose);
        txtItemsPerDose = findViewById(R.id.txtItemsPerDose);
        spnFrequency = findViewById(R.id.spnFrequency);
        txtRefillDate = findViewById(R.id.editRefillDate);

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

    private void saveDispensationToDatabase() {
        genericName = spnDrugsFromDatabase.getSelectedItem().toString();
        meddrug_uuid = getUuidFromGenericName(genericName);
        dbHandler.saveDispensationToDatabase(meddrug_uuid,client_uuid,dispensation_date,txtDose.getText().toString(),txtItemsPerDose.getText().toString(),spnFrequency.getSelectedItem().toString(),txtRefillDate.getText().toString(),video_path);
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