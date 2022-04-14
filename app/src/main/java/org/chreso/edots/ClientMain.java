package org.chreso.edots;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ClientMain extends AppCompatActivity {

    private Button btnDispenseDrugToClient;
    private TextView name ,dob, gender, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);

        }

        name = findViewById(R.id.name);
        dob = findViewById(R.id.dob);
        gender = findViewById(R.id.gender);
        mobile = findViewById(R.id.mobileNumber);

        Bundle bundle = getIntent().getExtras();
        name.setText(bundle.getString("fName") + " "+ bundle.getString("lName"));
        dob.setText(bundle.getString("dob"));
        gender.setText(bundle.getString("sex"));
        mobile.setText(bundle.getString("mobile"));




        btnDispenseDrugToClient = findViewById(R.id.btnDispenseDrugToClient);
        btnDispenseDrugToClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDispensationActivity();
            }
        });
    }

    private void openDispensationActivity() {
        Intent intent = new Intent(this, DispensationActivity.class);
        startActivity(intent);
    }


}