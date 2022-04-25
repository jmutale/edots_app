package org.chreso.edots;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class SearchClient extends AppCompatActivity implements Validator.ValidationListener {

    private TextView txtDueToday;
    private TextView txtRefillToday;
    private DBHandler dbHandler;
    private Button btnSearchClient;
    private Validator validator;
    @NotEmpty
    private EditText nrcNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_client);

        validator = new Validator(this);
        validator.setValidationListener(this);

        dbHandler = new DBHandler(this);
        nrcNumber = findViewById(R.id.editTextTextNRCNumber);
        txtDueToday = findViewById(R.id.txtValueOfNumberOfClientsDueForRefillToday);
        txtRefillToday = findViewById(R.id.txtValueOfClientsWithRefillToday);

        txtDueToday.setText(dbHandler.getNumberOfClientsDueForRefillToday());
        txtRefillToday.setText(dbHandler.getNumberOfClientsWithRefillToday());

        btnSearchClient = findViewById(R.id.btnSearchClient);
        btnSearchClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

    }

    @Override
    public void onValidationSucceeded() {
        //TODO: implement search logic here

        if(dbHandler.doesClientExist(nrcNumber.getText())){

        }
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
            }
        }
    }
}