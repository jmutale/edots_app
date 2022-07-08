package org.chreso.edots;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.util.List;

public class ClientTBLabsActivity extends AppCompatActivity implements Validator.ValidationListener {

    @Select
    private Spinner spnLabResult;
    private Validator validator;
    private DatePicker dteClientTBLabDate;
    private String client_uuid;
    DBHandler dbHandler;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_tblabs);
        Bundle bundle = getIntent().getExtras();
        client_uuid = bundle.getString("client_uuid");
        dbHandler = new DBHandler(getApplicationContext());
        validator = new Validator(this);
        validator.setValidationListener(this);
        dteClientTBLabDate = findViewById(R.id.dteClientTBLabDate);
        spnLabResult = findViewById(R.id.spnClientTBLabResult);
        btnSubmit = findViewById(R.id.btnSubmitLabResult);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
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
        String labResult = spnLabResult.getSelectedItem().toString();
        String treatmentFailure = "";
        dbHandler.addNewClientTBLabResult(client_tb_lab_uuid,client_tb_lab_date,client_uuid,labResult,treatmentFailure);
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
}