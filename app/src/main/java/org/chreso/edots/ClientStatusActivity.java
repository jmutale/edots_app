package org.chreso.edots;

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

public class ClientStatusActivity extends EdotActivity implements Validator.ValidationListener {
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
    private EditText editTextFacilityTransferredTo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_status);

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
        rgTransOut = findViewById(R.id.rdgrpClientTransOut);

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
        String statusDate = getStatusDate();
        String reportingFacility = "";
        String clientDied =
                ((RadioButton)findViewById(rgClientDied.getCheckedRadioButtonId())) == null?"":
                        ((RadioButton)findViewById(rgClientDied.getCheckedRadioButtonId())).getText().toString();
        String clientDiedDate = clientDied==""?"":getClientDeathDate();
        String causeOfDeath = ((RadioButton)findViewById(rgCauseOfDeath.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgCauseOfDeath.getCheckedRadioButtonId())).getText().toString();
        String transOut = ((RadioButton)findViewById(rgTransOut.getCheckedRadioButtonId())) == null?"":
                ((RadioButton)findViewById(rgTransOut.getCheckedRadioButtonId())).getText().toString();
        String clientTransOutDate = transOut == ""?"":getClientTransOutDate();
        String facilityTransferredTo = editTextFacilityTransferredTo.getText().toString();
        dbHandler.saveClientStatusToDatabase(Utils.getNewUuid(),reportingFacility,client_uuid,statusDate,clientDied,clientDiedDate,causeOfDeath,transOut,clientTransOutDate,facilityTransferredTo);
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

    private String getClientTransOutDate() {
        int day  = dteClientTransOutDate.getDayOfMonth();
        int month = dteClientTransOutDate.getMonth()+1;
        int year = dteClientTransOutDate.getYear();

        String transOutDate = year + "-"+ month + "-" +day;
        return transOutDate;
    }

    private String getClientDeathDate() {
        int day  = dteClientDiedDate.getDayOfMonth();
        int month = dteClientDiedDate.getMonth()+1;
        int year = dteClientDiedDate.getYear();

        String deathDate = year + "-"+ month + "-" +day;
        return deathDate;
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

    private String getStatusDate()
    {
        int day  = dteStatusDate.getDayOfMonth();
        int month = dteStatusDate.getMonth()+1;
        int year = dteStatusDate.getYear();

        String statusDate = year + "-"+ month + "-" +day;
        return statusDate;
    }
}