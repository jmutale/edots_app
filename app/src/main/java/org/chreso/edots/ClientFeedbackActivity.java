package org.chreso.edots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.List;

public class ClientFeedbackActivity extends AppCompatActivity implements Validator.ValidationListener {

    private LinearLayout layoutClientConcerns, layoutAdverseReactions, layoutAdviceGiven;

    private DatePicker dteClientFeedbackDate;
    private Button btnSubmitFeedback;
    private Validator validator;
    private DBHandler dbHandler;
    private String client_uuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_feedback);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");

        dbHandler = new DBHandler(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
        dteClientFeedbackDate = findViewById(R.id.dteFeedbackDate);
        layoutClientConcerns = findViewById(R.id.clientConcernsCheckBoxGroup);
        layoutAdverseReactions = findViewById(R.id.adverseReactionCheckBoxGroup);
        layoutAdviceGiven = findViewById(R.id.adviceGivenToClientCheckBoxGroup);
        //editTextAdverseReaction = findViewById(R.id.editTextAdverseReactions);
        //editTextAdviceGivenToClients = findViewById(R.id.editTextAdviceGiveToClient);

        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveClientFeedbackForm();
            }
        });
    }

    private void saveClientFeedbackForm() {
        String feedbackDate = Utils.getDateFromDatePicker(dteClientFeedbackDate);
        dbHandler.saveClientFeedbackToDatabase(Utils.getNewUuid(), feedbackDate, client_uuid, getSelectedCheckboxValuesFromCheckboxGroup(layoutAdverseReactions), getSelectedCheckboxValuesFromCheckboxGroup(layoutClientConcerns), getSelectedCheckboxValuesFromCheckboxGroup(layoutAdviceGiven));

        AlertDialog.Builder builder = new AlertDialog.Builder(ClientFeedbackActivity.this)
                .setTitle("Success")
                .setMessage("Feedback successfully saved.")
                .setCancelable(true)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        builder.create();
        builder.show();
    }

    @Override
    public void onValidationSucceeded() {
        saveClientFeedbackForm();
    }

    private String getSelectedCheckboxValuesFromCheckboxGroup(LinearLayout layout) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<layout.getChildCount();i++)
        {
            View v = layout.getChildAt(i);
            if(v instanceof CheckBox){
                builder.append(((CheckBox)v).getText()).append(",");
            }
        }
        String toReturn = builder.toString();
        StringBuffer sb = new StringBuffer(toReturn);
        //remove trailing comma
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            String message = error.getCollatedErrorMessage(this);

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(message);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}