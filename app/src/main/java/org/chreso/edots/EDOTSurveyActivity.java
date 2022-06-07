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
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class EDOTSurveyActivity extends EdotActivity implements Validator.ValidationListener {

    private String client_uuid;
    private DBHandler dbHandler;
    private DatePicker dteEDOTSurveyDate;
    @Checked
    private RadioGroup rdgrpClientSatisfiedWithEDOT;
    @Checked
    private RadioGroup rdgrpWouldClientLikeToContinueWithEDOT;
    @NotEmpty
    private EditText editTextReasonsClientSatisfiedOrNot;
    @NotEmpty
    private EditText editTextReasonsClientWouldLikeToContinueWithEDOTOrNot;

    private Button btnSubmitEDOTSurvey;

    private Validator validator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edotsurvey);

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");

        validator = new Validator(this);
        validator.setValidationListener(this);

        dteEDOTSurveyDate = findViewById(R.id.dteEDOTSurveyDate);
        rdgrpClientSatisfiedWithEDOT = findViewById(R.id.rdgrpClientSatisfiedWithEDOT);
        editTextReasonsClientSatisfiedOrNot = findViewById(R.id.editTextReasonsClientSatisfiedOrNot);
        rdgrpWouldClientLikeToContinueWithEDOT = findViewById(R.id.rdgrpWouldClientLikeToContinueWithEDOT);
        editTextReasonsClientWouldLikeToContinueWithEDOTOrNot = findViewById(R.id.editTextReasonsClientWouldLikeToContinueWithEDOTOrNot);
        btnSubmitEDOTSurvey = findViewById(R.id.btnSubmitSurvey);
        btnSubmitEDOTSurvey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        dbHandler = new DBHandler(this);
    }

    @Override
    public void onValidationSucceeded() {
        String surveyDate = getEDOTSurveyDate();
        String isPatientSatisfiedWithEDOT =  ((RadioButton)findViewById(rdgrpClientSatisfiedWithEDOT.getCheckedRadioButtonId()))
                .getText().toString();
        String reasonsPatientSatisfiedWithEDOTOrNot = editTextReasonsClientSatisfiedOrNot.getText().toString();
        String wouldClientLikeToContinueEDOT = ((RadioButton)findViewById(rdgrpWouldClientLikeToContinueWithEDOT.getCheckedRadioButtonId()))
                .getText().toString();
        String reasonsPatientWouldLikeToContinueWithEDOTOrNot = editTextReasonsClientWouldLikeToContinueWithEDOTOrNot.getText().toString();
        dbHandler.saveEDOTSurveyToDatabase(Utils.getNewUuid(),surveyDate,client_uuid,isPatientSatisfiedWithEDOT,reasonsPatientSatisfiedWithEDOTOrNot,wouldClientLikeToContinueEDOT,reasonsPatientWouldLikeToContinueWithEDOTOrNot);
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Survey successfully saved.")
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

    private String getEDOTSurveyDate() {
        int day  = dteEDOTSurveyDate.getDayOfMonth();
        int month = dteEDOTSurveyDate.getMonth()+1;
        int year = dteEDOTSurveyDate.getYear();

        String surveyDate = year + "-"+ month + "-" +day;
        return surveyDate;
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
            } else if(view instanceof RadioGroup){
                ((TextView)((RadioGroup) view).getFocusedChild()).setError(message);
            }
        }
    }
}