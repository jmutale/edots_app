package org.chreso.edots;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

public class LoginActivity extends EdotActivity implements Validator.ValidationListener {

    private Button btnLogin;
    @NotEmpty
    private EditText username;
    @NotEmpty
    private EditText password;


    private Validator validator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        validator = new Validator(this);
        validator.setValidationListener(this);

        btnLogin = findViewById(R.id.btnLogin);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextTextPassword);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: implement login loic
                validator.validate();
            }
        });
    }


    private void openDashboard() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onValidationSucceeded() {
        openDashboard();
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle("Login Error")
                .setMessage("Please enter username and password.")
                .setCancelable(true)

                ;
        builder.create();
        builder.show();
    }
}