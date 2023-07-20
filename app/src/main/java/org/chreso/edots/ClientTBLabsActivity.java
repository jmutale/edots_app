package org.chreso.edots;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.List;

public class ClientTBLabsActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Select
    private Spinner spnLabResult, spnLevelOfTreatment, spnLabTestType;
    private Validator validator;
    private DatePicker dteClientTBLabDate, dteXRayDate;
    private String client_uuid;
    DBHandler dbHandler;
    private Button btnSubmit;
    private RadioButton rNormal, rAbnormal;
    private RadioGroup rgXRayDone, rgXRayResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_tblabs);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");
        dbHandler = new DBHandler(getApplicationContext());
        validator = new Validator(this);
        validator.setValidationListener(this);
        dteClientTBLabDate = findViewById(R.id.dteClientTBLabDate);
        spnLevelOfTreatment = findViewById(R.id.spnClientTBLevelOfTreatment);
        spnLabTestType = findViewById(R.id.spnClientTBLabTestType);
        spnLabResult = findViewById(R.id.spnClientTBLabResult);
        btnSubmit = findViewById(R.id.btnSubmitLabResult);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        rgXRayDone = findViewById(R.id.rdgrpXRayDone);
        dteXRayDate = findViewById(R.id.dteClientXRayDate);
        rgXRayResult = findViewById(R.id.rdgrpXRayResults);
        rNormal = findViewById(R.id.xrayNormal);
        rAbnormal = findViewById(R.id.xrayAbnormal);

        rgXRayDone.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.xrayDoneYes) {
                    rAbnormal.setClickable(true);
                    rNormal.setClickable(true);
                }
                if(checkedId==R.id.xrayDoneNo){
                    rAbnormal.setClickable(false);
                    rNormal.setClickable(false);
                    rAbnormal.setChecked(false);
                    rNormal.setChecked(false);
                }
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        saveClientTBLabToDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Lab result successfully saved.")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                ;
        builder.create();
        builder.show();
    }

    private void saveClientTBLabToDatabase() {
        String client_tb_lab_uuid = Utils.getNewUuid();
        String client_tb_lab_date = Utils.getDateFromDatePicker(dteClientTBLabDate);
        String levelOfTreatmentSelection = spnLevelOfTreatment.getSelectedItem().toString();
        String labTestTypeSelection = spnLabTestType.getSelectedItem().toString();
        String labResult = spnLabResult.getSelectedItem().toString();
        String treatmentFailure = "false";
        String xRayDone = ((RadioButton)findViewById(rgXRayDone.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgXRayDone.getCheckedRadioButtonId())).getText().toString();
        String xRayDate = Utils.getDateFromDatePicker(dteXRayDate);
        String xRayResult =   ((RadioButton)findViewById(rgXRayResult.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgXRayResult.getCheckedRadioButtonId())).getText().toString();
        dbHandler.addNewClientTBLabResult(client_tb_lab_uuid,client_tb_lab_date,client_uuid,levelOfTreatmentSelection,labTestTypeSelection,labResult,treatmentFailure, xRayDone, xRayDate, xRayResult);
    }


    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            } else if (view instanceof Spinner) {
                ((TextView) ((Spinner) view).getSelectedView()).setError(message);
            } else if(view instanceof TextView){
                ((TextView)view).setError(message);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}