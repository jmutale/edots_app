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
import com.mobsandgeeks.saripaar.annotation.Future;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class ClientStatusActivity extends AppCompatActivity implements Validator.ValidationListener {
    private Button btnSubmitStatus;
    private Validator validator;
    private String client_uuid;
    private DBHandler dbHandler;
    private DatePicker dteStatusDate;
    private DatePicker dteClientDiedDate;
    private DatePicker dteClientTransOutDate;
    private RadioGroup rgClientDied;
    private RadioGroup rgCauseOfDeath;
    private RadioGroup rgTransOut;
    private RadioGroup rgClientRefusesToContinueTreatment;
    private RadioGroup rgClientIsLTFU;
    private EditText editTextFacilityTransferredTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_status);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");

        dbHandler = new DBHandler(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
        dteStatusDate = findViewById(R.id.dteStatusDate);
        dteClientDiedDate = findViewById(R.id.dteDeathDate);
        dteClientTransOutDate = findViewById(R.id.dteTransferDate);

        rgClientDied = findViewById(R.id.rdgrpClientDied);
        rgCauseOfDeath = findViewById(R.id.rdgrpCauseOfDeath);
        rgClientRefusesToContinueTreatment = findViewById(R.id.rdgrpClientRefusesToContinueTreatment);
        rgClientIsLTFU = findViewById(R.id.rdgrpClientIsLTFU);
        rgTransOut = findViewById(R.id.rdgrpClientTransOut);

        rgClientDied.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.clientDiedYes) {
                    disableOrEnableRGButton(rgClientRefusesToContinueTreatment, false);
                    disableOrEnableRGButton(rgClientIsLTFU, false);
                    disableOrEnableRGButton(rgTransOut, false);
                    dteClientTransOutDate.setEnabled(false);
                    editTextFacilityTransferredTo.setEnabled(false);
                }
                if(i==R.id.clientDiedNo){
                    disableOrEnableRGButton(rgClientRefusesToContinueTreatment, true);
                    disableOrEnableRGButton(rgClientIsLTFU, true);
                    disableOrEnableRGButton(rgTransOut, true);
                    dteClientTransOutDate.setEnabled(true);
                    editTextFacilityTransferredTo.setEnabled(true);
                }
            }
        });
        rgTransOut.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i==R.id.clientTransOutYes){
                    dteClientTransOutDate.setEnabled(true);
                }
                if(i==R.id.clientTransOutNo){
                    dteClientTransOutDate.setEnabled(false);
                }
            }
        });
        editTextFacilityTransferredTo = findViewById(R.id.editTextFacilityTransferredTo);

        btnSubmitStatus = findViewById(R.id.btnSubmitStatus);
        btnSubmitStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveClientStatusToDatabase();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        saveClientStatusToDatabase();

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Status successfully saved.")
                .setCancelable(true)
                ;
        builder.create();
        builder.show();
    }

    private void saveClientStatusToDatabase() {
        String statusDate = Utils.getDateFromDatePicker(dteStatusDate);
        String reportingFacility = "";
        String clientDied =
                ((RadioButton)findViewById(rgClientDied.getCheckedRadioButtonId())) == null?"":
                        ((RadioButton)findViewById(rgClientDied.getCheckedRadioButtonId())).getText().toString();
        String clientDiedDate = clientDied==""?null:Utils.getDateFromDatePicker(dteClientDiedDate);
        String causeOfDeath = ((RadioButton)findViewById(rgCauseOfDeath.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgCauseOfDeath.getCheckedRadioButtonId())).getText().toString();
        String clientRefusesTreatment = ((RadioButton)findViewById(rgClientRefusesToContinueTreatment.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgClientRefusesToContinueTreatment.getCheckedRadioButtonId())).getText().toString();
        String clientIsLTFU = ((RadioButton)findViewById(rgClientIsLTFU.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgClientIsLTFU.getCheckedRadioButtonId())).getText().toString();
        String transOut = ((RadioButton)findViewById(rgTransOut.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgTransOut.getCheckedRadioButtonId())).getText().toString();
        String clientTransOutDate = transOut == ""?null:Utils.getDateFromDatePicker(dteClientTransOutDate);
        String facilityTransferredTo = editTextFacilityTransferredTo.getText().toString();
        dbHandler.saveClientStatusToDatabase(Utils.getNewUuid(),reportingFacility,client_uuid,statusDate,clientDied,clientDiedDate,causeOfDeath,clientRefusesTreatment,clientIsLTFU,transOut,clientTransOutDate,facilityTransferredTo);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Status successfully saved.")
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




    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }else if (view instanceof DatePicker) {
                ((TextView) ((DatePicker) view).getParent()).setError(message);
            }
        }
    }

    private void disableOrEnableRGButton(RadioGroup radioGroup,boolean enable_or_disable){
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            ((RadioButton) radioGroup.getChildAt(i)).setEnabled(enable_or_disable);
            ((RadioButton) radioGroup.getChildAt(i)).setChecked(false);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}