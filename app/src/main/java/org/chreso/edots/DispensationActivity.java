package org.chreso.edots;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Select;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class DispensationActivity extends AppCompatActivity implements Validator.ValidationListener {

    private Button btnAddDispensation , btnRecordVideo;
    @NotEmpty
    private EditText txtDose;
    @NotEmpty
    private EditText txtItemsPerDose;
    DBHandler dbHandler;
    private DatePicker dteRefillDate;
    private String meddrug_uuid;
    private String genericName;
    private String client_uuid;
    @Select
    private Spinner spnFrequency;
    private Spinner spnDrugsFromDatabase;
    private String dispensation_date;
    private Map<String,String> namesOfDrugs;
    private static int CAMERA_PERMISSION_CODE = 100;
    private static int VIDEO_RECORD_CODE = 101;
    private Uri video_path;
    private Validator validator;
    private TextView txtVideoUri;
    private DatePicker dteDispensationDate;
    private DatePicker dteNextClinicAppointmentDate;
    private TimePicker refillTimePicker;

    ActivityResultLauncher<Intent> startActivityForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result!=null && result.getResultCode() == RESULT_OK){
                if(result.getData()!=null){
                    video_path = result.getData().getData();
                    txtVideoUri.setText(video_path.toString());
                    Log.i("VIDEO_RECORD_TAG", "Video recorded "+ video_path);
                }
            }
        }
    });


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispensation);
        Bundle bundle = getIntent().getExtras();
        ActionBar actionBar = getSupportActionBar();
        client_uuid = bundle.getString("client_uuid");
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(bundle.get("patient_dispensation_status").toString() +" | Last seen by : "+ bundle.get("last_seen_by").toString());
        }
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();

        validator = new Validator(this);
        validator.setValidationListener(this);

        if(isCameraPresentInDevice()){
            getCameraPermission();
        }else{

        }

        dteDispensationDate = findViewById(R.id.dteDispensationDate);
        dteRefillDate = findViewById(R.id.editRefillDate);
        refillTimePicker = findViewById(R.id.timePickerRefill);
        dteNextClinicAppointmentDate = findViewById(R.id.editNextClinicAppointmentDate);
        setRefillDateToCurrentDate();

        spnDrugsFromDatabase = findViewById(R.id.spnDrugsFromDatabase);
        txtDose = findViewById(R.id.txtDose);
        txtItemsPerDose = findViewById(R.id.txtItemsPerDose);
        spnFrequency = findViewById(R.id.spnFrequency);

        spnFrequency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //Set the refill date here. Make sure dose, items per dose are filled in before doing this.

                Calendar cal = Calendar.getInstance();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
                LocalDate now = LocalDate.parse(Utils.getDateFromDatePicker(dteDispensationDate),formatter);

                cal.set(now.getYear(), now.getMonthValue()-1, now.getDayOfMonth());
                String dose = txtDose.getText().toString();
                String itemsPerDose = txtItemsPerDose.getText().toString();
                if(!dose.isEmpty() && !itemsPerDose.isEmpty()) {
                    int numberOfDaysToAddToDispensationDate = getNumberOfDaysToAddToDispensationDateFromDoseItemsPerDoseAndFrequency(Integer.parseInt(txtDose.getText().toString()), Integer.parseInt(txtItemsPerDose.getText().toString()));
                    cal.add(Calendar.DATE, numberOfDaysToAddToDispensationDate);
                }
                dteRefillDate.updateDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        btnAddDispensation = findViewById(R.id.btnAddDispensation);
        btnAddDispensation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validator.validate();
            }
        });

        btnRecordVideo = findViewById(R.id.btnRecordVideoDispensation);
        btnRecordVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                recordVideo();
            }
        });
        dbHandler = new DBHandler(getApplicationContext());
        namesOfDrugs = dbHandler.loadDrugsIntoSpinnerFromDatabase();
        ArrayList<String> listOfValues
                = new ArrayList<>(namesOfDrugs.values());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item,listOfValues );

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = (Spinner) findViewById(R.id.spnDrugsFromDatabase);
        sItems.setAdapter(adapter);

        txtVideoUri = findViewById(R.id.txtVideoURL);

    }



    private String getCHWNameWhoAttendedToPatient(String client_uuid) {
        return dbHandler.getCHWIdForChwWhoLastAttendedToPatient(client_uuid);
    }

    private boolean isPatientIsContinuation(String client_uuid) {
        return dbHandler.getCheckIfPatientIsContinuing(client_uuid);

    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setRefillDateToCurrentDate() {
        Calendar cal = Calendar.getInstance();
        LocalDateTime now = LocalDateTime.now();
        cal.set(now.getYear(), now.getMonthValue(), now.getDayOfMonth());
        dteRefillDate.updateDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DATE));
    }


    private int getNumberOfDaysToAddToDispensationDateFromDoseItemsPerDoseAndFrequency(int dose, int itemsPerDose) {
        int toReturn = 0;

        switch(spnFrequency.getSelectedItem().toString()){
            case "od":
                toReturn = (dose/1)/itemsPerDose;
                break;
            case "bd":
                toReturn = (dose/2)/itemsPerDose;
                break;
            case "td":
                toReturn = (dose/3)/itemsPerDose;
                break;
            case "qid":
                toReturn = (dose/4)/itemsPerDose;
                break;
            default:
                toReturn = 0;
                break;
        }

        return toReturn;
    }

    private void saveDispensationToDatabase() {

            genericName = spnDrugsFromDatabase.getSelectedItem().toString();
            meddrug_uuid = getUuidFromGenericName(genericName);
            dispensation_date = Utils.getDateFromDatePicker(dteDispensationDate);
            String refillDate = Utils.getDateFromDatePicker(dteRefillDate);
            String refillTime = getRefillTime();
            String location = getConfiguredLocation();
            String dispensation_uuid = Utils.getNewUuid();
            String nextClinicAppointmentDate = Utils.getDateFromDatePicker(dteNextClinicAppointmentDate);
            String chw = PreferenceManager
                    .getDefaultSharedPreferences(getApplicationContext()).getString("chw_id",null);

            dbHandler.saveDispensationToDatabase(dispensation_uuid, meddrug_uuid, client_uuid, chw, dispensation_date, txtDose.getText().toString(), txtItemsPerDose.getText().toString(), spnFrequency.getSelectedItem().toString(), refillDate, String.valueOf(video_path), location, nextClinicAppointmentDate, refillTime);
            dbHandler.savePaitentDispensationStatusToDatabase(Utils.getNewUuid(),client_uuid,Utils.PatientDispensationStatus.CONTINUATION.toString());
    }

    private boolean clientStatusPermits() {
        return !dbHandler.isClientDead(client_uuid);
    }

    private String getRefillTime() {
        int hour, minute;
        hour = refillTimePicker.getHour();
        minute = refillTimePicker.getMinute();
        return hour +":"+ minute+":00";
    }


    private String getConfiguredLocation() {
        String location= PreferenceManager
                .getDefaultSharedPreferences(this).getString("facility",null);
        return location;
    }




    private String getUuidFromGenericName(String genericName) {
        // Check for null or empty genericName
        if (genericName == null || genericName.isEmpty()) {
            return ""; // Or throw an IllegalArgumentException
        }
        for (Map.Entry<String, String> pair : namesOfDrugs.entrySet()) {
            if(pair.getValue().equals(genericName))
                return pair.getKey();
        }
        return "";
    }


    private boolean isCameraPresentInDevice() {
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }


    private void getCameraPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        }
    }

    private void recordVideo(){
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        startActivityForResult.launch(intent);
    }


    @Override
    public void onValidationSucceeded() {
        saveDispensationToDatabase();
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Success")
                .setMessage("Dispensation successfully saved.")
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