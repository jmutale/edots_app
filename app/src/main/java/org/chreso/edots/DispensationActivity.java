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
import android.widget.Spinner;

import java.util.List;


public class DispensationActivity extends AppCompatActivity {

    private Button btnAddDispensation;
    DBHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensation);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(R.string.pharmacy_title_text);
        }

        btnAddDispensation = findViewById(R.id.btnAddDispensation);
        btnAddDispensation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDispensationVideoActivity();
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



    private void openDispensationVideoActivity() {
        Intent intent = new Intent(this, DispensationVideoActivity.class);
        startActivity(intent);
    }


}