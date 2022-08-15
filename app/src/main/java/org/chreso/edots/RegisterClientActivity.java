package org.chreso.edots;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import org.intellij.lang.annotations.Pattern;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



public class RegisterClientActivity extends AppCompatActivity implements Validator.ValidationListener  {

    DBHandler dbHandler;

    private Map<String, String> namesOfFacilities;
    private Validator validator;
    @NotEmpty
    private EditText clientNrcNumber;
    @NotEmpty
    EditText editNumberOfIndividualsInHousehold;
    private EditText phone;
    private EditText editReasonsNotOnIPT;

    @NotEmpty
    private EditText clientFirstName;
    @NotEmpty
    private EditText clientLastName;
    private Button btnSubmitClientRecord;
    private DatePicker dteDateOfBirth;
    private Spinner spnFacilityClientBelongsTo;
    private Spinner clientSex;
    private RadioGroup rgClientHouseholdOnIPT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_client);

        validator = new Validator(this);
        validator.setValidationListener(this);

        dbHandler = new DBHandler(getApplicationContext());
        editNumberOfIndividualsInHousehold = findViewById(R.id.editNumberOfIndividualsInHousehold);

        spnFacilityClientBelongsTo = findViewById(R.id.spnFacilitiesFromDatabase);
        clientNrcNumber = findViewById(R.id.txtNRCNumber);
        clientFirstName = findViewById(R.id.txtFirstName);
        clientLastName = findViewById(R.id.txtLastName);
        phone = findViewById(R.id.txtPhoneNumber);
        clientSex = findViewById(R.id.spnClientSex);
        btnSubmitClientRecord = findViewById(R.id.btnSubmitClientRecord);
        btnSubmitClientRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        dteDateOfBirth = findViewById(R.id.editDateOfBirth);

        namesOfFacilities = dbHandler.getListOfHealthFacilitiesFromDatabase();
        ArrayList<String> listOfValues
                = new ArrayList<>(namesOfFacilities.values());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,listOfValues );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spnFacilitiesFromDatabase);
        sItems.setAdapter(adapter);
        editReasonsNotOnIPT = findViewById(R.id.editTextReasonsNotOnIPT);
        rgClientHouseholdOnIPT = findViewById(R.id.rdgrpHouseholdOnIPT);
        rgClientHouseholdOnIPT.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.householdOnIPTYes) {
                    editReasonsNotOnIPT.setEnabled(false);
                    editReasonsNotOnIPT.setText("");
                }
                if(checkedId==R.id.householdOnIPTNo){
                    editReasonsNotOnIPT.setEnabled(true);
                }
            }
        });
    }

    private void saveClientRecord() {
        String client_uuid = Utils.getNewUuid();
        String dateOfBirth = Utils.getDateFromDatePicker(dteDateOfBirth);
        String numberOfIndividualsInHousehold = editNumberOfIndividualsInHousehold.getText().toString();
        String householdOnIPT =
                ((RadioButton)findViewById(rgClientHouseholdOnIPT.getCheckedRadioButtonId())) == null?"":
                        ((RadioButton)findViewById(rgClientHouseholdOnIPT.getCheckedRadioButtonId())).getText().toString();
        String reasonsNotOnIPT = editReasonsNotOnIPT.getText().toString();
        String nrcNumber = clientNrcNumber.getText().toString();
        String fname = clientFirstName.getText().toString();
        String lname = clientLastName.getText().toString();
        String phoneNumber = phone.getText().toString();
        String sex = clientSex.getSelectedItem().toString();
        String facilityUuid = getUuidFromFacilityName(spnFacilityClientBelongsTo.getSelectedItem().toString());
        dbHandler.addNewClient(client_uuid, nrcNumber, "","",fname,lname,dateOfBirth,numberOfIndividualsInHousehold,householdOnIPT,reasonsNotOnIPT,sex,phoneNumber, facilityUuid, false);
    }

    private String getUuidFromFacilityName(String facilityName) {
        String toReturn = "";
        for (Map.Entry<String, String> pair : namesOfFacilities.entrySet()) {
            if(pair.getValue() == facilityName) toReturn = pair.getKey();
        }
        return toReturn;
    }

    @Override
    public void onValidationSucceeded() {
        saveClientRecord();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Client successfully saved.")
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

    }
}