package org.chreso.edots;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

public class ClientHIVTestingAndTreatmentActivity extends AppCompatActivity {

    private Validator validator;
    private String client_uuid;
    private DBHandler dbHandler;

    private EditText placeOfTest, resultIfNoAcceptedDuringIntensivePhase, resultIfNoAcceptedDuringContinuationPhase, hivCareRegNo;
    private RadioGroup rgAcceptedTesting, rgRetestCounselling, rgIfNoAcceptedDuringIntensivePhase, rgIfNoAcceptedDuringContinuationPhase, rgHIVCareEligible;
    private DatePicker dateOfTest, cptDateStart, hivCareDate, arvStartDate;
    private Spinner results;

    private Button btnSubmitNewEntry;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_hiv_testing_and_treatment);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");
        dbHandler = new DBHandler(this);

        placeOfTest = findViewById(R.id.editPlaceOfTest);
        resultIfNoAcceptedDuringIntensivePhase = findViewById(R.id.editResultAcceptedDuringIntensive);
        resultIfNoAcceptedDuringContinuationPhase = findViewById(R.id.editResultAcceptedDuringContinuation);
        rgAcceptedTesting = findViewById(R.id.rdgrpAcceptedTesting);
        dateOfTest = findViewById(R.id.dteClientHIVTestDate);
        rgIfNoAcceptedDuringIntensivePhase = findViewById(R.id.rdgrpNoIntensivePhase);
        results = findViewById(R.id.spnClientHIVResult);
        rgRetestCounselling = findViewById(R.id.rdgrpRetestCounselling);
        rgIfNoAcceptedDuringContinuationPhase = findViewById(R.id.rdgrpNoContinuationPhase);

        cptDateStart = findViewById(R.id.dteClientHIVCareDate);
        hivCareRegNo = findViewById(R.id.editClientHIVCareRegNo);
        hivCareDate = findViewById(R.id.dteClientHIVCareDate);
        arvStartDate = findViewById(R.id.dteClientHIVCareARVStartDate);
        rgHIVCareEligible = findViewById(R.id.rdgrpClientHIVCareEligible);

        btnSubmitNewEntry = findViewById(R.id.btnSubmitHIVTestingAndTreatmentData);

        btnSubmitNewEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClientHIVTestingAndTreatmentToDatabase();

            }
        });
    }


    private void saveClientHIVTestingAndTreatmentToDatabase() {
        String hivCounsellingAndTestingUuid = Utils.getNewUuid();
        String acceptedTesting =  ((RadioButton)findViewById(rgAcceptedTesting.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgAcceptedTesting.getCheckedRadioButtonId())).getText().toString();

        String _placeOfTest = placeOfTest.getText().toString();
        String _dateOfTest = Utils.getDateFromDatePicker(dateOfTest);
        String acceptedDuringIntensivePhase = ((RadioButton)findViewById(rgIfNoAcceptedDuringIntensivePhase.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgIfNoAcceptedDuringIntensivePhase.getCheckedRadioButtonId())).getText().toString();
        String resultIntensive = resultIfNoAcceptedDuringIntensivePhase.getText().toString();
        String hivTestResults =  results.getSelectedItem().toString();
        String retestCounselling = ((RadioButton)findViewById(rgRetestCounselling.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgRetestCounselling.getCheckedRadioButtonId())).getText().toString();
        String acceptedDuringContinuationPhase = ((RadioButton)findViewById(rgIfNoAcceptedDuringContinuationPhase.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgIfNoAcceptedDuringContinuationPhase.getCheckedRadioButtonId())).getText().toString();
        String resultContinuation = resultIfNoAcceptedDuringContinuationPhase.getText().toString();

        dbHandler.addNewHIVTestingAndCounsellingEntry(hivCounsellingAndTestingUuid,client_uuid,
                acceptedTesting,acceptedDuringIntensivePhase,resultIntensive,_placeOfTest,
                _dateOfTest,hivTestResults,retestCounselling, acceptedDuringContinuationPhase, resultContinuation);


        String hivCareUuid = Utils.getNewUuid();
        String _cptDateStart = Utils.getDateFromDatePicker(cptDateStart);
        String _hivCareRegNo = hivCareRegNo.getText().toString();
        String _hivCareDate = Utils.getDateFromDatePicker(hivCareDate);
        String _hivCareEligible = ((RadioButton)findViewById(rgHIVCareEligible.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgHIVCareEligible.getCheckedRadioButtonId())).getText().toString();
        String _arvStartDate = Utils.getDateFromDatePicker(arvStartDate);
        dbHandler.addNewHIVCareEntry(hivCareUuid,client_uuid,_cptDateStart,_hivCareRegNo,_hivCareDate,_hivCareEligible,_arvStartDate);

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Status successfully saved.")
                .setCancelable(true)
                ;
        builder.create();
        builder.show();
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
