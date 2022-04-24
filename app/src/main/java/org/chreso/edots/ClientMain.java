package org.chreso.edots;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ClientMain extends AppCompatActivity {

    private Button btnDispenseDrugToClient;
    private TextView name ,dob, gender, mobile;
    private String uuid;
    private DBHandler dbHandler;
    private ClientDispensationAdapter clientDispensationAdapter;

    private ListView list;

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
        uuid = bundle.getString("uuid");
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


        list=(ListView)findViewById(R.id.dispensationList);
        dbHandler = new DBHandler(this);
        loadClientDispensationHistory(uuid);
    }

    private void loadClientDispensationHistory(String patientGuid) {

        ArrayList<ClientDispensation> arrayOfClientDispensations = dbHandler.getListOfClientDispensationsFromDatabase(patientGuid);
        clientDispensationAdapter = new ClientDispensationAdapter(this, arrayOfClientDispensations);
        list.setAdapter(clientDispensationAdapter);
    }

    private void openDispensationActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        Intent intent = new Intent(this, DispensationActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }


}