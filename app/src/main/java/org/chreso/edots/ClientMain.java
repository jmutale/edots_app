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

    private Button btnDispenseDrugToClient, btnClientStatus, btnClientFeedback, btnEDOTSurvey, btnTBLabResults, btnClientDOTCardA, btnHIVTestingCare;
    private TextView name ,dob, gender, mobile;
    private String uuid;
    private DBHandler dbHandler;
    private ClientDispensationAdapter clientDispensationAdapter;
    private TextView txtNextDrugPickupDate;
    private TextView txtNextDrugPickupTime;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_main);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dbHandler = new DBHandler(this);

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
        txtNextDrugPickupDate = findViewById(R.id.textNextDrugPickupDateContent);
        txtNextDrugPickupDate.setText(dbHandler.getNextDrugPickupDate(uuid));
        txtNextDrugPickupTime = findViewById(R.id.textViewNextDrugPickupTimeContent);
        txtNextDrugPickupTime.setText(dbHandler.getNextDrugPickupTime(uuid));
        btnClientDOTCardA = findViewById(R.id.btnClientDOTCardA);
        btnClientDOTCardA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClientDOTCardActivity();
            }
        });

        btnDispenseDrugToClient = findViewById(R.id.btnDispenseDrugToClient);
        btnDispenseDrugToClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDispensationActivity();
            }
        });

        btnClientStatus = findViewById(R.id.btnClientStatus);
        btnClientStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClientStatusActivity();
            }
        });

        btnEDOTSurvey = findViewById(R.id.btnEdotSurvey);
        btnEDOTSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openEDOTSurveyActivity();
            }
        });

        btnClientFeedback = findViewById(R.id.btnClientFeedback);
        btnClientFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClientFeedbackActivity();
            }
        });
        
        btnTBLabResults = findViewById(R.id.btnClientTBLabs);
        btnTBLabResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openClientTBLabsActivity();
            }
        });

        btnHIVTestingCare = findViewById(R.id.btnClientHIVTestingCare);
        btnHIVTestingCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openClientHIVTestingCareActivity();
            }
        });

        list=(ListView)findViewById(R.id.dispensationList);

        loadClientDispensationHistory(uuid);
    }

    private void openClientHIVTestingCareActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        Intent intent = new Intent(this, ClientHIVTestingAndTreatmentActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void openClientDOTCardActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        Intent intent = new Intent(this, ClientDOTCardActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void openClientTBLabsActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        Intent intent = new Intent(this, ClientTBLabsActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void openEDOTSurveyActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        Intent intent = new Intent(this, EDOTSurveyActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void openClientFeedbackActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        Intent intent = new Intent(this, ClientFeedbackActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    private void openClientStatusActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        Intent intent = new Intent(this, ClientStatusActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onResume(){
        super.onResume();
        loadClientDispensationHistory(uuid);
        if(txtNextDrugPickupTime!=null&&txtNextDrugPickupDate!=null&&dbHandler!=null) {
            txtNextDrugPickupDate.setText(dbHandler.getNextDrugPickupDate(uuid));
            txtNextDrugPickupTime.setText(dbHandler.getNextDrugPickupTime(uuid));
        }
    }

    private void loadClientDispensationHistory(String patientGuid) {

        ArrayList<ClientDispensation> arrayOfClientDispensations = dbHandler.getListOfClientDispensationsFromDatabase(patientGuid);
        clientDispensationAdapter = new ClientDispensationAdapter(this, arrayOfClientDispensations);
        list.setAdapter(clientDispensationAdapter);
    }

    private void openDispensationActivity() {
        Bundle b = new Bundle();
        b.putString("client_uuid", uuid);
        b.putString("last_seen_by", dbHandler.getCHWNameWhoLastAttendedToPatient(uuid));
        b.putString("patient_dispensation_status", dbHandler.getCheckIfPatientIsContinuing(uuid)?"Continuation":"Initiation");
        Intent intent = new Intent(this, DispensationActivity.class);
        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}