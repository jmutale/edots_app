package org.chreso.edots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
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

import java.util.List;

import okhttp3.internal.Util;

public class ClientFeedbackActivity extends AppCompatActivity implements Validator.ValidationListener {
    @NotEmpty
    private LinearLayout layoutClientConcerns;
    @NotEmpty
    private EditText editTextAdviceGivenToClients;
    @NotEmpty
    private EditText editTextAdverseReaction;
    @NonNull
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
        //editTextAdverseReaction = findViewById(R.id.editTextAdverseReactions);
        //editTextAdviceGivenToClients = findViewById(R.id.editTextAdviceGiveToClient);

        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        btnSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });
    }

    @Override
    public void onValidationSucceeded() {
        String feedbackDate = Utils.getDateFromDatePicker(dteClientFeedbackDate);
        dbHandler.saveClientFeedbackToDatabase(Utils.getNewUuid(),feedbackDate,client_uuid,editTextAdverseReaction.getText().toString(),getClientConcernsFromCheckboxGroup(layoutClientConcerns).toString(),editTextAdviceGivenToClients.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Feedback successfully saved.")
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

    private Object getClientConcernsFromCheckboxGroup(LinearLayout layout) {
        StringBuilder builder = new StringBuilder();
        for(int i=0;i<layout.getChildCount();i++)
        {
            View v = layout.getChildAt(i);
            if(v instanceof CheckBox){
                builder.append(((CheckBox)v).getText()).append(",");
            }
        }
        return builder.toString();
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