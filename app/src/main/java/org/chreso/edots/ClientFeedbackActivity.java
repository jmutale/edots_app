package org.chreso.edots;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import okhttp3.internal.Util;

public class ClientFeedbackActivity extends EdotActivity implements Validator.ValidationListener {
    @NotEmpty
    private EditText editTextClientConcerns;
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

        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");

        dbHandler = new DBHandler(this);

        validator = new Validator(this);
        validator.setValidationListener(this);
        dteClientFeedbackDate = findViewById(R.id.dteFeedbackDate);
        editTextClientConcerns = findViewById(R.id.editTextClientConcerns);
        editTextAdverseReaction = findViewById(R.id.editTextAdverseReactions);
        editTextAdviceGivenToClients = findViewById(R.id.editTextAdviceGiveToClient);

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
        String feedbackDate = getFeedbackDate();
        dbHandler.saveClientFeedbackToDatabase(Utils.getNewUuid(),feedbackDate,client_uuid,editTextAdverseReaction.getText().toString(),editTextClientConcerns.getText().toString(),editTextAdviceGivenToClients.getText().toString());

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

    @NonNull
    private String getFeedbackDate()
    {
        int day  = dteClientFeedbackDate.getDayOfMonth();
        int month = dteClientFeedbackDate.getMonth()+1;
        int year = dteClientFeedbackDate.getYear();

        String dispesationDate = year + "-"+ month + "-" +day;
        return dispesationDate;
    }
}