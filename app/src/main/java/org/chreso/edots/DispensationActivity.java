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

import java.util.List;


public class DispensationActivity extends AppCompatActivity {

    private Button btnAddDispensation;
    private EditText txtDose, txtItemsPerDose, txtRefillDate;
    DBHandler dbHandler;
    private String meddrug_uuid;
    private String patient_uuid;
    private Spinner spnFrequency;
    private String video_path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensation);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.pharmacy_title_text);
        }

        txtDose = findViewById(R.id.txtDose);
        txtItemsPerDose = findViewById(R.id.txtItemsPerDose);
        spnFrequency = findViewById(R.id.spnFrequency);
        txtRefillDate = findViewById(R.id.editRefillDate);

        btnAddDispensation = findViewById(R.id.btnAddDispensation);
        btnAddDispensation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDispensationVideoActivity();
                saveDispensationToDatabase();
            }
        });
        dbHandler = new DBHandler(getApplicationContext());
        List<String> namesOfDrugs = dbHandler.loadDrugsIntoSpinnerFromDatabase();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, namesOfDrugs);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spnDrugsFromDatabase);
        sItems.setAdapter(adapter);

    }

    private void saveDispensationToDatabase() {
        dbHandler.saveDispensationToDatabase(meddrug_uuid,patient_uuid,txtDose.getText().toString(),txtItemsPerDose.getText().toString(),spnFrequency.getSelectedItem().toString(),txtRefillDate.getText().toString(),video_path);
    }


    private void openDispensationVideoActivity() {
        Intent intent = new Intent(this, DispensationVideoActivity.class);
        startActivity(intent);
    }


}