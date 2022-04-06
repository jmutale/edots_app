package org.chreso.edots;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class DispensationActivity extends AppCompatActivity {

    private Button btnAddDispensation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensation);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btnAddDispensation = findViewById(R.id.btnAddDispensation);
        btnAddDispensation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDispensationVideoActivity();
            }
        });
    }

    private void openDispensationVideoActivity() {
        Intent intent = new Intent(this, DispensationVideoActivity.class);
        startActivity(intent);
    }


}